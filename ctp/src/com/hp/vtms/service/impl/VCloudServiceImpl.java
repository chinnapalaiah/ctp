package com.hp.vtms.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.hp.vtms.VCloudExceptionsHandler;
import com.hp.vtms.service.VCloudService;
import com.hp.vtms.vcloud.AppVCloudClient;
import com.hp.vtms.vcloud.VCloudResponseXpathSupport;
import com.hp.vtms.vcloud.model.*;
import com.vmware.vcloud.api.rest.schema.MksTicketType;
import com.vmware.vcloud.api.rest.schema.ReferenceType;
import com.vmware.vcloud.sdk.VCloudException;
import com.vmware.vcloud.sdk.Vapp;
import com.vmware.vcloud.sdk.VcloudClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class VCloudServiceImpl implements VCloudService, Callable<ResourceTask> {

	private static final Logger _LOG = LoggerFactory.getLogger(VCloudServiceImpl.class);

	//@Value("#{envConfig.vcloud_versionUrl}")
	private String vcloudVersionUrl;

	@Value("#{envConfig.vcloud_version}")
	private String vcloudVersion;

	@Autowired
	private AppVCloudClient vCloudClient;
	

	private VcloudClient vCloudClientHtml;
	
	@Autowired
	private VCloudExceptionsHandler vCloudExceptionsHandler;

	@Autowired
	private VCloudResponseXpathSupport vCloudxPathSupport;

	private HttpContext httpContext = new BasicHttpContext();

	private boolean userBasedServiceInited;

	private String vDcUrl;

	private VApp vapp;

	private static ExecutorService executor = Executors.newFixedThreadPool(100); 
	
	public void init(String orgName, String vCloudAddress) {
		_LOG.info("method:init start: orgName= {}", orgName);
		
		if(!vCloudAddress.equals("Not Assigned")&&!orgName.equals("Not Assigned")){
			vcloudVersionUrl = "https://"+vCloudAddress+"/api/versions";
		}else{
			//vCloudExceptionsHandler.customVcloudExceptions(String.valueOf("601"));
		}
		vCloudClientHtml = vCloudClient.htmlCloudClient(vCloudAddress,orgName);
		// 1. get version xml and get login url
		String loginUrl = getLoginUrl();

		// 2. do login and get org url
		byte[] loginByteResponse = vCloudClient.doLogin(httpContext, loginUrl, orgName);
		String orgUrl = vCloudxPathSupport.parseLoginSessionToOrgUrl(loginByteResponse, orgName);
		// 3. get Vdc url
		byte[] orgByteResponse = vCloudClient.doHttpsGetXmlToBytesWithCookie(httpContext, orgUrl);
		vDcUrl = vCloudxPathSupport.parseOrgToVdcUrl(orgByteResponse);
		_LOG.info("method:init finished: vDcUrl= {}", vDcUrl);
	}

	private String getLoginUrl() {
		_LOG.info("method:getLoginUrl start:");
		String loginUrl = "";
		SupportedVersions supportedVersions = vCloudClient.doGetXmlToModel(SupportedVersions.class, vcloudVersionUrl);
		if(supportedVersions!=null&&supportedVersions.getVersions()!=null){
			for (VersionInfo i : supportedVersions.getVersions()) {
				if (i.getVersion().equals(vcloudVersion)) {
					loginUrl = changeUrlToHttps(i.getLoginUrl());
					// loginUrl = i.getLoginUrl();
					_LOG.info(loginUrl);
					break;
				}
			}
		}
		_LOG.info("method:getLoginUrl finish: loginUrl= {}", loginUrl);
		return loginUrl;
	}
	public MksTicketType getHtmlConsoleParam(String vappUrl,String vmName) {
		MksTicketType ticket = new MksTicketType();
		if (vappUrl != null && !vappUrl.trim().equals("")) {
			ReferenceType vAppRef = new ReferenceType();
			vAppRef.setHref(vappUrl);
			vAppRef.setType("");
			Vapp vapp;
			
			List<com.vmware.vcloud.sdk.VM> vms = new ArrayList<com.vmware.vcloud.sdk.VM>();			
			try {
				if(vCloudClientHtml!=null){
					vapp = Vapp.getVappByReference(vCloudClientHtml, vAppRef);
					vms = vapp.getChildrenVms();
				}
			} catch (VCloudException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (com.vmware.vcloud.sdk.VM vm : vms) {
				if(vms.size()==1){
					try {
						ticket = vm.acquireMksTicket();
						if(ticket.getHost().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
							try {
								InetAddress dns = InetAddress.getByName(ticket.getHost());
								ticket.setHost(dns.getHostName());
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					} catch (VCloudException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					
					if(vm.getResource().getName().equals(vmName)){
						try {
							ticket = vm.acquireMksTicket();
							if(ticket.getHost().matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
								try {
									InetAddress dns = InetAddress.getByName(ticket.getHost());
									ticket.setHost(dns.getHostName());
								} catch (UnknownHostException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						} catch (VCloudException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return ticket;
	}
	
	public synchronized ResourceTask getvmFromThreadPool(final String appName,final String orgName, final String cellLocation){
		ResourceTask rt=null;
		FutureTask<ResourceTask> future =(FutureTask<ResourceTask>) executor.submit( new Callable<ResourceTask>() {
		   public ResourceTask call() {   
			   ResourceTask resourceTask =getAms(appName, orgName, cellLocation);
			   return resourceTask;
		  }});  
		try {
			rt= future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
			future.cancel(true);   
		} catch (ExecutionException e) {
			e.printStackTrace();
			
			future.cancel(true);
			_LOG.info("ExecutionException: errorcode={}", "500");
		//	vCloudExceptionsHandler.customVcloudExceptions("500");
		} finally {   
//		   executor.shutdown();   
		}  
		return rt;
	}
	/**
	 * get information of vApp with the vms information in it. vAppName:name of
	 * App, orgName: the orgnization which the login user owns
	 */
	@Override
	public  ResourceTask getAms(String vAppName, String orgName, String vCloudAddress) {
		_LOG.info("method:getAms start: vAppName= {};orgName={};vCloudAddress={}", vAppName,orgName,vCloudAddress);
		if (!userBasedServiceInited) {
			init(orgName,vCloudAddress);
			userBasedServiceInited = true;
		}

		byte[] vAppByteResponse = vCloudClient.doHttpsGetXmlToBytesWithCookie(httpContext, vDcUrl);
		VDc vdc = vCloudClient.doHttpsGetXmlToModelWithCookie(httpContext, VDc.class, vDcUrl);
		String vAppUrl = vCloudxPathSupport.parseVdcToVAppUrl(vAppByteResponse, vAppName);
		ResourceTask vappInfo = intiateVappInfo(vAppUrl);
		
		_LOG.info("\r\nmethod:getAms finish: vappInfo= {}\r\n,vdc={}\r\n,vDcUrl={}\r\n,vAppUrl={}", vappInfo!=null?vappInfo.toString():"",vdc!=null?vdc.toString():"",vDcUrl,vAppUrl);
		
		return vappInfo;
	}

	/**
	 * 
	 * @param vmUrl
	 * @return if it do not get any vm, it will return null;
	 */
	private VM getVmInfo(String vmUrl) {
		VM vm = vCloudClient.doHttpsPostXmlToModelWithCookie(httpContext, VM.class, vmUrl);

		return vm;
	}

	public synchronized VCloudMsg operateVmThreadPool(final String url,final String orgName,final String action){
		VCloudMsg rt=null;
		FutureTask<VCloudMsg> future =(FutureTask<VCloudMsg>) executor.submit( new Callable<VCloudMsg>() {
		   public VCloudMsg call() {  
			   VCloudMsg msg=null;
			   if(action.equalsIgnoreCase("start")||action.equalsIgnoreCase("resume")){
			   msg =startVmOrVapp(url, orgName);
			  
			   }else if(action.equalsIgnoreCase("stop")){
				   msg =stopVmOrVapp(url, orgName);
			   }else if(action.equalsIgnoreCase("revert")){
				   msg =revertVmOrVapp(url, orgName);
			   }else if(action.equalsIgnoreCase("pause")){
				   msg =suspendVmOrVapp(url, orgName);
				   
			   }
			   return msg;
		  }});  
		try {
			rt= future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
			future.cancel(true);   
		} catch (ExecutionException e) {
			e.printStackTrace();
			
			future.cancel(true);
			_LOG.info("ExecutionException: errorcode={}", "500");
		//	vCloudExceptionsHandler.customVcloudExceptions("500");
		} finally {   
//		   executor.shutdown();   
		}  
		return null;
	}
	/**
	 * power on Vm or Vapp. url:the link for powering on, orgName: the
	 * orgnization which the login user owns
	 * 
	 */
	@Override
	public VCloudMsg startVmOrVapp(String url, String orgName) {
		_LOG.info("method:startVmOrVapp start: url= {};orgName={}", url,orgName);
		byte[] startVmResponse = null;
		String taskurl = null;
		VCloudMsg msg = new VCloudMsg();
		if (url != null && !url.equals("")) {
			msg = vCloudClient.doHttpsPostXmlToBytesWithCookie(httpContext, url);

			taskurl = vCloudxPathSupport.getTaskUrlOfVmOrVapp(msg.getTaskin());
			msg.setTaskUrl(taskurl);
		}
		_LOG.info("method:startVmOrVapp finish: taskurl= {}", taskurl);
		return msg;
	}

	/**
	 * power off Vm or Vapp. url:the link for powering off, orgName: the
	 * orgnization which the login user owns
	 */
	@Override
	public VCloudMsg stopVmOrVapp(String url, String orgName) {
		_LOG.info("method:stopVmOrVapp start: url= {};orgName={}", url,orgName);
		byte[] taskin = null;

		String taskurl = null;
		VCloudMsg msg = new VCloudMsg();
		if (url != null && !url.equals("")) {
			String contentType = "application/vnd.vmware.vcloud.undeployVAppParams+xml";
			String requestBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><UndeployVAppParams xmlns=\"http://www.vmware.com/vcloud/v1.5\"><UndeployPowerAction>powerOff</UndeployPowerAction></UndeployVAppParams>";
			msg = vCloudClient.doHttpsPostXmlToBytesWithCookieAndRequestBody(httpContext, url, contentType,
					requestBody);

			taskurl = vCloudxPathSupport.getTaskUrlOfVmOrVapp(msg.getTaskin());
			msg.setTaskUrl(taskurl);
		}
		_LOG.info("method:stopVmOrVapp finish: taskurl= {}", taskurl);
		return msg;
	}

	/**
	 * return to the status of Vm or Vapp to the status it is snapshoted last
	 * time. url:the link for reverting, orgName: the orgnization which the
	 * login user owns
	 */
	@Override
	public VCloudMsg revertVmOrVapp(String url, String orgName) {
		_LOG.info("method:startVmOrVapp start: url= {};orgName={}", url,orgName);
		byte[] taskin = null;

		String taskurl = null;
		VCloudMsg msg = new VCloudMsg();
		if (url != null && !url.equals("")) {
			msg = vCloudClient.doHttpsPostXmlToBytesWithCookie(httpContext, url);

			taskurl = vCloudxPathSupport.getTaskUrlOfVmOrVapp(msg.getTaskin());
			msg.setTaskUrl(taskurl);
		}
		_LOG.info("method:revertVmOrVapp finish: taskurl= {}", taskurl);
		return msg;
	}
	/**
	 * return to the status of Vm or Vapp to the status it is snapshoted last
	 * time. url:the link for suspend, orgName: the orgnization which the
	 * login user owns
	 */
	@Override
	public VCloudMsg suspendVmOrVapp(String url, String orgName) {
		_LOG.info("method:suspendVmOrVapp start: url= {};orgName={}", url,orgName);
		byte[] taskin = null;
		
		String taskurl = null;

		VCloudMsg msg = new VCloudMsg();
		if (url != null && !url.equals("")) {
			String contentType = "application/vnd.vmware.vcloud.undeployVAppParams+xml";
			String requestBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><UndeployVAppParams xmlns=\"http://www.vmware.com/vcloud/v1.5\"><UndeployPowerAction>suspend</UndeployPowerAction></UndeployVAppParams>";
			msg = vCloudClient.doHttpsPostXmlToBytesWithCookieAndRequestBody(httpContext, url, contentType,
					requestBody);

			taskurl = vCloudxPathSupport.getTaskUrlOfVmOrVapp(msg.getTaskin());
			msg.setTaskUrl(taskurl);
		}
		_LOG.info("method:suspendVmOrVapp finish: taskurl= {}", taskurl);
		return msg;
	}
	/**
	 * get the status of vApp or Vm in vCloud for now. type: is it vm or
	 * vapp(value: vm, vapp) name: the name of vApp or vm vAppUrl: the url to
	 * get the information of vApp orgName: the orgnization which the login user
	 * owns
	 */
	@Override
	public ResourceTask getStatusOfVappOrVm(String type, String name, String vAppUrl, String orgName) {
		_LOG.info("method:getStatusOfVappOrVm start: type= {};name={};vAppUrl={};orgName={}",type,orgName,name,vAppUrl,orgName);
		byte[] vappBytes = vCloudClient.doHttpsGetXmlToBytesWithCookie(httpContext, vAppUrl);

		ObjectMapper xmlMapper = new XmlMapper();
		xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			vapp = xmlMapper.readValue(byte2Input(vappBytes), VApp.class);
			if (vapp == null) {
				return null;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		ResourceTask info = getVappInfo(vAppUrl, type, name, vappBytes);
		
		_LOG.info("method:getStatusOfVappOrVm finish: info= {}", info!=null?info.toString():"");
		
		return info;
	}

	/**
	 * get the status of vApp or Vm in vCloud for now. type: is it vm or
	 * vapp(value: vm, vapp) name: the name of vApp or vm vAppUrl: the url to
	 * get the information of vApp orgName: the orgnization which the login user
	 * owns
	 */
	@Override
	public ConsoleParam getConsoleParam(String acquireTicketUrl) {
		_LOG.info("method:getConsoleParam start: acquireTicketUrl= {}",acquireTicketUrl);
		
		VCloudMsg msg = vCloudClient.doHttpsPostXmlToBytesWithCookie(httpContext, acquireTicketUrl);
		

		ConsoleParam param = null;
		param = vCloudxPathSupport.parseScreenTicket(msg.getTaskin());
		_LOG.info("method:getConsoleParam finish: param= {}",param.toString());
		return param;
	}

	@Override
	public Map<String, ResourceTask> getVappsInCenter(Set<String> vAppSet, String orgName, String vCloudAddress) {

		Map<String, ResourceTask> map = new HashMap<String, ResourceTask>();
		init(orgName, vCloudAddress);
		byte[] vAppByteResponse = vCloudClient.doHttpsGetXmlToBytesWithCookie(httpContext, vDcUrl);
		for (String i : vAppSet) {
			String vAppUrl = vCloudxPathSupport.parseVdcToVAppUrl(vAppByteResponse, i);
			ResourceTask vappInfo = intiateVappInfo(vAppUrl);
			map.put(i, vappInfo);
		}

		return map;
	}

	private String changeUrlToHttps(String url) {
		if (url != null && !url.contains("https")) {
			url = url.replace("http", "https");
		}
		return url;
	}

	/**
	 * 
	 * @param vAppUrl
	 * @param type
	 * @param name
	 * @param vappBytes
	 * @return if the value of vAppUrl is null or "" or this vm the url is
	 *         referred to do not exist, it will return null;
	 */

	private ResourceTask getVappInfo(String vAppUrl, String type, String name, byte[] vappBytes) {
		_LOG.info("method:getVappInfo start: vAppUrl= {};type={};name={}",vAppUrl,type,name);
		// cache the vapp for future use
		if (vAppUrl != null && !vAppUrl.trim().equals("")) {
			vapp = vCloudClient.doHttpsGetXmlToModelWithCookie(httpContext, VApp.class, vAppUrl);
		} else {
			return null;
		}
		ResourceTask info = new ResourceTask();

		info.setName(vapp.getName());
		info.setLinks(vapp.getLinks());
		info.setStatus(vapp.getStatus());
		info.setUrl(vAppUrl);

		if (type != null && type.equals("vapp")) {
			String taskUrl = vCloudxPathSupport.parseVappToVappTaskStatus(vappBytes);
			if (taskUrl != null && !taskUrl.equals("")) {
				info.setTaskstatus(ResourceStatus.IN_PROGRESS);
			} else {
				info.setTaskstatus(ResourceStatus.FINISHED);
			}
		}

		List<ResourceTask> vmlists = new ArrayList<ResourceTask>();
		ResourceTask vm = null;

		List<String> arraylist = new ArrayList<String>();

		if (vapp != null && vapp.getChildrens() != null) {
			for (Childrens i : vapp.getChildrens()) {
				for (VM j : i.getVms()) {
					arraylist.add(j.getName());
				}
			}
			Collections.sort(arraylist);
			for (Childrens i : vapp.getChildrens()) {
				if (arraylist != null) {
					for (String j : arraylist) {
						for (VM k : i.getVms()) {
							if (j.equals(k.getName())) {
								if (type != null && name != null && type.equals("vm") && name.equals(k.getName())) {
									String taskUrl = vCloudxPathSupport.parseVappToVmTaskStatus(name, vappBytes);
									if (taskUrl != null && !taskUrl.equals("")) {
										info.setTaskstatus(ResourceStatus.IN_PROGRESS);
									} else {
										info.setTaskstatus(ResourceStatus.FINISHED);
									}
								}
								vm = new ResourceTask();
								vm.setName(k.getName());
								vm.setStatus(k.getStatus());
								vm.setLinks(k.getLinks());
								vm.setId(k.getId());
								vmlists.add(vm);
							}
						}
					}
				}
			}
		}

		info.setChildren(vmlists);
		
		_LOG.info("method:getVappInfo finish: info= {}",info!=null?info.toString():"");
		
		return info;
	}

	/**
	 * 
	 * @param vAppUrl
	 * @return if the value of vAppUrl is null or "" or this vm the url is
	 *         referred to do not exist, it will return null;
	 */
	private ResourceTask intiateVappInfo(String vAppUrl) {
		_LOG.info("method:intiateVappInfo start: vAppUrl= {}",vAppUrl);
		byte[] vappBytes = null;
		// cache the vapp for future use
		if (vAppUrl != null && !vAppUrl.trim().equals("")) {
			vapp = vCloudClient.doHttpsGetXmlToModelWithCookie(httpContext, VApp.class, vAppUrl);
			_LOG.info("method:intiateVappInfo: vapp= {}",vapp.toString());
			vappBytes = vCloudClient.doHttpsGetXmlToBytesWithCookie(httpContext, vAppUrl);
		} else {
			return null;
		}
		ResourceTask info = new ResourceTask();

		info.setName(vapp.getName());
		info.setLinks(vapp.getLinks());
		info.setStatus(vapp.getStatus());
		info.setUrl(vAppUrl);

		String taskUrl = vCloudxPathSupport.parseVappToVappTaskStatus(vappBytes);
		if (taskUrl != null && !taskUrl.equals("")) {
			info.setTaskstatus(ResourceStatus.IN_PROGRESS);
		} else {
			info.setTaskstatus(ResourceStatus.FINISHED);
		}

		List<ResourceTask> vmlists = new ArrayList<ResourceTask>();
		ResourceTask vm = null;

		List<String> arraylist = new ArrayList<String>();

		if (vapp != null && vapp.getChildrens() != null) {
			for (Childrens i : vapp.getChildrens()) {
				for (VM j : i.getVms()) {
					arraylist.add(j.getName());
				}
			}
			Collections.sort(arraylist);
			for (Childrens i : vapp.getChildrens()) {
				if (arraylist != null) {
					for (String j : arraylist) {
						for (VM k : i.getVms()) {
							if (j.equals(k.getName())) {
								vm = new ResourceTask();
								String vmtaskUrl = vCloudxPathSupport.parseVappToVmTaskStatus(k.getName(), vappBytes);
								if (vmtaskUrl != null && !vmtaskUrl.trim().equals("")) {
									vm.setTaskstatus(ResourceStatus.IN_PROGRESS);
								} else {
									vm.setTaskstatus(ResourceStatus.FINISHED);
								}

								vm.setName(k.getName());
								vm.setStatus(k.getStatus());
								vm.setLinks(k.getLinks());
								vm.setId(k.getId());
								vmlists.add(vm);
							}
						}
					}
				}
			}
		}

		info.setChildren(vmlists);
		_LOG.info("method:intiateVappInfo finish: info= {}",info!=null?info.toString():"");
		return info;
	}
	

	/**
	 * get task infomation
	 * 
	 * @param taskUrl
	 * @return if there is no this task, it will return null;
	 */
	private byte[] getTask(String taskUrl) {
		if (taskUrl != null && !taskUrl.trim().equals("")) {
			VCloudMsg msg = vCloudClient.doHttpsPostXmlToBytesWithCookie(httpContext, taskUrl);
			return msg.getTaskin();
		}
		throw new IllegalArgumentException("task url can't be null");
	}

	/**
	 * 
	 * @param buf
	 * @return if buf is null, return null;
	 */
	public InputStream byte2Input(byte[] buf) {
		InputStream input = null;
		if (buf != null) {
			input = new ByteArrayInputStream(buf);
		}

		return input;
	}

	@Override
	public ResourceTask call() throws Exception {
//		getAms(appName, orgName, cellLocation);
		return null;
	}



}

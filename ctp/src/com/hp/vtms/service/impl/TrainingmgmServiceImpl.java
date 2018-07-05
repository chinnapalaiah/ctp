package com.hp.vtms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hp.vtms.Constants;
import com.hp.vtms.controller.TrainingmgmController;
import com.hp.vtms.dao.AttendeesDao;
import com.hp.vtms.dao.InstructorDao;
import com.hp.vtms.dao.impl.AttendeesDaoImpl;
import com.hp.vtms.dao.impl.InstructorDaoImpl;
import com.hp.vtms.model.Attendees;
import com.hp.vtms.model.Event;
import com.hp.vtms.model.Instructor;
import com.hp.vtms.model.User;
import com.hp.vtms.model.VAppModel;
import com.hp.vtms.model.VmModel;
import com.hp.vtms.service.AttendeesService;
import com.hp.vtms.service.ConnectService;
import com.hp.vtms.service.EventService;
import com.hp.vtms.service.InstructorService;
import com.hp.vtms.service.TemplatesService;
import com.hp.vtms.service.TrainingmgmService;
import com.hp.vtms.service.VCloudService;
import com.hp.vtms.util.ApplicationContainer;
import com.hp.vtms.vcloud.model.Link;
import com.hp.vtms.vcloud.model.ResourceStatus;
import com.hp.vtms.vcloud.model.ResourceTask;

import org.apache.commons.collections.map.ListOrderedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA. User: meij Date: 13-12-3 Time: 2:48 To change
 * this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class TrainingmgmServiceImpl implements TrainingmgmService {

	private static final Logger log = LoggerFactory.getLogger(TrainingmgmServiceImpl.class);
	@Autowired
	private AttendeesDao attendeesDao;

	@Autowired
	private InstructorDao instructorDao;
	
	@Autowired
	private EventService eventService;

	@Autowired
	private VCloudService vCloudService;

	@Autowired
	private TemplatesService templatesService;

	@Autowired
	private InstructorService instructorService;

	@Autowired
	private AttendeesService attendeesService;
	
	@Autowired
	private ConnectService connService;

	/**
	 * @return if return null prove the user don't have vappName
	 */

	@Transactional(readOnly = true)
	@Override
	public String getVAppByUser(String username, String type) {

		if (type.equals("attendees")) {
			Attendees attendees = attendeesDao.login(username);
			return attendees.getAttAppName();
		} else if (type.equals("instructor")) {
			Instructor instructor = instructorDao.login(username);

			return instructor.getInAppName();
		}

		return null;
	}
	
	public VAppModel getVappToView(VAppModel vapp , List<VAppModel> vAppModelList,User user){
		log.info("Inside getVappToView()........................");
		String type = user.getType();
		String appName = user.getVappName();
		Boolean isFirstTime=user.getIsFirstTime();

		Event event = eventService.getEvent(user);
		user.setEventId(event.getId().toString());
		String orgName = event.getCell();
		String cellLocation = event.getCellLocation();
		Boolean advanced = event.getAdvanced();
		user.setShowVRoom(event.getShowVRoom());
		ResourceTask resourceTask = null;
    	resourceTask = vCloudService.getvmFromThreadPool(appName, orgName, cellLocation);
		//resourceTask=vCloudService.getAms(appName, orgName, cellLocation);
		// change VAppModel to VApp
		List<VmModel> vmsModel =new ArrayList<VmModel>();
		vmsModel=vapp.getVmModelList();
		for (int i = 0; i < vmsModel.size(); i++) {
			vmsModel.get(i).setPowerOffUrl("");
			vmsModel.get(i).setPowerOnUrl("");
			vmsModel.get(i).setRevertUrl("");
			vmsModel.get(i).setPauseUrl("");
			vmsModel.get(i).setVcloudVm(false);
			vmsModel.get(i).setStatus("");
			vmsModel.get(i).setTaskStatus("");
		}
		String vappUrl = "";
		if (resourceTask != null) {
			log.info("checking condition resourceTask not equal to null....................");
			vmsModel = getVmsModel(resourceTask, vmsModel);
			vapp = this.getVapp(resourceTask, vmsModel,vapp);
			vappUrl = resourceTask.getUrl();
		} else{
			  for(int i=0;i<vmsModel.size();i++){
				if (vmsModel.get(i).getVmRdp() != null&& vmsModel.get(i).getVmRdp() == true) {
					log.info("rdp display is true.................");
					vmsModel.get(i).setRdpDisplay(true);
				} else {
					vmsModel.get(i).setRdpDisplay(false);
					log.info("rdp display is false.................");
				}
			  }
		}
		log.info("vAppModelList is ---------> "+vAppModelList);
		log.info("isFirstTime --------> "+isFirstTime);
		
		if(isFirstTime==null || isFirstTime==false || isFirstTime==true ){
			  if(vAppModelList!=null){
				  log.info("calling setStatusFromMerroy()...............");
				 vapp=setStatusFromMerroy(vapp,vAppModelList);
			  }
			}
		if (advanced == true) {
			vapp.setAdvanced(1);
		} else {
			vapp.setAdvanced(0);
		}
		for (int i = 0; i < vmsModel.size(); i++) {
			if (vmsModel.get(i).getStatus()!=null&&!vmsModel.get(i).getStatus().equals("9")) {
				if (vmsModel.get(i).getPowerOffUrl().equals("")&& vmsModel.get(i).getPowerOnUrl().equals("")) {
					vmsModel.get(i).setTaskStatus("Progressing");
					if(vmsModel.get(i).getTaskError()!=null&&!vmsModel.get(i).getTaskError().equals("")){
						vmsModel.get(i).setTaskError("");
						vmsModel.get(i).setHasTask(true);
					}
				}
			}
		}
		if ((vapp.getPowerOffUrl() == null || vapp.getPowerOffUrl().equals(""))&& (vapp.getPowerOnUrl() == null || vapp.getPowerOnUrl().equals(""))
		&& (vapp.getStatus() != null && !vapp.getStatus().equalsIgnoreCase("10"))) {
			vapp.setTaskStatus("Progressing");
		}
		vmsModel = getVmErrors(vapp, vmsModel,type);
		// vmsModel
		vapp.setVmModelList(vmsModel);
		vapp = getVappErrors(vapp);
		user.setVappurl(vapp.getUrl());
		vapp.setOrgName(orgName);
		return vapp;
	}

	public synchronized List<VAppModel> getvappListToTraining(HttpServletRequest request,List<VAppModel> vAppModelListFromServlet,User user){
		HttpSession session = request.getSession();
		String instructorName = user.getUserName();
		String type = user.getType();
		Boolean isFirstTime = user.getIsFirstTime();
		List<VAppModel> vAppModelList = (List<VAppModel>) session.getAttribute("vAppModelList");
		List<VAppModel> vAppList=new ArrayList<VAppModel>();
		Event event = eventService.getEvent(user);
		String orgName = event.getCell();
		session.setAttribute(Constants.SESSION_ORGNAME, orgName);
		Boolean advanced = event.getAdvanced();
		int vappCount=0;
		for (int i = 0; i < vAppModelList.size(); i++) {
			String cellLocation = event.getCellLocation();
			ResourceTask resourceTask = null;
			VAppModel vapp=vAppModelList.get(i);
			String appName = vapp.getName();
			resourceTask=vCloudService.getvmFromThreadPool(appName, orgName, cellLocation);
			if ((isFirstTime == null || isFirstTime != true)&&vAppModelListFromServlet!=null) {
				vapp = setStatusFromMerroy(vapp,
						vAppModelListFromServlet);
			}
			List<VmModel> vmsModel = vapp.getVmModelList();
			for (int k = 0; k < vmsModel.size(); k++) {
				vmsModel.get(k).setPowerOffUrl("");
				vmsModel.get(k).setPowerOnUrl("");
				vmsModel.get(k).setRevertUrl("");
				vmsModel.get(k).setPauseUrl("");
				vmsModel.get(k).setVcloudVm(false);
				vmsModel.get(k).setStatus("");
				vmsModel.get(k).setTaskStatus("");
			}
			String vappUrl = "";
			if (resourceTask != null) {
				vmsModel = getVmsModel(resourceTask, vmsModel);
				vapp = this.getVapp(resourceTask, vmsModel,vapp);
				vappUrl = resourceTask.getUrl();
			} else{
				 for(int j=0;j<vmsModel.size();j++){
						if (vmsModel.get(j).getVmRdp() != null&& vmsModel.get(j).getVmRdp() == true) {
							vmsModel.get(j).setRdpDisplay(true);
						} else {
							vmsModel.get(j).setRdpDisplay(false);
						}
					  }
			}
			if (advanced == true) {
				vapp.setAdvanced(1);
			} else {
				vapp.setAdvanced(0);
			}
			for (int j = 0; j < vmsModel.size(); j++) {
				if (vmsModel.get(j).getStatus()!=null&&!vmsModel.get(j).getStatus().equals("9")) {

					if (vmsModel.get(j).getPowerOffUrl().equals("")
							&& vmsModel.get(j).getPowerOnUrl().equals("")) {
						vmsModel.get(j).setTaskStatus("Progressing");
					}
				}

			}
			
			if ((vapp.getPowerOffUrl() == null || vapp.getPowerOffUrl().equals(
					""))
					&& (vapp.getPowerOnUrl() == null || vapp.getPowerOnUrl()
							.equals(""))
					&& (vapp.getStatus() != null && !vapp.getStatus()
							.equalsIgnoreCase("10"))) {
				vapp.setTaskStatus("Progressing");
			}
//			
			vmsModel = getVmErrors(vapp, vmsModel, "instructor");
			vapp.setVmModelList(vmsModel);
			vapp.setUrl(vappUrl);
			vapp = getVappErrors(vapp);
			if(vapp.getUrl()!=null&&!vapp.getUrl().equals("")){
				vappCount++;
			}
		}
		request.setAttribute("vappCount", vappCount);
		return vAppModelList;
	}
	public VAppModel getVapp(ResourceTask resourceTask,List<VmModel> vmModelList,VAppModel vapp) {
		
		vapp.setStatus(resourceTask.getStatus());
		List<Link> links = resourceTask.getLinks();
		vapp.setUrl(resourceTask.getUrl());
		if (ResourceStatus.IN_PROGRESS.equals(resourceTask.getTaskstatus())) {

			vapp.setTaskStatus("Progressing");
		} else {
			vapp.setTaskStatus("Finished");
			if(vapp.getHasTask()!=null &&vapp.getStatus()!=null&& vapp.getHasTask()&&vapp.getBeforeOPStatus().equals(resourceTask.getStatus())){
				vapp.setHasTask(false);	
				vapp.setTaskError("Operation Failed");
			}else if(vapp.getBeforeOPStatus()!=null && !vapp.getBeforeOPStatus().equals(resourceTask.getStatus())){
				vapp.setHasTask(false);
				vapp.setTaskError("");
			}
		}
		vapp.setPauseUrl("");
		vapp.setPowerOffUrl("");
		vapp.setPowerOnUrl("");
		vapp.setRevertUrl("");

         boolean poweroff = false;
		for (Link link : links) {
			if (link.getRel() != null && link.getRel().equals("power:powerOff")) {
				poweroff = true;
			} else if (link.getRel() != null
					&& link.getRel().equals("power:powerOn")) {
				vapp.setPowerOnUrl(link.getHref());
			} else if (link.getRel() != null
					&& link.getRel().equals("snapshot:revertToCurrent")) {
				vapp.setRevertUrl(link.getHref());
			}
			if (link.getRel().contains("undeploy") && poweroff) {
				vapp.setPowerOffUrl(link.getHref());
				vapp.setPauseUrl(link.getHref());
			}
		}
		int count = 0;
		for (int i = 0; i < vmModelList.size(); i++) {
			if (vmModelList.get(i).getPowerOffUrl() == null
					|| ("").equals(vmModelList.get(i).getPowerOffUrl())) {
				count++;
			}
		}
		vapp.setStopedVm(count);
		return vapp;
	}

	public List<VmModel> getVmErrors(VAppModel vapp, List<VmModel> vmModelList,
			String type) {
		for (int i = 0; i < vmModelList.size(); i++) {
			List<String> vmErrorList = new ArrayList<String>();
					if (vmModelList.get(i).getIsWindows() != null
							//&& vmModelList.get(i).getIsWindows() == true) {
							) {
							if (type != null && type.equals("attendees")) {
								if (vmModelList.get(i).getRdpDisplay() != null
										&& vmModelList.get(i).getRdpDisplay() == true) {
									if(vmModelList.get(i).getConnect()==null){
									vmErrorList.add("Could not create RDP file");
									}
										
								}else{
									if (vapp.getUrl() == null || ("").equals(vapp.getUrl())) {
										vmErrorList.add("There is a problem connecting to the Environment and VM");
								     }else{
										if (vmModelList.get(i).getVcloudVm() == null
												|| vmModelList.get(i).getVcloudVm() == false) {
											vmErrorList.add("VM does not exist son server, contact support");
										}
								     }
								}
							} else if (type != null && type.equals("instructor")) {
									if(vmModelList.get(i).getConnect()==null ){
									vmErrorList.add("Could not create RDP file");
									}
										
								
						} 
					} else {
						if (vapp.getUrl() == null || ("").equals(vapp.getUrl())) {
							vmErrorList.add("There is a problem connecting to the Environment and VM");
					     }else{
							if (vmModelList.get(i).getVcloudVm() == null
									|| vmModelList.get(i).getVcloudVm() == false) {
								vmErrorList.add("VM does not exist son server, contact support");
							}
					     }
					}
			vmModelList.get(i).setErrorList(vmErrorList);
		}
		return vmModelList;
	}

	public VAppModel getVappErrors(VAppModel vapp) {
		List<String> vappErrorList = new ArrayList<String>();
		if (vapp.getStopedVm() > 0) {
			if(vapp.getStopedVm()==vapp.getVmModelList().size()){
				vappErrorList.add("The Environment is not running");
			}else if(vapp.getStopedVm() <vapp.getVmModelList().size()){
				vappErrorList.add("The Environment is partially running");
			}
		}
		if (vapp.getUrl() == null || ("").equals(vapp.getUrl())) {
			vappErrorList.add("There is a problem connecting to the Environment and VM");
		}
		vapp.setVappErrors(vappErrorList);
		return vapp;
	}

	/**
	 * set status of rdp and console by servletContext
	 */

	public void setStatusToMerroy(VAppModel vapp, List<VAppModel> vAppModelList) {
		String vappName = vapp.getName();
		int i = 0;
		for (i = 0; i < vAppModelList.size(); i++) {
			if (vAppModelList.get(i).getName().equals(vappName)) {
				VAppModel vappFromServlet = vAppModelList.get(i);
				for (int j = 0; j < vapp.getVmModelList().size(); j++) {
					for (int k = 0; k < vappFromServlet.getVmModelList().size(); k++) {
						if (vapp.getVmModelList().get(j).getVmName().equals(vappFromServlet.getVmModelList().get(k).getVmName())) {
							vappFromServlet.getVmModelList().get(k).setVmConsole(vapp.getVmModelList().get(j).getVmConsole());
							vappFromServlet.getVmModelList().get(k).setVmRdp(vapp.getVmModelList().get(j).getVmRdp());
							break;
						}
					}
				}
				break;
			}

		}
	}

	public VAppModel setStatusFromMerroy(VAppModel vapp,List<VAppModel> vAppModelList) {
		String vappName = vapp.getName();
		log.info("vappName --------> "+vappName);
		int i = 0;
		for (i = 0; i < vAppModelList.size(); i++) {
			if (vAppModelList.get(i).getName().equals(vappName)) {
				VAppModel vappFromServlet = vAppModelList.get(i);
				for (int j = 0; j < vapp.getVmModelList().size(); j++) {
					for (int k = 0; k < vappFromServlet.getVmModelList().size(); k++) {
						if (vapp.getVmModelList().get(j).getVmName().equals(vappFromServlet.getVmModelList().get(k).getVmName())) {
							vapp.getVmModelList().get(j).setVmConsole(vappFromServlet.getVmModelList().get(k).getVmConsole());
							vapp.getVmModelList().get(j).setVmRdp(vappFromServlet.getVmModelList().get(k).getVmRdp());
							log.info("setting vmConsole ------------> "+vappFromServlet.getVmModelList().get(k).getVmConsole());
							log.info("setting vmRdp --------------> "+vappFromServlet.getVmModelList().get(k).getVmRdp());

						}
					}
				}
				break;
			}
		}
		return vapp;

	}
	
	public List<VmModel> getVmsModel(ResourceTask resourceTask, List<VmModel> vmsModel) {
		List<ResourceTask> vms = resourceTask.getChildren();
		
			for (int k = 0; k < vmsModel.size(); k++) {
				int i = 0;
			 if (vms != null && vms.size() > 0) {
				for (i = 0; i < vms.size(); i++) {
					if (vms.get(i).getName().equals(vmsModel.get(k).getVmName())
							|| (vms.size() == 1&&vmsModel.size()==1)) {
						vmsModel.get(k).setVcloudVm(true);
						
						vmsModel.get(k).setStatus(vms.get(i).getStatus());
						vmsModel.get(k).setId(vms.get(i).getId());
						List<Link> linkList = vms.get(i).getLinks();
						boolean poweroff = false;
						for (Link link : linkList) {
							if (link.getRel() != null && link.getRel().equals("power:powerOff")) {
								poweroff = true;
							} else if (link.getRel() != null && link.getRel().equals("power:powerOn")) {
								vmsModel.get(k).setPowerOnUrl(link.getHref());
							} else if (link.getRel() != null && link.getRel().equals("snapshot:revertToCurrent")) {
								vmsModel.get(k).setRevertUrl(link.getHref());
							} else if (link.getRel() != null && link.getRel().equals("screen:acquireTicket")) {
								vmsModel.get(k).setTicket(link.getHref());
							} else if (link.getRel().contains("undeploy")&& poweroff) {
								vmsModel.get(k).setPowerOffUrl(link.getHref());
								vmsModel.get(k).setPauseUrl(link.getHref());
							}
//							else if(link.getRel().contains("power:suspend")){
//								vmsModel.get(k).setPauseUrl(link.getHref());
//							}
						}
						if (ResourceStatus.IN_PROGRESS.equals(vms.get(i)
								.getTaskstatus())) {
							vmsModel.get(k).setTaskStatus("Progressing");
						} else {
							vmsModel.get(k).setTaskStatus("Finished");
							if(vmsModel.get(k).getHasTask()!=null &&vmsModel.get(k).getBeforeOPStatus()!=null&& vmsModel.get(k).getHasTask()&&vmsModel.get(k).getBeforeOPStatus().equals(vms.get(i).getStatus())){
								vmsModel.get(k).setTaskError("Operation Failed");
								vmsModel.get(k).setHasTask(false);
							}else if(vmsModel.get(k).getBeforeOPStatus()!=null&&!vmsModel.get(k).getBeforeOPStatus().equals(vms.get(i).getStatus())){
								vmsModel.get(k).setHasTask(false);
								vmsModel.get(k).setTaskError("");
							}
						}
						break;
					}
				}
				if (i >= vms.size()) {
					vmsModel.get(k).setVcloudVm(false);
				}
				// set status of VAPP,VM,console and RDP
				
					if (resourceTask.getUrl() != null&& !resourceTask.getUrl().equals("")) {
						// set RDP display status
						if (vmsModel.get(k).getVcloudVm() != null&& vmsModel.get(k).getVcloudVm() == true) {
							//if (vmsModel.get(k).getIsWindows() == null|| vmsModel.get(k).getIsWindows() == false) {
							if (vmsModel.get(k).getIsWindows() == null) {
								vmsModel.get(k).setRdpDisplay(false);
//								vmsModel.get(k).setRdpDisplay(true);
							} else {
								if (vmsModel.get(k).getVmRdp() != null&& vmsModel.get(k).getVmRdp() == true) {
									vmsModel.get(k).setRdpDisplay(true);
								} else {
									vmsModel.get(k).setRdpDisplay(false);
//									vmsModel.get(k).setRdpDisplay(true);
								}
							}
							if (vmsModel.get(k).getVmConsole() != null
									&& vmsModel.get(k).getVmConsole() == true) {
								vmsModel.get(k).setConsoleDisplay(true);
							} else {
								vmsModel.get(k).setConsoleDisplay(false);
							}
						} else {
							vmsModel.get(k).setConsoleDisplay(false);
							vmsModel.get(k).setRdpDisplay(false);
						}
					} else {
						vmsModel.get(k).setVappStatus(false);
							
					}
				}
			  
			}
		return vmsModel;
	}

	public ResourceTask getvmFromThreadPool(final String appName,final String orgName, final String cellLocation){
		ExecutorService executor = Executors.newFixedThreadPool(50);   
		ResourceTask rt=null;
		FutureTask<ResourceTask> future =(FutureTask<ResourceTask>) executor.submit( new Callable<ResourceTask>() {
		   public ResourceTask call() {   
//			   User user=new User();
//			   user.setUserName("661-13923-Student2");
//			   user.setType("attendees");
//			   Event event = eventService.getEvent(user);
//			   return null;
			   ResourceTask resourceTask = vCloudService.getAms(appName, orgName, cellLocation);
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
		} finally {   
		   executor.shutdown();   
		}  
		return rt;
		
	}

}

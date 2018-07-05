package com.hp.vtms.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.ListOrderedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.vtms.Constants;
import com.hp.vtms.VCloudExceptionsHandler;
import com.hp.vtms.model.Attendees;
import com.hp.vtms.model.Connect;
import com.hp.vtms.model.Event;
import com.hp.vtms.model.Info;
import com.hp.vtms.model.Instructor;
import com.hp.vtms.model.InstructorGloab;
import com.hp.vtms.model.User;
import com.hp.vtms.model.VAppModel;
import com.hp.vtms.model.VmModel;
import com.hp.vtms.model.Webstatsvm;
import com.hp.vtms.service.AttendeesService;
import com.hp.vtms.service.ConnectService;
import com.hp.vtms.service.EventService;
import com.hp.vtms.service.FTPService;
import com.hp.vtms.service.InfoService;
import com.hp.vtms.service.InstructorService;
import com.hp.vtms.service.TemplatesService;
import com.hp.vtms.service.TrainingmgmService;
import com.hp.vtms.service.VCloudService;
import com.hp.vtms.service.WebstatsvmService;
import com.hp.vtms.util.ApplicationContainer;
import com.hp.vtms.vcloud.model.ConsoleParam;
import com.hp.vtms.vcloud.model.Link;
import com.hp.vtms.vcloud.model.ResourceStatus;
import com.hp.vtms.vcloud.model.ResourceTask;
import com.vmware.vcloud.api.rest.schema.MksTicketType;

/**
 * Created with IntelliJ IDEA. User: meij Date: 13-12-3 Time: 2:43 To change this template use File
 * | Settings | File Templates.
 */
@Controller
@RequestMapping("trainingmgm")
public class TrainingmgmController implements Cloneable {

    private static final Logger _LOG = LoggerFactory.getLogger(TrainingmgmController.class);

    @Autowired
    private VCloudExceptionsHandler vCloudExceptionsHandler;

    @Autowired
    private TrainingmgmService trainingmgmService;

    @Autowired
    private FTPService ftpService;

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

    @Autowired
    private InfoService infoService;
    
    @Autowired
    private WebstatsvmService webstatsvmService;


    private static boolean isValid = false;
    private static Pattern p = null;
    private static Matcher m = null;

    @RequestMapping()
    public String index(ModelMap map, HttpServletRequest request) {
    	_LOG.info("index().....................");
        HttpSession session = request.getSession();
        VAppModel vapp = (VAppModel) session.getAttribute("vapp");
        if (vapp == null) {
            vapp = new VAppModel();
            User user = (User) session.getAttribute("user");

            String type = user.getType();
            String username = user.getUserName();
            if (type.equals("instructor")) {

                map.put("getAttList", eventService.getMatchStudents(username));
            }
         //   List<VmModel> vms = templatesService.getVmsName(user.getEventId(), username);
		   List<VmModel> vms = templatesService.getVmsName(user.getEventId(), username.concat(","+type));
            vapp.setVmModelList(vms);
            vapp.setName(user.getVappName());
            session.setAttribute("vapp", vapp);
            session.setAttribute("vappTask", vapp);
			          
        }
        return "trainingmgm";
    }

    @RequestMapping("studentmgm")
    public String student(ModelMap map, HttpServletRequest request) {
    	_LOG.info("studentmgm()..........................");
        HttpSession session = request.getSession();
        ListOrderedMap appNameList = new ListOrderedMap();
        List<VAppModel> vappModelList = (List<VAppModel>) session.getAttribute("vAppModelList");
        if (vappModelList == null) {
            vappModelList = new ArrayList<VAppModel>();
            Instructor in = null;
            User user = (User) session.getAttribute("user");
            if (user.getType().equals("instructor")) {
                String instructorName = user.getUserName();

                in = instructorService.login(instructorName);
                // map.put("getAttList", eventService.getMatchStudents(username));
                int vmIsWCount = 0;

                List<VmModel> vms = new ArrayList<VmModel>();
                for (Attendees attendees : eventService.getMatchStudents(instructorName)) {
                    VAppModel vapp = new VAppModel();
                    // String appName = trainingmgmService.getVAppByUser(attendees.getUsername(),
                    // "attendees");
                    String appName = attendees.getAttAppName();
                    vms = templatesService.getVmsName(instructorName, attendees);
                    vapp.setVmModelList(vms);
                    vapp.setName(appName);
                    vapp.setIsAllowed(in.getInAllowControl());
                    vapp.setStudentName(attendees.getUsername());
                    appNameList.put(attendees.getUsername(), vapp);
                    vappModelList.add(vapp);
                }
                for (int j = 0; j < vms.size(); j++) {
                    //if (vms.get(j).getIsWindows() == true) {
                        vmIsWCount++;
                    //}
                }
                session.setAttribute("vmIsWCount", vmIsWCount);
                user.setAllowed(in.getInAllowControl());
            }

        }
        if (vappModelList != null && vappModelList.size() > 0) {
            session.setAttribute("vAppModelList", vappModelList);
            session.setAttribute("vAppModelListTask", vappModelList);

        } else {
            _LOG.error("student list is null");
            vCloudExceptionsHandler.customVcloudExceptions("602");
        }

        _LOG.info("returning studentmgm......................");
        return "studentmgm";
    }

    @RequestMapping("initialStudentmgm")
    public String initialStudentmgm(ModelMap map, HttpServletRequest request) {

    	_LOG.info("initialStudentmgm()........................");
        HttpSession session = request.getSession();
        ApplicationContainer applicationContainer = ApplicationContainer.getInstance();
        User user = (User) session.getAttribute("user");
        user = infoService.getInfoByType(user);
        session.setAttribute("user", user);
        Boolean isFirstTime = user.getIsFirstTime();
        String contextDataKey = user.getEventId() + Event.contextDataFlag;
        List<VAppModel> vAppModelListFromServlet = (List<VAppModel>) applicationContainer.getObject(contextDataKey);
        List<VAppModel> vAppModelList = trainingmgmService.getvappListToTraining(request, vAppModelListFromServlet,
            user);
        // vAppModelList.get(1).getStudentName()
        if (isFirstTime == true) {
            applicationContainer.setObject(contextDataKey, vAppModelList);
            user.setIsFirstTime(false);
        }
        List<VAppModel> merroyVAppModelList = (List<VAppModel>) session.getAttribute("vAppModelListTask");
        for (int i = 0; i < vAppModelList.size(); i++) {
            if (merroyVAppModelList != null) {
                for (int j = 0; j < merroyVAppModelList.size(); j++) {
                    if (vAppModelList.get(i).getName().equals(merroyVAppModelList.get(j).getName())) {
                        setVapp(merroyVAppModelList.get(j), vAppModelList.get(i));
                        break;
                    }
                }
            }
        }

        _LOG.info("returning vmsStudent.................................");
        session.setAttribute("vAppModelList", vAppModelList);
        return "vmsStudent";
    }

    @RequestMapping("download")
    @ResponseBody
    public void index(@RequestParam(value = "vmName", required = false) String vmName, ModelMap map,
        HttpServletResponse response, HttpServletRequest request) {

    	_LOG.info("index:download()..............................");
        String username = (String) request.getParameter("userName");
        String downType = request.getParameter("downType");
        Connect conn = null;
        if ("rdpTest".equals(downType)) {
            Info info = infoService.getInfo("test_user_connection");
            conn = new Connect();
            conn.setConIpXAddress(info.getInfoValue());
            conn.setConUsername(info.getInfoValue2());
            conn.setConGateway(info.getInfoValue3());
        } else {
            conn = connService.getConn(username, vmName);
        }
        try {
            downLoadRdp(response, conn, request);

        } catch (Exception e) {
            e.printStackTrace(); // To change body of catch statement use File |
                                 // Settings | File Templates.
        }
    }

    // student training management page
    @RequestMapping("getVmsHtml")
    public String getVmsHtml(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        _LOG.info("get data for Training page");
        _LOG.info("getVmsHtml()............................");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        user = infoService.getInfoByType(user);
        session.setAttribute("user", user);
        String username = user.getUserName();
        String type = user.getType();
        Boolean isFirstTime = user.getIsFirstTime();
        String instructorName = "";
        if (type.equals("attendees")) {
            instructorName = attendeesService.getInstructorName(username);
        } else {
            instructorName = username;
        }
        ApplicationContainer applicationContainer = ApplicationContainer.getInstance();
        String contextDataKey = user.getEventId() + Event.contextDataFlag;
        _LOG.info("User Event Id  -----------> "+user.getEventId()+"     Event ContextDataFlag -----------> "+Event.contextDataFlag);
        _LOG.info("contex Data Key is --------> "+contextDataKey);
        _LOG.info("User Name is -------> "+user.getUserName());
        
        List<VAppModel> vAppModelList = (List<VAppModel>) applicationContainer.getObject(contextDataKey);
        // List<VAppModel> vAppModelList = (List<VAppModel>)
        // applicationContainer.getObject(instructorName);
        String participantUrl = (String) applicationContainer.getObject(user.getEventId());
        if (participantUrl != null && !participantUrl.equals("")) {
            user.setParticipantUrl(participantUrl);
        }
        _LOG.info("VAppModelList in  controller ................"+vAppModelList);
        
        
        VAppModel vapp = (VAppModel) session.getAttribute("vapp");
        // here we will pass studentName and vappName  // we will retrieve 3 records
        
        vapp = trainingmgmService.getVappToView(vapp, vAppModelList, user);

        _LOG.info("isFirstTime -----------------> "+isFirstTime);
        if (isFirstTime != null && isFirstTime == true) {        	
            if (vAppModelList != null && type.equals("attendees")) {
            	_LOG.info("calling setStatusToMemory().....................");
                setStatusToMerroy(vapp, vAppModelList);
                applicationContainer.setObject(contextDataKey, vAppModelList);
            }
            user.setIsFirstTime(false);
        }
        session.setAttribute(Constants.SESSION_ORGNAME, vapp.getOrgName());
        session.setAttribute("instructorName", instructorName);
        VAppModel merroyApp = (VAppModel) session.getAttribute("vappTask");

        vapp = setVapp(merroyApp, vapp);
        
        // chinna start
        
        
        _LOG.info("Iterating VAppModel ....................start....");
        _LOG.info("vapp Name -----------> "+vapp.getName());
        _LOG.info("vapp student name -----------> "+vapp.getStudentName());
        
        	List<VmModel> vmModelList = vapp.getVmModelList();
        	_LOG.info("The VmModelList size is --------> "+vmModelList.size());
        	Iterator vmModelListIterator = vmModelList.iterator();
        	while(vmModelListIterator.hasNext()){
        		_LOG.info("*************************************************");
        		VmModel model = (VmModel)vmModelListIterator.next();
        		_LOG.info("VM Name is -------------> "+model.getVmName());
        		_LOG.info("RDP --------> "+model.getRdpDisplay());
        		_LOG.info("Console ---------> "+model.getConsoleDisplay());
        	}
        
        	_LOG.info("Iterating VAppModel ....................end....");
        
        String currentTomcatServerName = null;
        try {
			     currentTomcatServerName = InetAddress.getLocalHost().getHostName();
			     _LOG.info("Current Tomcast Server ----------> "+currentTomcatServerName);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // logic to check same server or not
        _LOG.info("vapp Name is --------> "+vapp.getName());
        _LOG.info("Current User is --------> "+vapp.getStudentName());
        _LOG.info("Current User from User oject ---------> "+user.getUserName());
        List<Webstatsvm> webstatsvmDbList = webstatsvmService.selectInstructorSession(user.getUserName());
        
        _LOG.info("The webstatsvmDbList is ------------> "+webstatsvmDbList);
		if (webstatsvmDbList != null && webstatsvmDbList.size() != 0) {
			if (webstatsvmDbList.get(0).getSessionServer().equals(currentTomcatServerName)) {
				_LOG.info("Both Instructor and Antendee login with the same session..............");
			} else {
				_LOG.info("Both Instructor and Antendee login with the Different session..............");

				if (webstatsvmDbList != null) {
					for (byte i = 0; i < webstatsvmDbList.size(); i++) {
						for (byte j = 0; j < vapp.getVmModelList().size(); j++) {
							//if (webstatsvmDbList.get(i).getUserName().equals(vapp.getStudentName())&& webstatsvmDbList.get(i).getVmname().equals(vapp.getVmModelList().get(j).getVmName())) {
							if (webstatsvmDbList.get(i).getUserName().equals(user.getUserName())&& webstatsvmDbList.get(i).getVmname().equals(vapp.getVmModelList().get(j).getVmName())) {
								_LOG.info("Student Name ----------> "+ webstatsvmDbList.get(i).getUserName());
								_LOG.info("VM Name ---------> "	+ webstatsvmDbList.get(i).getVmname());
								vapp.getVmModelList().get(j).setConsoleDisplay(webstatsvmDbList.get(i).getShowConsole());
								vapp.getVmModelList().get(j).setRdpDisplay(webstatsvmDbList.get(i).getShowRdp());
							}
						}
					}// End of first if condition
				}// End of for loop

			}
		}

		
		_LOG.info("After setting values checking rdp and console data.............................");
		
		  _LOG.info("Iterating VAppModel .........2...........start....");
	        _LOG.info("vapp Name -----------> "+vapp.getName());
	        _LOG.info("vapp student name -----------> "+vapp.getStudentName());
	        
	        	List<VmModel> vmModelList2 = vapp.getVmModelList();
	        	_LOG.info("The VmModelList size is --------> "+vmModelList2.size());
	        	Iterator vmModelListIterator2 = vmModelList.iterator();
	        	while(vmModelListIterator2.hasNext()){
	        		_LOG.info("*************************************************");
	        		VmModel model = (VmModel)vmModelListIterator2.next();
	        		_LOG.info("VM Name is -------------> "+model.getVmName());
	        		_LOG.info("RDP --------> "+model.getRdpDisplay());
	        		_LOG.info("Console ---------> "+model.getConsoleDisplay());
	        	}
	        
	        	_LOG.info("Iterating VAppModel ..........2..........end....");
	      
		
        // chinna end
        session.setAttribute("vapp", vapp);
        _LOG.info("return to Training page");
        _LOG.info("returning vms.............................");
        return "vms";
    }

    public VAppModel setVapp(VAppModel merroyApp, VAppModel newApp) {
    	_LOG.info("setVapp()........................");
        if (merroyApp.getHasTask() != null && merroyApp.getHasTask()) {
            newApp.setHasTask(merroyApp.getHasTask());
            newApp.setBeforeOPStatus(merroyApp.getBeforeOPStatus());
        }
        for (int i = 0; i < merroyApp.getVmModelList().size(); i++) {
            if (merroyApp.getVmModelList().get(i).getVmName().equals(newApp.getVmModelList().get(i).getVmName())) {
                if (merroyApp.getVmModelList().get(i).getHasTask() != null
                    && merroyApp.getVmModelList().get(i).getHasTask())
                    newApp.getVmModelList().get(i).setHasTask(merroyApp.getVmModelList().get(i).getHasTask());
                newApp.getVmModelList().get(i).setBeforeOPStatus(merroyApp.getVmModelList().get(i).getBeforeOPStatus());
            }
        }
        return newApp;
    }

    @RequestMapping("console")
    @ResponseBody
    public ConsoleParam console(@RequestParam(value = "url", required = false) String url, ModelMap map,
        HttpServletRequest request) {
_LOG.info("console()...........................");
        ConsoleParam param = vCloudService.getConsoleParam(url);

        return param;
    }

    @RequestMapping("htmlConsole")
    @ResponseBody
    public MksTicketType htmlConsole(@RequestParam(value = "vappUrl", required = false) String url,
        @RequestParam(value = "vmName", required = false) String vmName, ModelMap map, HttpServletRequest request) {
_LOG.info("htmlConsole().........................");
        MksTicketType t = vCloudService.getHtmlConsoleParam(url, vmName);
        _LOG.info("ticket:" + t.getTicket());
        return t;
    }

    @RequestMapping("toPluginConsole")
    public String toPluginConsole(@RequestParam(value = "vappName", required = false) String vappName,
        @RequestParam(value = "vmName", required = false) String vmName,
        @RequestParam(value = "isStudentPage", required = false) Boolean isStudentPage, ModelMap map,
        HttpServletRequest request) {

        return "pluginconsole";
    }

    @RequestMapping("getConsoleData")
    @ResponseBody
    public String getConsoleData(@RequestParam(value = "vappName", required = false) String vappName,
        @RequestParam(value = "vmName", required = false) String vmName,
        @RequestParam(value = "isStudentPage", required = false) Boolean isStudentPage, ModelMap map,
        HttpServletRequest request) {
_LOG.info("getConsoleData().............................");
        HttpSession session = request.getSession();
        session.setAttribute(vappName + "_" + vmName, "");

        String vappUrl = "";
        if (isStudentPage) {
            List<VAppModel> vAppModelList = (List<VAppModel>) session.getAttribute("vAppModelList");
            if (vAppModelList != null) {
                for (VAppModel i : vAppModelList) {
                    if (i.getName() != null && i.getName().equals(vappName)) {
                        for (VmModel j : i.getVmModelList()) {
                            if (j.getVmName() != null && j.getVmName().equals(vmName)) {
                                j.setAdvanced(i.getAdvanced());
                                vappUrl = i.getUrl();
                            }
                        }
                    }
                }
            }
        } else {
            VAppModel vapp = (VAppModel) session.getAttribute("vapp");
            for (VmModel i : vapp.getVmModelList()) {
                if (i.getVmName() != null && i.getVmName().equals(vmName)) {
                    i.setAdvanced(vapp.getAdvanced());
                    vappUrl = vapp.getUrl();
                }
            }
        }
        return vappUrl;
    }

    @RequestMapping("toHtmlConsole")
    public String toHtmlConsole(@RequestParam(value = "vappName", required = false) String vappName,
        @RequestParam(value = "vmName", required = false) String vmName,
        @RequestParam(value = "isStudentPage", required = false) Boolean isStudentPage, ModelMap map,
        HttpServletRequest request) {
    	_LOG.info("toHtmlConsole();...............................");
        return "htmlconsole";
    }

    @RequestMapping("getStatusOfVm")
    @ResponseBody
    public VmModel getStatusOfVm(ModelMap map, @RequestParam(value = "vappName", required = false) String vappName,
        @RequestParam(value = "vmName", required = false) String vmName,

        HttpServletRequest request) {
        // String name = request.getParameter("name");

    	_LOG.info("getStatusOfVm()..........................");
        HttpSession session = request.getSession();
        String orgName = (String) session.getAttribute(Constants.SESSION_ORGNAME);
        String vappUrl = request.getParameter("vappUrl");
        VmModel vm = new VmModel();
        ResourceTask resourceTask = vCloudService.getStatusOfVappOrVm("vm", vmName, vappUrl, orgName);
        if (resourceTask.getTaskstatus() != null && resourceTask.getTaskstatus().equals(ResourceStatus.FINISHED)) {
            vm.setStatus("true");
        } else if (resourceTask.getTaskstatus() == null
            || resourceTask.getTaskstatus().equals(ResourceStatus.IN_PROGRESS)) {
            vm.setStatus("false");
        }

        if (resourceTask != null && resourceTask.getChildren() != null) {
            if (resourceTask.getChildren().size() != 1) {
                for (ResourceTask i : resourceTask.getChildren()) {
                    if (i.getName().equals(vmName)) {
                        for (Link link : i.getLinks()) {
                            if (link.getRel() != null && link.getRel().equals("undeploy")) {
                                vm.setPowerOffUrl(link.getHref());
                                vm.setPauseUrl(link.getHref());
                            } else if (link.getRel() != null && link.getRel().equals("power:powerOn")) {
                                vm.setPowerOnUrl(link.getHref());
                            } else if (link.getRel() != null && link.getRel().equals("snapshot:revertToCurrent")) {
                                vm.setRevertUrl(link.getHref());
                            } else if (link.getRel() != null && link.getRel().equals("screen:acquireTicket")) {
                                vm.setTicket(link.getHref());
                            }
                        }
                    }
                }
            } else {
                for (ResourceTask i : resourceTask.getChildren()) {
                    for (Link link : i.getLinks()) {
                        if (link.getRel() != null && link.getRel().equals("undeploy")) {
                            vm.setPowerOffUrl(link.getHref());
                        } else if (link.getRel() != null && link.getRel().equals("power:powerOn")) {
                            vm.setPowerOnUrl(link.getHref());
                        } else if (link.getRel() != null && link.getRel().equals("snapshot:revertToCurrent")) {
                            vm.setRevertUrl(link.getHref());
                        } else if (link.getRel() != null && link.getRel().equals("screen:acquireTicket")) {
                            vm.setTicket(link.getHref());
                        } else if (link.getRel() != null && link.getRel().equals("power:suspend")) {
                            vm.setPauseUrl(link.getHref());
                        }

                    }
                }
            }
        }

        User user = (User) session.getAttribute("user");
        Event event = eventService.getEvent(user);
        vm.setAdvanced(event.getAdvanced() == true ? 1 : 0);
        return vm;

    }

    @RequestMapping("chooseRdpOrConsole")
    @ResponseBody
    public String chooseRdpOrConsole(@RequestParam(value = "vappName", required = false) String vappName,
        @RequestParam(value = "vmName", required = false) String vmName, ModelMap map, HttpServletRequest request) {
_LOG.info("chooseRdpOrConsole().........................");
        String rdpOrConsole = request.getParameter("rdpOrConsole");
        _LOG.info("The RDP or CONSOLE is --------> "+rdpOrConsole);
        _LOG.info("The vappName is ---------> "+vappName);
        HttpSession session = request.getSession();
        String currentTomcatServerName = null;
        try {
			     currentTomcatServerName = InetAddress.getLocalHost().getHostName();
			     _LOG.info("Current Tomcast Server ----------> "+currentTomcatServerName);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        User user = (User) session.getAttribute("user");
        String instructorName = user.getUserName();
        _LOG.info("User Name is ----------> "+instructorName);
        
        ApplicationContainer applicationContainer = ApplicationContainer.getInstance();
        List<VAppModel> vAppModelList = new ArrayList<VAppModel>();
        
        Webstatsvm webstatsvmObj = new Webstatsvm();
        webstatsvmObj.setUserName(user.getUserName());
        webstatsvmObj.setSessionServer(currentTomcatServerName);             
        
        String contextDataKey = user.getEventId() + Event.contextDataFlag;
        _LOG.info("The contextDataKey is --------->"+contextDataKey);
        _LOG.info("User Event Id  --------------> "+user.getEventId()+"     Event ContextDataFlag -------------> "+Event.contextDataFlag);
        vAppModelList = (List<VAppModel>) applicationContainer.getObject(contextDataKey);
        for (int i = 0; i < vAppModelList.size(); i++) {
            if (vAppModelList.get(i).getName().equals(vappName)) {
            	
                List<VmModel> vms = vAppModelList.get(i).getVmModelList();
                _LOG.info("The vappName is inside loop  ---------> "+vAppModelList.get(i).getName());
                _LOG.info("Student Name ---------> "+vAppModelList.get(i).getStudentName());
                for (int j = 0; j < vms.size(); j++) {
                	 
                    if (vms.get(j).getVmName().equals(vmName)) {
                    	_LOG.info("VM Name ------> "+vmName);
                    	
                        if (rdpOrConsole.equals("showRdp")) {
                        	_LOG.info("setting show RDP.....................");
                        	_LOG.info("getVmName() ---------> "+vms.get(j).getVmName());
                            vms.get(j).setVmConsole(false);
                            _LOG.info("method----chooseRdpOrConsole:vmsName={},RDPDisplay={}", vms.get(j).getVmName(),vms.get(j).getRdpDisplay());
                            vms.get(j).setVmRdp(true);
                            
                            webstatsvmObj.setUserName(vAppModelList.get(i).getStudentName());
                            webstatsvmObj.setVmname(vms.get(j).getVmName());
                            webstatsvmObj.setShowRdp(true);
                            webstatsvmObj.setShowConsole(false);
                         //   webstatsvmObj.setUpdate(true);
                            
                            // check Record is available in DB or not
                            List<Webstatsvm> webstatsvmList = webstatsvmService.retrieveStudentRecord(webstatsvmObj);
                            _LOG.info("The List Size is-------> "+webstatsvmList.size());
                            
                            if(webstatsvmList.size()>0){
                            	_LOG.info("updating the record..........showRdp.........");
                            	webstatsvmService.updateRdpConsoleDetails(webstatsvmObj);
                            }else{
                            	_LOG.info("inserting the record..........showRdp........");
                            	webstatsvmService.insertRdpConsoleDetails(webstatsvmObj);
                            }
                            
                            
                            _LOG.info("method----chooseRdpOrConsole:vmsName={},ConsoleDisplay={}", vms.get(j)
                                .getVmName(), vms.get(j).getConsoleDisplay());

                        } else if (rdpOrConsole.equals("showConsole")) {
                        	
                        	_LOG.info("setting show Console..................");
                            vms.get(j).setVmConsole(true);                            
                            vms.get(j).setVmRdp(false);
                            
                            webstatsvmObj.setUserName(vAppModelList.get(i).getStudentName());
                            webstatsvmObj.setVmname(vms.get(j).getVmName());
                            webstatsvmObj.setShowRdp(false);
                            webstatsvmObj.setShowConsole(true);
                           // webstatsvmObj.setUpdate(true);
                            
                            // check Record is available in DB or not
                            List<Webstatsvm> webstatsvmList = webstatsvmService.retrieveStudentRecord(webstatsvmObj);
                            _LOG.info("The List Size is-------> "+webstatsvmList.size());
                            
							if (webstatsvmList.size() > 0) {
								_LOG.info("updating the record.........showConsole..........");
								webstatsvmService
										.updateRdpConsoleDetails(webstatsvmObj);
							} else {
								_LOG.info("inserting the record........showConsole..........");
								webstatsvmService
										.insertRdpConsoleDetails(webstatsvmObj);
							}
                            
                            } else if (rdpOrConsole.equals("showBoth")) {
                        	_LOG.info("set both console and rdp........................");
                            vms.get(j).setVmConsole(true);
                            vms.get(j).setVmRdp(true);
                            
                            webstatsvmObj.setUserName(vAppModelList.get(i).getStudentName());
                            webstatsvmObj.setVmname(vms.get(j).getVmName());
                            webstatsvmObj.setShowRdp(true);
                            webstatsvmObj.setShowConsole(true);
                            //webstatsvmObj.setUpdate(true);
                            
                            // check Record is available in DB or not
                            List<Webstatsvm> webstatsvmList = webstatsvmService.retrieveStudentRecord(webstatsvmObj);
                            _LOG.info("The List Size is-------> "+webstatsvmList.size());
                            
                            if(webstatsvmList.size()>0){
                            	_LOG.info("updating the record........showBoth...........");
                            	webstatsvmService.updateRdpConsoleDetails(webstatsvmObj);
                            }else{
                            	_LOG.info("inserting the record.........showBoth.........");
                            	webstatsvmService.insertRdpConsoleDetails(webstatsvmObj);
                            }
                            
                            
                            
                        }

                        vAppModelList.get(i).setVmModelList(vms);
                        _LOG.info("Finally  showRDP ----> "+vms.get(j).getRdpDisplay());
                        _LOG.info("Finally  showConsole ----> "+vms.get(j).getVmConsole());
                        
                        break;

                    }
                }
                break;

            }

        }
       //  applicationContainer.setObject(instructorName, vAppModelList);
        vAppModelList = (List<VAppModel>) applicationContainer.getObject(contextDataKey);
        
        // chinna start

        
		for (int i = 0; i < vAppModelList.size(); i++) {
			if (vAppModelList.get(i).getName().equals(vappName)) {
				List<VmModel> vms = vAppModelList.get(i).getVmModelList();

				for (int j = 0; j < vms.size(); j++) {
					if (vms.get(j).getVmName().equals(vmName)) {
						  _LOG.info("Finally2  showRDP ----> "+vms.get(j).getRdpDisplay());
	                        _LOG.info("Finally2  showConsole ----> "+vms.get(j).getVmConsole());
	                      
					}
				}
			}

		}
        
        // chinna end
        _LOG.info("RDPDisplay:" + vAppModelList.get(0).getVmModelList().get(0).getRdpDisplay() + "ConsoleDisplay:"
            + vAppModelList.get(0).getVmModelList().get(1).getConsoleDisplay());

        return "ok";
    }

    /**
     * chooseRdpOrConsoleGloab change all display of VMs
     */
    @RequestMapping("chooseRdpOrConsoleGloab")
    @ResponseBody
    public String chooseRdpOrConsoleGloab(@RequestParam(value = "rdpOrConsole", required = false) String rdpOrConsole,
        ModelMap map, HttpServletRequest request) {
    	_LOG.info("chooseRdpOrConsoleGloab().............................");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String instructorName = user.getUserName();
        
        if(user.getType().equals("attendees")){
        	_LOG.info("returning attendees..............");
        	return "<html><script>alert(\"You are Not Authorized User\");</script></html>";
        }
       
        ApplicationContainer applicationContainer = ApplicationContainer.getInstance();

        String contextDataKey = user.getEventId() + Event.contextDataFlag;
        List<VAppModel> vAppModelList = (List<VAppModel>) applicationContainer.getObject(contextDataKey);

        InstructorGloab gloabInstructor = new InstructorGloab();
        gloabInstructor.setInstructorName(instructorName);
        gloabInstructor.setGloabOption(true);

        for (int i = 0; i < vAppModelList.size(); i++) {
            for (int j = 0; j < vAppModelList.get(i).getVmModelList().size(); j++) {
                if (vAppModelList.get(i).getVmModelList().get(j).getIsWindows() != null
                    //&& vAppModelList.get(i).getVmModelList().get(j).getIsWindows() == true) {
                	) {
                    if (rdpOrConsole.equals("showRdp")) {

                        vAppModelList.get(i).getVmModelList().get(j).setVmRdp(true);
                        vAppModelList.get(i).getVmModelList().get(j).setVmConsole(false);
                        gloabInstructor.setGloabConsole(false);
                        gloabInstructor.setGloabRdp(true);
                    } else if (rdpOrConsole.equals("showConsole")) {

                        vAppModelList.get(i).getVmModelList().get(j).setVmConsole(true);
                        vAppModelList.get(i).getVmModelList().get(j).setVmRdp(false);
                        gloabInstructor.setGloabConsole(true);
                        gloabInstructor.setGloabRdp(false);
                    } else if (rdpOrConsole.equals("showBoth")) {
                        vAppModelList.get(i).getVmModelList().get(j).setVmConsole(true);
                        vAppModelList.get(i).getVmModelList().get(j).setVmRdp(true);
                        gloabInstructor.setGloabConsole(true);
                        gloabInstructor.setGloabRdp(true);
                    }
                } else {
                    vAppModelList.get(i).getVmModelList().get(j).setVmRdp(false);
                    vAppModelList.get(i).getVmModelList().get(j).setVmConsole(true);
                }

            }
        }

        session.setAttribute("instructorGloabOption", gloabInstructor);
        applicationContainer.setObject(contextDataKey, vAppModelList);
        return "ok";
    }

    @RequestMapping("connContent")
    @ResponseBody
    public String getContent(@RequestParam(value = "vmName", required = false) String vmName, ModelMap map,
        HttpServletResponse response, HttpServletRequest request) {
    	_LOG.info("getContent().......................");
       
	    String username = (String) request.getParameter("userName");        
        boolean validated = isValidString(username);
        _LOG.info("The validated status is ------> "+validated);
      
        Connect conn = null;
        if (!validated) {		
        	conn = connService.getConn(username, vmName);
			String rdpString = getRdp(conn, request);
			return rdpString;
		}
		return null;
    }

    /**
     * set status of rdp and console by servletContext
     */

    public void setStatusToMerroy(VAppModel vapp, List<VAppModel> vAppModelList) {
    	_LOG.info("setStatusToMerroy().......................");
        String vappName = vapp.getName();
        _LOG.info("vappName ------------> "+vappName);
        int i = 0;
        for (i = 0; i < vAppModelList.size(); i++) {
            if (vAppModelList.get(i).getName().equals(vappName)) {
                VAppModel vappFromServlet = vAppModelList.get(i);
                for (int j = 0; j < vapp.getVmModelList().size(); j++) {
                    for (int k = 0; k < vappFromServlet.getVmModelList().size(); k++) {
                        if (vapp.getVmModelList().get(j).getVmName().equals(
                            vappFromServlet.getVmModelList().get(k).getVmName())) {
                        	_LOG.info("getVmName(); ----------> "+vappFromServlet.getVmModelList().get(k).getVmName());
                            vappFromServlet.getVmModelList().get(k).setVmConsole(
                                vapp.getVmModelList().get(j).getVmConsole());
                            vappFromServlet.getVmModelList().get(k).setVmRdp(vapp.getVmModelList().get(j).getVmRdp());
                            
                            _LOG.info("Final vmConsole --------> "+vapp.getVmModelList().get(j).getVmConsole());
                            _LOG.info("Final vmRdp -----------> "+vapp.getVmModelList().get(j).getVmRdp());
                            break;
                        }
                    }

                }
                break;
            }
        }
    }

    public void downLoadRdp(HttpServletResponse response, Connect connect, HttpServletRequest request) {
    	_LOG.info("downLoadRdp().............................");
        String path = request.getSession().getServletContext().getRealPath("/");
        //String filePath = path + "\\rdp.rdp";
        
        String filePath = path +File.separator+"rdp.rdp";
        
        String line = "";
        String fileContent = "";
        String vmName = (String) request.getParameter("vmName");
        InputStream ins = this.getClass().getClassLoader().getResourceAsStream("/rdp.rdp");

        File file = new File(filePath);
        if (ins != null) {

            InputStreamReader read = null;
            BufferedReader reader = null;
            OutputStream out = null;
            try {
                read = new InputStreamReader(ins, "UTF-8");
                reader = new BufferedReader(read);
                response.setContentType("application/x-rdp");
                // response.setHeader("Content-Disposition", "inline;filename=student1.rdp" );
                response.setHeader("Content-Disposition", "attachment;filename=" + vmName + ".rdp");
                // response.setHeader("Content-Disposition", "inline;filename=student1.rdp" );

                out = response.getOutputStream();
                // out.write(fileContent.getBytes());
                while ((line = reader.readLine()) != null) {
                    if (line.indexOf("full address:s:") != -1) {
                        line = line + connect.getConIpXAddress();
                    }
                    if (line.indexOf("username:s:") != -1) {
                        line = line + connect.getConUsername();
                    }
                    if (line.indexOf("gatewayhostname:s:") != -1) {
                        line = line + connect.getConGateway();
                    }
                    line = line + "\n";
                    // fileContent += line+"\n";
                    byte[] buf = new byte[1024];
                    buf = line.getBytes();
                    out.write(buf);
                }
                response.setStatus(HttpServletResponse.SC_OK);
                // response.setContentType("application/x-rdp");
                // response.setHeader("Content-Disposition",
                // "attachment; filename="+connect.getConUsername()+"-"+connect.getConVmId()+".rdp"
                // );

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                	if(out != null){
                        out.flush();
                        out.close();
                    	}
                    if(reader!=null){
                		reader.close();
                	}
                    if(read!=null){
                		read.close();
                	}
                    if(ins!=null){
                		ins.close();
                	}
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public String getRdp(Connect connect, HttpServletRequest request) {
    	_LOG.info("getRdp().......................");
        String path = request.getSession().getServletContext().getRealPath("/");
//        String filePath = path + "\\rdp.rdp";
        
        String filePath = path +File.separator+"rdp.rdp";
        
        String line = "";
        String fileContent = "";

        InputStream ins = this.getClass().getClassLoader().getResourceAsStream("/rdp.rdp");

        File file = new File(filePath);
        if (ins != null) {

            InputStreamReader read = null;
            OutputStream out = null;
            try {
                read = new InputStreamReader(ins, "UTF-8");
                BufferedReader reader = new BufferedReader(read);
                while ((line = reader.readLine()) != null) {
                    if (line.indexOf("full address:s:") != -1) {
                        line = line + connect.getConIpXAddress();
                    }
                    if (line.indexOf("username:s:") != -1) {
                        line = line + connect.getConUsername();
                    }
                    if (line.indexOf("gatewayhostname:s:") != -1) {
                        line = line + connect.getConGateway();
                    }
                    fileContent += line + "\n";
                }
                reader.close();
                read.close();

                return fileContent;

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally {
    			try {
    				if(read!=null){
					read.close();
    				}
    				if(out!=null){
					out.close();
    				}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
        }
        return null;
    }  
    
	// VALIDATION FOR SPECIAL CHARACTERS
	public boolean isValidString(String data) {    
        String blackList = "[<>;/@#$%^&+=]";
        p = Pattern.compile(blackList);
        m = p.matcher(data);
        isValid = m.find();        
        if(isValid)
        	isValid = containsScriptTag(data);
     return isValid;
    }

    // VALIDATION FOR <SCRIPT> TAG
    public boolean containsScriptTag(String data) {
        isValid = data.contains("<script>");
        return isValid;
    }


}

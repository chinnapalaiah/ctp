package com.hp.vtms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.vtms.VCloudExceptionsHandler;
import com.hp.vtms.dao.AttendeesDao;
import com.hp.vtms.dao.ConnectDao;
import com.hp.vtms.dao.EventDao;
import com.hp.vtms.dao.InstructorDao;
import com.hp.vtms.dao.TemplatesDao;
import com.hp.vtms.dao.VmsDao;
import com.hp.vtms.model.Attendees;
import com.hp.vtms.model.Connect;
import com.hp.vtms.model.Event;
import com.hp.vtms.model.Instructor;
import com.hp.vtms.model.VmModel;
import com.hp.vtms.model.Vms;
import com.hp.vtms.service.TemplatesService;

@Service
public class TemplatesServiceImpl implements TemplatesService {
	private static final Logger _LOG = LoggerFactory.getLogger(TemplatesServiceImpl.class);
	@Autowired
	private VCloudExceptionsHandler vCloudExceptionsHandler;
	@Autowired
	private TemplatesDao templatesDao;

	@Autowired
	private AttendeesDao attendeesDao;

	@Autowired
	private InstructorDao instructorDao;

	@Autowired
	private EventDao eventDao;
	
	@Autowired
	private VmsDao vmsDao;
	
	@Autowired
	private ConnectDao connectDao;

	@Transactional(readOnly = true)
	public List<VmModel> getVmsName(String eventId,String username) {
		Long eventid = (long) Integer.parseInt(eventId);

		Event event = eventDao.getEventById(eventid);
        if(event==null){
        	vCloudExceptionsHandler.customVcloudExceptions("603");
        }
		List<VmModel> vms = new ArrayList<VmModel>();
		
		Long templateId=event.getEventTempid();
	
		List<Vms> vmsList=null;
		
		
		String userNameAndUserType[] = username.split(",");
		
		if(event.getEventTiered()==0)
		{
			vmsList=vmsDao.getVmsListByTemplateId(templateId); 
		}else{
			Vms vmsModel = vmsDao.getVmByName(userNameAndUserType[0]);  // passing user name here
			vmsList = new ArrayList<Vms>();
			vmsList.add(vmsModel);
		}	// End of else	
		
		// List<Vms> vmsList=vmsDao.getVmsListByTemplateId(templateId);     // getting vmslist()				
		// This logic is to retrieve con_app_name value
		String conAppName =null;
		
		if(event.getEventTiered()!=0)  // For Non-Tiered not required...
		{
			Connect c=new Connect();
			c.setConUserId(userNameAndUserType[0].trim());
			c=connectDao.getConnectByUserId(c);	
			conAppName = c.getConAppName();			
		}
		// checking the For Tiered or Non-Tiered  0 -  Non-Tiered ; 1 - Tiered
		// For Non-Tiered we are passing userId to DB and retrieve the record, from that record we take conAppName to pass to DB
		if(event.getEventTiered()==0){
			_LOG.info("For NON-Tiered Event we are retieving Connection............");
			for(int i=0;i<vmsList.size();i++){				
				if(vmsList.get(i).getVmConsole()==true || vmsList.get(i).getVmRdp()==true){
					VmModel vmModel=new VmModel();
					vmModel.setVmRdp(vmsList.get(i).getVmRdp());
					vmModel.setVmConsole(vmsList.get(i).getVmConsole());
					vmModel.setVmName(vmsList.get(i).getVmName());
					vmModel.setIsWindows(vmsList.get(i).getVmIsWindows());
				
				//if(vmModel.getIsWindows()!=null&&vmModel.getIsWindows())
				if(vmModel.getIsWindows()!=null)
				{
					Connect conn=new Connect();
					// conn.setConUserId(username);  
					conn.setConUserId(userNameAndUserType[0].trim()); 
					conn.setConVmId((vmsList.get(i).getVmId()));
					conn=connectDao.getConnectByVmId(conn);
					vmModel.setConnect(conn);
					
					
				}
				 vms.add(vmModel);
			}
			}
		} else {
			_LOG.info("For Tiered Event we are retieving Connection............");
			for (int i = 0; i < vmsList.size(); i++) {
				if (vmsList.get(i).getVmConsole() == true
						|| vmsList.get(i).getVmRdp() == true) {
					VmModel vmModel = new VmModel();
					vmModel.setVmRdp(vmsList.get(i).getVmRdp());
					vmModel.setVmConsole(vmsList.get(i).getVmConsole());
					vmModel.setVmName(vmsList.get(i).getVmName());
					vmModel.setIsWindows(vmsList.get(i).getVmIsWindows());

					if (userNameAndUserType[1].trim().equals("instructor")) {

						//if (vmModel.getIsWindows() != null&& vmModel.getIsWindows()) {
						if (vmModel.getIsWindows() != null) {
							Connect conn = new Connect();
							// conn.setConUserId(username);						
							if (conAppName != null)
								conn.setConAppName(conAppName.trim());
							conn.setConVmId((vmsList.get(i).getVmId()));
							conn = connectDao.getConnectByAppName(conn);
							vmModel.setConnect(conn);
						}
					} else {

						//if (vmModel.getIsWindows() != null&& vmModel.getIsWindows()) {
						if (vmModel.getIsWindows() != null) {	
							Connect conn = new Connect();
							// conn.setConUserId(username);
							conn.setConUserId(userNameAndUserType[0].trim());
							conn.setConVmId((vmsList.get(i).getVmId()));
							conn = connectDao.getConnectByVmId(conn);
							vmModel.setConnect(conn);
						}
					}

					vms.add(vmModel);
				} // End of
			}
		}  // End of Else
		
		return vms;
	}
	
	@Transactional(readOnly = true)
	public List<VmModel> getVmsName(String instructorName,Attendees attendees) {
		Long eventId = 0L;
		Instructor instructor = instructorDao.login(instructorName);
		eventId = instructor.getEventId();
		Event event = eventDao.getEventById(eventId);

		List<VmModel> vms = new ArrayList<VmModel>();
		
		Long templateId=event.getEventTempid();
				
		List<Vms> vmsList = null;
	
		if (event.getEventTiered() == 0) {
			vmsList = vmsDao.getVmsListByTemplateId(templateId);
		} else {
			Vms vm = vmsDao.getVmByName(attendees.getUsername());
			vmsList = new ArrayList<Vms>();
			vmsList.add(vm);
		}
	
		
	//	List<Vms> vmsList=vmsDao.getVmsListByTemplateId(templateId);
		for(int i=0;i<vmsList.size();i++){
			VmModel vmModel=new VmModel();
			vmModel.setVmRdp(vmsList.get(i).getVmRdp());
			vmModel.setVmConsole(vmsList.get(i).getVmConsole());
			vmModel.setVmName(vmsList.get(i).getVmName());
			vmModel.setIsWindows(vmsList.get(i).getVmIsWindows());
			//if(vmModel.getIsWindows()){
				Connect conn=new Connect();
				conn.setConUserId(attendees.getUsername());
				conn.setConVmId((vmsList.get(i).getVmId()));
				conn=connectDao.getConnectByVmId(conn);
				vmModel.setConnect(conn);
			//}
		    vms.add(vmModel);
		}
		return vms;
	}


}

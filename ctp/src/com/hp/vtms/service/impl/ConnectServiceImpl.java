package com.hp.vtms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.hp.vtms.model.Vms;
import com.hp.vtms.service.ConnectService;

@Service
public class ConnectServiceImpl implements ConnectService {
	@Autowired
	private ConnectDao ConnectDao;

	@Autowired
	private VmsDao vmsDao;

	@Autowired
	private AttendeesDao attendeesDao;

	@Autowired
	private InstructorDao instructorDao;

	@Autowired
	private EventDao eventDao;

	@Autowired
	private TemplatesDao templatesDao;

	@Override
	public Connect getRdpByConn(Connect conn) {
		conn = ConnectDao.getConnectByVmId(conn);
		return conn;
	}

	public Connect getConn(String username, String vmName) {

		Vms vm = new Vms();
		Long eventId = 0L;
		Attendees attendees = attendeesDao.login(username);
		if (attendees == null) {
			Instructor instructor = instructorDao.login(username);
			eventId = instructor.getEventId();
		} else {

			eventId = attendees.getEventId();
		}
		Event event = eventDao.getEventById(eventId);

		Long templateId = event.getEventTempid();

		vm.setVmName(vmName);
		vm.setVmTempId(templateId);
		Connect conn = new Connect();
		conn.setConUserId(username);
		conn.setConTempId(templateId);
		conn.setConVmName(vmName);
		Long vmId = vmsDao.getVmID(vm);
		conn.setConVmId(vmId);
		conn = ConnectDao.getConnectByVmId(conn);
		return conn;
	}

}

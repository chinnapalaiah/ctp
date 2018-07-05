package com.hp.vtms.service.impl;

import com.hp.vtms.dao.AttendeesDao;
import com.hp.vtms.dao.ConnectDao;
import com.hp.vtms.dao.EventDao;
import com.hp.vtms.dao.InfoDao;
import com.hp.vtms.dao.InstructorDao;
import com.hp.vtms.dao.impl.EventDaoImpl;
import com.hp.vtms.model.Attendees;
import com.hp.vtms.model.Connect;
import com.hp.vtms.model.Event;
import com.hp.vtms.model.Info;
import com.hp.vtms.model.Instructor;
import com.hp.vtms.model.User;
import com.hp.vtms.service.EventService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventServiceImpl implements EventService {

	@Autowired
	private EventDao eventDao;

	@Autowired
	private AttendeesDao attendeesDao;

	@Autowired
	private InstructorDao instructorDao;
	
	@Autowired
	private ConnectDao connectDao;
	
	@Autowired
	private InfoDao infoDao;

	/**
	 * 
	 * 
	 * @param event
	 */
	public void insertEvent(Event event) {

		eventDao.insert(event);
	}

	@Transactional(readOnly = true)
	public List<Event> getAllEvents() {
		return eventDao.getAllEvent();
	}

	/**
	 * 
	 * select event by Attendees'username
	 */
	@Transactional(readOnly = true)
	public Event getEvent(User user) {

		String type=user.getType();
		String username=user.getUserName();
		Long eventId = 0l;
		if (type.equals("attendees")) {

			Attendees attendees = attendeesDao.login(username);
			eventId = attendees.getEventId();
		} else {
			Instructor instructor = instructorDao.login(username);
			eventId = instructor.getEventId();
		}

		Event event = eventDao.getEventById(eventId);

		return event;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Attendees> getMatchStudents(String username) {
		Instructor instructor = instructorDao.login(username);
		return attendeesDao.allMatchStudents(instructor.getEventId());
	}

	/**
	 * 
	 * 
	 * 
	 * @param event
	 * @return
	 */
	@Transactional(readOnly = true)
	public Event getEventById(Event event) {

		return eventDao.getEventById(event);
	}

	/**
	 * query events by attendees
	 * 
	 * @return
	 */
	public List<Event> getEventsByAttendees(Attendees attendees) {

		return null;
	}

	/**
	 * @return select event by user(instructor or attendees)
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Event> getEventsByUser(User user) {
		String type=user.getType();
		String username=user.getUserName();
		List<Event> events = null;
		Connect conn=connectDao.getTopConnectByUserName(username);
		if (!type.equals("") && type.equals("instructor")) {

			Instructor instructor = new Instructor();

			instructor = instructorDao.login(username);
			events = eventDao.getEventsByInstructor(instructor);
			
		}
		if (!type.equals("") && type.equals("attendees")) {
			Attendees attendees = new Attendees();
			attendees = attendeesDao.login(username);
			events = eventDao.getEventsByAttendees(attendees);
		}
		if(events!=null&& events.size()>0&& conn!=null){
			events.get(0).setRdpUrl(conn.getConGateway());
		}
//		else{
//			Info info=infoDao.getLinkFromInfo("connection_server");
////			events.get(0).setRdpUrl(info.getInfoValue());
//			
//		}
		return events;
	}

}

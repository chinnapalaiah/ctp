package com.hp.vtms.service.impl;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hp.vtms.dao.AttendeesDao;
import com.hp.vtms.dao.EventDao;
import com.hp.vtms.dao.InfoDao;
import com.hp.vtms.dao.InstructorDao;
import com.hp.vtms.model.Attendees;
import com.hp.vtms.model.Event;
import com.hp.vtms.model.Instructor;
import com.hp.vtms.model.User;
import com.hp.vtms.service.AttendeesService;
import com.hp.vtms.service.EventService;
import com.hp.vtms.service.InfoService;

@Service
@Transactional
public class AttendeesServiceImpl implements AttendeesService {

	@Autowired
	private AttendeesDao attendeesDao;

	@Autowired
	private InstructorDao instructorDao;
	
	@Autowired
    private InfoDao infoDao;
	
    @Autowired
    private EventDao eventDao;

	@Autowired
	private InfoService infoService;

	@Autowired
	private EventService eventService;
	/**
	 * @return if return null prove that the user didn't exist in database
	 */
	@Transactional(readOnly = true)
	@Override
	public User login(User user) {
		String password=user.getPassword();
		
		Instructor instructor = instructorDao.login(user.getUserName());
		if (instructor != null) {
			boolean flag = password.equals(instructor.getInPassword().trim());
			if (flag) {
				user.setType("instructor");
				user.setVappName(instructor.getInAppName());
                user.setFirstName(instructor.getInFrame());
                user.setLastName(instructor.getInLastName());
//				List<Info> infoList=infoDao.getInfoList();
//				user.setInfoList(infoList);
				user.setEventId(instructor.getEventId().toString());
                user.setUserEmail(instructor.getInEmailAddress());
//				user.setIsDemo(instructor.get);
				user=infoService.getInfoByType(user);
				return user;
			}
			return null;

		} else {
			Attendees attendees = attendeesDao.login(user.getUserName());
			if (attendees != null) {
				boolean flag = password.equals(attendees.getPassword().trim());
				if (flag) {

					user.setType("attendees");
					user.setVappName(attendees.getAttAppName());
					user.setEventId(attendees.getEventId().toString());
                    user.setFirstName(attendees.getFirstName());
                    user.setLastName(attendees.getLastName());
//						user.setInfoList(infoDao.getInfoList());
					user.setIsDemo(attendees.getIsDemo());
                    user.setUserEmail(attendees.getEmailAddress());
					user=infoService.getInfoByType(user);
					if(Boolean.valueOf(true).equals(attendees.getIsDemo())) {
						if(Long.valueOf(0).equals(attendees.getEventId())){
							return user;
						}else{
							return null;
						}
					}else {
						if(StringUtils.isEmpty(attendees.getUserActDate()) || StringUtils.isEmpty(attendees.getUserExpDate()) ){
							user.setSystemError("eventDateError");
							return user;
						}

						List<Event> events=eventService.getEventsByUser(user);
						if(events == null || events.size() == 0){
							return null;
						}
						return user;
					}
				}
				return null;
			}
			return null;

		}
	}
	
	@Transactional(readOnly = true)
	public String getInstructorName(String attendeesName)
	{
		Attendees attendees=attendeesDao.login(attendeesName);
//		String instructorName=instructorDao.getNameByEventId(attendees.getEventId());
		Event event = eventDao.getEventById(attendees.getEventId());
        String instructorId = event.getInstructor();
        String instructorName = instructorDao.getInstructorNameById(instructorId);
		return instructorName;
	}
	@Transactional(readOnly = true)
	public List<Attendees> getAttendeesByInstructorName(String username)
	{
		Instructor in=instructorDao.login(username);
	    Long eventId=in.getEventId();
	    List<Attendees> attendeesList= attendeesDao.allMatchStudents(eventId);
		return attendeesList;
	}
	
	
	public Attendees getAttendeesByName(String username){
		Attendees attendees = attendeesDao.login(username);
		
		return attendees;
		
	}
	
}

package com.hp.vtms.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.vtms.model.User;
import com.hp.vtms.service.AttendeesService;
import com.hp.vtms.service.EventService;
import com.hp.vtms.util.ApplicationContainer;

@Controller
@RequestMapping("participant")
public class ParticipantController {
	private static final Logger _LOG = LoggerFactory.getLogger(ParticipantController.class);
	private static Pattern p = null;
	private static Matcher m = null;
	private static boolean isValid = false;
	@Autowired
	private AttendeesService attendeesService;
	
	@Autowired
	private EventService eventService;
	
	@RequestMapping()
	@ResponseBody
	public String setParticipantController(
			HttpServletRequest request,
			@RequestParam(value = "participantValue", required = false) String participantUrl) {
		
		boolean status = isValidURL(participantUrl);
		_LOG.info("The Status is ----------> "+status);
		if(status)
			return "errors";
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String eventId = user.getEventId();
		// List<Attendees>
		// attendeesList=eventService.getMatchStudents(user.getUserName());
		ApplicationContainer applicationContainer = ApplicationContainer
				.getInstance();
		String key = (String) applicationContainer.getObject(eventId);
		applicationContainer.setObject(eventId, participantUrl);
		user.setParticipantUrl(participantUrl);
		return "OK";
	}

	// VALIDATION FOR SPECIAL CHARACTERS
	public static boolean isValidURL(String data) {
		// String blackList = "[<>\";@#$%^&+=]()\'";
		String blackList = "[<>\";@#()$%^&+=\']";
		p = Pattern.compile(blackList);
		m = p.matcher(data);
		isValid = m.find();
		return isValid;
	}
}

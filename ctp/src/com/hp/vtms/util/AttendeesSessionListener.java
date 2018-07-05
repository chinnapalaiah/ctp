package com.hp.vtms.util;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hp.vtms.model.Attendees;
import com.hp.vtms.model.VAppModel;
import com.hp.vtms.model.VmModel;
import com.hp.vtms.service.AttendeesService;
import com.hp.vtms.service.InstructorService;
import com.hp.vtms.service.TemplatesService;

@Component
public class AttendeesSessionListener implements HttpSessionListener{
	
	@Autowired
	private InstructorService instructorService;

	@Autowired
	private AttendeesService attendeesService;

	@Autowired
	private TemplatesService templatesService;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

		HttpSession session = se.getSession();
		String username=(String) session.getAttribute("username");
		session.setAttribute(username, false);

	}

}

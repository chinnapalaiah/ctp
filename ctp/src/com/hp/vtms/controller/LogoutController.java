package com.hp.vtms.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hp.vtms.service.AttendeesService;
import com.hp.vtms.service.InstructorService;
import com.hp.vtms.service.TemplatesService;

@Controller
@RequestMapping("logout")
public class LogoutController {
	@Autowired
	private InstructorService instructorService;

	@Autowired
	private AttendeesService attendeesService;

	@Autowired
	private TemplatesService templatesService;

	@RequestMapping
	public void logout(HttpServletRequest request,HttpServletResponse response) {

		HttpSession session = request.getSession();
        // session.removeAttribute("user");
        session.invalidate();
		try {
			response.sendRedirect("login.do");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		response.Redirect("/User/Edit");
//		return "login";

	}
	
	
	

}

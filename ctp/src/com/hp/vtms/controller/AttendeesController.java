package com.hp.vtms.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.vtms.model.Event;
import com.hp.vtms.model.User;
import com.hp.vtms.service.AttendeesService;
import com.hp.vtms.service.EventService;
import com.hp.vtms.util.TraceRecord;

@Controller
@RequestMapping("attendees")
public class AttendeesController {

    private static Logger _LOG = LoggerFactory.getLogger(AttendeesController.class);

    Boolean booleantest = Boolean.TRUE;
	@Autowired
	private AttendeesService attendeesService;

    @Autowired
    private EventService eventService;

	@RequestMapping("login")
	@ResponseBody
	public String login(HttpServletRequest request) {

        _LOG.info("longin main thread start");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpSession oldSession = request.getSession();
				Map<String,Object> values = getSessionValues(oldSession);
				HttpSession session = null;
				
				if(oldSession!=null)
				{
					oldSession.invalidate();
					session = request.getSession(true);
					
					for (Map.Entry<String, Object> entry : values.entrySet())
					{
						session.setAttribute(entry.getKey(),entry.getValue());			    
					}
			}	
				
	    String clientIP = request.getParameter("clientIP");
        session.setAttribute("clientIP", clientIP);
		String password = request.getParameter("pass");
		//KeyPairData keyPairData=(KeyPairData) session.getAttribute("keyPairData");
        _LOG.info("---------------------------clientIP:" + clientIP);
      //  _LOG.info("---------------------------request Password:" + password);
		User user = new User(); 
		user.setUserName(request.getParameter("username"));
		
		user.setOs(request.getParameter("os"));
		//_LOG.info("user Password:" + password);
		//_LOG.info("RSA Password:" + password);
		user.setPassword(password);
		User newUser=null;

        _LOG.info("get user start");
		newUser = attendeesService.login(user);
        _LOG.info("get user end");
        String result = "";
		if (newUser == null) {
            result = "acountExp";
            // return "acountExp";
		} else {
			if(!StringUtils.isEmpty(newUser.getSystemError())) {
                result = newUser.getSystemError();
                // return newUser.getSystemError();
            } else {
                newUser.setIsFirstTime(true);
                _LOG.info("the current user is:" + newUser.getUserName());

    	        session.setAttribute("user", newUser);

                _LOG.info("addTraceRecord start");


                List<Event> events = eventService.getEventsByUser(newUser);
                TraceRecord traceRecord = new TraceRecord();
                traceRecord.addTraceRecord(newUser, events, session);

                _LOG.info("addTraceRecord end");

                result = "success";
            }
        }
        String callBack = request.getParameter("jsoncallback");
        String resultJsonp = callBack + "({ result:'" + result + "'} )";

        _LOG.info("longin main thread end");

        return resultJsonp;
    }

	public Map<String,Object> getSessionValues(HttpSession session)
	{
		String names[]=session.getValueNames();
		Map<String,Object> map = new HashMap<String,Object>();
		for(String name : names){
			map.put(name, session.getValue(name));
		}
		return map;
	}

}

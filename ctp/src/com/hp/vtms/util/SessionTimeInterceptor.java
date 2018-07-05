package com.hp.vtms.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionTimeInterceptor extends HandlerInterceptorAdapter{
	
	private static Logger _LOG = LoggerFactory.getLogger(SessionTimeInterceptor.class);
	private static int remainder;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String flushType=request.getParameter("flushType");
		HttpSession session=request.getSession();
		if((request.getRequestURI().contains("getStatusOfVm")||request.getRequestURI().contains("getVmsHtml")||request.getRequestURI().contains("initialStudentmgm")) && flushType!=null && flushType.equals("autoFlush")){
				 remainder=(Integer) session.getAttribute("remainder");
				Long lastTime=session.getLastAccessedTime();
				Long current=System.currentTimeMillis();
				int time=(int) ((current-lastTime)/1000.0);
				remainder=remainder-time;
				if(remainder!=0){
					session.setMaxInactiveInterval(remainder);
					session.setAttribute("remainder", remainder);
				}
				if(time>=remainder){
					_LOG.info("session is time out");
					//response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					session.invalidate();
//					String path = request.getContextPath();
//					String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//					response.sendRedirect(basePath+"login.do");
//					session.invalidate();
					return false;
				}
		}else{
			Long lastTime=session.getLastAccessedTime();
			Long current=System.currentTimeMillis();
			int time=(int) ((current-lastTime)/1000);
			//int remainder=30*60-time;
			int remainder=540*60-time;
			session.setAttribute("remainder", remainder);
			//session.setMaxInactiveInterval(30*60);
			session.setMaxInactiveInterval(540*60);
					
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}




}

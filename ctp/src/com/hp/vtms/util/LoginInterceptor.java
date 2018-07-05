package com.hp.vtms.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hp.vtms.model.User;
import com.hp.vtms.service.WebstatsvmService;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static Logger _LOG = LoggerFactory.getLogger(LoginInterceptor.class);
	
    @Autowired
    private WebstatsvmService webstatsvmService;


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();

		String username="" ;
		User user=(User) session.getAttribute("user");
		if(user!=null){
		username=user.getUserName();
		}
		Long s=session.getLastAccessedTime();
        _LOG.info("----------------------request url" + request.getRequestURI());
		_LOG.info("Successful User:" + username);
		
		if(username!=null&&request.getRequestURI().contains("logout")){
			_LOG.info("Deleting the user records from webstats table....");
			webstatsvmService.deleteAllStudentRecords(username);
			_LOG.info("Records deleted from webstats table..............");
			
		}
		if (username != null&&!username.equals("")) {
			if (request.getRequestURI().contains("login")) {
				response.sendRedirect("event.do");
				return false;
			}
			_LOG.info("url:"+request.getRequestURI());
			return true;
		}
		if (request.getRequestURI().contains("login")) {

            if (request.getRequestURI().contains("ctp/login.do")) {
                String secheme = request.getScheme();
                if ("https".equalsIgnoreCase(secheme)) {
                    String path = request.getContextPath();
                    String basePath = "http" + "://" + request.getServerName() + path
                        + "/";
                    response.sendRedirect(basePath + "login.do");
                }
            }
			return true;
		}

		else {
			
			if ((request.getHeader("accept")!=null&&request.getHeader("accept").indexOf("application/json") > -1)
					|| (request.getHeader("X-Requested-With") != null
					&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return false;
			} else {

				String path = request.getContextPath();
                String basePath = "http" + "://" + request.getServerName() + path + "/";
				response.sendRedirect(basePath+"login.do");

				return false;
			}
		}
	}
}

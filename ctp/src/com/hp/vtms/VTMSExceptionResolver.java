package com.hp.vtms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class VTMSExceptionResolver extends SimpleMappingExceptionResolver {

	/**
	 * logger
	 */
	private final Logger lOGGER = LoggerFactory.getLogger(VTMSExceptionResolver.class);

	/**
	 * override method add log
	 */
	@Override
	protected final ModelAndView doResolveException(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler, final Exception ex) {
		lOGGER.error(ex.getMessage(), ex);
		request.setAttribute("errorMsg", getErrorMsg(ex));
		if(getErrorMsg(ex)!=null && !getErrorMsg(ex).equals("")){
			if (!(!(request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With")) != null
					&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				
				if (request.getRequestURI().contains("initialStudentmgm")) {
					return super.getModelAndView("error", ex, request);
				} else if (request.getRequestURI().contains("getVmsHtml")) {
					return super.getModelAndView("error", ex, request);
				}
			}
			
			if(request.getRequestURI().contains("trainingmgm.do")||request.getRequestURI().contains("studentmgm.do"))
			{
				return super.getModelAndView("error", ex, request);
			}
		}else{
			request.setAttribute("errorMsg", "Server temporary unavailable: Internal Server Error!!");
			return super.getModelAndView("error", ex, request);
		}
		
		return super.doResolveException(request, response, handler, ex);
	}

	/**
	 * override method return json
	 */
	@Override
	protected final ModelAndView getModelAndView(final String viewName, final Exception ex,
			final HttpServletRequest request) {

		// deal with the vcloud exception
		request.setAttribute("errorCode", getErrorMsg(ex));
		return super.getModelAndView(viewName, ex, request);
	}

	private String getErrorMsg(Exception e) {
		String errorMsg = "";
		Throwable cause = e.getCause();
		if (cause != null && !StringUtils.isEmpty(cause.getMessage())) {
			errorMsg = e.getCause().getMessage();
		} else if (!StringUtils.isEmpty(e.getMessage())) {
			errorMsg = e.getMessage();
		}
		return errorMsg;
	}
}

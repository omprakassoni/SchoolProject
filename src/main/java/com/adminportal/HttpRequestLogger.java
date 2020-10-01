package com.adminportal;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Component
public class HttpRequestLogger extends AbstractRequestLoggingFilter{

	org.slf4j.Logger logger=LoggerFactory.getLogger(HttpRequestLogger.class);
	
	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		
		logger.info("{},{},{},{}",request.getMethod(),request.getRequestURL(),request.getRemoteAddr(),request.getHeader("User-Agent"));
		
	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		// TODO Auto-generated method stub
		
	}

}

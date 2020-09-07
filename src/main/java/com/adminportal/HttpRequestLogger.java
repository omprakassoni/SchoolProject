package com.adminportal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

public class HttpRequestLogger extends AbstractRequestLoggingFilter{

	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		
		System.out.println(request.getRemoteAddr());
		System.out.println(message);
		
	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		// TODO Auto-generated method stub
		
	}

}

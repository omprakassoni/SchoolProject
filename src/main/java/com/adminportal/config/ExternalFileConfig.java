package com.adminportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ExternalFileConfig implements WebMvcConfigurer{
	
	@Autowired
	private Environment env;
		
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	
		
		registry.addResourceHandler("/files/**")
		.addResourceLocations("file:"+env.getProperty("spring.applicationexternalPath.name"));
		
		System.out.println("Config side");
		
	}
	
	

}

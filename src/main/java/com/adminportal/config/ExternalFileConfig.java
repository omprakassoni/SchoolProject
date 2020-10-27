package com.adminportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class is responsible for configuring external path(outside project Directory) to save resource like file and videos
 * to be used in project
 * @author om prakash
 *
 */
@Configuration
public class ExternalFileConfig implements WebMvcConfigurer{
	
	@Autowired
	private Environment env;
	
	
	/**
	 *This method is to register path which can be used for saving resources outside the project Directory
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	
		
		registry.addResourceHandler("/files/**")
		.addResourceLocations("file:"+env.getProperty("spring.applicationexternalPath.name"));
		
		System.out.println("Config side");
		
	}
	
	

}

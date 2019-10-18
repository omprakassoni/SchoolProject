/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.service;

import java.util.List;

import com.adminportal.content.Class;

public interface ClassService {
	
	Class findByClassName(String className);
	
	List<Class> findAll();
	
	Class save(Class temp);
	
	

}

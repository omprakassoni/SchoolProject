/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Class interacting with its repository for database operation
 */

package com.adminportal.service;

import java.util.List;

import com.adminportal.content.Class;

public interface ClassService {
	
	Class findByClassName(int className) throws Exception;
	
	List<Class> findAll();
	
	Class save(Class temp);
	
	int countRow();
	
	

}

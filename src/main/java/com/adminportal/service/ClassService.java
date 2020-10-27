/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Class interacting with its repository for database operation
 */

package com.adminportal.service;

import java.util.List;

import com.adminportal.content.Class;

/**
 * This interface has all the method declaration related to Class's database operation 
 * @author om prakash
 *
 */
public interface ClassService {
	
	/**
	 * Find class object given class name as input parameter
	 * @param className name of class
	 * @return class object
	 * @throws Exception
	 */
	Class findByClassName(int className) throws Exception;
	
	/**
	 * Finds all the class object present in database
	 * @return list of class object
	 */
	List<Class> findAll();
	
	/**
	 * Persist class object in database
	 * @param temp class object
	 * @return class object
	 */
	Class save(Class temp);
	
	/**
	 * count total number of class object present in database
	 * @return integer value
	 */
	int countRow();
	
	

}

/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Event interacting with its repository for database operation
 */
package com.adminportal.service;

import java.sql.Date;
import java.util.List;

import com.adminportal.content.Events;

public interface EventService {
	
	int getCount();
	void save(Events temp);
	
	List<Events> findAll();

	List<Events> getAllEventdata();
	
	Events getbyid(int id);
	
	boolean updateEvent(String head,String desc,Date date,String poster_path,int id);
	
	
}

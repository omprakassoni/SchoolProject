/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Event interacting with its repository for database operation
 */
package com.adminportal.service;

import java.sql.Date;
import java.util.List;

import com.adminportal.content.ConceptMap;
import com.adminportal.content.Events;

import antlr.debug.Event;

public interface EventService {
	
	int getCount();
	Events save(Events temp);
	
	List<Events> findAll();

	List<Events> getAllEventdata();
	
	Events getbyid(int id);
	
	boolean updateEvent(String head,String desc,Date startDate,Date enddate,Date regStart,Date regEnd,String coordName,String poster_Path,int id) throws Exception;
	
	void deleteEvent(Events event);
}

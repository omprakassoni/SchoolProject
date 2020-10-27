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
/**
 * This interface has all the method declaration related to Event database operation
 * @author om prakash
 *
 */
public interface EventService {
	/**
	 * Count total number of event record from database
	 * @return integer value
	 */
	int getCount();
	
	/**
	 * Persist event object into database
	 * @param temp Event object
	 * @return Event object
	 */
	Events save(Events temp);
	
	/**
	 * List all the event object from database
	 * @return list of event object
	 */
	List<Events> findAll();

	/**
	 * Find all the event from database in descending order based on start date of event
	 * @return list of event object
	 */
	List<Events> getAllEventdata();
	
	/**
	 * Find event object using event ID
	 * @param id Event ID
	 * @return Event object
	 */
	Events getbyid(int id);
	
	/**
	 * update Event attributes in database given Event ID as one of the input parameter
	 * @param head Headline
	 * @param desc Description
	 * @param startDate Start date of event
	 * @param enddate End date of event
	 * @param regStart Start date of registration
	 * @param regEnd End date of registration
	 * @param coordName Coordinator name
	 * @param poster_Path path of file saved
	 * @param id Event ID
	 * @return boolean value based on successful operation or not ?
	 * @throws Exception
	 */
	boolean updateEvent(String head,String desc,Date startDate,Date enddate,Date regStart,Date regEnd,String coordName,String poster_Path,int id) throws Exception;
	
	/**
	 * Delete event object from database
	 * @param event event object
	 */
	void deleteEvent(Events event);
}

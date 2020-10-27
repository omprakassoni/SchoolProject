/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */
package com.adminportal.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.Events;

/**
 * This Interface Extend JpaRepository to handle all database operation related to Event data
 * @author om prakash
 *
 */
public interface EventsRepository extends JpaRepository<Events, Integer> {
	
	/**
	 * Find all the event from database in descending order based on start date of event
	 * @return List of Event
	 */
	@Query("from Events e order by e.dateToHappenStart desc")  // fetching list of event 
	List<Events> getAllEvent();

	/**
	 * Update the event data given eventID as one of the input parameter
	 * @param head headline of event to be updated
	 * @param desc description to be updated
	 * @param startDate start date of event to be updated
	 * @param enddate end date of event to be updated
	 * @param regStart Start date of registration to be updated
	 * @param regEnd end date of registration to be updated
	 * @param coordName Coordinator name to be updated
	 * @param poster_Path Image of Event linked to be updated
	 * @param id Event ID
	 * @return number of record updated
	 */
	@Modifying
	@Query("update Events set headline=?1,description=?2,dateToHappenStart=?3,dateToHappenEnd=?4,registStart=?5,registEnd=?6,coordName=?7,potser_path=?8 where eventId=?9") // updating event information.
	int updateEvent(String head,String desc,Date startDate,Date enddate,Date regStart,Date regEnd,String coordName,String poster_Path,int id);

	
	@Modifying
	@Query("delete from Events U where U.eventId=?1")
	@Transactional
	void deleteEvent(int eventID);

}

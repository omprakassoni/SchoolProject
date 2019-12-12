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

import com.adminportal.content.Events;

public interface EventsRepository extends JpaRepository<Events, Integer> {
	
	@Query("from Events e order by e.dateToHappen desc")  // fetching list of event 
	List<Events> getAllEvent();

	@Modifying
	@Query("update Events set headline=?1,description=?2,dateToHappen=?3 where eventId=?4") // updating event information.
	int updateEvent(String head,String desc,Date date,int id);
}

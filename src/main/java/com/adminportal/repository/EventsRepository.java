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
	
	@Query("from Events e order by e.dateToHappenStart desc")  // fetching list of event 
	List<Events> getAllEvent();

	@Modifying
	@Query("update Events set headline=?1,description=?2,dateToHappenStart=?3,dateToHappenStart=?4,registStart=?5,registEnd=?6,coordName=?7,potser_path=?8 where eventId=?9") // updating event information.
	int updateEvent(String head,String desc,Date startDate,Date enddate,Date regStart,Date regEnd,String coordName,String poster_Path,int id);
}

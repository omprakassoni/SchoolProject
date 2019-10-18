package com.adminportal.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.adminportal.content.Events;

public interface EventsRepository extends CrudRepository<Events, Integer> {
	
	@Query("from Events e order by e.dateToHappen desc")
	List<Events> getAllEvent();

	@Modifying
	@Query("update Events set headline=?1,description=?2,dateToHappen=?3 where eventId=?4")
	int updateEvent(String head,String desc,Date date,int id);
}

/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of Event Service Interface to define method for database operation.
 */
package com.adminportal.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.Events;
import com.adminportal.repository.EventsRepository;
import com.adminportal.service.EventService;

@Service
public class EventServiceImpl implements EventService{

	@Autowired
	EventsRepository eventRepo;
	
	@Override
	public int getCount() {
		
		return (int) eventRepo.count();
	}

	@Override
	public Events save(Events temp) {
		
		return eventRepo.save(temp);
		
	}

	@Override
	public List<Events> findAll() {
		
		return (List<Events>) eventRepo.findAll();
	}

	@Override
	public List<Events> getAllEventdata() {
		
		return eventRepo.getAllEvent();
	}

	@Override
	public Events getbyid(int id) {
		Optional<Events> local=eventRepo.findById(id);
		return local.get();
	}

	@Override
	@Transactional
	public boolean updateEvent(String head,String desc,Date startDate,Date enddate,Date regStart,Date regEnd,String coordName,String poster_Path,int id) {
		int status=eventRepo.updateEvent(head, desc,startDate,enddate,regStart,regEnd,coordName,poster_Path,id );
		if(status>0) {
			return true;
		}else {
		return false;
		}
	}

	@Override
	public void deleteEvent(Events event) {
		
		eventRepo.deleteEvent(event.getEventId());
		
	}

}

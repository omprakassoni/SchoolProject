package com.adminportal.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.adminportal.content.Events;
import com.adminportal.repository.EventsRepository;
import com.spoken.Utility.ServiceUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventServiceTest {
	
	@Autowired
	private EventService evenntService;
	
	@MockBean
	private EventsRepository eventRepo;
	
	@Test
	public void saveEvent() {
		
		Events event=new Events();
		event.setCoordName("OM");
		event.setDateAdded(ServiceUtility.getCurrentTime());
		event.setDescription("sdkfbs");
		event.setLocation("mumbai");
		event.setMode("online");
		event.setEventId(1);
		event.setHeadline("smdvsadvad");
		
		when(eventRepo.save(event)).thenReturn(event);
		
		assertEquals(event, evenntService.save(event));
		
	}
	
	@Test
	public void updateEventTest() throws Exception {
		
		Events event=new Events();
		event.setCoordName("OM");
		event.setDateAdded(ServiceUtility.getCurrentTime());
		event.setDescription("sdkfbs");
		event.setLocation("mumbai");
		event.setMode("online");
		event.setEventId(1);
		event.setHeadline("smdvsadvad");
		
		when(eventRepo.save(event)).thenReturn(event);
		
		assertEquals(event, evenntService.save(event));
		
		int eventValue=0;
		boolean eventBoolean=false;
		
		when(eventRepo.updateEvent("dfhsdhf", "sdfhvdshfvs", null, null, null, null, "dfhsdvf", "zdfhsdfhds", 1)).thenReturn(eventValue);
		
		if(eventValue>0) {
			eventBoolean=true;
		}
		
		assertEquals(eventBoolean, evenntService.updateEvent("dfhsdhf", "sdfhvdshfvs", null, null, null, null, "dfhsdvf", "zdfhsdfhds", 1));
	}
	

}

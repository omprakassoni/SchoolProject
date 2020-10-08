package com.adminportal.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.repository.LessonPlanRepository;
import com.adminportal.repository.PhetsRepository;
import com.spoken.Utility.ServiceUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhetServiceTest {
	
	@Autowired
	private PhetsService phetService;
	
	@MockBean
	private PhetsRepository phetrepo;
	
	@Test
	public void savePhetTest() {
		
		Phets phet=new Phets(1, "Phet", ServiceUtility.getCurrentTime()	, ServiceUtility.getCurrentTime(), "dfhdsfsdfgsdfg", "zfhgdfsf", "zdfjgsduf", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(phetrepo.save(phet)).thenReturn(phet);
		
		
	}
	
	@Test
	public void UpdatePhetTest() {
		
		Phets phet=new Phets(1, "Phet", ServiceUtility.getCurrentTime()	, ServiceUtility.getCurrentTime(), "dfhdsfsdfgsdfg", "zfhgdfsf", "zdfjgsduf", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(phetrepo.save(phet)).thenReturn(phet);
		
		int updatePhetValue=0;
		boolean updatePhetBoolean=false;
		
		when(phetrepo.updatePhet("afsf", "sdfsfdgsdf", ServiceUtility.getCurrentTime(), "sdfsdf", 1)).thenReturn(updatePhetValue);
		
		if(updatePhetValue>0) {
			updatePhetBoolean=true;
		}
		
		assertEquals(updatePhetBoolean, phetService.updatePhet("afsf", "sdfsfdgsdf", ServiceUtility.getCurrentTime(), "sdfsdf", 1));
	}
	
	@Test
	public void UpdatePhetAcceptedByAdminTest() {
		
		Phets phet=new Phets(1, "Phet", ServiceUtility.getCurrentTime()	, ServiceUtility.getCurrentTime(), "dfhdsfsdfgsdfg", "zfhgdfsf", "zdfjgsduf", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(phetrepo.save(phet)).thenReturn(phet);
		
		int updatePhetValue=0;
		boolean updatePhetBoolean=false;
		
		when(phetrepo.EnableAcceptedByAdminContent(1, ServiceUtility.getCurrentTime(), 1)).thenReturn(updatePhetValue);
		
		if(updatePhetValue>0) {
			updatePhetBoolean=true;
		}
		
		assertEquals(updatePhetBoolean, phetService.EnableAcceptedByAdminPhetContent(1, 1));
	}
	
	@Test
	public void EnableDisablePhetTest() {
		
		Phets phet=new Phets(1, "Phet", ServiceUtility.getCurrentTime()	, ServiceUtility.getCurrentTime(), "dfhdsfsdfgsdfg", "zfhgdfsf", "zdfjgsduf", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(phetrepo.save(phet)).thenReturn(phet);
		
		int updatePhetValue=0;
		boolean updatePhetBoolean=false;
		
		when(phetrepo.EnablePhetContent(1, 1)).thenReturn(updatePhetValue);
		
		if(updatePhetValue>0) {
			updatePhetBoolean=true;
		}
		
		assertEquals(updatePhetBoolean, phetService.EnablePhetContent(1, 1));
	}

}

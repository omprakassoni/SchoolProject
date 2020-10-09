package com.adminportal.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.adminportal.content.Tutorial;
import com.adminportal.repository.TutorialRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TutorialServiceTest {

	@Autowired
	private TutorialService tutorialService;
	
	@MockBean
	private TutorialRepository tutoRepo;
	
	@Test
	public void saveTestimonialTest() {
		
		Tutorial tut=new Tutorial(1, 1, 1, 1, 1, null, null);
		
		when(tutoRepo.save(tut)).thenReturn(tut);
		
		
	}
	
	@Test
	public void enableDisableTestimonialTest() {
		
		Tutorial tut=new Tutorial(1, 1, 1, 1, 1, null, null);
		
		when(tutoRepo.save(tut)).thenReturn(tut);
		
		int updateTutValue=0;
		boolean updateTutBoolean=false;
		
		when(tutoRepo.enableDisableTutorial(1, 1)).thenReturn(updateTutValue);
		
		if(updateTutValue>0) {
			updateTutBoolean=true;
		}
		
		assertEquals(updateTutBoolean, tutorialService.enableDisableTutorial(1, 1));
		
		
	}
}

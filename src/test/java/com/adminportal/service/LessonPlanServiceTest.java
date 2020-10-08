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
import com.adminportal.repository.LessonPlanRepository;
import com.spoken.Utility.ServiceUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LessonPlanServiceTest {
	
	@Autowired
	private LessonPlanService lessonPlanservice;
	
	@MockBean
	private LessonPlanRepository lessonRepo;
	
	@Test
	public void saveLessonPlanTest() {
		
		LessonPlan lesson=new LessonPlan(1, "Lesson", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "cvdfgdfgdf", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(lessonRepo.save(lesson)).thenReturn(lesson);
		
		assertEquals(0, lessonPlanservice.save(lesson));
	}
	
	@Test
	public void UpdateLessonTest() {
		
		LessonPlan lesson=new LessonPlan(1, "Lesson", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "cvdfgdfgdf", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(lessonRepo.save(lesson)).thenReturn(lesson);
		
		int updateLessonValue=0;
		boolean updateLessonBoolean=false;
		
		when(lessonRepo.updateLessonPlan(ServiceUtility.getCurrentTime(), "sampkl path", 1)).thenReturn(updateLessonValue);
		
		if(updateLessonValue>0) {
			updateLessonBoolean=true;
		}
		
		assertEquals(updateLessonBoolean, lessonPlanservice.updateLessonPlan(ServiceUtility.getCurrentTime(), "sampkl path", 1));
		
	}
	
	@Test
	public void UpdateLessonAcceptedByAdminTest() {
		
		LessonPlan lesson=new LessonPlan(1, "Lesson", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "cvdfgdfgdf", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(lessonRepo.save(lesson)).thenReturn(lesson);
		
		int updateLessonValue=0;
		boolean updateLessonBoolean=false;
		
		when(lessonRepo.EnableAcceptedByAdminContent(1, ServiceUtility.getCurrentTime(), 1)).thenReturn(updateLessonValue);
		
		if(updateLessonValue>0) {
			updateLessonBoolean=true;
		}
		
		assertEquals(updateLessonBoolean, lessonPlanservice.EnableAcceptedByAdminLessonPlanContent(1, 1));
		
	}
	
	@Test
	public void EnableDisableLessonTest() {
		
		LessonPlan lesson=new LessonPlan(1, "Lesson", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "cvdfgdfgdf", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(lessonRepo.save(lesson)).thenReturn(lesson);
		
		int updateLessonValue=0;
		boolean updateLessonBoolean=false;
		
		when(lessonRepo.EnableLessonPlanContent(1, 1)).thenReturn(updateLessonValue);
		
		if(updateLessonValue>0) {
			updateLessonBoolean=true;
		}
		
		assertEquals(updateLessonBoolean, lessonPlanservice.EnableLessonPlanContent(1, 1));
		
	}

}

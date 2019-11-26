/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of Lesson Service Interface to define method for database operation.
 */

package com.adminportal.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;
import com.adminportal.repository.LessonPlanRepository;
import com.adminportal.service.LessonPlanService;
import com.spoken.Utility.ServiceUtility;

@Service
public class LessonPlanServiceImpl implements LessonPlanService{

	@Autowired
	private LessonPlanRepository lessonRepo;
	@Override
	public List<LessonPlan> findAll() {
	
		return lessonRepo.findAllBytype("Lesson");
	}
	@Override
	public void deleteLessonById(int id) {
		Optional<LessonPlan> local=lessonRepo.findById(id);
		LessonPlan temp=local.get();
		lessonRepo.deleteById(temp.getLessonPlanId());
		
	}
	@Override
	public int countRow() {
	
		return (int) lessonRepo.count();
	}
	
	@Override
	@Transactional
	public boolean EnableLessonPlanContent(int status, int id) {
		
		int status1=lessonRepo.EnableLessonPlanContent(status, id);
		if(status1>0) {
			return true;
		}else {
			return false;
		}
		
	}
	@Override
	public LessonPlan findById(int id) {
		
		Optional<LessonPlan> lesson=lessonRepo.findById(id);
		
		return lesson.get();
	}
	@Override
	public List<LessonPlan> findAllByTopic(Topic topic) {
		
		List<LessonPlan> localLesson=lessonRepo.findAllBytopicAndType(topic, "Lesson");
	
		return localLesson;
	}
	@Override
	public List<LessonPlan> findALlByUser(User usr) {
		
		return lessonRepo.findAllByuser(usr,"Lesson");
	}
	
	@Override
	@Transactional
	public boolean updateLessonPlan(Timestamp dat, String lessonPath, int id) {
		int Status=lessonRepo.updateLessonPlan(dat, lessonPath, id);
		if(Status>0) {
			return true;
		}else {
			return false;
		}
		
		
	}
	@Override
	@Transactional
	public boolean EnableAcceptedByAdminLessonPlanContent(int status, int id) {
		int status1=lessonRepo.EnableAcceptedByAdminContent(status,ServiceUtility.getCurrentTime(), id);
		if(status1>0) {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public List<LessonPlan> findAllByTopicAndStatus(Topic topic) {
		
		return lessonRepo.findAllByTopicAndStatus(topic, 1,"Lesson");
	}

}

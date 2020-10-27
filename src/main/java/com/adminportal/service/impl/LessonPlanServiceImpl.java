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
/**
 * Default implementation of the {@link com.adminportal.service.LessonPlanService} interface.  
 * @author om prakash
 *
 */
@Service
public class LessonPlanServiceImpl implements LessonPlanService{

	@Autowired
	private LessonPlanRepository lessonRepo;
	
	/**
	 * @see com.adminportal.service.LessonPlanService#findAll()
	 */
	@Override
	public List<LessonPlan> findAll() {
	
		return lessonRepo.findAllBytype("Lesson");
	}
	
	/**
	 * @see com.adminportal.service.LessonPlanService#deleteLessonById(int id)
	 */
	@Override
	public void deleteLessonById(int id) {
		Optional<LessonPlan> local=lessonRepo.findById(id);
		LessonPlan temp=local.get();
		lessonRepo.deleteById(temp.getLessonPlanId());
		
	}
	
	/**
	 * @see com.adminportal.service.LessonPlanService#countRow()
	 */
	@Override
	public int countRow() {
	
		return (int) lessonRepo.count();
	}
	
	/**
	 * @see com.adminportal.service.LessonPlanService#EnableLessonPlanContent(int status, int id)
	 */
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
	
	/**
	 * @see com.adminportal.service.LessonPlanService#findById(int id)
	 */
	@Override
	public LessonPlan findById(int id) {
		
		Optional<LessonPlan> lesson=lessonRepo.findById(id);
		
		return lesson.get();
	}
	
	/**
	 * @see com.adminportal.service.LessonPlanService#findAllByTopic(Topic topic)
	 */
	@Override
	public List<LessonPlan> findAllByTopic(Topic topic) {
		
		List<LessonPlan> localLesson=lessonRepo.findAllBytopicAndType(topic, "Lesson");
	
		return localLesson;
	}
	
	/**
	 * @see com.adminportal.service.LessonPlanService#findALlByUser(User usr)
	 */
	@Override
	public List<LessonPlan> findALlByUser(User usr) {
		
		return lessonRepo.findAllByuser(usr,"Lesson");
	}
	
	/**
	 * @see com.adminportal.service.LessonPlanService#updateLessonPlan(Timestamp dat, String lessonPath, int id)
	 */
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
	
	/**
	 * @see com.adminportal.service.LessonPlanService#EnableAcceptedByAdminLessonPlanContent(int status, int id)
	 */
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
	
	/**
	 * @see com.adminportal.service.LessonPlanService#findAllByTopicAndStatus(Topic topic)
	 */
	@Override
	public List<LessonPlan> findAllByTopicAndStatus(Topic topic) {
		
		return lessonRepo.findAllByTopicAndStatus(topic, 1,"Lesson");
	}
	
	/**
	 * @see com.adminportal.service.LessonPlanService#save(LessonPlan temp)
	 */
	@Override
	public int save(LessonPlan temp) {
		lessonRepo.save(temp);
		return 0;
	}
	
	/**
	 * @see com.adminportal.service.LessonPlanService#deleteLessonPlan(LessonPlan lesson)
	 */
	@Override
	public void deleteLessonPlan(LessonPlan lesson) {
		// TODO Auto-generated method stub
		
		lessonRepo.deleteLesson(lesson.getLessonPlanId());
	}

}

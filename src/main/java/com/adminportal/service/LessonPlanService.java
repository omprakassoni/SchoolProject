/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Lesson interacting with its repository for database operation
 */

package com.adminportal.service;

import java.sql.Timestamp;
import java.util.List;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;


public interface LessonPlanService {
	List<LessonPlan> findAll();
	
	void deleteLessonById(int id);
	
	int countRow();
	
	boolean EnableLessonPlanContent(int status,int id);
	
	LessonPlan findById(int id);
	
	List<LessonPlan> findAllByTopic(Topic topic) ;
	
	List<LessonPlan> findALlByUser(User usr) ;


	boolean updateLessonPlan(Timestamp dat,String lessonPath,int id);
	
	boolean EnableAcceptedByAdminLessonPlanContent(int status,int id) ;
	
	List<LessonPlan> findAllByTopicAndStatus(Topic topic);
	
	int save(LessonPlan temp);
	
	void deleteLessonPlan(LessonPlan lesson);
}

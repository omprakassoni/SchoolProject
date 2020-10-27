/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Lesson resource
 * @author om prakash
 *
 */
public interface LessonPlanRepository extends CrudRepository<LessonPlan, Integer>{
	
	/**
	 * Find list of Lesson Object based on list of Topic given as input parameter
	 * @param topic A topic object
	 * @return List of lesson Object
	 */
	List<LessonPlan> findAllBytopic(List<Topic> topic);							//fetching List of Lesson based on Topic
	
	/**
	 * Find list of Lesson Object based on type of resource given as input parameter
	 * @param type Resource Type preferably Lesson
	 * @return List of lesson Object
	 */
	List<LessonPlan> findAllBytype(String type);								//fetching list of Lesson based on Type
	
	/**
	 * Find list of Lesson Object based on type of resource and topic object given as input parameter
	 * @param topic A topic object
	 * @param type type of resource(Lesson)
	 * @return List of lesson Object
	 */
	@Query("from LessonPlan U where U.topic=?1 and U.type=?2")
	ArrayList<LessonPlan> findAllBytopicAndType(Topic topic,String type);		//fetching list of Lesson based on type and topic
	
	/**
	 * update lesson attribute status based on lesson ID given as one of the input argument
	 * @param status status value to be set
	 * @param id Lesson ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update LessonPlan set status=?1 where lessonPlanId=?2")				//Enabling or disabling status of Lesson based on primary key		
	int EnableLessonPlanContent(int status,int id);
	
	/**
	 * Find list of lesson object based on topic object, status value and type of resource(lesson) given as input parameter
	 * @param topic Topic object
	 * @param status Status value
	 * @param type type of resource (lesson)
	 * @return List of lesson object
	 */
	@Query("from LessonPlan U where U.topic=?1 and U.status=?2 and U.type=?3")
	List<LessonPlan> findAllByTopicAndStatus(Topic topic,int status,String type);

	/**
	 * Find list of lesson object based on USer object and type of resource(Document) given as input parameter
	 * @param usr User object
	 * @param type Type of resource (Document)
	 * @return List of lesson Object
	 */
	@Query("from LessonPlan U where U.user=?1 and U.type=?2")				//listing Lesson based on user and type
	List<LessonPlan> findAllByuser(User usr,String type);
	
	/**
	 * update lesson attribute Lesson resource path, date modified based on lesson ID given as one of the input argument
	 * @param dat current date
	 * @param lessonPath path of lesson resource saved
	 * @param id lesson ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update LessonPlan set dateModified=?1 ,lessonPlan=?2 where lessonPlanId=?3")		//updating Lesson Information
	int updateLessonPlan(Timestamp dat,String lessonPath,int id);
	
	/**
	 * update lesson attribute acceptedByAdmin, status,date approved based on lesson ID given as one of the input argument
	 * @param status status value to be set
	 * @param time current time
	 * @param id Lesson ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update LessonPlan set acceptedByAdmin=?1,status=?1,dateApproved=?2 where lessonPlanId=?3")
	int EnableAcceptedByAdminContent(int status,Timestamp time,int id);
	
	@Modifying
	@Query("delete from LessonPlan U where U.lessonPlanId=?1")
	@Transactional
	void deleteLesson(int lessonID);
	
}








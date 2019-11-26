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

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

public interface LessonPlanRepository extends CrudRepository<LessonPlan, Integer>{
	
	List<LessonPlan> findAllBytopic(List<Topic> temp);							//fetching List of Lesson based on Topic
	
	List<LessonPlan> findAllBytype(String type);								//fetching list of Lesson based on Type
	
	@Query("from LessonPlan U where U.topic=?1 and U.type=?2")
	ArrayList<LessonPlan> findAllBytopicAndType(Topic temp,String topic);		//fetching list of Lesson based on type and topic
	
	@Modifying
	@Query("update LessonPlan set status=?1 where lessonPlanId=?2")				//Enabling or disabling status of Lesson based on primary key		
	int EnableLessonPlanContent(int status,int id);
	
	@Query("from LessonPlan U where U.topic=?1 and U.status=?2 and U.type=?3")
	List<LessonPlan> findAllByTopicAndStatus(Topic topic,int status,String type);

	@Query("from LessonPlan U where U.user=?1 and U.type=?2")				//listing Lesson based on user and type
	List<LessonPlan> findAllByuser(User usr,String type);
	
	@Modifying
	@Query("update LessonPlan set dateModified=?1 ,lessonPlan=?2 where lessonPlanId=?3")		//updating Lesson Information
	int updateLessonPlan(Timestamp dat,String lessonPath,int id);
	
	@Modifying
	@Query("update LessonPlan set acceptedByAdmin=?1,status=?1,dateApproved=?2 where lessonPlanId=?3")
	int EnableAcceptedByAdminContent(int status,Timestamp time,int id);
	
}








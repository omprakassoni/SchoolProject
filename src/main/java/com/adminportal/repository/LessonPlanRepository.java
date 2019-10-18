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
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

public interface LessonPlanRepository extends CrudRepository<LessonPlan, Integer>{
	
	List<LessonPlan> findAllBytopic(List<Topic> temp);
	
	List<LessonPlan> findAllBytype(String type);
	
	@Query("from LessonPlan U where U.topic=?1 and U.type=?2")
	ArrayList<LessonPlan> findAllBytopicAndType(Topic temp,String topic);
	
	@Modifying
	@Query("update LessonPlan set status=?1 where lessonPlanId=?2")
	int EnableLessonPlanContent(int status,int id);

	@Query("from LessonPlan U where U.user=?1 and U.type=?2")
	List<LessonPlan> findAllByuser(User usr,String type);
	
	@Modifying
	@Query("update LessonPlan set dateModified=?1 ,lessonPlan=?2 where lessonPlanId=?3")
	int updateLessonPlan(Timestamp dat,String lessonPath,int id);
	
}

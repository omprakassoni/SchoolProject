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

import com.adminportal.content.SubjectClassMapping;
import com.adminportal.content.Topic;

public interface TopicRepository extends CrudRepository<Topic, Integer> {
	
	List<Topic> findBysubjectClassMapping(SubjectClassMapping temp); 		// listing list topic based on mapping of class and subject
	
	@Query("from Topic T where T.subjectClassMapping=?1 and T.topicName=?2")	//topic based on subjectClassAmpping AND TOPICNAME
	Topic findBysubjectClassMappingAndtopicName(SubjectClassMapping subjectClassMapping,String topicName);
	
	boolean existsBysubjectClassMapping(SubjectClassMapping sub);
	
	/* not in use right now  */
	ArrayList<Topic> findAllBysubjectClassMapping(ArrayList<SubjectClassMapping> temp);
	
	@Modifying
	@Query("update Topic set description=?1, poster=?2 , dateModified=?3 where topicId=?4")	//UPDATING TOPIC
	int updateTopic(String desc,String poster,Timestamp date,int topicID);

	@Modifying
	@Query("update Topic set status=?1 where topicId=?2")		// DISABLE OR ENABLE TOPIC BASED ON TOPIC id
	int disableTopic(int status,int topicId);
	
}

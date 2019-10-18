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
	
	List<Topic> findBysubjectClassMapping(SubjectClassMapping temp); 
	
	@Query("from Topic T where T.subjectClassMapping=?1 and T.topicName=?2")
	Topic findBysubjectClassMappingAndtopicName(SubjectClassMapping subjectClassMapping,String topicName);
	
	boolean existsBysubjectClassMapping(SubjectClassMapping sub);
	
	/* not in use right now  */
	ArrayList<Topic> findAllBysubjectClassMapping(ArrayList<SubjectClassMapping> temp);
	
	@Modifying
	@Query("update Topic set description=?1, poster=?2 , dateModified=?3 where topicId=?4")
	int updateTopic(String desc,String poster,Timestamp date,int topicID);

	
}

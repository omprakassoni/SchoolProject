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
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.SubjectClassMapping;
import com.adminportal.content.Topic;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Topic resource
 * @author om prakash
 *
 */
public interface TopicRepository extends CrudRepository<Topic, Integer> {
	
	/**
	 * Find list of Topic object based on SubjectClassMapping Object given as input parameter
	 * @param temp SubjectClassMapping object
	 * @return list of Topic object
	 */
	List<Topic> findBysubjectClassMapping(SubjectClassMapping temp); 		// listing list topic based on mapping of class and subject
	
	/**
	 * Find Topic object based on SubjectClassMapping object and topic name given as input parameter
	 * @param subjectClassMapping SubjectClassMapping object
	 * @param topicName name of topic
	 * @return Topic object
	 */
	@Query("from Topic T where T.subjectClassMapping=?1 and T.topicName=?2")	//topic based on subjectClassAmpping AND TOPICNAME
	Topic findBysubjectClassMappingAndtopicName(SubjectClassMapping subjectClassMapping,String topicName);
	
	/**
	 * Checks whether topic exist based on SubjectClassMapping given as input parameter
	 * @param sub SubjectClassMapping object
	 * @return boolean value
	 */
	boolean existsBysubjectClassMapping(SubjectClassMapping sub);
	
	/* not in use right now  */
	ArrayList<Topic> findAllBysubjectClassMapping(ArrayList<SubjectClassMapping> temp);
	
	
	/**
	 * Find list of Topic object based on array SubjectClassMapping object given as inout parameter 
	 * @param temp list of SubjectClassMapping object
	 * @return list of Topic object
	 */
	@Query("from Topic T where T.subjectClassMapping IN (:SubjectClass)")
	ArrayList<Topic> findAllByClassStandard(@Param("SubjectClass")ArrayList<SubjectClassMapping> temp);
	
	/**
	 * update Topic given TopicID as one of the input argument
	 * @param desc Description to be updated
	 * @param poster poster path to be updated
	 * @param topicName TopicName to be updated
	 * @param date current date
	 * @param topicID Topic ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update Topic set description=?1, poster=?2 , topicName=?3,dateModified=?4 where topicId=?5")	//UPDATING TOPIC
	int updateTopicDescAndQuiz(String desc,String poster,String topicName,Timestamp date,int topicID);

	/**
	 * update Topic given TopicID as one of the input argument 
	 * @param status status value to be updated
	 * @param topicId Topic ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update Topic set status=?1 where topicId=?2")		// DISABLE OR ENABLE TOPIC BASED ON TOPIC id
	int disableTopic(int status,int topicId);
	
	/**
	 * update Topic given TopicID as one of the input argument 
	 * @param desc Description to be updated
	 * @param topicName TopicName to be updated
	 * @param date current date
	 * @param topicId Topic ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update Topic set description=?1,topicName=?2,dateModified=?3 where topicId=?4")
	int updateTopicDesc(String desc,String topicName,Timestamp date,int topicId);
	
	/**
	 * update Topic given TopicID as one of the input argument
	 * @param path path where poster is saved
	 * @param topicId Topic ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update Topic set poster=?1 where topicId=?2")
	int updateTopicPoster(String path,int topicId);
	
	@Modifying
	@Query("delete from Topic U where U.topicId=?1")
	@Transactional
	void deleteTopic(int topicID);
	
	/**
	 * update topic date modified date based on topic ID given as input argument
	 * @param date current date
	 * @param topicId Topic ID
	 * @return number of updated record
	 */
	@Modifying
	@Transactional
	@Query("update Topic set dateModified=?1 where topicId=?2")
	int updateRecentmodificationDate(Timestamp date,int topicId);
	
	/**
	 * Update list of Topic object based on list of SubjectClassMapping object given as one of input parameter
	 * @param val status to be set
	 * @param temp list of SubjectClassMapping object
	 * @return number of updated record
	 */
	@Modifying
	@Query("update Topic T set status=:valdisEna where T.subjectClassMapping IN (:SubjectClass)")
	int disableEnableAllByClassStandard(@Param("valdisEna")int val,@Param("SubjectClass")List<SubjectClassMapping> temp);
	

	
}

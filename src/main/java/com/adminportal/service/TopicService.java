/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Topic interacting with its repository for database operation
 */

package com.adminportal.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Subject;
import com.adminportal.content.SubjectClassMapping;
import com.adminportal.content.Topic;
import com.adminportal.content.VideoExternal;

/**
 * This interface has all the method declaration related to Topic database operation
 * @author om prakash
 *
 */
public interface TopicService {
	
	
	/**
	 * Find list of Topic based on SubjectCLassMapping object given as input parameter
	 * @param local SubjectClassMapping object
	 * @return List of topic Object
	 */
	List<Topic> findBysubjectclassMapping(SubjectClassMapping local);
	
	/**
	 * Find topic object based on SubjectClassMapping object and topic name given as input parameter
	 * @param sub SubjectClassMapping object
	 * @param topicName topic Name
	 * @return Topic Object
	 */
	Topic findBysubjectClassMappingAndtopicName(SubjectClassMapping sub, String topicName);
	
	/**
	 * Lists out all topic record from the database
	 * @return list of topic object
	 */
	List<Topic> findAll();
	
	/**
	 * update topic attribute status value based on topic ID given as one of input parameter
	 * @param status status value to be set
	 * @param id Topic ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean disableEnableTopicById(int status,int id);
	
	/**
	 * Find topic object given Topic Id as input parameter
	 * @param id Topic ID
	 * @return Topic object
	 */
	Topic findById(int id);
	
	/**
	 * Update Topic's description, poster path, topicName, Date modified given Topic ID as one of input parameter
	 * @param desc Description to be set
	 * @param poster file path to be ser
	 * @param topicName Topic Name to be set
	 * @param date Current date
	 * @param topicID Topic ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean updateTopic(String desc,String poster,String topicName,Timestamp date,int topicID);
	
	/**
	 * Count total number of topic record from database
	 * @return integer
	 */
	int countRow();
	
	/**
	 * Update Topic's description,topicName, Date modified given Topic ID as one of input parameter
	 * @param desc Description to be set
	 * @param topicName Topic Name to be set
	 * @param date current date
	 * @param topicId Topic ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean updateTopicDesc(String desc,String topicName,Timestamp date,int topicId);
	
	/**
	 * List out topic object given list of subjectClassMapping object as input parameter
	 * @param tempSubjectClass list of SubjectClassMapping object
	 * @return list of topic
	 */
	List<Topic> findBySubjectClassMppaing(List<SubjectClassMapping> tempSubjectClass);
	
	/**
	 * update Topic's Poster path given Topic ID as input parameter
	 * @param path File path to be set
	 * @param topicId Topic ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean updateTopicPoster(String path,int topicId);
	
	/**
	 * delete Topic object from database
	 * @param topic Topic object
	 */
	void deleteTopic(Topic topic);
	
	/**
	 * Update Topic visibility given list of SubjectClassMapping object.
	 * @param status status value to be set
	 * @param temp list of SubjectClassMapping object
	 * @return integer value
	 */
	int disableEnableAllByClassStandard(int status,List<SubjectClassMapping> temp); 
	
	

}

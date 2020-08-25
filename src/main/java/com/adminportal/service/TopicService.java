/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Topic interacting with its repository for database operation
 */

package com.adminportal.service;

import java.sql.Timestamp;
import java.util.List;

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

public interface TopicService {
	
	
	
	List<Topic> findBysubjectclassMapping(SubjectClassMapping local);
	
	Topic findBysubjectClassMappingAndtopicName(SubjectClassMapping sub, String topicName);
	
	List<Topic> findAll();
	
	boolean disableEnableTopicById(int status,int id);
	
	Topic findById(int id);
	
	boolean updateTopic(String desc,String poster,String topicName,Timestamp date,int topicID);
	
	int countRow();
	
	boolean updateTopicDesc(String desc,String topicName,Timestamp date,int topicId);
	
	List<Topic> findBySubjectClassMppaing(List<SubjectClassMapping> tempSubjectClass);
	
	boolean updateTopicPoster(String path,int topicId);
	
	void deleteTopic(Topic topic);
	
	

}

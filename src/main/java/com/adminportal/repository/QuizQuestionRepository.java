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
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;
/**
 * This Interface Extend CrudRepository to handle all database operation related to Quiz resource
 * @author om prakash
 *
 */
public interface QuizQuestionRepository extends CrudRepository<QuizQuestion, Integer>{
	
	/**
	 * Find list of Quiz Object based on list of Topic given as input parameter
	 * @param topic list of topic object
	 * @return List of Quiz object
	 */
	List<QuizQuestion> findAllBytopic(List<Topic> topic);						//fetching List of Quiz based on Topic
	
	/**
	 *  Find list of Quiz Object based on type of resource(Quiz) given as input parameter
	 * @param type resource type(quiz)
	 * @return List of Quiz object
	 */
	List<QuizQuestion> findAllBytype(String type);								//fetching list of Quiz based on Type
	
	/**
	 * Find list of Quiz Object based on type of resource and topic object given as input parameter
	 * @param topic A topic object
	 * @param type type of resource(Quiz)
	 * @return List of Quiz object
	 */
	@Query("from QuizQuestion U where U.topic=?1 and U.type=?2")				//fetching list of Quiz based on type and topic
	ArrayList<QuizQuestion> findAllBytopicAndType(Topic topic,String type);
	
	/**
	 * update quiz question path, answer path, date modified quiz ID as one of input parameter
	 * @param question question set path
	 * @param answer answer set path
	 * @param date current date
	 * @param quizId quiz ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update QuizQuestion set question=?1, answer=?2, dateModified=?3 where quizQuestionId=?4")	//updating Quiz Information
	int updateQuiz(String question,String answer,Timestamp date,int quizId);
	
	/**
	 * Find list of Quiz object given topic, status and type of resource (Quiz) as input parameter
	 * @param topic topic object
	 * @param status status value
	 * @param type resource type (quiz)
	 * @return List of Quiz object
	 */
	@Query("from QuizQuestion U where U.topic=?1 and U.status=?2 and U.type=?3")
	List<QuizQuestion> findAllByTopicAndStatus(Topic topic,int status,String type);

	/**
	 * update quiz status given quiz ID as one of input parameter
	 * @param status status value to be set
	 * @param id quiz ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update QuizQuestion set status=?1 where quizQuestionId=?2")			//Enabling or disabling status of Quiz based on primary key
	int EnableQuizContent(int status,int id);		
	
	/**
	 * Find  list of quiz object given user object and type of resource(quiz)
	 * @param usr A USer object
	 * @param type type of resource (Quiz)
	 * @return List of Quiz object
	 */
	@Query("from QuizQuestion U where U.user=?1 and U.type=?2")					//listing Quiz based on user and type
	List<QuizQuestion> findAllByuser(User usr,String type);
	
	/**
	 * update quiz acceptedByAdmin, status and date approved given quiz ID as one of input parameter
	 * @param status status value to be set
	 * @param time current time
	 * @param id quiz ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update QuizQuestion set acceptedByAdmin=?1,status=?1,dateApproved=?2 where quizQuestionId=?3")
	int EnableAcceptedByAdminContent(int status,Timestamp time,int id);
	
	/**
	 * update quiz question file and date modified given quiz ID as one of input parameter
	 * @param question question set file saved path
	 * @param date current date
	 * @param quizId Quiz ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update QuizQuestion set question=?1, dateModified=?2 where quizQuestionId=?3")	//updating Quiz Information
	int updateQuizQuestion(String question,Timestamp date,int quizId);
	
	/**
	 * update quiz answer file and date modified given quiz ID as one of input parameter
	 * @param answer answer set file saved path
	 * @param date current date
	 * @param quizId Quiz ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update QuizQuestion set answer=?1, dateModified=?2 where quizQuestionId=?3")	//updating Quiz Information
	int updateQuizAnswer(String answer,Timestamp date,int quizId);
	
	@Modifying
	@Query("delete from QuizQuestion U where U.quizQuestionId=?1")
	@Transactional
	void deleteQuiz(int quizID);
	
}






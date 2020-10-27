/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Quiz interacting with its repository for database operation
 */

package com.adminportal.service;

import java.sql.Timestamp;
import java.util.List;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

/**
 * This interface has all the method declaration related to QuizQuestion database operation
 * @author om prakash
 *
 */
public interface QuizQuestionService {
	
	/**
	 * Lists out all the QuizQuestion object from database 
	 * @return list of QuizQuestion object
	 */
	List<QuizQuestion> findAll();
	
	/**
	 * delete QuizQuestion object from database
	 * @param id QuizQuestion ID
	 */
	void deleteQuizById(int id) ;
	
	/**
	 * Find QuizQuestion object given QuizQuestion ID
	 * @param id QuizQuestion ID
	 * @return  QuizQuestion object
	 */
	QuizQuestion findById(int id);
	
	/**
	 * Update QuizQuestion's answer file path,question file path, modified date value given QuizQuestion ID
	 * @param question question path to be set
	 * @param answer answer path to be set
	 * @param date current date
	 * @param quizId QuizQuestion ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean updateQuiz(String question,String answer,Timestamp date,int quizId) ;
	
	/**
	 * Update QuizQuestion's question file path, modified date value given QuizQuestion ID 
	 * @param question question path to be set
	 * @param date current date
	 * @param quizId QuizQuestion ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean updateQuizQuestion(String question,Timestamp date,int quizId) ;
	
	/**
	 *  Update QuizQuestion's answer file path, modified date value given QuizQuestion ID
	 * @param answer answer path to be set
	 * @param date current date
	 * @param quizId QuizQuestion ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean updateQuizAnswer(String answer,Timestamp date,int quizId) ;
	
	/**
	 * Count total number of QuizQuestion record from the database
	 * @return integer
	 */
	int countRow();
	
	/**
	 * Update QuizQuestion's visibility value given QuizQuestion ID
	 * @param status status value to be set
	 * @param id QuizQuestion ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean EnableQuizContent(int status,int id);
	
	/**
	 * Find list of QuizQuestion object given Topic object
	 * @param topic Topic Object
	 * @return list of QuizQuestion object
	 */
	List<QuizQuestion> findAllByTopic(Topic topic);
	
	/**
	 * Find list of QuizQuestion object given User object
	 * @param usr User Object
	 * @return list of QuizQuestion object
	 */
	List<QuizQuestion> findALlByUser(User usr);
	
	/**
	 * Update QuizQuestion's acceptedByAdmin value given QuizQuestion ID
	 * @param status status to be set
	 * @param id QuizQuestion ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean EnableAcceptedByAdminQuizContent(int status,int id);
	
	/**
	 * Find list of QuizQuestion object given Topic and status value
	 * @param topic Topic Object
	 * @return list of QuizQuestion object
	 */
	List<QuizQuestion> findAllByTopicAndStatus(Topic topic);
	
	/**
	 * Persist QuizQuestion object into database
	 * @param temp QuizQuestion object
	 * @return integer 
	 */
	int save(QuizQuestion temp);
	
	/**
	 * delete QuizQuestion object from database
	 * @param quiz QuizQuestion object
	 */
	void deleteQuiz(QuizQuestion quiz);

}

/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Quiz interacting with its repository for database operation
 */

package com.adminportal.service;

import java.sql.Timestamp;
import java.util.List;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

public interface QuizQuestionService {
	
	List<QuizQuestion> findAll();
	
	void deleteQuizById(int id);
	
	QuizQuestion findById(int id);
	
	boolean updateQuiz(String question,String answer,Timestamp date,int quizId);
	
	int countRow();
	
	boolean EnableQuizContent(int status,int id);
	
	List<QuizQuestion> findAllByTopic(Topic topic);
	
	List<QuizQuestion> findALlByUser(User usr);
	
	boolean EnableAcceptedByAdminQuizContent(int status,int id);
	
	List<QuizQuestion> findAllByTopicAndStatus(Topic topic);

}

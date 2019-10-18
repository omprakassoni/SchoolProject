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
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

public interface QuizQuestionRepository extends CrudRepository<QuizQuestion, Integer>{
	
	List<QuizQuestion> findAllBytopic(List<Topic> temp);
	
	List<QuizQuestion> findAllBytype(String type);
	
	@Query("from QuizQuestion U where U.topic=?1 and U.type=?2")
	ArrayList<QuizQuestion> findAllBytopicAndType(Topic temp,String topic);
	
	@Modifying
	@Query("update QuizQuestion set question=?1, answer=?2, dateModified=?3 where quizQuestionId=?4")
	int updateQuiz(String question,String answer,Timestamp date,int quizId);

	@Modifying
	@Query("update QuizQuestion set status=?1 where quizQuestionId=?2")
	int EnableQuizContent(int status,int id);
	
	@Query("from QuizQuestion U where U.user=?1 and U.type=?2")
	List<QuizQuestion> findAllByuser(User usr,String type);
	
}

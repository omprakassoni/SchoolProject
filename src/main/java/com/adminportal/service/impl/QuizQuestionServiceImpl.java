/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of Quiz Service Interface to define method for database operation.
 */

package com.adminportal.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;
import com.adminportal.repository.QuizQuestionRepository;
import com.adminportal.service.QuizQuestionService;
import com.spoken.Utility.ServiceUtility;

/**
 * Default implementation of the {@link com.adminportal.service.QuizQuestionService} interface.  
 * @author om prakash
 *
 */
@Service
public class QuizQuestionServiceImpl implements QuizQuestionService{
	
	@Autowired
	private QuizQuestionRepository quizRepo;

	/**
	 * @see com.adminportal.service.QuizQuestionService#findAll()
	 */
	@Override
	public List<QuizQuestion> findAll() {
		
		return (List<QuizQuestion>) quizRepo.findAllBytype("Quiz");
	}

	/**
	 * @see com.adminportal.service.QuizQuestionService#deleteQuizById(int id)
	 */
	@Override
	public void deleteQuizById(int id) {
		Optional<QuizQuestion> local=quizRepo.findById(id);
		QuizQuestion temp=local.get();
		quizRepo.deleteById(temp.getQuizQuestionId());
		
	}

	/**
	 * @see com.adminportal.service.QuizQuestionService#findById(int id)
	 */
	@Override
	public QuizQuestion findById(int id) {
			
		Optional<QuizQuestion> local=quizRepo.findById(id);
		
		return local.get();
	}

	/**
	 * @see com.adminportal.service.QuizQuestionService#updateQuiz(String question, String answer, Timestamp date, int quizId)
	 */
	@Override
	@Transactional
	public boolean updateQuiz(String question, String answer, Timestamp date, int quizId) {
		
		int status=quizRepo.updateQuiz(question, answer, date, quizId);
		if(status>0) {
			return true;
		}else {
			return false;
		}
		
	}

	/**
	 * @see com.adminportal.service.QuizQuestionService#countRow()
	 */
	@Override
	public int countRow() {
		return (int) quizRepo.count();
		
	}

	/**
	 * @see com.adminportal.service.QuizQuestionService#EnableQuizContent(int status, int id)
	 */
	@Override
	@Transactional
	public boolean EnableQuizContent(int status, int id) {
		int status1=quizRepo.EnableQuizContent(status, id);
		
		if(status1>0) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * @see com.adminportal.service.QuizQuestionService#findAllByTopic(Topic topic)
	 */
	@Override
	public List<QuizQuestion> findAllByTopic(Topic topic) {
		List<QuizQuestion> localQuiz=quizRepo.findAllBytopicAndType(topic, "Quiz");
		
		return localQuiz;
	}

	/**
	 * @see com.adminportal.service.QuizQuestionService#findALlByUser(User usr)
	 */
	@Override
	public List<QuizQuestion> findALlByUser(User usr) {
		
		return quizRepo.findAllByuser(usr,"Quiz");
	}
	
	/**
	 * @see com.adminportal.service.QuizQuestionService#EnableAcceptedByAdminQuizContent(int status, int id)
	 */
	@Override
	@Transactional
	public boolean EnableAcceptedByAdminQuizContent(int status, int id) {
		int status1=quizRepo.EnableAcceptedByAdminContent(status,ServiceUtility.getCurrentTime(), id);
		
		if(status1>0) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * @see com.adminportal.service.QuizQuestionService#findAllByTopicAndStatus(Topic topic)
	 */
	@Override
	public List<QuizQuestion> findAllByTopicAndStatus(Topic topic) {
		
		return quizRepo.findAllByTopicAndStatus(topic, 1,"Quiz");
	}

	/**
	 * @see com.adminportal.service.QuizQuestionService#updateQuizQuestion(String question, Timestamp date, int quizId)
	 */
	@Override
	@Transactional
	public boolean updateQuizQuestion(String question, Timestamp date, int quizId) {
		int status=quizRepo.updateQuizQuestion(question,  date, quizId);
		if(status>0) {
			return true;
		}else {
			return false;
		}
		
	}

	/**
	 * @see com.adminportal.service.QuizQuestionService#updateQuizAnswer(String answer, Timestamp date, int quizId)
	 */
	@Override
	@Transactional
	public boolean updateQuizAnswer(String answer, Timestamp date, int quizId) {
		int status=quizRepo.updateQuizAnswer(answer,date, quizId);
		if(status>0) {
			return true;
		}else {
			return false;
		}
		
	}

	/**
	 * @see com.adminportal.service.QuizQuestionService#save(QuizQuestion temp)
	 */
	@Override
	public int save(QuizQuestion temp) {
		quizRepo.save(temp);
		return 0;
	}

	/**
	 * @see com.adminportal.service.QuizQuestionService#deleteQuiz(QuizQuestion quiz)
	 */
	@Override
	public void deleteQuiz(QuizQuestion quiz) {
		// TODO Auto-generated method stub
		quizRepo.deleteQuiz(quiz.getQuizQuestionId());
		
	}

}

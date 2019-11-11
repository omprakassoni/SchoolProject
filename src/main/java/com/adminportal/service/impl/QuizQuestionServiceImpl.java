/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
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

@Service
public class QuizQuestionServiceImpl implements QuizQuestionService{
	
	@Autowired
	private QuizQuestionRepository quizRepo;

	@Override
	public List<QuizQuestion> findAll() {
		
		return (List<QuizQuestion>) quizRepo.findAllBytype("Quiz");
	}

	@Override
	public void deleteQuizById(int id) {
		Optional<QuizQuestion> local=quizRepo.findById(id);
		QuizQuestion temp=local.get();
		quizRepo.deleteById(temp.getQuizQuestionId());
		
	}

	@Override
	public QuizQuestion findById(int id) {
			
		Optional<QuizQuestion> local=quizRepo.findById(id);
		
		return local.get();
	}

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

	@Override
	public int countRow() {
		return (int) quizRepo.count();
		
	}

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

	@Override
	public List<QuizQuestion> findAllByTopic(Topic topic) {
		List<QuizQuestion> localQuiz=quizRepo.findAllBytopicAndType(topic, "Quiz");
		
		return localQuiz;
	}

	@Override
	public List<QuizQuestion> findALlByUser(User usr) {
		
		return quizRepo.findAllByuser(usr,"Quiz");
	}

}

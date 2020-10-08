package com.adminportal.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.adminportal.content.QuizQuestion;
import com.adminportal.repository.QuizQuestionRepository;
import com.spoken.Utility.ServiceUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuizQuestionServiceTest {

	@Autowired
	private QuizQuestionService quizService;
	
	@MockBean
	private QuizQuestionRepository quizRepo;
	
	@Test
	public void saveQuizTest() {
		QuizQuestion quiz=new QuizQuestion(1, "Quiz", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "pathtoQuestion", "pathtoAnswer", 1, 1, "zdhfgdshfbsdf", ServiceUtility.getCurrentTime(), null, null);
		
		when(quizRepo.save(quiz)).thenReturn(quiz);
		
		assertEquals(0, quizService.save(quiz));
	}
	
	@Test
	public void UpdateQuizQuestionTest() {
		QuizQuestion quiz=new QuizQuestion(1, "Quiz", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "pathtoQuestion", "pathtoAnswer", 1, 1, "zdhfgdshfbsdf", ServiceUtility.getCurrentTime(), null, null);
		
		when(quizRepo.save(quiz)).thenReturn(quiz);
		
		assertEquals(0, quizService.save(quiz));
		
		int updateQuizValue=0;
		boolean updateQuizBoolean=false;
		
		when(quizRepo.updateQuizQuestion("hsagddaf", ServiceUtility.getCurrentTime(), 1)).thenReturn(updateQuizValue);
		
		if(updateQuizValue>0) {
			updateQuizBoolean=true;
		}
		
		assertEquals(updateQuizBoolean, quizService.updateQuizQuestion("hsagddaf", ServiceUtility.getCurrentTime(), 1));
	}
	
	@Test
	public void UpdateQuizAnswerTest() {
		QuizQuestion quiz=new QuizQuestion(1, "Quiz", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "pathtoQuestion", "pathtoAnswer", 1, 1, "zdhfgdshfbsdf", ServiceUtility.getCurrentTime(), null, null);
		
		when(quizRepo.save(quiz)).thenReturn(quiz);
		
		assertEquals(0, quizService.save(quiz));
		
		int updateQuizValue=0;
		boolean updateQuizBoolean=false;
		
		when(quizRepo.updateQuizAnswer("sdfhsdf", ServiceUtility.getCurrentTime(), 1)).thenReturn(updateQuizValue);
		
		if(updateQuizValue>0) {
			updateQuizBoolean=true;
		}
		
		assertEquals(updateQuizBoolean, quizService.updateQuizAnswer("sdfhsdf", ServiceUtility.getCurrentTime(), 1));
	}
	
	@Test
	public void EnableAceptedByAdminQuizTest() {
		QuizQuestion quiz=new QuizQuestion(1, "Quiz", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "pathtoQuestion", "pathtoAnswer", 1, 1, "zdhfgdshfbsdf", ServiceUtility.getCurrentTime(), null, null);
		
		when(quizRepo.save(quiz)).thenReturn(quiz);
		
		assertEquals(0, quizService.save(quiz));
		
		int updateQuizValue=0;
		boolean updateQuizBoolean=false;
		
		when(quizRepo.EnableAcceptedByAdminContent(1, ServiceUtility.getCurrentTime(), 1)).thenReturn(updateQuizValue);
		
		if(updateQuizValue>0) {
			updateQuizBoolean=true;
		}
		
		assertEquals(updateQuizBoolean, quizService.EnableAcceptedByAdminQuizContent(1, 1));
	}
	
	@Test
	public void UpdateQuizTest() {
		QuizQuestion quiz=new QuizQuestion(1, "Quiz", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "pathtoQuestion", "pathtoAnswer", 1, 1, "zdhfgdshfbsdf", ServiceUtility.getCurrentTime(), null, null);
		
		when(quizRepo.save(quiz)).thenReturn(quiz);
		
		assertEquals(0, quizService.save(quiz));
		
		int updateQuizValue=0;
		boolean updateQuizBoolean=false;
		
		when(quizRepo.updateQuiz("hsagddaf", "sdfhsdf", ServiceUtility.getCurrentTime(), 1)).thenReturn(updateQuizValue);
		
		if(updateQuizValue>0) {
			updateQuizBoolean=true;
		}
		
		assertEquals(updateQuizBoolean, quizService.updateQuiz("hsagddaf", "sdfhsdf", ServiceUtility.getCurrentTime(), 1));
	}
	
	@Test
	public void EnableQuizTest() {
		QuizQuestion quiz=new QuizQuestion(1, "Quiz", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "pathtoQuestion", "pathtoAnswer", 1, 1, "zdhfgdshfbsdf", ServiceUtility.getCurrentTime(), null, null);
		
		when(quizRepo.save(quiz)).thenReturn(quiz);
		
		assertEquals(0, quizService.save(quiz));
		
		int updateQuizValue=0;
		boolean updateQuizBoolean=false;
		
		when(quizRepo.EnableQuizContent(1, 1)).thenReturn(updateQuizValue);
		
		if(updateQuizValue>0) {
			updateQuizBoolean=true;
		}
		
		assertEquals(updateQuizBoolean, quizService.EnableQuizContent(1, 1));
	}
}

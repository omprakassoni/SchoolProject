/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Subject;
import com.adminportal.content.SubjectClassMapping;
import com.adminportal.content.Topic;
import com.adminportal.content.VideoExternal;
import com.adminportal.repository.ArticleExternalRepository;
import com.adminportal.repository.DocumentExternalRepository;
import com.adminportal.repository.LessonPlanRepository;
import com.adminportal.repository.PhetsRepository;
import com.adminportal.repository.QuizQuestionRepository;
import com.adminportal.repository.TopicRepository;
import com.adminportal.repository.VideoExternalRepository;
import com.adminportal.service.TopicService;

@Service
public class TopicServiceimpl implements TopicService {

	@Autowired
	private TopicRepository topicRepo;
	
	@Autowired
	private ArticleExternalRepository articleRepo;
	
	@Autowired
	private DocumentExternalRepository documentRepo;
	
	@Autowired
	private LessonPlanRepository lessonRepo;
	
	@Autowired
	private PhetsRepository phetRepo;
	
	@Autowired
	private QuizQuestionRepository quizRepo;
	
	@Autowired
	private VideoExternalRepository videoRepo;
	
	
	
	@Override
	public List<Topic> findBysubjectclassMapping(SubjectClassMapping local) {
		System.out.println("vikash");
		
		List<Topic> tempTopic= topicRepo.findBysubjectClassMapping(local);
		return tempTopic;
	}
	@Override
	public Topic findBysubjectClassMappingAndtopicName(SubjectClassMapping sub, String topicName) {
		Topic localTopic=topicRepo.findBysubjectClassMappingAndtopicName(sub, topicName);
		return localTopic;
	}
	@Override
	public List<Topic> findAll() {
		
		return (List<Topic>) topicRepo.findAll();
	}
	
	@Override
	@Transactional
	public boolean disableEnableTopicById(int status,int id) {
		
		int status1=topicRepo.disableTopic(status, id);
		
		if(status1>0) {
			return true;
		}else {
			return false;
		}
		
		
		
	}
	@Override
	public Topic findById(int id) {
		Optional<Topic> local=topicRepo.findById(id);	
		
		return local.get();
		
	}
	@Override
	@Transactional
	public boolean updateTopic( String desc, String poster, Timestamp date, int topicID) {
		
		int status=topicRepo.updateTopic( desc, poster, date, topicID);
		if(status>0) {
			return true;
		}else {
			return false;
		}
		
		
	}
	
	

}

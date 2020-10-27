/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of Topic Service Interface to define method for database operation.
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

/**
 * Default implementation of the {@link com.adminportal.service.TopicService} interface.  
 * @author om prakash
 *
 */
@Service
public class TopicServiceimpl implements TopicService {

	@Autowired
	private TopicRepository topicRepo;
	
	/**
	 * @see com.adminportal.service.TopicService#findBysubjectclassMapping(SubjectClassMapping local)
	 */
	@Override
	public List<Topic> findBysubjectclassMapping(SubjectClassMapping local) {
		System.out.println("vikash");
		
		List<Topic> tempTopic= topicRepo.findBysubjectClassMapping(local);
		return tempTopic;
	}
	
	/**
	 * @see com.adminportal.service.TopicService#findBysubjectClassMappingAndtopicName(SubjectClassMapping sub, String topicName)
	 */
	@Override
	public Topic findBysubjectClassMappingAndtopicName(SubjectClassMapping sub, String topicName) {
		Topic localTopic=topicRepo.findBysubjectClassMappingAndtopicName(sub, topicName);
		return localTopic;
	}
	
	/**
	 * @see com.adminportal.service.TopicService#findAll()
	 */
	@Override
	public List<Topic> findAll() {
		
		return (List<Topic>) topicRepo.findAll();
	}
	
	/**
	 * @see com.adminportal.service.TopicService#disableEnableTopicById(int status,int id)
	 */
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
	
	/**
	 * @see com.adminportal.service.TopicService#findById(int id)
	 */
	@Override
	public Topic findById(int id) {
		Optional<Topic> local=topicRepo.findById(id);	
		
		return local.get();
		
	}
	
	/**
	 * @see com.adminportal.service.TopicService#updateTopic( String desc, String poster,String topicName, Timestamp date, int topicID)
	 */
	@Override
	@Transactional
	public boolean updateTopic( String desc, String poster,String topicName, Timestamp date, int topicID) {
		
		int status=topicRepo.updateTopicDescAndQuiz( desc, poster,topicName, date, topicID);
		if(status>0) {
			return true;
		}else {
			return false;
		}
		
		
	}
	
	/**
	 * @see com.adminportal.service.TopicService#countRow()
	 */
	@Override
	public int countRow() {
		
		return (int) topicRepo.count();
	}
	
	/**
	 * @see com.adminportal.service.TopicService#updateTopicDesc(String desc,String topicName, Timestamp date, int topicId)
	 */
	@Override
	@Transactional
	public boolean updateTopicDesc(String desc,String topicName, Timestamp date, int topicId) {
		
		int status=topicRepo.updateTopicDesc( desc, topicName,date, topicId);
		if(status>0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @see com.adminportal.service.TopicService#findBySubjectClassMppaing(List<SubjectClassMapping> tempSubjectClass)
	 */
	@Override
	public List<Topic> findBySubjectClassMppaing(List<SubjectClassMapping> tempSubjectClass) {
		
		return topicRepo.findAllByClassStandard((ArrayList<SubjectClassMapping>) tempSubjectClass);
	}
	
	/**
	 * @see com.adminportal.service.TopicService#updateTopicPoster(String path, int topicId)
	 */
	@Override
	@Transactional
	public boolean updateTopicPoster(String path, int topicId) {
		
		int status=topicRepo.updateTopicPoster(path, topicId);
		if(status>0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @see com.adminportal.service.TopicService#deleteTopic(Topic topic)
	 */
	@Override
	public void deleteTopic(Topic topic) {
		// TODO Auto-generated method stub
		topicRepo.deleteTopic(topic.getTopicId());
	}
	
	/**
	 * @see com.adminportal.service.TopicService#disableEnableAllByClassStandard(int status, List<SubjectClassMapping> temp)
	 */
	@Override
	@Transactional
	public int disableEnableAllByClassStandard(int status, List<SubjectClassMapping> temp) {
		// TODO Auto-generated method stub
		return topicRepo.disableEnableAllByClassStandard(status, temp);
	}
	
	

}

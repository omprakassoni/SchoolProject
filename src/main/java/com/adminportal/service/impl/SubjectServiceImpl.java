/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of Subject Service Interface to define method for database operation.
 */

package com.adminportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Class;
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
import com.adminportal.repository.SubjectClassMappingRepository;
import com.adminportal.repository.SubjectRepository;
import com.adminportal.repository.TopicRepository;
import com.adminportal.repository.VideoExternalRepository;
import com.adminportal.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService{

	
	@Autowired
	private SubjectRepository subjectRepo;
	
	@Autowired
	private SubjectClassMappingRepository subjectClassRepo;
	
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
	public Subject findBySubjectName(String subjectName) {
		
		Subject localSubject=subjectRepo.findBysubName(subjectName);
		
		return localSubject;
	}

	@Override
	public List<Subject> findAll() {
		
		List<Subject> local=(List<Subject>)subjectRepo.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.ASC, "subName"));
		return local;
	}

	@Override
	public Subject findBysubName(String sub) {
		
		Subject localSubject=subjectRepo.findBysubName(sub);
		return localSubject;
	}

	@Override
	public void deleteById(int id) {
		
		Optional<Subject> localsubject=subjectRepo.findById(id);
		Subject tempsub=localsubject.get();
		ArrayList<SubjectClassMapping> temp=subjectClassRepo.findBysub(tempsub);
		
		
		for(SubjectClassMapping a:temp) {
			System.out.println(a);
		}
		
		ArrayList<Topic> tempTopic=getAllTopicBySubjectClassMapping(temp);
		
		for(Topic a:tempTopic) {
			System.out.println(a);
		}
		
		List<ArticleExternal> tempArticle=getAllArticleByTopic(tempTopic);
		
		for(ArticleExternal a:tempArticle) {
			System.out.println(a);
		}
		
		List<DocumentExternal> tempDocument=getAllDocumentByTopic(tempTopic);
		for(DocumentExternal a:tempDocument) {
			System.out.println(a);
		}
		
		List<VideoExternal> tempVideo=getAllVideoByTopic(tempTopic);
		for(VideoExternal a:tempVideo) {
			System.out.println(a);
		}
		
		List<Phets> tempPhets=getAllPhetsByTopic(tempTopic);
		for(Phets a:tempPhets) {
			System.out.println(a);
		}
		
		List<QuizQuestion> tempQuiz=getAllQuizByTopic(tempTopic);
		for(QuizQuestion a:tempQuiz) {
			System.out.println(a);
		}
		
		List<LessonPlan> tempLesson=getAllLessonByTopic(tempTopic);
		for(LessonPlan a:tempLesson) {
			System.out.println(a);
		}
		// need to delegate for deletion operation
		
	}

	@Override
	public ArrayList<Topic> getAllTopicBySubjectClassMapping(ArrayList<SubjectClassMapping> subClass) {
		
		ArrayList<Topic> topic=new ArrayList<Topic>();
		
		for(SubjectClassMapping t:subClass) {
			if(topicRepo.existsBysubjectClassMapping(t)) {
				ArrayList<Topic> local=(ArrayList<Topic>) topicRepo.findBysubjectClassMapping(t);
				topic.addAll(local);
			}
		}
		return topic;
	}

	@Override
	public ArrayList<ArticleExternal> getAllArticleByTopic(ArrayList<Topic> topic) {
	
		ArrayList<ArticleExternal> article=new ArrayList<ArticleExternal>();
		
		for(Topic tp:topic) {
			
			ArrayList<ArticleExternal> local=articleRepo.findAllBytopicAndType(tp, "Article");
			article.addAll(local);
			
		}
		return article;
	}

	@Override
	public ArrayList<DocumentExternal> getAllDocumentByTopic(ArrayList<Topic> topic) {
	ArrayList<DocumentExternal> document=new ArrayList<DocumentExternal>();
		
		for(Topic tp:topic) {
			
			ArrayList<DocumentExternal> local=documentRepo.findAllBytopicAndType(tp, "Document");
			document.addAll(local);
			
		}
		return document;
	}

	@Override
	public ArrayList<VideoExternal> getAllVideoByTopic(ArrayList<Topic> topic) {
	ArrayList<VideoExternal> video=new ArrayList<VideoExternal>();
		
		for(Topic tp:topic) {
			
			ArrayList<VideoExternal> local=videoRepo.findAllBytopicAndType(tp, "Video");
			video.addAll(local);
			
		}
		return video;
	}

	@Override
	public ArrayList<QuizQuestion> getAllQuizByTopic(ArrayList<Topic> topic) {
	ArrayList<QuizQuestion> quiz=new ArrayList<QuizQuestion>();
		
		for(Topic tp:topic) {
			
			ArrayList<QuizQuestion> local=quizRepo.findAllBytopicAndType(tp, "Quiz");
			quiz.addAll(local);
			
		}
		return quiz;
	}

	@Override
	public ArrayList<LessonPlan> getAllLessonByTopic(ArrayList<Topic> topic) {
	ArrayList<LessonPlan> lesson=new ArrayList<LessonPlan>();
		
		for(Topic tp:topic) {
			
			ArrayList<LessonPlan> local=lessonRepo.findAllBytopicAndType(tp, "Lesson");
			lesson.addAll(local);
			
		}
		return lesson;
	}

	@Override
	public ArrayList<Phets> getAllPhetsByTopic(ArrayList<Topic> topic) {
	ArrayList<Phets> phet=new ArrayList<Phets>();
		
		for(Topic tp:topic) {
			
			ArrayList<Phets> local=phetRepo.findAllBytopicAndType(tp, "Phets");
			phet.addAll(local);
			
		}
		return phet;
	}

	@Override
	public Subject findById(int id) {
		
		Optional<Subject> sub=subjectRepo.findById(id);
		
		Subject localSub=sub.get();
	
		
		return localSub;
	}

	@Override
	public int countRow() {
		
		return (int) subjectRepo.count();
	}

	@Override
	public Subject save(Subject sub) {
		
		Subject subj=subjectRepo.save(sub);
		return subj;
	}

	@Override
	@Transactional
	public boolean updateSubjectName(String subName, int subId) {
		int status=subjectRepo.updateSubjectName(subName, subId);
		if(status>0) {
			return true;
		}else {
			return false;
		}
	}

}

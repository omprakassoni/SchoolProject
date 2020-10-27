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

/**
 * Default implementation of the {@link com.adminportal.service.SubjectService} interface.  
 * @author om prakash
 *
 */
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
	
	/**
	 * @see com.adminportal.service.SubjectService#findBySubjectName(String subjectName)
	 */
	@Override
	public Subject findBySubjectName(String subjectName) {
		
		Subject localSubject=subjectRepo.findBysubName(subjectName);
		
		return localSubject;
	}

	/**
	 * @see com.adminportal.service.SubjectService#findAll()
	 */
	@Override
	public List<Subject> findAll() {
		
		List<Subject> local=(List<Subject>)subjectRepo.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.ASC, "subName"));
		return local;
	}

	/**
	 * @see com.adminportal.service.SubjectService#findBysubName(String sub)
	 */
	@Override
	public Subject findBysubName(String sub) {
		
		Subject localSubject=subjectRepo.findBysubName(sub);
		return localSubject;
	}

	
	/**
	 * @see com.adminportal.service.SubjectService#findById(int id)
	 */
	@Override
	public Subject findById(int id) {
		
		Optional<Subject> sub=subjectRepo.findById(id);
		
		Subject localSub=sub.get();
	
		
		return localSub;
	}

	/**
	 * @see com.adminportal.service.SubjectService#countRow()
	 */
	@Override
	public int countRow() {
		
		return (int) subjectRepo.count();
	}

	/**
	 * @see com.adminportal.service.SubjectService#save(Subject sub)
	 */
	@Override
	public Subject save(Subject sub) {
		
		Subject subj=subjectRepo.save(sub);
		return subj;
	}

	/**
	 * @see com.adminportal.service.SubjectService#updateSubjectName(String subName, int subId)
	 */
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

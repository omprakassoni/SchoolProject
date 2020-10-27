/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of SubjectClass Service Interface to define method for database operation.
 */

package com.adminportal.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.Class;
import com.adminportal.content.Subject;
import com.adminportal.content.SubjectClassMapping;
import com.adminportal.content.Topic;
import com.adminportal.repository.SubjectClassMappingRepository;
import com.adminportal.repository.SubjectRepository;
import com.adminportal.service.SubjectClassService;

/**
 * Default implementation of the {@link com.adminportal.service.SubjectClassService} interface.  
 * @author om prakash
 *
 */
@Service
public class SubjectClassServiceimpl implements SubjectClassService{
	
	@Autowired
	private SubjectRepository subjectrepo;
	
	@Autowired
	private SubjectClassMappingRepository subjectClassRepo;

	/**
	 * @see com.adminportal.service.SubjectClassService#createSubjectClass(Subject sub, Set<SubjectClassMapping> subClass)
	 */
	@Override
	public Subject createSubjectClass(Subject sub, Set<SubjectClassMapping> subClass) throws Exception{
		
		sub.getSubClasMapp().addAll(subClass);
		Subject sub1=subjectrepo.save(sub);
		
		return sub1;
	}

	/**
	 * @see com.adminportal.service.SubjectClassService#createTopic(Topic topic, SubjectClassMapping subjectClassmapping)
	 */
	@Override
	public Subject createTopic(Topic topic, SubjectClassMapping subjectClassmapping) throws Exception {
		
		subjectClassmapping.getTopic().add(topic);
		subjectClassRepo.save(subjectClassmapping);
		
		return null;
	}

	/**
	 * @see com.adminportal.service.SubjectClassService#getSubjectFromClass(Class classTemp)
	 */
	@Override
	public List<SubjectClassMapping> getSubjectFromClass(Class classTemp) {
		
		List<SubjectClassMapping> localSubjectClass=subjectClassRepo.findBystandard(classTemp);
		
		return localSubjectClass;
	}

	/**
	 * @see com.adminportal.service.SubjectClassService#findBysubAndstandard(Class tempClass, Subject subject)
	 */
	@Override
	public SubjectClassMapping findBysubAndstandard(Class tempClass, Subject subject) {
		
		SubjectClassMapping localSubjectClass=subjectClassRepo.findBysubAndstandard(subject, tempClass);
		return localSubjectClass;
	}

	/**
	 * @see com.adminportal.service.SubjectClassService#countRow()
	 */
	@Override
	public int countRow() {
		
		return (int) subjectClassRepo.count();
	}

	/**
	 * @see com.adminportal.service.SubjectClassService#getClassFromMapping(Class classTemp)
	 */
	@Override
	public List<SubjectClassMapping> getClassFromMapping(Class classTemp) {
		
		return subjectClassRepo.findBystandard(classTemp);
	}

	/**
	 * @see com.adminportal.service.SubjectClassService#getClassFromSubject(Subject subTemp)
	 */
	@Override
	public List<SubjectClassMapping> getClassFromSubject(Subject subTemp) {
		
		return subjectClassRepo.findBysub(subTemp);
	}

	/**
	 * @see com.adminportal.service.SubjectClassService#updateSubjectinAllField(boolean status, Subject sub)
	 */
	@Override
	@Transactional
	public int updateSubjectinAllField(boolean status, Subject sub) {
		
		return subjectClassRepo.updateAllSubject(status, sub);
	}

	/**
	 * @see com.adminportal.service.SubjectClassService#updateClassinAllField(boolean status, Class clas)
	 */
	@Override
	@Transactional
	public int updateClassinAllField(boolean status, Class clas) {
		// TODO Auto-generated method stub
		return subjectClassRepo.updateAllClass(status, clas);
	}

}

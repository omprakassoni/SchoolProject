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

import com.adminportal.content.Class;
import com.adminportal.content.Subject;
import com.adminportal.content.SubjectClassMapping;
import com.adminportal.content.Topic;
import com.adminportal.repository.SubjectClassMappingRepository;
import com.adminportal.repository.SubjectRepository;
import com.adminportal.service.SubjectClassService;

@Service
public class SubjectClassServiceimpl implements SubjectClassService{
	
	@Autowired
	private SubjectRepository subjectrepo;
	
	@Autowired
	private SubjectClassMappingRepository subjectClassRepo;

	@Override
	public Subject createSubjectClass(Subject sub, Set<SubjectClassMapping> subClass) throws Exception{
		
		sub.getSubClasMapp().addAll(subClass);
		Subject sub1=subjectrepo.save(sub);
		
		return sub1;
	}

	@Override
	public Subject createTopic(Topic topic, SubjectClassMapping subjectClassmapping) throws Exception {
		
		subjectClassmapping.getTopic().add(topic);
		subjectClassRepo.save(subjectClassmapping);
		
		return null;
	}

	@Override
	public List<SubjectClassMapping> getSubjectFromClass(Class classTemp) {
		
		List<SubjectClassMapping> localSubjectClass=subjectClassRepo.findBystandard(classTemp);
		
		return localSubjectClass;
	}

	@Override
	public SubjectClassMapping findBysubAndstandard(Class tempClass, Subject subject) {
		
		SubjectClassMapping localSubjectClass=subjectClassRepo.findBysubAndstandard(subject, tempClass);
		return localSubjectClass;
	}

}

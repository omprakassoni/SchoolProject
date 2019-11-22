/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for SubjectClassMapping interacting with its repository for database operation
 */

package com.adminportal.service;

import java.util.List;
import java.util.Set;

import com.adminportal.content.Class;
import com.adminportal.content.Subject;
import com.adminportal.content.SubjectClassMapping;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;
import com.adminportal.domain.UserRole;

public interface SubjectClassService {
	
	Subject createSubjectClass(Subject sub,Set<SubjectClassMapping> subClass) throws Exception;
	
	Subject createTopic(Topic topic,SubjectClassMapping subjectClassmapping) throws Exception;
	
	List<SubjectClassMapping> getSubjectFromClass(Class classTemp);
	
	SubjectClassMapping findBysubAndstandard(Class tempClass,Subject subject);
	
	
	
}

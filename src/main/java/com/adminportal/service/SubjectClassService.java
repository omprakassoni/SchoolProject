/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for SubjectClassMapping interacting with its repository for database operation
 */

package com.adminportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.adminportal.content.Class;
import com.adminportal.content.Subject;
import com.adminportal.content.SubjectClassMapping;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;
import com.adminportal.domain.UserRole;

/**
 * This interface has all the method declaration related to SubjectClass database operation
 * @author om prakash
 *
 */
public interface SubjectClassService {
	
	/**
	 * Persist SubjectClass object with proper relationship among subject and class object
	 * @param sub Subject Object
	 * @param subClass Set of class object
	 * @return Subject object
	 * @throws Exception
	 */
	Subject createSubjectClass(Subject sub,Set<SubjectClassMapping> subClass) throws Exception;
	
	/**
	 * Persist SubjectClass object with proper relationship among Topic
	 * @param topic Topic object
	 * @param subjectClassmapping SubjectClassMapping object
	 * @return Subject object
	 * @throws Exception
	 */
	Subject createTopic(Topic topic,SubjectClassMapping subjectClassmapping) throws Exception;
	
	/**
	 * Find list of SubjectClassMapping object based on class object
	 * @param classTemp class object
	 * @return list of SubjectClassMapping object
	 * @throws Exception
	 */
	List<SubjectClassMapping> getSubjectFromClass(Class classTemp) throws Exception;
	
	/**
	 * Find SubjectClassMapping object based on class object and subject object
	 * @param tempClass class object
	 * @param subject subject object
	 * @return SubjectClassMapping object
	 * @throws Exception
	 */
	SubjectClassMapping findBysubAndstandard(Class tempClass,Subject subject) throws Exception;
	
	/**
	 * Find list of SubjectClassMapping object based on class object
	 * @param classTemp class object
	 * @return list of SubjectClassMapping object
	 * @throws Exception
	 */
	List<SubjectClassMapping> getClassFromMapping(Class classTemp) throws Exception;
	
	/**
	 * count total number SubjectClassMapping object from database
	 * @return integer
	 */
	int countRow();
	
	/**
	 * Find list of SubjectClassMapping object based on subject object
	 * @param subTemp subject object
	 * @return list of SubjectClassMapping object
	 * @throws Exception
	 */
	List<SubjectClassMapping> getClassFromSubject(Subject subTemp) throws Exception;
	
	/**
	 * update visibility in SubjectClassMapping's status value in record given subject object
	 * @param status status value to be set
	 * @param sub  subject object
	 * @return number of updated record
	 */
	int updateSubjectinAllField(boolean status,Subject sub);
	
	/**
	 * update visibility in SubjectClassMapping's status value in record given Class object
	 * @param status status value to be set
	 * @param clas class object
	 * @return number of updated record
	 */
	int updateClassinAllField(boolean status,Class clas);
	
	
}

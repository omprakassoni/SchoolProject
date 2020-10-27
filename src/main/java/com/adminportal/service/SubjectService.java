/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0.
 * 	Description	  : Service class for Subject interacting with its repository for database operation
 */

package com.adminportal.service;

import java.util.ArrayList;
import java.util.List;

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
/**
 * This interface has all the method declaration related to Subject database operation
 * @author om prakash
 *
 */
public interface SubjectService {
	
	/**
	 * Find Subject object given subject's subject name as input parameter from database
	 * @param subjectName subject name
	 * @return Subject object
	 * @throws Exception
	 */
	Subject findBySubjectName(String subjectName) throws Exception;
	
	/**
	 * List out all the subject object from database
	 * @return list of subject object
	 */
	List<Subject> findAll();
	
	/**
	 * Find Subject object given subject's subject name as input parameter from database
	 * @param sub Subject name
	 * @return Subject object
	 * @throws Exception
	 */
	Subject findBysubName(String sub) throws Exception;
	
	/**
	 * Find Subject object given subject ID from database
	 * @param id subject ID
	 * @return Subject Object
	 * @throws Exception
	 */
	Subject findById(int id) throws Exception;
	
	/**
	 * Count total number of subject record from the database
	 * @return integer
	 */
	int countRow();
	
	/**
	 * Persist Subject object into database
	 * @param sub subject object
	 * @return subject object
	 */
	Subject save(Subject sub);
	
	/**
	 * Update Subject's subject name given Subject ID
	 * @param subName subject name to be set
	 * @param subId Subject ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean updateSubjectName(String subName,int subId);

}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
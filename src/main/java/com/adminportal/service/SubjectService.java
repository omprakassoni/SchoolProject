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

public interface SubjectService {
	
	Subject findBySubjectName(String subjectName);
	
	List<Subject> findAll();
	
	Subject findBysubName(String sub);
	
	void deleteById(int id);
	
	ArrayList<Topic> getAllTopicBySubjectClassMapping(ArrayList<SubjectClassMapping> subClass);
	
	ArrayList<ArticleExternal> getAllArticleByTopic(ArrayList<Topic> topic);
	
	ArrayList<DocumentExternal> getAllDocumentByTopic(ArrayList<Topic> topic);
	
	ArrayList<VideoExternal> getAllVideoByTopic(ArrayList<Topic> topic);
	
	ArrayList<QuizQuestion> getAllQuizByTopic(ArrayList<Topic> topic);
	
	ArrayList<LessonPlan> getAllLessonByTopic(ArrayList<Topic> topic);
	
	ArrayList<Phets> getAllPhetsByTopic(ArrayList<Topic> topic);
	
	Subject findById(int id);
	
	int countRow();

}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
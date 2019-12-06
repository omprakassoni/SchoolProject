/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Tutorial interacting with its repository for database operation
 */
package com.adminportal.service;

import java.util.List;

import com.adminportal.content.Topic;
import com.adminportal.content.Tutorial;

public interface TutorialService {
	
	int countRow();
	
	List<Tutorial> getAllTutorial();
	
	List<Tutorial> getAllTutorialByTopic(Topic topic);
	
	boolean enableDisableTutorial(int status,int id);
	
	Tutorial getById(int tutorialID);
	
	List<Tutorial> findAllByTopicAndStatus(Topic topic);

}

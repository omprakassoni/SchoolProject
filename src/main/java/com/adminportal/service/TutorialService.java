/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Tutorial interacting with its repository for database operation
 */
package com.adminportal.service;

import java.util.List;

import com.adminportal.content.ConceptMap;
import com.adminportal.content.Topic;
import com.adminportal.content.Tutorial;
/**
 * This interface has all the method declaration related to Tutorial database operation
 * @author om prakash
 *
 */
public interface TutorialService {
	
	/**
	 * Count total number Tutorial resource in database
	 * @return integer
	 */
	int countRow();
	
	/**
	 * Lists out all the tutorial stored in database
	 * @return list of tutorial object
	 */
	List<Tutorial> getAllTutorial();
	
	/**
	 * Lists out all the tutorial stored in database given Topic object as input parameter
	 * @param topic Topic object
	 * @return list of tutorial object
	 */
	List<Tutorial> getAllTutorialByTopic(Topic topic);
	
	/**
	 * update topic visibility flag value given tutorial ID as one of input parameter
	 * @param status status value to be set
	 * @param id Tutorial ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean enableDisableTutorial(int status,int id);
	
	/**
	 * FInd Tutorial object given tutorial Id
	 * @param tutorialID Tutorial ID
	 * @return tutorial object
	 */
	Tutorial getById(int tutorialID);
	
	/**
	 * Find list of tutorial based on topic object and status value(Implicit) given as input parameter
	 * @param topic Topic object
	 * @return list of tutorial object
	 */
	List<Tutorial> findAllByTopicAndStatus(Topic topic);

	/**
	 * Delete tutorial record from database
	 * @param tutorial Tutorial Object
	 */
	void deleteTutorial(Tutorial tutorial);
}

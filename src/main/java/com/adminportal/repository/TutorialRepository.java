/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */
package com.adminportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.Topic;
import com.adminportal.content.Tutorial;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Tutorial resource
 * @author om prakash
 *
 */
public interface TutorialRepository extends CrudRepository<Tutorial, Integer>{
	
	/**
	 * Find list of tutorial based on topic given as input parameter
	 * @param topic Topic object
	 * @return List of tutorial object
	 */
	List<Tutorial> findAllBytopic(Topic topic);			// listing list of tutorial
	
	/**
	 * Find list of tutorial based on topic and status value given as input parameter
	 * @param topic Topic object
	 * @param status status value (like 0,1)
	 * @return List of tutorial object
	 */
	@Query("from Tutorial U where U.topic=?1 and U.status=?2")	
	List<Tutorial> findAllByTopicAndStatus(Topic topic,int status);
	
	/**
	 * update tutorial status value based on tutorial Id given as one of input argument
	 * @param status value to set
	 * @param id Tutorial ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update Tutorial set status=?1 where tutorialId=?2")		// enable or disable tutorial to view
	int enableDisableTutorial(int status,int id);
	
	/**
	 * Find Tutorial object based on tutorial ID
	 * @param tutorialId Tutorial ID
	 * @return Tutorial Object
	 */
	Tutorial findBytutorialId(int tutorialId);			// tutorial Object based on tutorialId
	
	@Modifying
	@Query("delete from Tutorial U where U.tutorialId=?1")
	@Transactional
	void deleteTutorial(int tutorialID);

}

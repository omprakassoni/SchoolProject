/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */
package com.adminportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.adminportal.content.Topic;
import com.adminportal.content.Tutorial;

public interface TutorialRepository extends CrudRepository<Tutorial, Integer>{
	
	List<Tutorial> findAllBytopic(Topic topic);			// listing list of tutorial
	
	@Modifying
	@Query("update Tutorial set status=?1 where tutorialId=?2")		// enable or disable tutorial to view
	int enableDisableTutorial(int status,int id);
	
	Tutorial findBytutorialId(int tutorialId);			// tutorial Object based on tutorialId

}

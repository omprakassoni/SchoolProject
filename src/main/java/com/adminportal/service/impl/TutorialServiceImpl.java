/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of Tutorial Service Interface to define method for database operation.
 */
package com.adminportal.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.Topic;
import com.adminportal.content.Tutorial;
import com.adminportal.repository.TutorialRepository;
import com.adminportal.service.TutorialService;

@Service
public class TutorialServiceImpl implements TutorialService{

	@Autowired
	TutorialRepository tutorialRepo;
	
	@Override
	public int countRow() {
		
		return (int) tutorialRepo.count();
		
		
	}

	@Override
	public List<Tutorial> getAllTutorial() {
		
		List<Tutorial> temp=(List<Tutorial>) tutorialRepo.findAll();
		return temp;
	}

	@Override
	public List<Tutorial> getAllTutorialByTopic(Topic topic) {
		
		List<Tutorial> temp=tutorialRepo.findAllBytopic(topic);
		
		return temp;
	}

	@Override
	@Transactional
	public boolean enableDisableTutorial(int status, int id) {
		
		int stat=tutorialRepo.enableDisableTutorial(status, id);
		if(stat>0) {
			return true;
		}
		
		return false;
	}

	@Override
	public Tutorial getById(int tutorialID) {
		
		Optional<Tutorial> local=tutorialRepo.findById(tutorialID);
		return local.get();
	}

}

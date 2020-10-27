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

/**
 * Default implementation of the {@link com.adminportal.service.TutorialService} interface.  
 * @author om prakash
 *
 */
@Service
public class TutorialServiceImpl implements TutorialService{

	@Autowired
	TutorialRepository tutorialRepo;
	
	/**
	 * @see com.adminportal.service.TutorialService#countRow()
	 */
	@Override
	public int countRow() {
		
		return (int) tutorialRepo.count();
		
		
	}

	/**
	 * @see com.adminportal.service.TutorialService#getAllTutorial()
	 */
	@Override
	public List<Tutorial> getAllTutorial() {
		
		List<Tutorial> temp=(List<Tutorial>) tutorialRepo.findAll();
		return temp;
	}

	/**
	 * @see com.adminportal.service.TutorialService#getAllTutorialByTopic(Topic topic)
	 */
	@Override
	public List<Tutorial> getAllTutorialByTopic(Topic topic) {
		
		List<Tutorial> temp=tutorialRepo.findAllBytopic(topic);
		
		return temp;
	}

	/**
	 * @see com.adminportal.service.TutorialService#enableDisableTutorial(int status, int id)
	 */
	@Override
	@Transactional
	public boolean enableDisableTutorial(int status, int id) {
		
		int stat=tutorialRepo.enableDisableTutorial(status, id);
		if(stat>0) {
			return true;
		}
		
		return false;
	}

	/**
	 * @see com.adminportal.service.TutorialService#getById(int tutorialID)
	 */
	@Override
	public Tutorial getById(int tutorialID) {
		
		Optional<Tutorial> local=tutorialRepo.findById(tutorialID);
		return local.get();
	}

	/**
	 * @see com.adminportal.service.TutorialService#findAllByTopicAndStatus(Topic topic)
	 */
	@Override
	public List<Tutorial> findAllByTopicAndStatus(Topic topic) {
		
		List<Tutorial> temp=tutorialRepo.findAllByTopicAndStatus(topic,1);
		return temp;
	}

	/**
	 * @see com.adminportal.service.TutorialService#deleteTutorial(Tutorial tutorial)
	 */
	@Override
	public void deleteTutorial(Tutorial tutorial) {
		// TODO Auto-generated method stub
		tutorialRepo.deleteTutorial(tutorial.getTutorialId());
	}

}

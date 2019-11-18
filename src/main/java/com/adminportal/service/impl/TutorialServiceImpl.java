package com.adminportal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}

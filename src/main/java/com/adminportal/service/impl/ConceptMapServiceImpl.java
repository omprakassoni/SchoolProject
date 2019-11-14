package com.adminportal.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.ConceptMap;

import com.adminportal.content.Topic;
import com.adminportal.domain.User;
import com.adminportal.repository.ConceptMapRepository;
import com.adminportal.service.ConceptMapService;

@Service
public class ConceptMapServiceImpl implements ConceptMapService{

	@Autowired
	ConceptMapRepository conceptRepo;
	
	@Override
	public List<ConceptMap> findAll() {
		
		
		return conceptRepo.findAllBytype("ConceptMap");
	}


	@Override
	public ConceptMap findByid(int id) {
		
		Optional<ConceptMap> temp=conceptRepo.findById(id);
		return temp.get();
	}

	@Override
	@Transactional
	public boolean updateConcept(String desc, String url, String remark, Timestamp date, int id) {
		int status=conceptRepo.updateConceptMap(desc, url, remark, date, id);
		if(status>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public int countRow() {
		
		return (int) conceptRepo.count();
	}

	@Override
	@Transactional
	public boolean EnableConceptContent(int status, int id) {
		
		int status1=conceptRepo.EnableConceptMapContent(status, id);
		
		if(status1>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<ConceptMap> findAllByTopic(Topic topic) {
		
		List<ConceptMap> localConcept=conceptRepo.findAllBytopicAndType(topic, "ConceptMap");
		
		return localConcept;
	}

	@Override
	public List<ConceptMap> findALlByUser(User usr) {
		List<ConceptMap> localConcept=conceptRepo.findAllByuser(usr, "ConceptMap");
		return localConcept;
	}

}

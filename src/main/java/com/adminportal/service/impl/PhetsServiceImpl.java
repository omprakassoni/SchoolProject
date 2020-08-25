/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of Phets Service Interface to define method for database operation.
 */

package com.adminportal.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;
import com.adminportal.repository.PhetsRepository;
import com.adminportal.service.PhetsService;
import com.spoken.Utility.ServiceUtility;

@Service
public class PhetsServiceImpl implements PhetsService{
	

	@Autowired
	private PhetsRepository phetRepo;
	
	@Override
	public List<Phets> findAll() {
		
		return phetRepo.findAllBytype("Phets");
	}

	@Override
	public void deletePhetById(int id) {
		Optional<Phets> local=phetRepo.findById(id);
		Phets temp=local.get();
		phetRepo.deleteById(temp.getPhetId());
		
	}

	@Override
	public Phets findByid(int id) {
		Optional<Phets> local=phetRepo.findById(id);
		return local.get();
	}



	@Override
	@Transactional
	public boolean updatePhet(String source, String url, Timestamp date, String desc, int id) {
		
		int status=phetRepo.updatePhet(source, url, date, desc, id);
		if(status>0) {
			return true;
		}else {
			return false;
		}

	}

	@Override
	public int countRow() {
		
		return (int) phetRepo.count();
	}

	@Override
	@Transactional
	public boolean EnablePhetContent(int status, int id) {
		int status1=phetRepo.EnablePhetContent(status, id);
		
		if(status1>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Phets> findAllByTopic(Topic topic) {
		
		List<Phets> localPhets=phetRepo.findAllBytopicAndType(topic, "Phets");
		
		return localPhets;
	}

	@Override
	public List<Phets> findALlByUser(User usr) {
		
		return phetRepo.findAllByuser(usr,"Phets");
	}

	@Override
	@Transactional
	public boolean EnableAcceptedByAdminPhetContent(int status, int id) {
		int status1=phetRepo.EnableAcceptedByAdminContent(status,ServiceUtility.getCurrentTime(), id);
		
		if(status1>0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Phets> findAllByTopicAndStatus(Topic topic) {
		
		return phetRepo.findAllByTopicAndStatus(topic, 1,"Phets");
	}

	@Override
	public void deletePhet(Phets phet) {
		// TODO Auto-generated method stub
		
		phetRepo.deletePhet(phet.getPhetId());
		
	}

}

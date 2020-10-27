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

/**
 * Default implementation of the {@link com.adminportal.service.PhetsService} interface.  
 * @author om prakash
 *
 */
@Service
public class PhetsServiceImpl implements PhetsService{
	

	@Autowired
	private PhetsRepository phetRepo;
	
	/**
	 * @see com.adminportal.service.PhetsService#findAll()
	 */
	@Override
	public List<Phets> findAll() {
		
		return phetRepo.findAllBytype("Phets");
	}

	/**
	 * @see com.adminportal.service.PhetsService#deletePhetById(int id)
	 */
	@Override
	public void deletePhetById(int id) {
		Optional<Phets> local=phetRepo.findById(id);
		Phets temp=local.get();
		phetRepo.deleteById(temp.getPhetId());
		
	}

	/**
	 * @see com.adminportal.service.PhetsService#findByid(int id)
	 */
	@Override
	public Phets findByid(int id) {
		Optional<Phets> local=phetRepo.findById(id);
		return local.get();
	}


	/**
	 * @see com.adminportal.service.PhetsService#updatePhet(String source, String url, Timestamp date, String desc, int id)
	 */
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

	/**
	 * @see com.adminportal.service.PhetsService#countRow()
	 */
	@Override
	public int countRow() {
		
		return (int) phetRepo.count();
	}

	/**
	 * @see com.adminportal.service.PhetsService#EnablePhetContent(int status, int id)
	 */
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

	/**
	 * @see com.adminportal.service.PhetsService#findAllByTopic(Topic topic) 
	 */
	@Override
	public List<Phets> findAllByTopic(Topic topic) {
		
		List<Phets> localPhets=phetRepo.findAllBytopicAndType(topic, "Phets");
		
		return localPhets;
	}

	/**
	 * @see com.adminportal.service.PhetsService#findALlByUser(User usr)
	 */
	@Override
	public List<Phets> findALlByUser(User usr) {
		
		return phetRepo.findAllByuser(usr,"Phets");
	}

	/**
	 * @see com.adminportal.service.PhetsService#EnableAcceptedByAdminPhetContent(int status, int id)
	 */
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
	
	/**
	 * @see com.adminportal.service.PhetsService#findAllByTopicAndStatus(Topic topic)
	 */
	@Override
	public List<Phets> findAllByTopicAndStatus(Topic topic) {
		
		return phetRepo.findAllByTopicAndStatus(topic, 1,"Phets");
	}

	/**
	 * @see com.adminportal.service.PhetsService#deletePhet(Phets phet)
	 */
	@Override
	public void deletePhet(Phets phet) {
		// TODO Auto-generated method stub
		
		phetRepo.deletePhet(phet.getPhetId());
		
	}

}

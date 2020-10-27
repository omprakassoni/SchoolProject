/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of Role Service Interface to define method for database operation.
 */

package com.adminportal.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.domain.RoleDetail;
import com.adminportal.repository.RoleRepository;
import com.adminportal.service.RoleDetailService;
/**
 * Default implementation of the {@link com.adminportal.service.RoleDetailService} interface.  
 * @author om prakash
 *
 */
@Service
public class RoleDetailServiceImpl implements RoleDetailService {

	
	@Autowired
	RoleRepository roleRepo;
	
	/**
	 * @see com.adminportal.service.RoleDetailService#findByRoleName(String sub)
	 */
	@Override
	public RoleDetail findByRoleName(String sub) {
		
		RoleDetail local=roleRepo.findByroleName(sub);
		
		return local;
	}

	/**
	 * @see com.adminportal.service.RoleDetailService#countRow() 
	 */
	@Override
	public int countRow() {
		
		return (int) roleRepo.count();
	}


	/**
	 * @see com.adminportal.service.RoleDetailService#save(RoleDetail role)
	 */
	@Override
	public RoleDetail save(RoleDetail role) {
		  return roleRepo.save(role);
		
	}

}

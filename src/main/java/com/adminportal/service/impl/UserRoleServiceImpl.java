package com.adminportal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.repository.UserRoleRepository;
import com.adminportal.service.UserRoleService;

/**
 * Default implementation of the {@link com.adminportal.service.UserRoleService} interface.  
 * @author om prakash
 *
 */
@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleRepository userRoleRepo;
	
	/**
	 * @see com.adminportal.service.UserRoleService#countRow()
	 */
	@Override
	public int countRow() {
	
		return (int) userRoleRepo.count();
	}

}

package com.adminportal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.repository.UserRoleRepository;
import com.adminportal.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleRepository userRoleRepo;
	
	@Override
	public int countRow() {
	
		return (int) userRoleRepo.count();
	}

}

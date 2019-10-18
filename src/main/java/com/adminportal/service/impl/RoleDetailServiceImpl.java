/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.domain.RoleDetail;
import com.adminportal.repository.RoleRepository;
import com.adminportal.service.RoleDetailService;

@Service
public class RoleDetailServiceImpl implements RoleDetailService {

	
	@Autowired
	RoleRepository roleRepo;
	
	@Override
	public RoleDetail findByRoleName(String sub) {
		
		RoleDetail local=roleRepo.findByroleName(sub);
		
		return local;
	}

}

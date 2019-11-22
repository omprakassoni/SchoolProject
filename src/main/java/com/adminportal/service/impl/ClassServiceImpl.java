/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of Class Service Interface to define method for database operation.
 */

package com.adminportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.content.Class;
import com.adminportal.repository.ClassRepository;
import com.adminportal.service.ClassService;

@Service
public class ClassServiceImpl implements ClassService{
	
	@Autowired
	private ClassRepository classRepo;

	@Override
	public Class findByClassName(String className) {
		
		Class localClass=classRepo.findByclassName(className);
		
		return localClass;
	}

	@Override
	public List<Class> findAll() {
		
		List<Class> local=(List<Class>) classRepo.findAll();
		
		return local;
	}

	@Override
	public Class save(Class temp) {
		
		
		Class local=classRepo.save(temp);
		return local;
	}

	

	
}

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
	public Class findByClassName(int className) {
		
		Class localClass=classRepo.findByclassName(className);
		
		return localClass;
	}

	@Override
	public List<Class> findAll() {
		
		List<Class> local=(List<Class>) classRepo.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.ASC, "className"));
		
		return local;
	}

	@Override
	public Class save(Class temp) {
		
		
		Class local=classRepo.save(temp);
		return local;
	}

	@Override
	public int countRow() {
		
		
		return (int) classRepo.count();
	}

	

	
}

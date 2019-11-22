/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.repository;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.content.Class;

public interface ClassRepository extends CrudRepository<Class, Integer>{
	
	Class findByclassName(String clas_name);	// fetching Class based on classNAme
	
}

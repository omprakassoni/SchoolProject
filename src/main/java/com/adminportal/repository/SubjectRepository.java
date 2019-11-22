/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.repository;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.content.Class;
import com.adminportal.content.Subject;
import com.adminportal.content.SubjectClassMapping;

public interface SubjectRepository extends CrudRepository<Subject, Integer>{
	
	Subject findBysubName(String subName);		// list subject based on subject name
	

}

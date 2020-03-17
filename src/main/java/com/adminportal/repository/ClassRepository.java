/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.adminportal.content.Class;

public interface ClassRepository extends JpaRepository<Class, Integer>{
	
	Class findByclassName(int clas_name);	// fetching Class based on classNAme
	
	
}

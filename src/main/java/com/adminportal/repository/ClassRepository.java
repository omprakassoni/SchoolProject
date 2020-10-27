/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.adminportal.content.Class;

/**
 * This Interface Extend JPARepository to handle all database operation related to Class
 * @author om prakash
 *
 */
public interface ClassRepository extends JpaRepository<Class, Integer>{
	
	/**
	 * Find class object based on classNAme given as input parameter
	 * @param clas_name class name in integer
	 * @return class Object
	 */
	Class findByclassName(int clas_name);	// fetching Class based on classNAme
	
	
}

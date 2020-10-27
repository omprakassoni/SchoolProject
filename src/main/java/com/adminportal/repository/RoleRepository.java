/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.repository;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.domain.RoleDetail;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Role data
 * @author om prakash
 *
 */
public interface RoleRepository extends CrudRepository<RoleDetail, Integer> {
	
	/**
	 * Find role object given role name as input parameter
	 * @param name name of role like (Learner,Teacher etc)
	 * @return RoleDetail Object
	 */
	RoleDetail findByroleName(String name);  // fetching role based on rolename
 
}

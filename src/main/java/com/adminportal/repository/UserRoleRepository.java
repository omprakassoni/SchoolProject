/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.adminportal.domain.User;
import com.adminportal.domain.UserRole;

/**
 * This Interface Extend CrudRepository to handle all database operation related to USERROLE data
 * @author om prakash
 *
 */
public interface UserRoleRepository extends CrudRepository<UserRole,Integer> {
	
	@Query("from UserRole U where U.user=?1")  
	void deleteByuser(User usr);
}

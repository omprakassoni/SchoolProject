/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.repository;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.domain.RoleDetail;


public interface RoleRepository extends CrudRepository<RoleDetail, Integer> {
	
	RoleDetail findByroleName(String name);

}

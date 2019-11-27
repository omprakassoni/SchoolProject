/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Role interacting with its repository for database operation
 */

package com.adminportal.service;

import com.adminportal.domain.RoleDetail;

public interface RoleDetailService {
	
	RoleDetail findByRoleName(String sub);
	
	int countRow();
	
	void save(RoleDetail role);

}

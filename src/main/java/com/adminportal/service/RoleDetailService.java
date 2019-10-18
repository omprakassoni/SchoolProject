/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.service;

import com.adminportal.domain.RoleDetail;

public interface RoleDetailService {
	
	RoleDetail findByRoleName(String sub);

}

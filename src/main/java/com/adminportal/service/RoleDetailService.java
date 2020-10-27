 /*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Role interacting with its repository for database operation
 */

package com.adminportal.service;

import com.adminportal.domain.RoleDetail;

/**
 * This interface has all the method declaration related to RoleDetail database operation
 * @author om prakash
 *
 */
public interface RoleDetailService {
	
	/**
	 * Find RoleDetail object using role name
	 * @param sub Role name
	 * @return RoleDetail object
	 * @throws Exception
	 */
	RoleDetail findByRoleName(String sub) throws Exception;
	
	/**
	 * Count total number of roleDetail object from database
	 * @return integer
	 */
	int countRow();
	
	/**
	 * Persist roleDetail object into database
	 * @param role roleDetail object
	 * @return RoleDeatil object
	 */
	RoleDetail save(RoleDetail role);

}

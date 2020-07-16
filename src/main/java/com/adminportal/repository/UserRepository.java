/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.adminportal.domain.User;



public interface UserRepository extends CrudRepository<User, Integer> {
	
	
	User findByemail(String username);  /// user by Email
	
	@Query("from User U where U.email=?1 and U.password=?2")		//check for existing user
	User existsByUser(String email,String password);
	
	@Modifying
	@Query("update User set Registered=?1 where id=?2")				// enabling user
	int disableuser(int registered,int id);
	
	@Modifying
	@Query("update User set approveTeacherFlag=?1 where id=?2")
	int updateApproveTeacher(int approve,int id);
	
	boolean existsByemail(String email);							
	
	@Modifying
	@Query("update User set password=?1 where id=?2")			// update password
	int updateUserPassword(String password,int id);
	
	User findBytoken(String token);
	


}

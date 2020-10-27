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


/**
 * This Interface Extend CurdRepository to handle all database operation related to USER data
 * @author om prakash
 *
 */
public interface UserRepository extends CrudRepository<User, Integer> {
	
	/**
	 * Find user object given email as input parameter
	 * @param username Email in String format
	 * @return User object
	 */
	User findByemail(String username);  /// user by Email
	
	/**
	 * Find User object given email and password as input parameter
	 * @param email email of user
	 * @param password password used during registration
	 * @return User Object
	 */
	@Query("from User U where U.email=?1 and U.password=?2")		//check for existing user
	User existsByUser(String email,String password);
	
	/**
	 * update user registered value (visibility in application)
	 * @param registered value to be set
	 * @param id User ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update User set Registered=?1 where id=?2")				// enabling user
	int disableuser(int registered,int id);
	
	/**
	 * update User's teacher flag given user ID
	 * @param approve value to be set
	 * @param id User's ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update User set approveTeacherFlag=?1 where id=?2")
	int updateApproveTeacher(int approve,int id);
	
	/**
	 * checks whether user exist using email given as input argument
	 * @param email email of user
	 * @return Boolean value
	 */
	boolean existsByemail(String email);							
	
	/**
	 * Update password for user 
	 * @param password new password to be updated
	 * @param id User ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update User set password=?1 where id=?2")			// update password
	int updateUserPassword(String password,int id);
	
	/**
	 * Update user basic details
	 * @param fname First name of user to be updated
	 * @param lname Last name of user to be updated
	 * @param id USer's ID
	 * @return number of record updated
	 */
	@Modifying
	@Query("update User set fname=?1,lname=?2 where id=?3")			// update password
	int updateUserDetails(String fname,String lname,int id);
	
	/**
	 * Find user based on Token used for retrieving password
	 * @param token String value
	 * @return User Object
	 */
	User findBytoken(String token);
	


}

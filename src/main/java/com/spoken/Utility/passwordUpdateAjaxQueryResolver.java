/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : This is a Helper Class to fetch password request change Related data from UI on ajax Call.
 */

package com.spoken.Utility;
/**
 * Utility class to update password of user
 * @author om Prakash
 *
 */
public class passwordUpdateAjaxQueryResolver {
	/**
	 * A new password given by User to be updated.
	 */
	String password;
	
	/**
	 * User current Password.
	 */
	String currentPassword;
	
	
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	int userId;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	

}

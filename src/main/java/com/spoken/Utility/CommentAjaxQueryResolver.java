/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : This is a Helper Class to fetch Comment Related data from UI on ajax Call.
 */

package com.spoken.Utility;
/**
 * A Utility Class to Store comment data made in application.
 * @author om prakash
 *
 */
public class CommentAjaxQueryResolver {
	
	/**
	 * Comment ID representing unique comment stored
	 */
	int comId;
	/**
	 * Actual comment 
	 */
	String comment;
	/**
	 * Name of person who made this comment
	 */
	String userName;
	/**
	 * Date on which comment is made.
	 */
	String dateReceived;
	
	/**
	 * Default Constructor
	 */
	public CommentAjaxQueryResolver() {}
	
	
	/**
	 * Parameterized Constructor
	 * @param comId integer value representing unique comment ID
	 * @param comment Actual comment in String format
	 * @param userName User name who made comment in String.
	 * @param dateReceived Date on which comment is made in string format
	 */
	public CommentAjaxQueryResolver(int comId, String comment, String userName, String dateReceived) {
		
		this.comId = comId;
		this.comment = comment;
		this.userName = userName;
		this.dateReceived = dateReceived;
	}


	/**
	 * Getter method for Comment ID
	 * @return int
	 */
	public int getComId() {
		return comId;
	}
	/**
	 * Setter method for Comment ID
	 * @param comId
	 */
	public void setComId(int comId) {
		this.comId = comId;
	}
	/**
	 * Getter method for Actual comment
	 * @return String
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * Setter method for Actual Comment
	 * @param comment
	 */
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * Getter method for Getting name of user who made Comment
	 * @return String
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * Setter method for username
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * Getter method for date to which comment made
	 * @return String
	 */
	public String getDateReceived() {
		return dateReceived;
	}
	/**
	 * Setter method for date
	 * @param dateReceived
	 */
	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;
	}
	
	

}

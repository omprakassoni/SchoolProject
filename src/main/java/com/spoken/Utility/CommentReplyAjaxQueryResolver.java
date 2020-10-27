/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : This is a Helper Class to fetch CommentReply Related data from UI on ajax Call.
 */

package com.spoken.Utility;

/**
 * This is utility Class to resolve data in ajax call
 * @author om prakash
 *
 */
public class CommentReplyAjaxQueryResolver {
	/**
	 * Comment ID 
	 */
	int id;
	/**
	 * Actual Comment
	 */
	String comment;
	
	boolean reply;
	boolean admin;
	
	/**
	 * Getter method for Comment ID
	 * @return int
	 */
	public int getId() {
		return id;
	}
	/**
	 * Setter method for Comment ID
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Getter method for Comment
	 * @return
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * Setter method for Comment
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * to check whether comment is being replied or not?
	 * @return boolean
	 */
	public boolean isReply() {
		return reply;
	}
	/**
	 * setting reply
	 * @param reply
	 */
	public void setReply(boolean reply) {
		this.reply = reply;
	}
	
	/**
	 * to check whether admin or not?
	 * @return
	 */
	public boolean isAdmin() {
		return admin;
	}
	
	/**
	 * setting admin
	 * @param admin
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
	

}

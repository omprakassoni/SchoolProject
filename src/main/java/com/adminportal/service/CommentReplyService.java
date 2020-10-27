/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Comment-Reply interacting with its repository for database operation
 */
package com.adminportal.service;

import java.util.List;

import com.adminportal.content.Comment;
import com.adminportal.content.CommentReply;

/**
 * This interface has all the method declaration related to Comment Reply's database operation
 * @author om prakash
 *
 */
public interface CommentReplyService {
	
	/**
	 * Find all the comment Reply object given main comment as input parameter
	 * @param com A comment object
	 * @return List of commentReply object
	 */
	List<CommentReply> getReplyOnComment(Comment com);
	
	/**
	 * count total number of commentReply object
	 * @return integer value
	 */
	int  countRow();

}

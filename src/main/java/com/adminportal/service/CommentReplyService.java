/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Comment-Reply interacting with its repository for database operation
 */
package com.adminportal.service;

import java.util.List;

import com.adminportal.content.Comment;
import com.adminportal.content.CommentReply;

public interface CommentReplyService {
	
	List<CommentReply> getReplyOnComment(Comment com);
	
	int  countRow();

}

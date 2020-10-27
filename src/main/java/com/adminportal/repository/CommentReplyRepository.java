/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */
package com.adminportal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.content.Comment;
import com.adminportal.content.CommentReply;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Comment Reply
 * @author om prakash
 *
 */
public interface CommentReplyRepository extends CrudRepository<CommentReply, Integer> {

	/**
	 * FInd list of comment Reply based on single comment given as input parameter
	 * @param com A Comment object
	 * @return List of Comment Reply
	 */
	List<CommentReply> findAllBycomment(Comment com); // fetching list of commentReply based on single comment 
}

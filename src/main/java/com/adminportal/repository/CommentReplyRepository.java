/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */
package com.adminportal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.content.Comment;
import com.adminportal.content.CommentReply;

public interface CommentReplyRepository extends CrudRepository<CommentReply, Integer> {

	List<CommentReply> findAllBycomment(Comment com); // fetching list of commentReply based on single comment 
}

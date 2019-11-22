/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of comment reply Service Interface to define method for database operation.
 */
package com.adminportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.content.Comment;
import com.adminportal.content.CommentReply;
import com.adminportal.repository.CommentReplyRepository;
import com.adminportal.service.CommentReplyService;

@Service
public class CommentReplyServiceImpl implements CommentReplyService{

	@Autowired
	private CommentReplyRepository comRepRepo;
	
	@Override
	public List<CommentReply> getReplyOnComment(Comment com) {
		
		
		return comRepRepo.findAllBycomment(com);
	}

	
	@Override
	public int countRow() {
		
		return (int) comRepRepo.count();
	}

}

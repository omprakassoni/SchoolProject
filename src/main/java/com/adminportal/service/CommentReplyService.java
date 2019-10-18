package com.adminportal.service;

import java.util.List;

import com.adminportal.content.Comment;
import com.adminportal.content.CommentReply;

public interface CommentReplyService {
	
	List<CommentReply> getReplyOnComment(Comment com);
	
	int  countRow();

}

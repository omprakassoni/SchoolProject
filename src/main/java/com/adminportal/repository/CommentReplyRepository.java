package com.adminportal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.content.Comment;
import com.adminportal.content.CommentReply;

public interface CommentReplyRepository extends CrudRepository<CommentReply, Integer> {

	List<CommentReply> findAllBycomment(Comment com);
}

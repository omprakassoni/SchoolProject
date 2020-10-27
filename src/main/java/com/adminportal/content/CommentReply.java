
/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : This is Comment Reply modal to capture reply of each comment made earlier from various user and then persist to database.
 */

package com.adminportal.content;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.adminportal.domain.User;
/**
 * This class Represent Entity which stores CommentReply
 * @author om prakash
 *
 */
@Entity
@Table(name="Comment_Reply")
public class CommentReply {

	/**
	 * A unique ID representing single CommentReply
	 */
	@Id
	@Column(name="com_reply_Id",nullable = false,updatable = false)
	private int commentReplyId;
	
	/**
	 * Actual Reply to Comment
	 */
	@Column(name = "comment",nullable = false)
	private String commentReply;
	
	/**
	 * Date on which reply is made.
	 */
	@Column(name = "date_added",nullable = false,updatable = false)
	private Timestamp dateAdded;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="comment_id")
	private Comment comment;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	public CommentReply() {}
	


	public CommentReply(int commentReplyId, String commentReply, Timestamp dateAdded, Comment comment, User user) {
		
		this.commentReplyId = commentReplyId;
		this.commentReply = commentReply;
		this.dateAdded = dateAdded;
		this.comment = comment;
		this.user = user;
	}


	public int getCommentReplyId() {
		return commentReplyId;
	}


	public void setCommentReplyId(int commentReplyId) {
		this.commentReplyId = commentReplyId;
	}


	public String getCommentReply() {
		return commentReply;
	}


	public void setCommentReply(String commentReply) {
		this.commentReply = commentReply;
	}


	public Timestamp getDateAdded() {
		return dateAdded;
	}


	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}


	public Comment getComment() {
		return comment;
	}


	public void setComment(Comment comment) {
		this.comment = comment;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	
}

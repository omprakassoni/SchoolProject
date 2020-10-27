
/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : This is Comment Modal for capturing comment from various USer from View and subsequently persist same to database.
 */
package com.adminportal.content;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.adminportal.domain.User;

/**
 * This class Represent Entity which stores Comment made in system under various resources
 * @author om Prakash
 *
 */
@Entity
@Table(name="comment")
public class Comment {

	/**
	 * A unique ID representing single Comment
	 */
	@Id
	@Column(name="comment_id",nullable = false,updatable = false)
	private int commentid;
	
	/**
	 * DAte on which comment is made
	 */
	@Column(name="date_added",nullable = false,updatable = false)
	private Timestamp dateAdded;
	
	/**
	 * Actual Comment
	 */
	@Column(name="comment",nullable = false)
	private String comment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "comment",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<CommentReply> commentreply=new HashSet<CommentReply>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="article_id",updatable = false)
	private ArticleExternal article;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="quiz_id",updatable = false)
	private QuizQuestion quiz;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="lesson_id",updatable = false)
	private LessonPlan lesson;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="video_id",updatable = false)
	private VideoExternal video;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="phet_id",updatable = false)
	private Phets phet;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="document_id",updatable = false)
	private DocumentExternal document;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ConceptMap_id",updatable = false)
	private ConceptMap conceptMap;

	public int getCommentid() {
		return commentid;
	}

	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Comment() {}

	public Comment(int commentid, Timestamp dateAdded, String comment, User user, VideoExternal video) {
		
		this.commentid = commentid;
		this.dateAdded = dateAdded;
		this.comment = comment;
		this.user = user;
		this.video = video;
	}
	
	


	public Comment(int commentid, Timestamp dateAdded, String comment, User user, DocumentExternal document) {
		
		this.commentid = commentid;
		this.dateAdded = dateAdded;
		this.comment = comment;
		this.user = user;
		this.document = document;
	}

	
	
	
	
	public Comment(int commentid, Timestamp dateAdded, String comment, User user, Phets phet) {
		
		this.commentid = commentid;
		this.dateAdded = dateAdded;
		this.comment = comment;
		this.user = user;
		this.phet = phet;
	}
	
	

	public Comment(int commentid, Timestamp dateAdded, String comment, User user, LessonPlan lesson) {
		
		this.commentid = commentid;
		this.dateAdded = dateAdded;
		this.comment = comment;
		this.user = user;
		this.lesson = lesson;
	}
	
	

	public Comment(int commentid, Timestamp dateAdded, String comment, User user, QuizQuestion quiz) {
		
		this.commentid = commentid;
		this.dateAdded = dateAdded;
		this.comment = comment;
		this.user = user;
		this.quiz = quiz;
	}
	
	

	public Comment(int commentid, Timestamp dateAdded, String comment, User user, ArticleExternal article) {
	
		this.commentid = commentid;
		this.dateAdded = dateAdded;
		this.comment = comment;
		this.user = user;
		this.article = article;
	}
	
	public Comment(int commentid, Timestamp dateAdded, String comment, User user, ConceptMap concept) {
		
		this.commentid = commentid;
		this.dateAdded = dateAdded;
		this.comment = comment;
		this.user = user;
		this.conceptMap = concept;
	}

	
	
	public Set<CommentReply> getCommentreply() {
		return commentreply;
	}

	public void setCommentreply(Set<CommentReply> commentreply) {
		this.commentreply = commentreply;
	}
	
	
	
}

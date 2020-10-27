/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : This is Quiz Modal to store Quiz related information like question and answer.
 */

package com.adminportal.content;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
 * This class Represent Entity which stores Quiz Resource data
 * @author om Prakash
 *
 */
@Entity
@Table(name="content")
public class QuizQuestion {

	/**
	 * A unique ID representing single Quiz Resource
	 */	
	@Id
	@Column(name="content_id",nullable = false,updatable = false)
	private int quizQuestionId;
	
	/**
	 * Resource Type like article,Phet etc
	 */
	@Column(name="type",nullable = false)
	private String type;
	
	/**
	 * Date on which resource added
	 */
	@Column(name="date_added",updatable = false,nullable = false)
	private Timestamp dateAdded;
	
	/**
	 * Date on which resource is modified
	 */
	@Column(name="date_modified",nullable = false)
	private Timestamp dateModified;
	
	/**
	 * Path to store Question_Paper
	 */
	@Column(name="question_path")
	private String question;
	
	/**
	 * Path to store Answer_paper
	 */
	@Column(name="answer_path")
	private String answer;
	
	@Column(name="status",nullable = false)
	private int status;
	
	@Column(name="acceptedByAdmin",nullable = false)
	private int acceptedByAdmin;
	

	/**
	 * A description of resource
	 */
	@Column(name="remarks")
	private String remark;
	
	/**
	 * date on which resource got approved.
	 */
	@Column(name="date_approved")
	private Timestamp dateApproved;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="topic_id")
	private Topic topic;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Comment> comment=new ArrayList<Comment>();
	
	public QuizQuestion() {}

	public QuizQuestion(int id,String type, Timestamp dateAdded, Timestamp dateModified, String question, String answer,
			int status,int acceptedByAdmin, String remark, Timestamp dateApproved, Topic topic, User user) {

		this.quizQuestionId=id;
		this.type = type;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.question = question;
		this.answer = answer;
		this.status = status;
		this.acceptedByAdmin=acceptedByAdmin;
		this.remark = remark;
		this.dateApproved = dateApproved;
		this.topic = topic;
		this.user = user;
	}

	public int getQuizQuestionId() {
		return quizQuestionId;
	}

	public void setQuizQuestionId(int quizQuestionId) {
		this.quizQuestionId = quizQuestionId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Timestamp getDateModified() {
		return dateModified;
	}

	public void setDateModified(Timestamp dateModified) {
		this.dateModified = dateModified;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int isStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getDateApproved() {
		return dateApproved;
	}

	public void setDateApproved(Timestamp dateApproved) {
		this.dateApproved = dateApproved;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getAcceptedByAdmin() {
		return acceptedByAdmin;
	}

	public void setAcceptedByAdmin(int acceptedByAdmin) {
		this.acceptedByAdmin = acceptedByAdmin;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}
	
	
}

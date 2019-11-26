/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : this is LEsson Plan Modal to store data related to lesson plan into database.
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

@Entity
@Table(name="content")
public class LessonPlan {

	@Id
	@Column(name="content_id",nullable = false,updatable = false)
	private int lessonPlanId;
	
	@Column(name="type",nullable = false)
	private String type;
	
	@Column(name="date_added",updatable = false,nullable = false)
	private Timestamp dateAdded;
	
	@Column(name="date_modified",nullable = false)
	private Timestamp dateModified;
	
	@Column(name="lesson_plan")
	private String lessonPlan;
	
	@Column(name="status",nullable = false)
	private int status;
	
	@Column(name="acceptedByAdmin",nullable = false)
	private int acceptedByAdmin;
	
	@Column(name="date_approved")
	private Timestamp dateApproved;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="topic_id")
	private Topic topic;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "lesson",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Comment> comment=new ArrayList<Comment>();
	
	public LessonPlan () {}
	
	public LessonPlan(int lessonPlanId, String type, Timestamp dateAdded, Timestamp dateModified, String lessonPlan,
			int status,int acceptedByAdmin, Timestamp dateApproved, Topic topic, User user) {
		super();
		this.lessonPlanId = lessonPlanId;
		this.type = type;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.lessonPlan = lessonPlan;
		this.status = status;
		this.acceptedByAdmin=acceptedByAdmin;
		this.dateApproved = dateApproved;
		this.topic = topic;
		this.user = user;
	}

	public int getLessonPlanId() {
		return lessonPlanId;
	}

	public void setLessonPlanId(int lessonPlanId) {
		this.lessonPlanId = lessonPlanId;
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

	public String getLessonPlan() {
		return lessonPlan;
	}

	public void setLessonPlan(String lessonPlan) {
		this.lessonPlan = lessonPlan;
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

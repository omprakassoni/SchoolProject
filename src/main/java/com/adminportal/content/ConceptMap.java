/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : This is Comment Concept-map modal to capture all concept-map related data and then persist same to database.
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
 * This class Represent Entity which stores ConceptMap Resource data
 * @author om Prakash
 *
 */
@Entity
@Table(name="content")
public class ConceptMap {
	
	/**
	 * A unique ID representing single ConceptMap Resource
	 */
	@Id
	@Column(name="content_id",nullable = false,updatable = false)
	private int concepMapid;
	
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
	 * A proper link of resource
	 */
	@Column(name="url")
	private String url;
	

	/**
	 * A long description of resource
	 */
	@Column(name="description",length = 10000)
	private String description;
	
	/**
	 * date on which resource got approved.
	 */
	@Column(name="date_approved")
	private Timestamp dateApproved;
	
	@Column(name="status",nullable = false)
	private int status;
	
	@Column(name="acceptedByAdmin",nullable = false)
	private int acceptedByAdmin;
	
	@Column(name="remarks")
	private String remark;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="topic_id")
	private Topic topic;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "conceptMap",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Comment> comment=new ArrayList<Comment>();
	
	public ConceptMap() {}



	public Timestamp getDateApproved() {
		return dateApproved;
	}



	public void setDateApproved(Timestamp dateApproved) {
		this.dateApproved = dateApproved;
	}



	public ConceptMap(int concepMapid, String type, Timestamp dateAdded, Timestamp dateModified, String url,
			String description, int status,int acceptedByAdmin, String remark, Topic topic, User user) {
		super();
		this.concepMapid = concepMapid;
		this.type = type;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.url = url;
		this.description = description;
		this.status = status;
		this.acceptedByAdmin=acceptedByAdmin;
		this.remark = remark;
		this.topic = topic;
		this.user = user;
	}



	public int getConcepMapid() {
		return concepMapid;
	}

	public void setConcepMapid(int concepMapid) {
		this.concepMapid = concepMapid;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}



	public int getAcceptedByAdmin() {
		return acceptedByAdmin;
	}



	public void setAcceptedByAdmin(int acceptedByAdmin) {
		this.acceptedByAdmin = acceptedByAdmin;
	}

	
	
	
}

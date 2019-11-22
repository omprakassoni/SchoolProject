/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : This Document Modal to capture All document related data and then persist same to database.
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
public class DocumentExternal {

	@Id
	@Column(name="content_id",nullable = false,updatable = false)
	private int documentId;
	
	@Column(name="type",nullable = false)
	private String type;
	
	@Column(name="date_added",updatable = false,nullable = false)
	private Timestamp dateAdded;
	
	@Column(name="date_modified",nullable = false)
	private Timestamp dateModified;
	
	@Column(name="description",length = 10000)
	private String description;
	
	@Column(name="source")
	private String source;
	
	@Column(name="url")
	private String url;
	
	@Column(name="status",nullable = false)
	private int status;
	
	@Column(name="date_approved")
	private Timestamp dateApproved;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="topic_id")
	private Topic topic;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "document",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Comment> comment=new ArrayList<Comment>();
	
	public DocumentExternal() {}

	public DocumentExternal(int documentId, String type, Timestamp dateAdded, Timestamp dateModified,
			String description, String source, String url, int status, Timestamp dateApproved, Topic topic,
			User user) {
	
		this.documentId = documentId;
		this.type = type;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.description = description;
		this.source = source;
		this.url = url;
		this.status = status;
		this.dateApproved = dateApproved;
		this.topic = topic;
		this.user = user;
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
	
	
}

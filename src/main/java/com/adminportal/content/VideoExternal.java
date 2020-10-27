/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Video related data will get saved into database.
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
 * This class Represent Entity which stores Video Resource data
 * @author om
 *
 */
@Entity
@Table(name="content")
public class VideoExternal {
	
	/**
	 * A unique ID representing single Video Resource
	 */
	@Id
	@Column(name="content_id",nullable = false,updatable = false)
	private int videoId;
	
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
	 * Date on which resource Modified
	 */
	@Column(name="date_modified",nullable = false)
	private Timestamp dateModified;
	
	/**
	 * A long description On Video
	 */
	@Column(name="description",length = 10000)
	private String description;
	
	/**
	 * Name of Reference from where video is being used. 
	 */
	@Column(name="source")
	private String source;
	
	/**
	 * A proper Url of video
	 */
	@Column(name="url")
	private String url;
	
	@Column(name="status",nullable = false)
	private int status;
	
	@Column(name="acceptedByAdmin",nullable = false)
	private int acceptedByAdmin;
	
	/**
	 * Date on which resource got approved from admin
	 */
	@Column(name="date_approved")
	private Timestamp dateApproved;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="topic_id")
	private Topic topic;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "video",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Comment> comment=new ArrayList<Comment>();
	
	public VideoExternal() {}

	public VideoExternal(int videoId, String type, Timestamp dateAdded, Timestamp dateModified, String description,
			String source, String url, int status, int acceptedByAdmin,Timestamp dateApproved, Topic topic, User user) {
		
		this.videoId = videoId;
		this.type = type;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.description = description;
		this.source = source;
		this.url = url;
		this.status = status;
		this.acceptedByAdmin=acceptedByAdmin;
		this.dateApproved = dateApproved;
		this.topic = topic;
		this.user = user;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public int getVideoId() {
		return videoId;
	}

	public void setVideoId(int videoId) {
		this.videoId = videoId;
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

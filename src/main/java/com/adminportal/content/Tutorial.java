package com.adminportal.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.adminportal.domain.User;

@Entity
@Table(name = "Tutorial")
public class Tutorial {
	
	@Id
	@Column(updatable = false,nullable = false)
	private int tutorialId;
	
	@Column(nullable = false)
	private int stfossId;
	
	@Column(nullable = false)
	private int stlanguageId;
	
	@Column(nullable = false)
	private String stLanguageName;
	
	@Column(nullable = false)
	private int stVideoId;
	
	@Column(nullable = false,length = 10000)
	private String stVideoName;
	
	@Column(nullable = false)
	private int status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="topic_id")
	private Topic topic;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;

	public int getTutorialId() {
		return tutorialId;
	}

	public void setTutorialId(int tutorialId) {
		this.tutorialId = tutorialId;
	}

	public int getFossId() {
		return stfossId;
	}

	public void setFossId(int fossId) {
		this.stfossId = fossId;
	}

	public int getLanguageId() {
		return stlanguageId;
	}

	public void setLanguageId(int languageId) {
		this.stlanguageId = languageId;
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

	public String getStLanguageName() {
		return stLanguageName;
	}

	public void setStLanguageName(String stLanguageName) {
		this.stLanguageName = stLanguageName;
	}

	public int getStVideoId() {
		return stVideoId;
	}

	public void setStVideoId(int stVideoId) {
		this.stVideoId = stVideoId;
	}

	public String getStVideoName() {
		return stVideoName;
	}

	public void setStVideoName(String stVideoName) {
		this.stVideoName = stVideoName;
	}
	
	
}

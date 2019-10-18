package com.adminportal.content;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "Event")
public class Events {
	
	@Id
	@Column(nullable = false,updatable = false)
	private int eventId;
	
	@Column(nullable = false)
	private String headline;
	
	@Column(nullable = false)
	@Length(max = 10000)
	private String description;
	
	@Column(nullable = false,updatable = false)
	private Timestamp dateAdded;
	
	@Column(nullable = false)
	private Date dateToHappen;

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Date getDateToHappen() {
		return dateToHappen;
	}

	public void setDateToHappen(Date dateToHappen) {
		this.dateToHappen = dateToHappen;
	}
	
	
	

}

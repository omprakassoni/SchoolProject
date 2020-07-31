/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : This is Event modal to persist Event related data into database.
 */
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
	private String coordName;
	
	@Column(nullable = false)
	private int coordContactNo;
	
	@Column(nullable = false,length = 10000)
	@Length(max = 10000)
	private String description;
	
	@Column(nullable = false,updatable = false)
	private Timestamp dateAdded;
	
	@Column(nullable = false)
	private Date dateToHappenStart;
	
	@Column(nullable = false)
	private Date dateToHappenEnd;
	
	@Column(nullable = false)
	private Date registStart;
	
	@Column(nullable = false)
	private Date registEnd;
	
	@Column(nullable = false)
	private String potser_path;
	
	@Column(nullable = false)
	private String mode;
	
	@Column(nullable = false)
	private String location;
	
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


	public String getPotser_path() {
		return potser_path;
	}

	public void setPotser_path(String potser_path) {
		this.potser_path = potser_path;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCoordName() {
		return coordName;
	}

	public void setCoordName(String coordName) {
		this.coordName = coordName;
	}

	public int getCoordContactNo() {
		return coordContactNo;
	}

	public void setCoordContactNo(int coordContactNo) {
		this.coordContactNo = coordContactNo;
	}

	public Date getDateToHappenStart() {
		return dateToHappenStart;
	}

	public void setDateToHappenStart(Date dateToHappenStart) {
		this.dateToHappenStart = dateToHappenStart;
	}

	public Date getDateToHappenEnd() {
		return dateToHappenEnd;
	}

	public void setDateToHappenEnd(Date dateToHappenEnd) {
		this.dateToHappenEnd = dateToHappenEnd;
	}

	public Date getRegistStart() {
		return registStart;
	}

	public void setRegistStart(Date registStart) {
		this.registStart = registStart;
	}

	public Date getRegistEnd() {
		return registEnd;
	}

	public void setRegistEnd(Date registEnd) {
		this.registEnd = registEnd;
	}
	
	
	

}

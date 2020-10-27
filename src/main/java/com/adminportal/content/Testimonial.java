/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Testimonial Modal representating modal for saving testimonial data into database.
 */
package com.adminportal.content;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

/**
 * This class Represent Entity which stores Testimonial Data
 * @author om Prakash
 *
 */
@Entity
@Table(name = "Testimonial")
public class Testimonial {
	
	/**
	 * A unique ID of Testimonial
	 */
	@Id
	@Column(nullable = false,updatable = false)
	private int testimonialId;
	
	/**
	 * Name of person
	 */
	@Column(nullable = false)
	private String name;
	
	/**
	 * Organization of Person
	 */
	@Column(nullable = false)
	private String organization;
	
	/**
	 * A long Description
	 */
	@Column(nullable = false)
	@Length(max = 10000)
	private String description;
	
	/**
	 * Date on which testimonial added
	 */
	@Column(nullable = false,updatable = false)
	private Timestamp dateAdded;
	
	/**
	 * Path of Video file
	 */
	@Column
	private String videoPath;
	
	/**
	 * Path of file data
	 */
	@Column
	private String filePath;
	
	public int getTestimonialId() {
		return testimonialId;
	}

	public void setTestimonialId(int testimonialId) {
		this.testimonialId = testimonialId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
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

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}

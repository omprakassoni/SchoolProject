package com.adminportal.content;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "Testimonial")
public class Testimonial {
	
	@Id
	@Column(nullable = false,updatable = false)
	private int testimonialId;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String organization;
	
	@Column(nullable = false)
	@Length(max = 10000)
	private String description;
	
	@Column(nullable = false,updatable = false)
	private Timestamp dateAdded;
	
	

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

	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}

}

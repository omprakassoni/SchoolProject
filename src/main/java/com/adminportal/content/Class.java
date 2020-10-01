/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : This is Class Modal to persist Class related data into database.
 */
package com.adminportal.content;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.adminportal.domain.User;

@Entity
@Table(name="Class")
public class Class {
	
	
	@Id
	@Column(nullable = false,updatable = false)
	private int class_id;
	
	@Column(name="class_name",nullable = false)
	private int className;
	
	@Column(name="status",nullable = false)
	private boolean status=true;
	
	@Column(name="date_added",nullable = false)
	private Timestamp dateAdded;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy = "standard",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<SubjectClassMapping> subClasMapp=new HashSet<SubjectClassMapping>();
	
	
	
	public Set<SubjectClassMapping> getSubClasMapp() {
		return subClasMapp;
	}
	public void setSubClasMapp(Set<SubjectClassMapping> subClasMapp) {
		this.subClasMapp = subClasMapp;
	}
	
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public int getClassName() {
		return className;
	}
	public void setClassName(int className) {
		this.className = className;
	}
	public Timestamp getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	

}

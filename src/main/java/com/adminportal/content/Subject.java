/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Subject Modal to Store subject related infor into database.
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
@Table(name="Subject")
public class Subject implements Comparable<Subject>{
	
	
	@Id
	@Column(nullable = false,updatable = false)
	private int subId;
	
	@Column(name="sub_name",nullable = false)
	private String subName;
	
	@Column(name="status",nullable = false)
	private boolean status=true;
	
	@Column(name="date_added",nullable = false)
	private Timestamp dateAdded;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;

	@OneToMany(mappedBy ="sub",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<SubjectClassMapping> subClasMapp=new HashSet<SubjectClassMapping>();

	
	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public Set<SubjectClassMapping> getSubClasMapp() {
		return subClasMapp;
	}

	public void setSubClasMapp(Set<SubjectClassMapping> subClasMapp) {
		this.subClasMapp = subClasMapp;
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

	@Override
	public int compareTo(Subject s1) {
		
		return this.subName.compareTo(s1.subName);
	}
	
	

}

package com.spoken.Utility;

import java.sql.Date;
import java.sql.Timestamp;



public class LoadUserLearner {
	
	private int id;
	

	private String email;
	

	private String sex;
	

	private String fname;
	
	
	private String lname;

	private String document;
	
	
	private String schoolName;
	

	private String schoolAddress;
	

	private int pincode;
	
	private Timestamp dateAdded;
	
	
	private Timestamp lastLogin;
	

	private Date dateOfBirth;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getFname() {
		return fname;
	}


	public void setFname(String fname) {
		this.fname = fname;
	}


	public String getLname() {
		return lname;
	}


	public void setLname(String lname) {
		this.lname = lname;
	}


	public String getDocument() {
		return document;
	}


	public void setDocument(String document) {
		this.document = document;
	}


	public String getSchoolName() {
		return schoolName;
	}


	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}


	public String getSchoolAddress() {
		return schoolAddress;
	}


	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}


	public int getPincode() {
		return pincode;
	}


	public void setPincode(int pincode) {
		this.pincode = pincode;
	}


	public Timestamp getDateAdded() {
		return dateAdded;
	}


	public void setDateAdded(Timestamp dateAdded) {
		this.dateAdded = dateAdded;
	}


	public Timestamp getLastLogin() {
		return lastLogin;
	}


	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}


	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
	

}

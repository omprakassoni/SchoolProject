/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : This is Comment feedback/contact form modal to capture visitor information and persist same to database.
 */

package com.adminportal.content;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "contact_Form")
public class ContactForm {

	@Id
	@Column(updatable = false,nullable = false)
	private int contactId;
	
	@Column(updatable = false,nullable = false)
	private String name;
	
	@Column(updatable = false,nullable = false)
	private String email;
	
	@Column(updatable = false,nullable = false,length = 10000)
	private String message;
	
	@Column(updatable = false,nullable = false)
	Timestamp dataAdded;

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getDataAdded() {
		return dataAdded;
	}

	public void setDataAdded(Timestamp dataAdded) {
		this.dataAdded = dataAdded;
	}

	@Override
	public String toString() {
		return "ContactForm [contactId=" + contactId + ", name=" + name + ", email=" + email + ", Message=" + message
				+ ", dataAdded=" + dataAdded + "]";
	}
	
	
	
}

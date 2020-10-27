/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Contact form interacting with its repository for database operation
 */
package com.adminportal.service;

import java.util.List;

import com.adminportal.content.ContactForm;

/**
 * This interface has all the method declaration related to ContactForm database operation
 * @author om prakash
 *
 */
public interface ContactFormService {
	
	/**
	 * List out All the received query from database
	 * @return List of ContactForm object
	 */
	List<ContactForm> getAllMessages();  // fetching list of message (entire)

}

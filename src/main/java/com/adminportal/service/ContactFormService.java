/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Contact form interacting with its repository for database operation
 */
package com.adminportal.service;

import java.util.List;

import com.adminportal.content.ContactForm;

public interface ContactFormService {
	
	List<ContactForm> getAllMessages();  // fetching list of message (entire)

}

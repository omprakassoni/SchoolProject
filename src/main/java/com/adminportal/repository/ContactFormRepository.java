/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */
package com.adminportal.repository;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.content.ContactForm;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Contact Form
 * @author om prakash
 *
 */
public interface ContactFormRepository extends CrudRepository<ContactForm, Integer>{
	
	
}

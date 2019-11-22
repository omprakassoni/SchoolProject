/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */
package com.adminportal.repository;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.content.ContactForm;

public interface ContactFormRepository extends CrudRepository<ContactForm, Integer>{
	
	
}

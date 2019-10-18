package com.adminportal.repository;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.content.ContactForm;

public interface ContactFormRepository extends CrudRepository<ContactForm, Integer>{
	
	
}

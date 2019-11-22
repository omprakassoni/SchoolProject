package com.adminportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.content.ContactForm;
import com.adminportal.repository.ContactFormRepository;
import com.adminportal.service.ContactFormService;

@Service
public class ContactFormServiceImpl implements ContactFormService{

	@Autowired
	private ContactFormRepository contactFormRepo;
	@Override
	public List<ContactForm> getAllMessages() {
		
		return (List<ContactForm>) contactFormRepo.findAll();
		
	}
	

}

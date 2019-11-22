/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of Testimonial Service Interface to define method for database operation.
 */
package com.adminportal.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.Testimonial;
import com.adminportal.repository.TestimonialRepository;
import com.adminportal.service.TestimonialService;

@Service
public class TestimonialServiceImpl implements TestimonialService{
	
	@Autowired
	TestimonialRepository testRepo;

	@Override
	public int getCount() {
		
		return (int) testRepo.count();
	}

	@Override
	public void save(Testimonial temp) {
		testRepo.save(temp);
		
	}

	@Override
	public List<Testimonial> findAll() {
		
		return (List<Testimonial>) testRepo.findAll();
	}

	@Override
	public List<Testimonial> getAlltestimonial() {
		
		return testRepo.getAllTestimonial();
	}

	@Override
	public Testimonial getbyId(int id) {
		
		Optional<Testimonial> local=testRepo.findById(id);
		
		return local.get();
				
	}

	
	@Override
	@Transactional
	public boolean updateTestimonial(String name, String desc, String org, int id) {
		int status=testRepo.updateTestimonial(name, desc, org, id);
		if(status>0) {
			return true;
		}else {
		return false;
		}
	}


}

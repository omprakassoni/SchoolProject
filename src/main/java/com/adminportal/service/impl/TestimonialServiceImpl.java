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

/**
 * Default implementation of the {@link com.adminportal.service.TestimonialService} interface.  
 * @author om prakash
 *
 */
@Service
public class TestimonialServiceImpl implements TestimonialService{
	
	@Autowired
	TestimonialRepository testRepo;

	/**
	 * @see com.adminportal.service.TestimonialService#getCount()
	 */
	@Override
	public int getCount() {
		
		return (int) testRepo.count();
	}

	/**
	 * @see com.adminportal.service.TestimonialService#save(Testimonial temp)
	 */
	@Override
	public void save(Testimonial temp) {
		testRepo.save(temp);
		
	}

	/**
	 * @see com.adminportal.service.TestimonialService#findAll()
	 */
	@Override
	public List<Testimonial> findAll() {
		
		return (List<Testimonial>) testRepo.findAll();
	}

	/**
	 * @see com.adminportal.service.TestimonialService#getAlltestimonial()
	 */
	@Override
	public List<Testimonial> getAlltestimonial() {
		
		return testRepo.getAllTestimonial();
	}

	/**
	 * @see com.adminportal.service.TestimonialService#getbyId(int id)
	 */
	@Override
	public Testimonial getbyId(int id) {
		
		Optional<Testimonial> local=testRepo.findById(id);
		
		return local.get();
				
	}

	/**
	 * @see com.adminportal.service.TestimonialService#updateTestimonial(String name, String desc, String org, int id)
	 */
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

	/**
	 * @see com.adminportal.service.TestimonialService#deleteTestimonail(Testimonial testi)
	 */
	@Override
	public void deleteTestimonail(Testimonial testi) {
		// TODO Auto-generated method stub
		testRepo.deleteTestimonial(testi.getTestimonialId());
		
	}


}

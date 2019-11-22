/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for testimonial interacting with its repository for database operation
 */
package com.adminportal.service;

import java.util.List;

import com.adminportal.content.Testimonial;

public interface TestimonialService {
	
	int getCount();
	
	void save(Testimonial temp);
	
	List<Testimonial> findAll();
	
	List<Testimonial> getAlltestimonial();
	
	Testimonial getbyId(int id);
	
	boolean updateTestimonial(String name,String desc,String org,int id);

}

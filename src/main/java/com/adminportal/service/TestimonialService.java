/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for testimonial interacting with its repository for database operation
 */
package com.adminportal.service;

import java.util.List;

import com.adminportal.content.ConceptMap;
import com.adminportal.content.Testimonial;

/**
 * This interface has all the method declaration related to Testimonial database operation
 * @author om prakash
 *
 */
public interface TestimonialService {
	
	/**
	 * Count total number of testimonial record in database
	 * @return integer
	 */
	int getCount();
	
	/**
	 * persist Testimonial object in database
	 * @param temp testimonial object
	 */
	void save(Testimonial temp);
	
	/**
	 * Find all testimonial record from database
	 * @return list of testimonial object
	 */
	List<Testimonial> findAll();
	
	/**
	 *  Find all testimonial record from database in descending order based in date on which testimonial added 
	 * @return list of testimonial object
	 */
	List<Testimonial> getAlltestimonial();
	
	/**
	 * Find testimonial record from database using testimonial ID
	 * @param id testimonial ID
	 * @return Testimonial object
	 */
	Testimonial getbyId(int id);
	
	/**
	 * Update testimonial attributes given testimonial ID as input argument
	 * @param name Name of person
	 * @param desc Description
	 * @param org Organization
	 * @param id Testimonial ID
	 * @return boolean value denoting successful or failure of database operation
	 */
	boolean updateTestimonial(String name,String desc,String org,int id);
	
	/**
	 * Delete testimonial record from database 
	 * @param testi Testimonial object
	 */
	void deleteTestimonail(Testimonial testi);

}

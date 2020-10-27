package com.adminportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.Testimonial;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Testimonial data
 * @author om prakash
 *
 */
public interface TestimonialRepository extends CrudRepository<Testimonial, Integer>{
	
	/**
	 * Find all testimonial record from database in descending order based in date on which testimonial added 
	 * @return A list of testimonial
	 */
	@Query("from Testimonial t order by t.dateAdded desc")			// fetching list of testimonial 
	List<Testimonial> getAllTestimonial();
	
	
	/**
	 * Update testimionial data given Testimonial ID as one of input parameter
	 * @param name name of person to be updated
	 * @param desc description to be updated
	 * @param org organization name to be updated
	 * @param id testimonial ID
	 * @return number of record updated
	 */
	@Modifying
	@Query("update Testimonial set name=?1,description=?2,organization=?3 where testimonialId=?4")	// updating testimonial
	int updateTestimonial(String name,String desc,String org,int id);
	

	@Modifying
	@Query("delete from Testimonial U where U.testimonialId=?1")
	@Transactional
	void deleteTestimonial(int testiID);

}

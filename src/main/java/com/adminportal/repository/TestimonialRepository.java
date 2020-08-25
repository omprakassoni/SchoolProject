package com.adminportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.Testimonial;

public interface TestimonialRepository extends CrudRepository<Testimonial, Integer>{
	
	@Query("from Testimonial t order by t.dateAdded desc")			// fetching list of testimonial 
	List<Testimonial> getAllTestimonial();
	
	
	@Modifying
	@Query("update Testimonial set name=?1,description=?2,organization=?3 where testimonialId=?4")	// updating testimonial
	int updateTestimonial(String name,String desc,String org,int id);
	
	@Modifying
	@Query("delete from Testimonial U where U.testimonialId=?1")
	@Transactional
	void deleteTestimonial(int testiID);

}

/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.adminportal.content.Class;
import com.adminportal.content.Subject;
import com.adminportal.content.SubjectClassMapping;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

public interface SubjectClassMappingRepository extends CrudRepository<SubjectClassMapping, Integer>{
	
	List<SubjectClassMapping> findBystandard(Class tempClass);			// listing list of object based on class
	
	@Query("from SubjectClassMapping U where U.sub=?1 and U.standard=?2")	// list object based on class and subject
	SubjectClassMapping findBysubAndstandard(Subject subject,Class clas);

	@Query("from SubjectClassMapping U where U.sub=?1")			
	void deleteBysub(Subject sub);
	
	ArrayList<SubjectClassMapping> findBysub(Subject sub);
	
	@Modifying
	@Query("update SubjectClassMapping set status=?1 where sub=?2")
	int updateAllSubject(boolean status,Subject sub);
	
	@Modifying
	@Query("update SubjectClassMapping set status=?1 where standard=?2")
	int updateAllClass(boolean status,Class clas);
	
	

}

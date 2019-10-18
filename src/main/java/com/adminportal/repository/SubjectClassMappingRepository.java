/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.adminportal.content.Class;
import com.adminportal.content.Subject;
import com.adminportal.content.SubjectClassMapping;
import com.adminportal.domain.User;

public interface SubjectClassMappingRepository extends CrudRepository<SubjectClassMapping, Integer>{
	
	List<SubjectClassMapping> findBystandard(Class tempClass);
	
	@Query("from SubjectClassMapping U where U.sub=?1 and U.standard=?2")
	SubjectClassMapping findBysubAndstandard(Subject subject,Class clas);

	@Query("from SubjectClassMapping U where U.sub=?1")
	void deleteBysub(Subject sub);
	
	ArrayList<SubjectClassMapping> findBysub(Subject sub);
}

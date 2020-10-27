/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.adminportal.content.Subject;

/**
 * This Interface Extend JpaRepository to handle all database operation related to Subject data
 * @author om prakash
 *
 */
public interface SubjectRepository extends JpaRepository<Subject, Integer>{
	
	/**
	 * Find Subject object given subject name as input parameter
	 * @param subName name of subject name
	 * @return Subject Object
	 */
	Subject findBysubName(String subName);		// list subject based on subject name
	
	/**
	 * update name of subject name given subject Id as input argument
	 * @param subName new subject name to be updated
	 * @param subId A unique Subject Id 
	 * @return number of record updated
	 */
	@Modifying
	@Query("update Subject set subName=?1 where subId=?2")				//updating Subject Information
	int updateSubjectName(String subName,int subId);
	
}

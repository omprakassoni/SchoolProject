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


public interface SubjectRepository extends JpaRepository<Subject, Integer>{
	
	Subject findBysubName(String subName);		// list subject based on subject name
	
	@Modifying
	@Query("update Subject set subName=?1 where subId=?2")				//updating Subject Information
	int updateSubjectName(String subName,int subId);
	
}

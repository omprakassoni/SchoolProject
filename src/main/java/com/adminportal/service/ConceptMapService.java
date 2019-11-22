/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Concept-map interacting with its repository for database operation
 */
package com.adminportal.service;

import java.sql.Timestamp;
import java.util.List;

import com.adminportal.content.ConceptMap;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

public interface ConceptMapService {
	
	List<ConceptMap> findAll();
	
	ConceptMap findByid(int id);
	
	boolean updateConcept(String desc, String url,String remark,Timestamp date,int id);
	
	int countRow();
	
	boolean EnableConceptContent(int status,int id);
	
	List<ConceptMap> findAllByTopic(Topic topic);
	
	List<ConceptMap> findALlByUser(User usr);

}

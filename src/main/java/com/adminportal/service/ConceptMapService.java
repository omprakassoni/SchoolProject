/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Concept-map interacting with its repository for database operation
 */
package com.adminportal.service;

import java.sql.Timestamp;
import java.util.List;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

/**
 * This interface has all the method declaration related to ConceptMap database operation
 * @author om prakash
 *
 */
public interface ConceptMapService {
	
	/**
	 * lists out all the conceptMap object from database
	 * @return list of ConceptMap object
	 */
	List<ConceptMap> findAll();
	
	/**
	 * Find conceptMap object given ConceptMap ID
	 * @param id ConceptMap ID
	 * @return ConceptMap object
	 */
	ConceptMap findByid(int id);
	
	/**
	 * Update conceptMap's description, Url, remark, date modified value given ConceptMap ID
	 * @param desc Description to be set
	 * @param url path to be set
	 * @param remark remark heading
	 * @param date current date
	 * @param id ConceptMap ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean updateConcept(String desc, String url,String remark,Timestamp date,int id);
	
	/**
	 * Update conceptMap's description,remark, date modified value given ConceptMap ID
	 * @param desc description to be set
	 * @param remark remark to be set
	 * @param date current date
	 * @param id ConceptMap ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean updateConceptDesc(String desc,String remark,Timestamp date,int id);
	
	/**
	 * Count total number of conceptMap record in database
	 * @return integer
	 */
	int countRow();
	
	/**
	 * Update conceptMap's status value given ConceptMap ID
	 * @param status status value to be set
	 * @param id ConceptMap ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean EnableConceptContent(int status,int id);
	
	/**
	 * Find list of ConceptMap object given topic object as input parameter
	 * @param topic Topic object
	 * @return list of ConceptMap object
	 */
	List<ConceptMap> findAllByTopic(Topic topic);
	
	/**
	 * Find list of ConceptMap object given User object as input parameter
	 * @param usr User object
	 * @return list of ConceptMap object
	 */
	List<ConceptMap> findALlByUser(User usr);
	
	/**
	 * Update conceptMap's acceptedByAdmin value given ConceptMap ID
	 * @param status status value to be set
	 * @param id ConceptMap ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean EnableAcceptedByAdminConceptContent(int status,int id);
	
	/**
	 * Find list of Conceptmap object based on Topic Object given as input parameter
	 * @param topic Topic object
	 * @return list of ConceptMap object
	 */
	List<ConceptMap> findAllByTopicAndStatus(Topic topic);
	
	/**
	 * Persist concepMap object into database
	 * @param temp ConceptMap object
	 * @return 0
	 */
	int save(ConceptMap temp);
	
	/**
	 * Delete ConceptMap record from database
	 * @param concept ConceptMap object
	 */
	void deleteConceptMap(ConceptMap concept);

}

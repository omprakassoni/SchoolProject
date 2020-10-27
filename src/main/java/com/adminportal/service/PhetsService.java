/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Phets interacting with its repository for database operation
 */

package com.adminportal.service;

import java.sql.Timestamp;
import java.util.List;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

/**
 * This interface has all the method declaration related to Phet database operation
 * @author om prakash
 *
 */
public interface PhetsService {
	
	/**
	 * Lists out all the Phet object from database
	 * @return list of phet Object
	 */
	List<Phets> findAll();
	
	/**
	 * Delete Phet from database
	 * @param id Phet ID
	 */
	void deletePhetById(int id) ;
	
	/**
	 * Find Phet object given Phet ID
	 * @param id Phet ID
	 * @return Phet object
	 */
	Phets findByid(int id) ;
	
	/**
	 * Update Phet's Source, url, date modified and description given Phet ID
	 * @param source reference value to be set
	 * @param url url to be set
	 * @param date current date
	 * @param desc description to be set
	 * @param id Phet ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean updatePhet(String source,String url,Timestamp date,String desc,int id) ;
	
	/**
	 * Count total number of Phet record in database
	 * @return integer
	 */
	int countRow();
	
	/**
	 * Update Phet's status value given Phet ID
	 * @param status status value to be set
	 * @param id Phet ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean EnablePhetContent(int status,int id) ;
	
	/**
	 * Finds all the Phet object from database given Topic Object
	 * @param topic topic Object
	 * @return list of phet Object
	 */
	List<Phets> findAllByTopic(Topic topic);
	
	/**
	 * Finds all the Phet object from database given User Object
	 * @param usr User object
	 * @return list of phet Object
	 */
	List<Phets> findALlByUser(User usr);
	
	/**
	 * Update Phet's acceptedByAdmin value given Phet ID
	 * @param status status value to be set
	 * @param id Phet ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean EnableAcceptedByAdminPhetContent(int status,int id);
	
	/**
	 * Finds all the Phet object from database given Topic Object and status value
	 * @param topic Topic object
	 * @return list of phet Object
	 */
	List<Phets> findAllByTopicAndStatus(Topic topic);
	
	/**
	 * Delete Phets object from database
	 * @param phet Phets object
	 */
	void deletePhet(Phets phet);


}

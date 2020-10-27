/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */
package com.adminportal.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.ConceptMap;

import com.adminportal.content.Topic;
import com.adminportal.domain.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related to ConceptMap resource
 * @author om prakash
 *
 */
public interface ConceptMapRepository extends  CrudRepository<ConceptMap, Integer> {
	
	/**
	 * Find list of ConceptMap object based on list of topic given as input parameter
	 * @param temp list of topic
	 * @return list of ConceptMap object
	 */
	List<ConceptMap> findAllBytopic(List<Topic> temp);									// fetching List of Concept-map based on Topic
	
	/**
	 * Find list of ConceptMap object based on type given as input parameter
	 * @param type Sting value like(phet,Conceptmap etc)
	 * @return list of ConceptMap object
	 */
	List<ConceptMap> findAllBytype(String type);										// fetching list of Concept-map  based on Type		

	/**
	 * Find list of ConceptMap object based on type and topic object given as input parameter
	 * @param topic topic object
	 * @param type type like(phet,Conceptmap etc)
	 * @return list of ConceptMap object
	 */
	@Query("from ConceptMap U where U.topic=?1 and U.type=?2")							// fetching list of Concept-map  based on type and topic
	ArrayList<ConceptMap> findAllBytopicAndType(Topic topic,String type);
	
	/**
	 * Find list of ConceptMap object based on type ,topic and status value given as input parameter
	 * @param topic topic object
	 * @param status status value
	 * @param type type like(phet,Conceptmap etc)
	 * @return list of ConceptMap object
	 */
	@Query("from ConceptMap U where U.topic=?1 and U.status=?2 and U.type=?3")
	List<ConceptMap> findAllByTopicAndStatus(Topic topic,int status,String type);
	
	/**
	 * update conceptmap attribute like description,url,remark and date modified based on conceptMap ID
	 * @param desc description to be updated
	 * @param url url path to be updated
	 * @param remark remark to be updated
	 * @param date current date
	 * @param id Concept ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update ConceptMap set description=?1,url=?2,remark=?3,dateModified=?4 where concepMapid=?5")	// updating Concept-map  Information
	int updateConceptMap(String desc, String url,String remark,Timestamp date,int id);
	
	/**
	 * update conceptmap attribute status value based on conceptMap ID
	 * @param status status value to be set
	 * @param id Concept ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update ConceptMap set status=?1 where concepMapid=?2")					// Enabling or disabling status of Concept-map  based on primary key
	int EnableConceptMapContent(int status,int id);
	
	/**
	 *  Find list of ConceptMap object based on type and User object given as input parameter
	 * @param usr User object
	 * @param type Type value
	 * @return list of concept map object
	 */
	@Query("from ConceptMap U where U.user=?1 and U.type=?2")						// listing Concept-map based on user and type
	List<ConceptMap> findAllByuser(User usr,String type);	

	/**
	 * update conceptmap attribute like acceptedByAdmin,status value,date approved based on conceptMap ID
	 * @param status acceptedByAdmin value to be set
	 * @param time current date
	 * @param id concept ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update ConceptMap set acceptedByAdmin=?1,status=?1,dateApproved=?2 where concepMapid=?3")
	int EnableAcceptedByAdminContent(int status,Timestamp time,int id);
	
	/**
	 * update conceptmap attribute like description,remark,date modified based on conceptMap ID
	 * @param desc description to be updated
	 * @param remark remark to be updated
	 * @param date current date
	 * @param id concept ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update ConceptMap set description=?1,remark=?2,dateModified=?3 where concepMapid=?4")	// updating Concept-map  Information
	int updateConceptMapDesc(String desc,String remark,Timestamp date,int id);
	
	@Modifying
	@Query("delete from ConceptMap U where U.concepMapid=?1")
	@Transactional
	void deleteConcep(int conceptID);
}

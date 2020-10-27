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
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;
/**
 * This Interface Extend CrudRepository to handle all database operation related to Document resource
 * @author om prakash
 *
 */
public interface DocumentExternalRepository extends CrudRepository<DocumentExternal, Integer>{
	
	/**
	 * Find list of Document based on list of Topic given as input parameter
	 * @param topic list of topic object
	 * @return list of Document
	 */
	List<DocumentExternal> findAllBytopic(List<Topic> topic);						//fetching List of document based on Topic
	
	/**
	 * Find list of Document based on type of resource given as input parameter
	 * @param type type of resource like (document,phet etc)
	 * @return list of Document
	 */
	List<DocumentExternal> findAllBytype(String type);								//fetching list of document based on Type
		
	/**
	 * Find list of Document based on topic object and type of resource given as input parameter
	 * @param topic topic object
	 * @param type type of resource
	 * @return  List of Document
	 */
	@Query("from DocumentExternal U where U.topic=?1 and U.type=?2")				//fetching list of document based on type and topic
	ArrayList<DocumentExternal> findAllBytopicAndType(Topic topic,String type);
	
	/**
	 * Find list of Document based on Topic object, type of resource and status value given as input parameter
	 * @param topic Topic object
	 * @param status status value
	 * @param type type of resource
	 * @return List of Document
	 */
	@Query("from DocumentExternal U where U.topic=?1 and U.status=?2 and U.type=?3")
	List<DocumentExternal> findAllByTopicAndStatus(Topic topic,int status,String type);
	
	/**
	 * update document attributes description, source ,url and datemodified given Document ID as one of input parameter 
	 * @param desc A long description to be updated
	 * @param source A reference of Document
	 * @param url Path of document saved
	 * @param date current date
	 * @param Id Document ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update DocumentExternal set description=?1 , source=?2, url=?3,dateModified=?4 where documentId=?5")	//updating document Information
	int updateDocument(String desc,String source,String url,Timestamp date,int Id);
	
	/**
	 * update document attributes status given Document ID as one of input parameter 
	 * @param status status value to be set
	 * @param id Document ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update DocumentExternal set status=?1 where documentId=?2")		//Enabling or disabling status of document based on primary key
	int EnableDocumentContent(int status,int id);
	
	
	/**
	 * Find list of document object given user object and type of resource as a input argument
	 * @param usr User object
	 * @param type type of Resource (preferably document)
	 * @return
	 */
	@Query("from DocumentExternal U where U.user=?1 and U.type=?2")			//listing document based on user and type
	List<DocumentExternal> findAllByuser(User usr,String type);

	/**
	 * update document attributes acceptedByadmin,status,date approved given Document ID as one of input parameter 
	 * @param status status value to be set
	 * @param time current time
	 * @param id Document ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update DocumentExternal set acceptedByAdmin=?1,status=?1,dateApproved=?2 where documentId=?3")
	int EnableAcceptedByAdminContent(int status,Timestamp time,int id);
	
	/**
	 * update document attributes description, source and date modified given Document ID as one of input parameter 
	 * @param desc A long description to be set
	 * @param source A reference of document
	 * @param date current date
	 * @param Id Document ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update DocumentExternal set description=?1 , source=?2, dateModified=?3 where documentId=?4")	//updating document Information
	int updateDocumentDesc(String desc,String source,Timestamp date,int Id);
	
	@Modifying
	@Query("delete from DocumentExternal U where U.documentId=?1")
	@Transactional
	void deleteDocument(int documentID);
}






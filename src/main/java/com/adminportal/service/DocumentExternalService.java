/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Document interacting with its repository for database operation
 */

package com.adminportal.service;

import java.sql.Timestamp;
import java.util.List;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

/**
 * This interface has all the method declaration related to Document database operation
 * @author om prakash
 *
 */
public interface DocumentExternalService {
	/**
	 * Lists out all the document object from database
	 * @return List of Document object
	 */
	List<DocumentExternal> findAll();
	
	/**
	 * Delete Document object from database given Document ID
	 * @param id Document ID
	 */
	void deleteDocumentById(int id) ;
	
	/**
	 * Find Document object given Document ID as input parameter
	 * @param id Document ID
	 * @return Document Object
	 */
	DocumentExternal findByid(int id) ;
	
	/**
	 * update Document's Description, source, path, date modified value given document ID
	 * @param desc Description to be set
	 * @param source source to be set
	 * @param url file path to be set
	 * @param date current date
	 * @param Id Document ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean updateDocument(String desc,String source,String url,Timestamp date,int Id) ;
	
	/**
	 * update Document's Description, source,date modified value given document ID
	 * @param desc Description to be set
	 * @param source source to be set
	 * @param date current date
	 * @param Id Document ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean updateDocumentDesc(String desc,String source,Timestamp date,int Id) ;

	/**
	 * Count total number of Document object in database
	 * @return integer
	 */
	int countRow();
	
	/**
	 * update Document's status value given document ID
	 * @param status status value to be set
	 * @param id Document ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean EnableDocumentContent(int status,int id) ;
	
	/**
	 * Find out document objects given Topic object as input parameter
	 * @param topic Topic object
	 * @return List of Document object
	 */
	List<DocumentExternal> findAllByTopic(Topic topic);
	
	/**
	 * Find out document objects given User object as input parameter
	 * @param usr User object
	 * @return List of Document object
	 */
	List<DocumentExternal> findALlByUser(User usr);
	
	/**
	 * update Document's acceptedByAdmin value given document ID
	 * @param status status value to be set
	 * @param id Document ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean EnableAcceptedByAdminDocumentContent(int status,int id);
	
	/**
	 * Find out list of Document object based on status and topic
	 * @param topic Topic object
	 * @return List of Document object
	 */
	List<DocumentExternal> findAllByTopicAndStatus(Topic topic);
	
	/**
	 * Persist Document Object into database
	 * @param temp Document object
	 * @return interger value
	 */
	int save(DocumentExternal temp);
	
	/**
	 * Delete Document record from database
	 * @param document Document Object
	 */
	void deleteDocuemnt(DocumentExternal document);
	
}

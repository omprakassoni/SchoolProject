/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Article interacting with its repository for database operation
 */

package com.adminportal.service;

import java.sql.Timestamp;
import java.util.List;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;
/**
 * This interface has all the method declaration related to Article Resource's database operation 
 * @author om prakash
 *
 */
public interface ArticleExternalService {
	
	/**
	 * Finds all the Article Resource record from database
	 * @return List of Article resource
	 */
	List<ArticleExternal> findAll();
	
	/**
	 * Delete article resource from database given Article ID as input parameter
	 * @param id Article ID
	 */
	void deleteArticleById(int id) ;
	
	/**
	 * Find Article record from database given Article ID as input parameter
	 * @param id Article ID
	 * @return Article object
	 */
	ArticleExternal findByid(int id);
	
	/**
	 * update Article attributes like description, source, url and modified date given Article ID 
	 * @param desc A long description to be set
	 * @param source A reference related to Article
	 * @param url A proper url 
	 * @param date Current date
	 * @param id Article ID
	 * @return boolean value on success or failure
	 */
	boolean updateArticle(String desc, String source,String url,Timestamp date,int id);
	
	/**
	 * Count total number of Article resource from database
	 * @return number of article resource
	 */
	int countRow();
	
	/**
	 * update status value in article resource record given Article ID as input argument
	 * @param status status value to be set
	 * @param id Article ID
	 * @return boolean value on success or failure
	 */
	boolean EnableArticleContent(int status,int id);
	
	/**
	 * Lists out all the article resource object based on Topic given as input argument
	 * @param topic Topic object
	 * @return List of article object
	 */
	List<ArticleExternal> findAllByTopic(Topic topic);
	
	/**
	 * Lists out all the article resource object based on User given as input argument
	 * @param usr User Object
	 * @return List of article object
	 */
	List<ArticleExternal> findALlByUser(User usr);
	
	/**
	 * update Article's status attribute given Article Id as input parameter
	 * @param status status value
	 * @param id Article ID
	 * @return boolean value on success or failure
	 */
	boolean EnableAcceptedByAdminArticleContent(int status,int id);
	
	/**
	 * Lists out all the article resource object based on Topic and status value given as input argument
	 * @param topic Topic object
	 * @return List of article object
	 */
	List<ArticleExternal> findAllByTopicAndStatus(Topic topic);
	
	/**
	 * Delete article resource from database given Article object as input parameter
	 * @param article  article object
	 * @return integer value
	 */
	int deleteArticle(ArticleExternal article);
	
	/**
	 * COunt total resource given Topic Object as  input parameter
	 * @param temp Topic object
	 * @return total number of resource
	 */
	int countTotalResource(Topic temp);
	
	/**
	 * COunt total resource given list of Topic Object as  input parameter
	 * @param temp list of Topic object
	 * @return  total number of resource
	 */
	int countTotalResource(List<Topic> temp);

}

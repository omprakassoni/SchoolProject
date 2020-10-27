/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;
/**
 * This Interface Extend CrudRepository to handle all database operation related to Article Resources
 * @author om prakash
 *
 */
public interface ArticleExternalRepository extends CrudRepository<ArticleExternal, Integer>{
	
	/**
	 * Find all the Article based on list of Topic 
	 * @param temp ArrayList of topic 
	 * @return List of Article resource 
	 */
	ArrayList<ArticleExternal> findAllBytopic(ArrayList<Topic> temp);				// fetching List of article based on Topic
	
	/**
	 * Find all the Article based on type like phet,concepMap etc
	 * @param type A String value like phet,lessonPlan etc
	 * @return List of Article resource
	 */
	List<ArticleExternal> findAllBytype(String type);								// fetching list of article based on Type		

	/**
	 * Find list of Article resource where topic, status and type given as input argument
	 * @param topic topic object 
	 * @param status status value like 0 or 1
	 * @param type type of resource
	 * @return List of Article resource
	 */
	@Query("from ArticleExternal U where U.topic=?1 and U.status=?2 and U.type=?3")
	List<ArticleExternal> findAllByTopicAndStatus(Topic topic,int status,String type);
	
	
	/**
	 * Find list of Article resource where topic,and type given as input argument
	 * @param topic topic object 
	 * @param type type of resource
	 * @return List of Article resource
	 */
	@Query("from ArticleExternal U where U.topic=?1 and U.type=?2")	
	ArrayList<ArticleExternal> findAllBytopicAndType(Topic topic,String type);		// fetching list of article based on type and topic
	
	
	/**
	 * Update the article resource where articleID given as one of the input argument
	 * @param desc Description of Article
	 * @param source Source of Article
	 * @param url Url path of article
	 * @param date current date 
	 * @param id Article ID
	 * @return integer value equivalent to number of record updated.
	 */
	@Modifying
	@Query("update ArticleExternal set description=?1,source=?2,url=?3,dateModified=?4 where articleId=?5") // updating Article Information
	int updateArticle(String desc, String source,String url,Timestamp date,int id);
	
	/**
	 * Update the article resource where articleID given as one of the input argument
	 * @param status status vaue to be set
	 * @param id Article ID
	 * @return integer value equivalent to number of record updated.
	 */
	@Modifying
	@Query("update ArticleExternal set status=?1 where articleId=?2")	// Enabling or disabling status of Article based on primary key
	int EnableArticleContent(int status,int id);
	
	
	/**
	 * Find list of Article resource where user and type given as input argument
	 * @param usr USer object who added resource
	 * @param type type of resource (article)
	 * @return List of article resource
	 */
	@Query("from ArticleExternal U where U.user=?1 and U.type=?2")		// listing Article based on user and type
	List<ArticleExternal> findAllByuser(User usr,String type);
	
	
	/**
	 * Update the article resource where articleID given as one of the input argument
	 * @param status status vale to be set
	 * @param time current date object
	 * @param id Article ID
	 * @return total number of article resource updated.
	 */
	@Modifying
	@Query("update ArticleExternal set acceptedByAdmin=?1,status=?1,dateApproved=?2 where articleId=?3")
	int EnableAcceptedByAdminContent(int status,Timestamp time,int id);
	
	@Modifying
	@Query("delete from ArticleExternal U where U.articleId=?1")
	@Transactional
	void deleteArticle(int artcileID);
	
	
	/**
	 * Count total number of resource given topic object
	 * @param temp Topic obeject
	 * @return number of resource
	 */
	@Query("select count(articleId) from ArticleExternal where topic=?1")
	int countTotalResource(Topic temp);
	
	/**
	 * Count number of resource where list of topic given as input parameter 
	 * @param temp list of topic
	 * @return number of resource
	 */
	@Query("select count(articleId) from ArticleExternal where topic  IN (:TopicList)")
	int countTotalResource(@org.springframework.data.repository.query.Param("TopicList")List<Topic> temp);

}

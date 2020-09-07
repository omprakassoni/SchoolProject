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

public interface ArticleExternalService {
	
	List<ArticleExternal> findAll();
	
	void deleteArticleById(int id) ;
	
	ArticleExternal findByid(int id);
	
	boolean updateArticle(String desc, String source,String url,Timestamp date,int id);
	
	int countRow();
	
	boolean EnableArticleContent(int status,int id);
	
	List<ArticleExternal> findAllByTopic(Topic topic);
	
	List<ArticleExternal> findALlByUser(User usr);
	
	boolean EnableAcceptedByAdminArticleContent(int status,int id);
	
	List<ArticleExternal> findAllByTopicAndStatus(Topic topic);
	
	int deleteArticle(ArticleExternal article);
	
	int countTotalResource(Topic temp);
	
	int countTotalResource(List<Topic> temp);

}

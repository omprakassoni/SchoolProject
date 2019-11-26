/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Phets interacting with its repository for database operation
 */

package com.adminportal.service;

import java.sql.Timestamp;
import java.util.List;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;


public interface PhetsService {
	
	List<Phets> findAll();
	
	void deletePhetById(int id);
	
	Phets findByid(int id);
	
	boolean updatePhet(String source,String url,Timestamp date,String desc,int id);
	
	int countRow();
	
	boolean EnablePhetContent(int status,int id);
	
	List<Phets> findAllByTopic(Topic topic);
	
	List<Phets> findALlByUser(User usr);
	
	boolean EnableAcceptedByAdminPhetContent(int status,int id);
	
	List<Phets> findAllByTopicAndStatus(Topic topic);


}

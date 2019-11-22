/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Video interacting with its repository for database operation
 */

package com.adminportal.service;

import java.sql.Timestamp;
import java.util.List;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Topic;
import com.adminportal.content.VideoExternal;
import com.adminportal.domain.User;

public interface VideoExternalService {
	List<VideoExternal> findAll();
	void deleteVideoById(int id);
	
	VideoExternal findById(int id);
	
	boolean updateVideo(String desc,String source,String url,Timestamp date,int id);
	
	int countRow();
	
	boolean EnableVideoContent(int status,int id);
	
	List<VideoExternal> findAllByTopic(Topic topic);
	
	List<VideoExternal> findALlByUser(User usr);

}

/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Video interacting with its repository for database operation
 */

package com.adminportal.service;

import java.sql.Timestamp;
import java.util.List;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Topic;
import com.adminportal.content.VideoExternal;
import com.adminportal.domain.User;

/**
 * This interface has all the method declaration related to Video database operation
 * @author om prakash
 *
 */
public interface VideoExternalService {
	/**
	 * lists out list of all video object from database
	 * @return list of video object
	 */
	List<VideoExternal> findAll();
	/**
	 * Delete Video record from database given Video ID
	 * @param id Video ID
	 */
	void deleteVideoById(int id);
	
	/**
	 * Find out video record from database given Video ID
	 * @param id Video ID
	 * @return video object
	 */
	VideoExternal findById(int id);
	
	/**
	 * update Video's description, source, video path, date modified given video ID
	 * @param desc description to be set
	 * @param source A reference to be set
	 * @param url video path to be set
	 * @param date current date
	 * @param id Video ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean updateVideo(String desc,String source,String url,Timestamp date,int id);
	
	/**
	 * Count total number of video object from database
	 * @return integer
	 */
	int countRow();
	
	/**
	 * update Video's visibility flag given video ID
	 * @param status status value to be set
	 * @param id Video ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean EnableVideoContent(int status,int id);
	
	/**
	 * Find all the video object based on topic object
	 * @param topic topic object
	 * @return list of video object
	 */
	List<VideoExternal> findAllByTopic(Topic topic);
	
	/**
	 * Find all the video object based on USER OBJECT
	 * @param usr user object
	 * @return list of video object
	 */
	List<VideoExternal> findALlByUser(User usr);
	
	/**
	 * update Video's acceptedByAdmin value given video ID
	 * @param status status value to be set
	 * @param id Video ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean EnableAcceptedByAdminVideoContent(int status,int id);
	
	/**
	 * Find all the video object based on topic and status value
	 * @param topic topic object
	 * @return list of video object
	 */
	List<VideoExternal> findAllByTopicAndStatus(Topic topic);
	
	/**
	 * persist video object into database
	 * @param temp video object
	 * @return integer
	 */
	int save(VideoExternal temp);
	
	/**
	 * Delete Video record from database
	 * @param video video object
	 */
	void deleteVideo(VideoExternal video);

}

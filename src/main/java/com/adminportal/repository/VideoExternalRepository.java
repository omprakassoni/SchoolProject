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
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Topic;
import com.adminportal.content.VideoExternal;
import com.adminportal.domain.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Video resource
 * @author om prakash
 *
 */
public interface VideoExternalRepository extends CrudRepository<VideoExternal, Integer> {
	
	/**
	 * Find list of Video Object based on list of Topic given as input parameter
	 * @param topic list of topic object
	 * @return list of video object
	 */
	List<VideoExternal> findAllBytopic(List<Topic> topic);			//fetching List of video based on Topic
	
	/**
	 * Find list of Video Object based on type of resource(Video) given as input parameter
	 * @param type type of resource (video)
	 * @return list of video object
	 */
	List<VideoExternal> findAllBytype(String type);					//fetching list of video based on Type
	
	/**
	 * Find list of Video object based on topic and type od resource (video) given as input paramter
	 * @param topic topic object
	 * @param type type of resource (video)
	 * @return list of video object
	 */
	@Query("from VideoExternal U where U.topic=?1 and U.type=?2")				//fetching list of video based on type and topic
	ArrayList<VideoExternal> findAllBytopicAndType(Topic topic,String type);

	/**
	 * update video description, source, url, date modified given Video ID as one of input parameter
	 * @param desc A long description to be set
	 * @param source A reference to video
	 * @param url Path of video
	 * @param date current ID
	 * @param id Video ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update VideoExternal set description=?1,source=?2,url=?3,dateModified=?4 where videoId=?5")		//updating video Information
	int updateVideo(String desc,String source,String url,Timestamp date,int id);
	
	/**
	 * update video status value given Video ID as one of input parameter
	 * @param status status value to be set
	 * @param id video ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update VideoExternal set status=?1 where videoId=?2")			//Enabling or disabling status of video based on primary key
	int EnableVideoContent(int status,int id);
	
	/**
	 * Find list of video object based on topic , status and type of resource(video) given as input parameter
	 * @param topic topic object
	 * @param status status value
	 * @param type type of resource (video)
	 * @return list of video object
	 */
	@Query("from VideoExternal U where U.topic=?1 and U.status=?2 and U.type=?3")
	List<VideoExternal> findAllByTopicAndStatus(Topic topic,int status,String type);
	
	/**
	 * Find list of video object based on user object and type of resource given as input parameter
	 * @param usr A USer object
	 * @param type type of Resource (Video)
	 * @return list of video object
	 */
	@Query("from VideoExternal U where U.user=?1 and U.type=?2")			//listing video based on user and type
	List<VideoExternal> findAllByuser(User usr,String type);
	
	/**
	 * update video acceptedByAdmin,status and date approved given Video ID as one of input parameter
	 * @param status status to be set
	 * @param time current time
	 * @param id Video ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update VideoExternal set acceptedByAdmin=?1,status=?1,dateApproved=?2 where videoId=?3")
	int EnableAcceptedByAdminContent(int status,Timestamp time,int id);
	
	@Modifying
	@Query("delete from VideoExternal U where U.videoId=?1")
	@Transactional
	void deleteVideo(int videoID);
}







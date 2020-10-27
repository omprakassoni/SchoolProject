/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of video Service Interface to define method for database operation.
 */

package com.adminportal.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.Topic;
import com.adminportal.content.VideoExternal;
import com.adminportal.domain.User;
import com.adminportal.repository.VideoExternalRepository;
import com.adminportal.service.VideoExternalService;
import com.spoken.Utility.ServiceUtility;

/**
 * Default implementation of the {@link com.adminportal.service.VideoExternalService} interface.  
 * @author om prakash
 *
 */
@Service
public class VideoExternalServiceImpl implements VideoExternalService{
	
	@Autowired
	private VideoExternalRepository videoRepo;

	/**
	 * @see com.adminportal.service.VideoExternalService#findAll()
	 */
	@Override
	public List<VideoExternal> findAll() {
		
		return videoRepo.findAllBytype("Video");
	}

	/**
	 * @see com.adminportal.service.VideoExternalService#deleteVideoById(int id)
	 */
	@Override
	public void deleteVideoById(int id) {
		Optional<VideoExternal> local=videoRepo.findById(id);
		VideoExternal temp=local.get();
		videoRepo.deleteById(temp.getVideoId());
		
	}
	

	/**
	 * @see com.adminportal.service.VideoExternalService#findById(int id)
	 */
	@Override
	public VideoExternal findById(int id) {
		Optional<VideoExternal> video=videoRepo.findById(id);
		return video.get();
	}

	/**
	 * @see com.adminportal.service.VideoExternalService#updateVideo(String desc, String source, String url, Timestamp date, int id)
	 */
	@Override
	@Transactional
	public boolean updateVideo(String desc, String source, String url, Timestamp date, int id) {
		int status= videoRepo.updateVideo(desc, source, url, date, id);
		if(status>0) {
			return true;
		}else {
			return false;
		}
		
	}

	/**
	 * @see com.adminportal.service.VideoExternalService#countRow() 
	 */
	@Override
	public int countRow() {
		
		return (int) videoRepo.count();
	}

	
	/**
	 * @see com.adminportal.service.VideoExternalService#EnableVideoContent(int status, int id)
	 */
	@Override
	@Transactional
	public boolean EnableVideoContent(int status, int id) {
		int status1=videoRepo.EnableVideoContent(status, id);
		if(status1>0) {
			return true;
		}else {
			return false;
		}
	
	}

	/**
	 * @see com.adminportal.service.VideoExternalService#findAllByTopic(Topic topic)
	 */
	@Override
	public List<VideoExternal> findAllByTopic(Topic topic) {
		
		List<VideoExternal> localVideo=videoRepo.findAllBytopicAndType(topic, "Video");
		
		return localVideo;
	}

	/**
	 * @see com.adminportal.service.VideoExternalService#findALlByUser(User usr)
	 */
	@Override
	public List<VideoExternal> findALlByUser(User usr) {
		
		return videoRepo.findAllByuser(usr,"Video");
	}

	/**
	 * @see com.adminportal.service.VideoExternalService#EnableAcceptedByAdminVideoContent(int status, int id)
	 */
	@Override
	@Transactional
	public boolean EnableAcceptedByAdminVideoContent(int status, int id) {
		int status1=videoRepo.EnableAcceptedByAdminContent(status,ServiceUtility.getCurrentTime(), id);
		if(status1>0) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * @see com.adminportal.service.VideoExternalService#findAllByTopicAndStatus(Topic topic)
	 */
	@Override
	public List<VideoExternal> findAllByTopicAndStatus(Topic topic) {
		
		return videoRepo.findAllByTopicAndStatus(topic, 1,"Video");
	}

	/**
	 * @see com.adminportal.service.VideoExternalService#save(VideoExternal temp)
	 */
	@Override
	public int save(VideoExternal temp) {
		videoRepo.save(temp);
		return 0;
	}

	/**
	 * @see com.adminportal.service.VideoExternalService#deleteVideo(VideoExternal video)
	 */
	@Override
	public void deleteVideo(VideoExternal video) {
		// TODO Auto-generated method stub
		
		videoRepo.deleteVideo(video.getVideoId());
	}

}

/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
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

@Service
public class VideoExternalServiceImpl implements VideoExternalService{
	
	@Autowired
	private VideoExternalRepository videoRepo;

	@Override
	public List<VideoExternal> findAll() {
		
		return videoRepo.findAllBytype("Video");
	}

	@Override
	public void deleteVideoById(int id) {
		Optional<VideoExternal> local=videoRepo.findById(id);
		VideoExternal temp=local.get();
		videoRepo.deleteById(temp.getVideoId());
		
	}

	@Override
	public VideoExternal findById(int id) {
		Optional<VideoExternal> video=videoRepo.findById(id);
		return video.get();
	}

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

	@Override
	public int countRow() {
		
		return (int) videoRepo.count();
	}

	
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

	@Override
	public List<VideoExternal> findAllByTopic(Topic topic) {
		
		List<VideoExternal> localVideo=videoRepo.findAllBytopicAndType(topic, "Video");
		
		return localVideo;
	}

	@Override
	public List<VideoExternal> findALlByUser(User usr) {
		
		return videoRepo.findAllByuser(usr,"Video");
	}

}

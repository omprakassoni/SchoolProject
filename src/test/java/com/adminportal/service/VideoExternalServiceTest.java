package com.adminportal.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.security.Provider.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.adminportal.content.VideoExternal;
import com.adminportal.repository.VideoExternalRepository;
import com.spoken.Utility.ServiceUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoExternalServiceTest {
	
	@Autowired
	private VideoExternalService videoService;
	
	@MockBean
	private VideoExternalRepository videoRepo;
	
	@Test
	public void saveVideoTest() {
		
		VideoExternal video=new VideoExternal(1, "Video", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "zdfhgsdgdsf", "dshgsdf", "google.com", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(videoRepo.save(video)).thenReturn(video);
		
		assertEquals(0, videoService.save(video));
	}
	
	@Test
	public void updateVideoTest() {
		
		VideoExternal video=new VideoExternal(1, "Video", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "zdfhgsdgdsf", "dshgsdf", "google.com", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(videoRepo.save(video)).thenReturn(video);
		
		int updateVideoValue=0;
		boolean updateVideoBoolean=false;
		
		when(videoRepo.updateVideo("adsfdsdsf", "sdfsdfsdf", "fgfsgfdg", ServiceUtility.getCurrentTime(), 1)).thenReturn(updateVideoValue);
		
		if(updateVideoValue>0) {
			updateVideoBoolean=true;
		}
		
		assertEquals(updateVideoBoolean, videoService.updateVideo("adsfdsdsf", "sdfsdfsdf", "fgfsgfdg", ServiceUtility.getCurrentTime(), 1));
	}
	
	@Test
	public void EnableVideoTest() {
		
		VideoExternal video=new VideoExternal(1, "Video", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "zdfhgsdgdsf", "dshgsdf", "google.com", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(videoRepo.save(video)).thenReturn(video);
		
		int updateVideoValue=0;
		boolean updateVideoBoolean=false;
		
		when(videoRepo.EnableVideoContent(1, 1)).thenReturn(updateVideoValue);
		
		if(updateVideoValue>0) {
			updateVideoBoolean=true;
		}
		
		assertEquals(updateVideoBoolean, videoService.EnableVideoContent(1, 1));
	}
	
	@Test
	public void updateAcceptedByAdminVideoTest() {
		
		VideoExternal video=new VideoExternal(1, "Video", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "zdfhgsdgdsf", "dshgsdf", "google.com", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(videoRepo.save(video)).thenReturn(video);
		
		int updateVideoValue=0;
		boolean updateVideoBoolean=false;
		
		when(videoRepo.EnableAcceptedByAdminContent(1, ServiceUtility.getCurrentTime(), 1)).thenReturn(updateVideoValue);
		
		if(updateVideoValue>0) {
			updateVideoBoolean=true;
		}
		
		assertEquals(updateVideoBoolean, videoService.EnableAcceptedByAdminVideoContent(1, 1));
	}
	
}

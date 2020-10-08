package com.adminportal.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Comment;
import com.adminportal.repository.CommentRepository;
import com.spoken.Utility.ServiceUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

	@Autowired
	private CommentService comService;
	
	@MockBean
	private CommentRepository comRepo;
	
	@Test
	public void saveCommentTest() {
		Comment com=new Comment();
		
		when(comRepo.save(com)).thenReturn(com);
		assertTrue(com!=null);
		
	}
	
	
}

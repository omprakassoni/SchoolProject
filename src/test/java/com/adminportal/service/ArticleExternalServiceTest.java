package com.adminportal.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.adminportal.content.ArticleExternal;
import com.adminportal.repository.ArticleExternalRepository;
import com.spoken.Utility.ServiceUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleExternalServiceTest {

	@Autowired
	private ArticleExternalService articleService;
	
	@MockBean
	private ArticleExternalRepository articleRepo;
	
	@Test
	public void articleSaveTest() {
		
		ArticleExternal article=new ArticleExternal(1, "Article", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "A sample Test", "wikipedia", "http://www.google.com", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(articleRepo.save(article)).thenReturn(article);
		
	}
	

	@Test
	public void ArticleUpdateTest() {
		
		ArticleExternal article=new ArticleExternal(1, "Article", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "A sample Test", "wikipedia", "http://www.google.com", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(articleRepo.save(article)).thenReturn(article);
		
		int updateStatus=0;
		boolean updateBoolean=false;
		
		when(articleRepo.updateArticle("sample", "youtube", "http://www.youtube.com", ServiceUtility.getCurrentTime(), 1)).thenReturn(updateStatus);
		
		if(updateStatus>0) {
			updateBoolean=true;
		}
		assertEquals(updateBoolean, articleService.updateArticle("sample", "youtube", "http://www.youtube.com", ServiceUtility.getCurrentTime(), 1));
	}
	

}

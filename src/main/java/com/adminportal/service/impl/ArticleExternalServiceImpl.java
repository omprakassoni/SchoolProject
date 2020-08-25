/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : Implementation of Article Service Interface to define method for database operation.
 */

package com.adminportal.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;
import com.adminportal.repository.ArticleExternalRepository;
import com.adminportal.service.ArticleExternalService;
import com.spoken.Utility.ServiceUtility;

@Service
public class ArticleExternalServiceImpl implements ArticleExternalService{

	@Autowired
	private ArticleExternalRepository articleRepo;
	
	@Override
	public List<ArticleExternal> findAll() {
		
		return articleRepo.findAllBytype("Article");
	}
	@Override
	@Transactional
	public void deleteArticleById(int id){
		
		Optional<ArticleExternal> local=articleRepo.findById(id);
		ArticleExternal temp=local.get();
		articleRepo.deleteById(temp.getArticleId());
		
	}
	@Override
	public ArticleExternal findByid(int id) {
		Optional<ArticleExternal> local=articleRepo.findById(id);
		return local.get();
	}
	@Override
	@Transactional
	public boolean updateArticle(String desc, String source, String url, Timestamp date, int id) {
		int status=articleRepo.updateArticle(desc, source, url, date, id);
		
		if(status>0) {
			return true;
		}else {
			return false;
		}
	
	}
	
	@Override
	public int countRow() {
		
		return (int) articleRepo.count();
	}
	
	@Override
	@Transactional
	public boolean EnableArticleContent(int status, int id) {
		int status1=articleRepo.EnableArticleContent(status, id);
		
		if(status1>0) {
			return true;
		}else {
			return false;
		}
		
	
	}
	@Override
	public List<ArticleExternal> findAllByTopic(Topic topic) {
		
		List<ArticleExternal> localArticle=articleRepo.findAllBytopicAndType(topic, "Article");
			
		return localArticle;
	}
	@Override
	public List<ArticleExternal> findALlByUser(User usr) {
		
		
		return articleRepo.findAllByuser(usr,"Article");
	}
	
	@Override
	@Transactional
	public boolean EnableAcceptedByAdminArticleContent(int status, int id) {
		
		int status1=articleRepo.EnableAcceptedByAdminContent(status,ServiceUtility.getCurrentTime(), id);
		
		if(status1>0) {
			return true;
		}else {
			return false;
		}
	}
	@Override
	public List<ArticleExternal> findAllByTopicAndStatus(Topic topic) {
		
		
		return articleRepo.findAllByTopicAndStatus(topic, 1,"Article");
	}
	@Override
	public int deleteArticle(ArticleExternal article) {
		
		articleRepo.deleteArticle(article.getArticleId());
		
		return 0;
	}

}

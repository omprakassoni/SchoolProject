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

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

public interface ArticleExternalRepository extends CrudRepository<ArticleExternal, Integer>{
	

	ArrayList<ArticleExternal> findAllBytopic(ArrayList<Topic> temp);
	
	List<ArticleExternal> findAllBytype(String type);

	
	@Query("from ArticleExternal U where U.topic=?1 and U.type=?2")
	ArrayList<ArticleExternal> findAllBytopicAndType(Topic temp,String topic);
	
	
	@Modifying
	@Query("update ArticleExternal set description=?1,source=?2,url=?3,dateModified=?4 where articleId=?5")
	int updateArticle(String desc, String source,String url,Timestamp date,int id);
	
	@Modifying
	@Query("update ArticleExternal set status=?1 where articleId=?2")
	int EnableArticleContent(int status,int id);
	
	@Query("from ArticleExternal U where U.user=?1 and U.type=?2")
	List<ArticleExternal> findAllByuser(User usr,String type);

}

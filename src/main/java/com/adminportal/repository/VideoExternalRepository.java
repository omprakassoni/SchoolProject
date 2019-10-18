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
import com.adminportal.content.VideoExternal;
import com.adminportal.domain.User;

public interface VideoExternalRepository extends CrudRepository<VideoExternal, Integer> {
	
	List<VideoExternal> findAllBytopic(List<Topic> temp);
	
	List<VideoExternal> findAllBytype(String type);
	
	@Query("from VideoExternal U where U.topic=?1 and U.type=?2")
	ArrayList<VideoExternal> findAllBytopicAndType(Topic temp,String topic);

	@Modifying
	@Query("update VideoExternal set description=?1,source=?2,url=?3,dateModified=?4 where videoId=?5")
	int updateVideo(String desc,String source,String url,Timestamp date,int id);
	
	@Modifying
	@Query("update VideoExternal set status=?1 where videoId=?2")
	int EnableVideoContent(int status,int id);
	
	@Query("from VideoExternal U where U.user=?1 and U.type=?2")
	List<VideoExternal> findAllByuser(User usr,String type);
}


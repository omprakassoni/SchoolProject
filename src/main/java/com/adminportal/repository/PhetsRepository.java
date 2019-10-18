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
import com.adminportal.content.Phets;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

public interface PhetsRepository extends CrudRepository<Phets, Integer>{

	List<Phets> findAllBytopic(List<Topic> temp);
	
	List<Phets> findAllBytype(String type);
	
	@Query("from Phets U where U.topic=?1 and U.type=?2")
	ArrayList<Phets> findAllBytopicAndType(Topic temp,String topic);
	
	@Modifying
	@Query("update Phets set source=?1,url=?2,dateModified=?3,description=?4 where phetId=?5")
	int updatePhet(String source,String url,Timestamp date,String desc,int id);
	
	@Modifying
	@Query("update Phets set status=?1 where phetId=?2")
	int EnablePhetContent(int status,int id);
	
	@Query("from Phets U where U.user=?1 and U.type=?2")
	List<Phets> findAllByuser(User usr,String type);
}



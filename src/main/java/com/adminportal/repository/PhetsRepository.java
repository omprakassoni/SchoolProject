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
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

public interface PhetsRepository extends CrudRepository<Phets, Integer>{

	List<Phets> findAllBytopic(List<Topic> temp);									//fetching List of Phet based on Topic
	
	List<Phets> findAllBytype(String type);											//fetching list of Phet based on Type
		
	@Query("from Phets U where U.topic=?1 and U.type=?2")							//fetching list of Phet based on type and topic
	ArrayList<Phets> findAllBytopicAndType(Topic temp,String topic);
	
	@Modifying
	@Query("update Phets set source=?1,url=?2,dateModified=?3,description=?4 where phetId=?5")		//updating Phet Information
	int updatePhet(String source,String url,Timestamp date,String desc,int id);
	
	@Query("from Phets U where U.topic=?1 and U.status=?2 and U.type=?3")
	List<Phets> findAllByTopicAndStatus(Topic topic,int status,String type);
	
	@Modifying
	@Query("update Phets set status=?1 where phetId=?2")				//Enabling or disabling status of Phet based on primary key
	int EnablePhetContent(int status,int id);
	
	@Query("from Phets U where U.user=?1 and U.type=?2")				//listing Phet based on user and type
	List<Phets> findAllByuser(User usr,String type);
	
	@Modifying
	@Query("update Phets set acceptedByAdmin=?1,status=?1,dateApproved=?2 where phetId=?3")
	int EnableAcceptedByAdminContent(int status,Timestamp time,int id);
}









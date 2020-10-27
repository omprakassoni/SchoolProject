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
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Phet resource
 * @author om prakash
 *
 */
public interface PhetsRepository extends CrudRepository<Phets, Integer>{

	/**
	 * Find list of Phet Object based on list of Topic given as input parameter
	 * @param topic
	 * @return List of phet Object
	 */
	List<Phets> findAllBytopic(List<Topic> topic);									//fetching List of Phet based on Topic
	
	/**
	 * Find list of Phet Object based on list of Topic given as input parameter
	 * @param type type of resource (Phet)
	 * @return List of phet Object
	 */
	List<Phets> findAllBytype(String type);											//fetching list of Phet based on Type
		
	/**
	 * Find list of Phet Object based on type of resource and topic object given as input parameter
	 * @param topic A topic object
	 * @param type type of resource(Phet)
	 * @return List of phet Object
	 */
	@Query("from Phets U where U.topic=?1 and U.type=?2")							//fetching list of Phet based on type and topic
	ArrayList<Phets> findAllBytopicAndType(Topic topic,String type);
	
	/**
	 * update phet attributes source, url, date modified, description given Phet ID as one of input parameter
	 * @param source A reference to phet resource
	 * @param url Url of Phet 
	 * @param date current date
	 * @param desc a long description to be set
	 * @param id Phet ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update Phets set source=?1,url=?2,dateModified=?3,description=?4 where phetId=?5")		//updating Phet Information
	int updatePhet(String source,String url,Timestamp date,String desc,int id);
	
	/**
	 * Find list of phet object based on topic, status and type of resource(phet) given as input argument
	 * @param topic topic object
	 * @param status status value to be set
	 * @param type type of resource(Phet) 
	 * @return List of phet Object
	 */
	@Query("from Phets U where U.topic=?1 and U.status=?2 and U.type=?3")
	List<Phets> findAllByTopicAndStatus(Topic topic,int status,String type);
	
	/**
	 * update phet attributes status given Phet ID as one of input parameter
	 * @param status status value to be set
	 * @param id Phet ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update Phets set status=?1 where phetId=?2")				//Enabling or disabling status of Phet based on primary key
	int EnablePhetContent(int status,int id);
	
	/**
	 * Find list of phet object based on User and type of resource(phet) given as input argument
	 * @param usr A user object
	 * @param type resource type (Phet)
	 * @return List of phet Object
	 */
	@Query("from Phets U where U.user=?1 and U.type=?2")				//listing Phet based on user and type
	List<Phets> findAllByuser(User usr,String type);
	
	/**
	 * update phet attributes acceptedByAdmin, status and date approved given Phet ID as one of input parameter
	 * @param status status value to be set
	 * @param time current time
	 * @param id Phet ID
	 * @return number of updated record
	 */
	@Modifying
	@Query("update Phets set acceptedByAdmin=?1,status=?1,dateApproved=?2 where phetId=?3")
	int EnableAcceptedByAdminContent(int status,Timestamp time,int id);
	
	@Modifying
	@Query("delete from Phets U where U.phetId=?1")
	@Transactional
	void deletePhet(int phetID);
}









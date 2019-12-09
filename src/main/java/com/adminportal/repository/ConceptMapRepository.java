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
import com.adminportal.content.ConceptMap;

import com.adminportal.content.Topic;
import com.adminportal.domain.User;

public interface ConceptMapRepository extends  CrudRepository<ConceptMap, Integer> {
	
	List<ConceptMap> findAllBytopic(List<Topic> temp);									// fetching List of Concept-map based on Topic
	
	List<ConceptMap> findAllBytype(String type);										// fetching list of Concept-map  based on Type		

	
	@Query("from ConceptMap U where U.topic=?1 and U.type=?2")							// fetching list of Concept-map  based on type and topic
	ArrayList<ConceptMap> findAllBytopicAndType(Topic temp,String topic);
	
	@Query("from ConceptMap U where U.topic=?1 and U.status=?2 and U.type=?3")
	List<ConceptMap> findAllByTopicAndStatus(Topic topic,int status,String type);
	
	@Modifying
	@Query("update ConceptMap set description=?1,url=?2,remark=?3,dateModified=?4 where concepMapid=?5")	// updating Concept-map  Information
	int updateConceptMap(String desc, String url,String remark,Timestamp date,int id);
	
	@Modifying
	@Query("update ConceptMap set status=?1 where concepMapid=?2")					// Enabling or disabling status of Concept-map  based on primary key
	int EnableConceptMapContent(int status,int id);
	
	@Query("from ConceptMap U where U.user=?1 and U.type=?2")						// listing Concept-map based on user and type
	List<ConceptMap> findAllByuser(User usr,String type);	

	@Modifying
	@Query("update ConceptMap set acceptedByAdmin=?1,status=?1,dateApproved=?2 where concepMapid=?3")
	int EnableAcceptedByAdminContent(int status,Timestamp time,int id);
	
	@Modifying
	@Query("update ConceptMap set description=?1,remark=?2,dateModified=?3 where concepMapid=?4")	// updating Concept-map  Information
	int updateConceptMapDesc(String desc,String remark,Timestamp date,int id);
}

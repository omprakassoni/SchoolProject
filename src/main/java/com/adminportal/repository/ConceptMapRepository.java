package com.adminportal.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.adminportal.content.ConceptMap;

import com.adminportal.content.Topic;
import com.adminportal.domain.User;

public interface ConceptMapRepository extends  CrudRepository<ConceptMap, Integer> {
	
	List<ConceptMap> findAllBytopic(List<Topic> temp);
	
	List<ConceptMap> findAllBytype(String type);
	
	@Query("from ConceptMap U where U.topic=?1 and U.type=?2")
	ArrayList<ConceptMap> findAllBytopicAndType(Topic temp,String topic);
	
	@Modifying
	@Query("update ConceptMap set description=?1,url=?2,remark=?3,dateModified=?4 where concepMapid=?5")
	int updateConceptMap(String desc, String url,String remark,Timestamp date,int id);
	
	@Modifying
	@Query("update ConceptMap set status=?1 where concepMapid=?2")
	int EnableConceptMapContent(int status,int id);
	
	@Query("from ConceptMap U where U.user=?1 and U.type=?2")
	List<ConceptMap> findAllByuser(User usr,String type);

}

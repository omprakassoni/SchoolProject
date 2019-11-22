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
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

public interface DocumentExternalRepository extends CrudRepository<DocumentExternal, Integer>{
	
	List<DocumentExternal> findAllBytopic(List<Topic> temp);						//fetching List of document based on Topic
	
	List<DocumentExternal> findAllBytype(String type);								//fetching list of document based on Type
		
	@Query("from DocumentExternal U where U.topic=?1 and U.type=?2")				//fetching list of document based on type and topic
	ArrayList<DocumentExternal> findAllBytopicAndType(Topic temp,String topic);
	
	@Modifying
	@Query("update DocumentExternal set description=?1 , source=?2, url=?3,dateModified=?4 where documentId=?5")	//updating document Information
	int updateDocument(String desc,String source,String url,Timestamp date,int Id);
	
	@Modifying
	@Query("update DocumentExternal set status=?1 where documentId=?2")		//Enabling or disabling status of document based on primary key
	int EnableDocumentContent(int status,int id);
	
	
	@Query("from DocumentExternal U where U.user=?1 and U.type=?2")			//listing document based on user and type
	List<DocumentExternal> findAllByuser(User usr,String type);

}






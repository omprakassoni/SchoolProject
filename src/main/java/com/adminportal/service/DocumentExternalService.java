/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Document interacting with its repository for database operation
 */

package com.adminportal.service;

import java.sql.Timestamp;
import java.util.List;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;

public interface DocumentExternalService {
	List<DocumentExternal> findAll();
	void deleteDocumentById(int id);
	
	DocumentExternal findByid(int id);
	
	boolean updateDocument(String desc,String source,String url,Timestamp date,int Id);

	int countRow();
	
	boolean EnableDocumentContent(int status,int id);
	
	List<DocumentExternal> findAllByTopic(Topic topic);
	
	List<DocumentExternal> findALlByUser(User usr);
	
	boolean EnableAcceptedByAdminDocumentContent(int status,int id);
	
	List<DocumentExternal> findAllByTopicAndStatus(Topic topic);
	
}

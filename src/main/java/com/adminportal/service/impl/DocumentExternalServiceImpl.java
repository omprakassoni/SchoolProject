/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of Document Service Interface to define method for database operation.
 */

package com.adminportal.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.Topic;
import com.adminportal.domain.User;
import com.adminportal.repository.DocumentExternalRepository;
import com.adminportal.service.DocumentExternalService;
import com.spoken.Utility.ServiceUtility;

/**
 * Default implementation of the {@link com.adminportal.service.DocumentExternalService} interface.  
 * @author om prakash
 *
 */
@Service
public class DocumentExternalServiceImpl implements DocumentExternalService{
	
	@Autowired
	private DocumentExternalRepository documentRepo;

	/**
	 * @see com.adminportal.service.DocumentExternalService#findAll()
	 */
	@Override
	public List<DocumentExternal> findAll() {
		
		return documentRepo.findAllBytype("Document");
	}

	/**
	 * @see com.adminportal.service.DocumentExternalService#deleteDocumentById(int id)
	 */
	@Override
	public void deleteDocumentById(int id) {
		Optional<DocumentExternal> local=documentRepo.findById(id);
		DocumentExternal temp=local.get();
		documentRepo.deleteById(temp.getDocumentId());
		
	}

	/**
	 * @see com.adminportal.service.DocumentExternalService#findByid(int id)
	 */
	@Override
	public DocumentExternal findByid(int id) {
		Optional<DocumentExternal> local=documentRepo.findById(id);
		return local.get();
	}

	/**
	 * @see com.adminportal.service.DocumentExternalService#updateDocument(String desc, String source, String url, Timestamp date, int Id)
	 */
	@Override
	@Transactional
	public boolean updateDocument(String desc, String source, String url, Timestamp date, int Id) {
		int temp=documentRepo.updateDocument(desc, source, url, date, Id);
		
		if(temp>0) {
			return true;
		}else {
			return false;
		}
			
		
		
	}

	/**
	 * @see com.adminportal.service.DocumentExternalService#countRow()
	 */
	@Override
	public int countRow() {
		
		return (int) documentRepo.count();
	}

	/**
	 * @see com.adminportal.service.DocumentExternalService#EnableDocumentContent(int status, int id) 
	 */
	@Override
	@Transactional
	public boolean EnableDocumentContent(int status, int id) {
		
		int status1=documentRepo.EnableDocumentContent(status, id);
		if(status1>0) {
			return true;
		}else {
			return false;
		}
		
	}

	/**
	 * @see com.adminportal.service.DocumentExternalService#findAllByTopic(Topic topic)
	 */
	@Override
	public List<DocumentExternal> findAllByTopic(Topic topic) {
		
		List<DocumentExternal> localDocument=documentRepo.findAllBytopicAndType(topic, "Document");
		
		return localDocument;
	}

	/**
	 * @see com.adminportal.service.DocumentExternalService#findALlByUser(User usr)
	 */
	@Override
	public List<DocumentExternal> findALlByUser(User usr) {
		
		return documentRepo.findAllByuser(usr,"Document");
	}

	/**
	 * @see com.adminportal.service.DocumentExternalService#EnableAcceptedByAdminDocumentContent(int status, int id)
	 */
	@Override
	@Transactional
	public boolean EnableAcceptedByAdminDocumentContent(int status, int id) {
		int status1=documentRepo.EnableAcceptedByAdminContent(status,ServiceUtility.getCurrentTime(), id);
		if(status1>0) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * @see com.adminportal.service.DocumentExternalService#findAllByTopicAndStatus(Topic topic)
	 */
	@Override
	public List<DocumentExternal> findAllByTopicAndStatus(Topic topic) {
	
		return documentRepo.findAllByTopicAndStatus(topic, 1,"Document");
	}

	/**
	 * @see com.adminportal.service.DocumentExternalService#updateDocumentDesc(String desc, String source, Timestamp date, int Id)
	 */
	@Override
	@Transactional
	public boolean updateDocumentDesc(String desc, String source, Timestamp date, int Id) {
	int temp=documentRepo.updateDocumentDesc(desc, source, date, Id);
		
		if(temp>0) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * @see com.adminportal.service.DocumentExternalService#save(DocumentExternal temp)
	 */
	@Override
	public int save(DocumentExternal temp) {
		documentRepo.save(temp);
		
		return 0;
	}

	/**
	 * @see com.adminportal.service.DocumentExternalService#deleteDocuemnt(DocumentExternal document)
	 */
	@Override
	public void deleteDocuemnt(DocumentExternal document) {
		// TODO Auto-generated method stub
		documentRepo.deleteDocument(document.getDocumentId());
		
	}

}

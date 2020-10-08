package com.adminportal.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.adminportal.content.DocumentExternal;
import com.adminportal.repository.ContactFormRepository;
import com.adminportal.repository.DocumentExternalRepository;
import com.spoken.Utility.ServiceUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentExternalServiceTest {
	
	@Autowired
	private DocumentExternalService docuService;
	
	@MockBean
	private DocumentExternalRepository docuRepo;
	
	@Test
	public void updateDocumentTest() {
		DocumentExternal document=new DocumentExternal(1, "document", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "dfhgsdhf", "dfhgsdf", "sample", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(docuRepo.save(document)).thenReturn(document);
		
		int updateDcoumentValue=0;
		boolean updateDocumentBoolean=false;
		
		when(docuRepo.updateDocument("adfdsgfds", "sdfsfg", "sample", ServiceUtility.getCurrentTime(), 1)).thenReturn(updateDcoumentValue);
		
		if(updateDcoumentValue>0) {
			updateDocumentBoolean=true;
		}
		
		assertEquals(updateDocumentBoolean, docuService.updateDocument("adfdsgfds", "sdfsfg", "sample", ServiceUtility.getCurrentTime(), 1));
	}
	
	@Test
	public void updateDocumentDescTest() {
		DocumentExternal document=new DocumentExternal(1, "document", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "dfhgsdhf", "dfhgsdf", "sample", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(docuRepo.save(document)).thenReturn(document);
		
		int updateDcoumentValue=0;
		boolean updateDocumentBoolean=false;
		
		when(docuRepo.updateDocumentDesc("adfdsgfds", "sample", ServiceUtility.getCurrentTime(), 1)).thenReturn(updateDcoumentValue);
		
		if(updateDcoumentValue>0) {
			updateDocumentBoolean=true;
		}
		
		assertEquals(updateDocumentBoolean, docuService.updateDocumentDesc("adfdsgfds", "sample", ServiceUtility.getCurrentTime(), 1));
	}
	
	@Test
	public void updateDocumentAcceptedByAdminTest() {
		DocumentExternal document=new DocumentExternal(1, "document", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "dfhgsdhf", "dfhgsdf", "sample", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(docuRepo.save(document)).thenReturn(document);
		
		int updateDcoumentValue=0;
		boolean updateDocumentBoolean=false;
		
		when(docuRepo.EnableAcceptedByAdminContent(1, ServiceUtility.getCurrentTime(), 1)).thenReturn(updateDcoumentValue);
		
		if(updateDcoumentValue>0) {
			updateDocumentBoolean=true;
		}
		
		assertEquals(updateDocumentBoolean, docuService.EnableAcceptedByAdminDocumentContent(1, 1));
	}
	
	@Test
	public void updateEnableDocumentTest() {
		DocumentExternal document=new DocumentExternal(1, "document", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "dfhgsdhf", "dfhgsdf", "sample", 1, 1, ServiceUtility.getCurrentTime(), null, null);
		
		when(docuRepo.save(document)).thenReturn(document);
		
		int updateDcoumentValue=0;
		boolean updateDocumentBoolean=false;
		
		when(docuRepo.EnableDocumentContent(1, 1)).thenReturn(updateDcoumentValue);
		
		if(updateDcoumentValue>0) {
			updateDocumentBoolean=true;
		}
		
		assertEquals(updateDocumentBoolean, docuService.EnableDocumentContent(1, 1));
	}
	

}

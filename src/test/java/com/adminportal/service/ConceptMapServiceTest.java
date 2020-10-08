package com.adminportal.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.adminportal.content.ConceptMap;
import com.adminportal.repository.ConceptMapRepository;
import com.spoken.Utility.ServiceUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConceptMapServiceTest {

	@Autowired
	private ConceptMapService conceptService;
	
	@MockBean
	private ConceptMapRepository conceptRepo;
	
	@Test
	public void updateConceptTest() {
		
		ConceptMap concept=new ConceptMap(1, "Concept", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "sample usr", "sample test ahgsh", 1, 1, "xzfhgdjvbxfkughfdsk", null, null);
		
		when(conceptRepo.save(concept)).thenReturn(concept);
		
		int updateConceptValue=0;
		boolean updateConceptBoolean=false;
		
		when(conceptRepo.updateConceptMap("sample", "\\dfd\\sdf", "adhkgsdhjdf", ServiceUtility.getCurrentTime(), 1)).thenReturn(updateConceptValue);
		
		if(updateConceptValue>0) {
			updateConceptBoolean=true;
		}
		
		assertEquals(updateConceptBoolean, conceptService.updateConcept("sample", "\\dfd\\sdf", "adhkgsdhjdf", ServiceUtility.getCurrentTime(), 1));
		
		
		
		
	}
	
	@Test
	public void updateConceptDescTest() {
		
		ConceptMap concept=new ConceptMap(1, "Concept", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "sample usr", "sample test ahgsh", 1, 1, "xzfhgdjvbxfkughfdsk", null, null);
		
		when(conceptRepo.save(concept)).thenReturn(concept);
		
		int updateConceptValue=0;
		boolean updateConceptBoolean=false;
		
		when(conceptRepo.updateConceptMapDesc("sample", "adhkgsdhjdf", ServiceUtility.getCurrentTime(), 1)).thenReturn(updateConceptValue);
		
		if(updateConceptValue>0) {
			updateConceptBoolean=true;
		}
		
		assertEquals(updateConceptBoolean, conceptService.updateConceptDesc("sample", "adhkgsdhjdf", ServiceUtility.getCurrentTime(), 1));
		
		
		
		
	}
	
	@Test
	public void EnableContentConceptTest() {
		
		ConceptMap concept=new ConceptMap(1, "Concept", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "sample usr", "sample test ahgsh", 1, 1, "xzfhgdjvbxfkughfdsk", null, null);
		
		when(conceptRepo.save(concept)).thenReturn(concept);
		
		int updateConceptValue=0;
		boolean updateConceptBoolean=false;
		
		when(conceptRepo.EnableConceptMapContent(1, 1)).thenReturn(updateConceptValue);
		
		if(updateConceptValue>0) {
			updateConceptBoolean=true;
		}
		
		assertEquals(updateConceptBoolean, conceptService.EnableConceptContent(1, 1));
		
		
	}
	
	@Test
	public void EnableContentAcceptedByAdminConceptTest() {
		
		ConceptMap concept=new ConceptMap(1, "Concept", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "sample usr", "sample test ahgsh", 1, 1, "xzfhgdjvbxfkughfdsk", null, null);
		
		when(conceptRepo.save(concept)).thenReturn(concept);
		
		int updateConceptValue=0;
		boolean updateConceptBoolean=false;
		
		when(conceptRepo.EnableAcceptedByAdminContent(1, ServiceUtility.getCurrentTime(), 1)).thenReturn(updateConceptValue);
		
		if(updateConceptValue>0) {
			updateConceptBoolean=true;
		}
		
		assertEquals(updateConceptBoolean, conceptService.EnableAcceptedByAdminConceptContent(1, 1));
		
		
	}
}

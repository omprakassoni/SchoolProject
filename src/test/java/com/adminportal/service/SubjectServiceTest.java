package com.adminportal.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.adminportal.content.Subject;
import com.adminportal.repository.SubjectRepository;
import com.spoken.Utility.ServiceUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubjectServiceTest {

	@Autowired
	private SubjectService subjectService;
	
	@MockBean
	private SubjectRepository subRepo;
	
	@Test
	public void saveSubjectTest() {
		Subject sub=new Subject();
		sub.setSubId(1);
		sub.setDateAdded(ServiceUtility.getCurrentTime());
		sub.setSubName("Physics");
		sub.setStatus(true);
		sub.setUser(null);
		sub.setSubClasMapp(null);
		
		when(subRepo.save(sub)).thenReturn(sub);
		
		assertEquals(sub, subjectService.save(sub));
	}
	
	@Test
	public void findBySubjectNameTest() throws Exception {
		
		Subject sub=new Subject();
		sub.setSubId(1);
		sub.setDateAdded(ServiceUtility.getCurrentTime());
		sub.setSubName("Physics");
		sub.setStatus(true);
		sub.setUser(null);
		sub.setSubClasMapp(null);
		
		when(subRepo.save(sub)).thenReturn(sub);
		
		assertEquals(sub, subjectService.save(sub));
		
		when(subRepo.findBysubName("Physics")).thenReturn(sub);
		
		assertEquals(sub, subjectService.findBysubName("Physics"));
	}
	
	@Test
	public void updateSubjectNameTest() {
		
		Subject sub=new Subject();
		sub.setSubId(1);
		sub.setDateAdded(ServiceUtility.getCurrentTime());
		sub.setSubName("Physics");
		sub.setStatus(true);
		sub.setUser(null);
		sub.setSubClasMapp(null);
		
		when(subRepo.save(sub)).thenReturn(sub);
		
		assertEquals(sub, subjectService.save(sub));
		
		boolean updateStatus=false;
		int updateValue=0;
		
		when(subRepo.updateSubjectName("Maths", 1)).thenReturn(updateValue);
		
		if(updateValue>0) {
			updateStatus=true;
		}
		
		assertEquals(updateStatus, subjectService.updateSubjectName("Maths", 1));
		
		
	}
}

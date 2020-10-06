package com.adminportal.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.adminportal.content.Class;
import com.adminportal.repository.ClassRepository;
import com.spoken.Utility.ServiceUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClassServiceTest {

	@Autowired
	private ClassService classService;
	
	@MockBean
	private ClassRepository classRepo;
	
	@Test
	public void saveTest() {
		
		Class classTemp=new Class();
		classTemp.setClass_id(1);
		classTemp.setClassName(5);
		classTemp.setDateAdded(ServiceUtility.getCurrentTime());
		classTemp.setStatus(true);
		classTemp.setSubClasMapp(null);
		classTemp.setUser(null);
		
		when(classRepo.save(classTemp)).thenReturn(classTemp);
		
		assertEquals(classTemp, classService.save(classTemp));
		
	}
	
	@Test
	public void findByClassNameTest() throws Exception {
		
		Class classTemp=new Class();
		classTemp.setClass_id(1);
		classTemp.setClassName(5);
		classTemp.setDateAdded(ServiceUtility.getCurrentTime());
		classTemp.setStatus(true);
		classTemp.setSubClasMapp(null);
		classTemp.setUser(null);
		
		when(classRepo.save(classTemp)).thenReturn(classTemp);
		
		assertEquals(classTemp, classService.save(classTemp));
		
		when(classRepo.findByclassName(5)).thenReturn(classTemp);
		
		assertEquals(classTemp, classService.findByClassName(5));
	}
	
//	@Test
//	public void findAllTest() {
//		
//		Class classTemp=new Class();
//		classTemp.setClass_id(1);
//		classTemp.setClassName(5);
//		classTemp.setDateAdded(ServiceUtility.getCurrentTime());
//		classTemp.setStatus(true);
//		classTemp.setSubClasMapp(null);
//		classTemp.setUser(null);
//		
//		Class classTemp1=new Class();
//		classTemp1.setClass_id(2);
//		classTemp1.setClassName(10);
//		classTemp1.setDateAdded(ServiceUtility.getCurrentTime());
//		classTemp1.setStatus(true);
//		classTemp1.setSubClasMapp(null);
//		classTemp1.setUser(null);
//		
////		when(classRepo.save(classTemp)).thenReturn(classTemp);
//		when(classRepo.save(classTemp1)).thenReturn(classTemp1);
//		
//		when(classRepo.findAll()).thenReturn(Stream.of(classTemp1).collect(Collectors.toList()));
//		
//		assertEquals(1, classService.findAll().size());
//		
//	}
}

package com.adminportal.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.adminportal.domain.RoleDetail;
import com.adminportal.repository.RoleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class roleDetailServiceTest {

	@Autowired
	private RoleDetailService roleDetailService;
	
	@MockBean
	private RoleRepository roleRepo;
	
	@Test
	public void saveRoleTest() {
		
		  RoleDetail admin=new RoleDetail(); 
		  admin.setRoleId(4);
		  admin.setRoleName("Admin");
		  
		  when(roleRepo.save(admin)).thenReturn(admin);
		  
		  assertEquals(admin, roleDetailService.save(admin));
	}
	
	@Test
	public void findByRoleNameTest() throws Exception {
		
		  RoleDetail admin=new RoleDetail(); 
		  admin.setRoleId(4);
		  admin.setRoleName("Admin");
		  
		  when(roleRepo.save(admin)).thenReturn(admin);
		  
		  assertEquals(admin, roleDetailService.save(admin));
		  
		  RoleDetail role=null;
		  when(roleRepo.findByroleName("Admin")).thenReturn(role);
		  
		  assertEquals(role, roleDetailService.findByRoleName("Admin"));
	}
}

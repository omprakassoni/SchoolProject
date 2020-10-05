package com.adminportal.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.adminportal.domain.RoleDetail;
import com.adminportal.domain.User;
import com.adminportal.domain.UserRole;
import com.adminportal.repository.RoleRepository;
import com.adminportal.repository.UserRepository;
import com.spoken.Utility.ServiceUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleDetailService roledetailService;
	
	@MockBean
	private UserRepository userRepo;
	
	@MockBean
	private RoleRepository roleRepo;
	
	@Test
	public void createUserTest() throws Exception {
		
		  User usr=new User();
		  usr.setId(1);
		  usr.setFname("spoken"); 
		  usr.setLname("Tutorial");
		  usr.setEmail("spoken@spoken.org");
		  usr.setPassword(ServiceUtility.passwordEncoder().encode("spoken"));
		  usr.setDateAdded(ServiceUtility.getCurrentTime());
		  usr.setLastLogin(ServiceUtility.getCurrentTime());
		  usr.setSex("Male");
		  usr.setRegistered(1);
		  usr.setApproveTeacherFlag(0);
		  
		  
		  RoleDetail admin=new RoleDetail(); 
		  admin.setRoleId(4);
		  admin.setRoleName("Admin");
		  
		  when(roleRepo.save(admin)).thenReturn(admin);
		  
		  assertEquals(admin, roledetailService.save(admin));
		  
		  
		 
		  RoleDetail role=null;
		  when(roleRepo.findByroleName("Admin")).thenReturn(role);
		  
		  Set<UserRole> userRoles=new HashSet<UserRole>(); 
		  userRoles.add(new UserRole(1,usr, role));
		  
		  usr.getUserRoles().addAll(userRoles);
		  
		  when(userRepo.save(usr)).thenReturn(usr);
		  
		  assertEquals(usr, userService.createUser(usr, userRoles));

		
	}
	
	@Test
	public void existsByUserTest() throws Exception {
		
		  boolean valueUsr=false;
		  User usr=new User();
		  usr.setId(1);
		  usr.setFname("spoken"); 
		  usr.setLname("Tutorial");
		  usr.setEmail("spoken@spoken.org");
		  usr.setPassword(ServiceUtility.passwordEncoder().encode("spoken"));
		  usr.setDateAdded(ServiceUtility.getCurrentTime());
		  usr.setLastLogin(ServiceUtility.getCurrentTime());
		  usr.setSex("Male");
		  usr.setRegistered(1);
		  usr.setApproveTeacherFlag(0);
		  
		  RoleDetail role=null;
	
		  
		  Set<UserRole> userRoles=new HashSet<UserRole>(); 
		  userRoles.add(new UserRole(1,usr, role));
		  
		  usr.getUserRoles().addAll(userRoles);
		  
		  when(userRepo.save(usr)).thenReturn(usr);
		  
		  assertEquals(usr, userService.createUser(usr, userRoles));
		  
		  
		  when(userRepo.existsByemail("spoken@spoken.org")).thenReturn(valueUsr);
		  assertEquals(valueUsr, userService.existByEmail("spoken@spoken.org"));
	}
	
	@Test
	public void findByUserNameTest() throws Exception {
		
		  User usr=new User();
		  usr.setId(1);
		  usr.setFname("spoken"); 
		  usr.setLname("Tutorial");
		  usr.setEmail("spoken@spoken.org");
		  usr.setPassword(ServiceUtility.passwordEncoder().encode("spoken"));
		  usr.setDateAdded(ServiceUtility.getCurrentTime());
		  usr.setLastLogin(ServiceUtility.getCurrentTime());
		  usr.setSex("Male");
		  usr.setRegistered(1);
		  usr.setApproveTeacherFlag(0);
		  
		  RoleDetail role=null;
	
		  
		  Set<UserRole> userRoles=new HashSet<UserRole>(); 
		  userRoles.add(new UserRole(1,usr, role));
		  
		  usr.getUserRoles().addAll(userRoles);
		  
		  when(userRepo.save(usr)).thenReturn(usr);
		  
		  assertEquals(usr, userService.createUser(usr, userRoles));
		  
		  
		  when(userRepo.findByemail("spoken@spoken.org")).thenReturn(usr);
		  assertEquals(usr, userService.findByUsername("spoken@spoken.org"));
	}
	
	@Test
	public void disableEnableUserTest() throws Exception {
		
		  int result=0;
		  boolean resultFinal=false;
		  User usr=new User();
		  usr.setId(1);
		  usr.setFname("spoken"); 
		  usr.setLname("Tutorial");
		  usr.setEmail("spoken@spoken.org");
		  usr.setPassword(ServiceUtility.passwordEncoder().encode("spoken"));
		  usr.setDateAdded(ServiceUtility.getCurrentTime());
		  usr.setLastLogin(ServiceUtility.getCurrentTime());
		  usr.setSex("Male");
		  usr.setRegistered(1);
		  usr.setApproveTeacherFlag(0);
		  
		  RoleDetail role=null;
	
		  
		  Set<UserRole> userRoles=new HashSet<UserRole>(); 
		  userRoles.add(new UserRole(1,usr, role));
		  
		  usr.getUserRoles().addAll(userRoles);
		  
		  when(userRepo.save(usr)).thenReturn(usr);
		  
		  assertEquals(usr, userService.createUser(usr, userRoles));
		  
		  
		  when(userRepo.disableuser(0, 1)).thenReturn(result);
		  if(result>0) {
			resultFinal=true;  
		  }
		  
		  assertTrue(userService.disableEnableUser(0, 1)==resultFinal);
	}
	
	@Test
	public void updateUserDetailsTest() throws Exception {
		
		  int result=0;
		  boolean resultFinal=false;
		  User usr=new User();
		  usr.setId(1);
		  usr.setFname("spoken"); 
		  usr.setLname("Tutorial");
		  usr.setEmail("spoken@spoken.org");
		  usr.setPassword(ServiceUtility.passwordEncoder().encode("spoken"));
		  usr.setDateAdded(ServiceUtility.getCurrentTime());
		  usr.setLastLogin(ServiceUtility.getCurrentTime());
		  usr.setSex("Male");
		  usr.setRegistered(1);
		  usr.setApproveTeacherFlag(0);
		  
		  RoleDetail role=null;
	
		  
		  Set<UserRole> userRoles=new HashSet<UserRole>(); 
		  userRoles.add(new UserRole(1,usr, role));
		  
		  usr.getUserRoles().addAll(userRoles);
		  
		  when(userRepo.save(usr)).thenReturn(usr);
		  
		  assertEquals(usr, userService.createUser(usr, userRoles));
		  
		  
		  when(userRepo.updateUserDetails("vikash", "soni", 1)).thenReturn(result);
		  if(result>0) {
			resultFinal=true;  
		  }
		  
		  assertTrue(userService.updateUserDetails("vikash", "soni", 1)==resultFinal);
	}
	
	@SuppressWarnings("null")
	@Test
	public void findAllTest() throws Exception {
		
		  User usr=new User();
		  usr.setId(1);
		  usr.setFname("spoken"); 
		  usr.setLname("Tutorial");
		  usr.setEmail("spoken@spoken.org");
		  usr.setPassword(ServiceUtility.passwordEncoder().encode("spoken"));
		  usr.setDateAdded(ServiceUtility.getCurrentTime());
		  usr.setLastLogin(ServiceUtility.getCurrentTime());
		  usr.setSex("Male");
		  usr.setRegistered(1);
		  usr.setApproveTeacherFlag(0);
		  
		  RoleDetail role=null;
	
		  
		  Set<UserRole> userRoles=new HashSet<UserRole>(); 
		  userRoles.add(new UserRole(1,usr, role));
		  
		  usr.getUserRoles().addAll(userRoles);
		  
		  when(userRepo.save(usr)).thenReturn(usr);
		  
		  assertEquals(usr, userService.createUser(usr, userRoles));
		  
		  when(userRepo.findAll()).thenReturn(Stream.of(usr).collect(Collectors.toList()));
		  
		  assertEquals(1, userService.findAll().size());
	}


}

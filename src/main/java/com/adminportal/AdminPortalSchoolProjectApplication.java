/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : Starting point of Spring Project.
 */
package com.adminportal;

import java.util.HashSet;
import java.util.Set;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.adminportal.domain.RoleDetail;
import com.adminportal.domain.User;
import com.adminportal.domain.UserRole;
import com.adminportal.service.RoleDetailService;
import com.adminportal.service.UserRoleService;
import com.adminportal.service.UserService;
import com.spoken.Utility.ServiceUtility;

@SpringBootApplication
public class AdminPortalSchoolProjectApplication extends SpringBootServletInitializer implements CommandLineRunner  {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleDetailService roleService;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		
		return builder.sources(AdminPortalSchoolProjectApplication.class);
	}
	

	public static void main(String[] args) {
		new java.io.File(HomeController.uploadDirectory).mkdir();
		new java.io.File(HomeController.uploadTeacherDirectory).mkdir();
		SpringApplication.run(AdminPortalSchoolProjectApplication.class, args);
	}
	
	


	@Override
	public void run(String... args) throws Exception {
		
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
		
		RoleDetail learner=new RoleDetail();
		learner.setRoleId(1);
		learner.setRoleName("Learner");
		roleService.save(learner);
		
		
		RoleDetail parent=new RoleDetail();
		parent.setRoleId(2);
		parent.setRoleName("Parent");
		
		roleService.save(parent);
	
		RoleDetail teacher=new RoleDetail();
		teacher.setRoleId(3);
		teacher.setRoleName("Teacher");
		
		roleService.save(teacher);
		
		RoleDetail admin=new RoleDetail();
		admin.setRoleId(4);
		admin.setRoleName("Admin");
		
		roleService.save(admin);
		
		
		RoleDetail role=roleService.findByRoleName("Admin");
		
		Set<UserRole> userRoles=new HashSet<UserRole>();
		userRoles.add(new UserRole(1,usr, role));												
		
		
		userService.createUser(usr, userRoles);
		
		
	}
	
	
}

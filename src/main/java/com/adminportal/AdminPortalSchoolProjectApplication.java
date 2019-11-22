/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : Starting point of Spring Project.
 */
package com.adminportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdminPortalSchoolProjectApplication {

	public static void main(String[] args) {
		new java.io.File(HomeController.uploadDirectory).mkdir();
		new java.io.File(HomeController.uploadTeacherDirectory).mkdir();
		SpringApplication.run(AdminPortalSchoolProjectApplication.class, args);
	}

}

/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : This Class is a Controller Class
 * 					All Registration url will be caught here.
 * 					New User is categorized in Learner,Parent and teacher
 */

package com.adminportal;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.adminportal.content.Class;
import com.adminportal.domain.RoleDetail;
import com.adminportal.domain.User;
import com.adminportal.domain.UserRole;
import com.adminportal.service.ClassService;
import com.adminportal.service.RoleDetailService;
import com.adminportal.service.UserService;
import com.spoken.Utility.ServiceUtility;

@Controller
public class RegistrationController {
	
	public static final String uploadDirectory="src/main/resources/static"+"/Media/content/";		/* path to which content will get stored */
	public static final String uploadTeacherDirectory="src/main/resources/static/Media/Teacher/";  /* path to which teachers document will get stored */
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private RoleDetailService roleService;
	
/*------------------------------------------Registration Task Method (LEARNER)-----------------------------------------------------------------*/
	
	
	@RequestMapping(value = "/newUserL", method = RequestMethod.POST )
	public ModelAndView addNewUserLearner(HttpServletRequest req,ModelAndView mv) throws Exception {
		String email=req.getParameter("email");
		String fname=req.getParameter("txtLearnersFirstname");
		String lname=req.getParameter("txtLearnersLastname");
		String password=req.getParameter("password1");
		String gender=req.getParameter("gender");
		String school_name=req.getParameter("txtLearnersSchoolName");
		String school_address=req.getParameter("txtLearnersSchoolAddress");
		String pincodeString=req.getParameter("txtPincode");
		int pincode=Integer.parseInt(pincodeString);
		String passwordEncrypt=ServiceUtility.passwordEncoder().encode(password);
		
		
		if(userService.existByEmail(email)) {										// check for already email exist
			
			ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
			mv.addObject("classfromDatabase", standard);
			
			mv.addObject("FailureL", "E-mail Already Exist");
			mv.addObject("checkedOptionLearner", "checked");
			mv.setViewName("Signup");
			
			return mv;
			
		}
	
		if(!password.equals(req.getParameter("password2"))) {						// check for password and confirm password equality
			
			ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
			mv.addObject("classfromDatabase", standard);
			
			mv.addObject("FailureL", "Password & Confirm Password Doesn't match");
			mv.addObject("checkedOptionLearner", "checked");
			mv.setViewName("Signup");
			
			return mv;
			
		}
		
		String date=req.getParameter("txtdate");									// conversion String to DAte Object value
		SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateUtil=sd1.parse(date);
		Date dateOfBirth=new Date(dateUtil.getTime());
		
	
		RoleDetail role=roleService.findByRoleName("Learner");
		
		User usr=new User();
		
		usr.setEmail(email);
		usr.setPassword(passwordEncrypt);
		usr.setFname(fname);
		usr.setLname(lname);
		usr.setSex(gender);
		usr.setSchoolName(school_name);
		usr.setSchoolAddress(school_address);
		usr.setPincode(pincode);
		usr.setDateAdded(ServiceUtility.getCurrentTime());
		usr.setLastLogin(ServiceUtility.getCurrentTime());
		usr.setDateOfBirth(dateOfBirth);
		usr.setRegistered(1);
		
		Set<UserRole> userRoles=new HashSet<UserRole>();
		userRoles.add(new UserRole(usr, role));												// persisting user(Learner)
			
		
		userService.createUser(usr, userRoles);
		mv.setViewName("redirect:/");
		
		return mv;
	}
	
	
/*-------------------------------------------------------END--------------------------------------------------------------------------------	
	/*------------------------------------------Registration Task Method (PARENT)-----------------------------------------------------------------*/	
	
	
	@RequestMapping(value = "/newUserP", method = RequestMethod.POST )
	public ModelAndView addNewUserParent(HttpServletRequest req,ModelAndView mv) throws Exception {
		
		String email=req.getParameter("email");
		String fname=req.getParameter("txtParentsFirstname");
		String lname=req.getParameter("txtParentsLastname");
		String password=req.getParameter("password");
		String gender=req.getParameter("gender");
		String passwordEncrypt=ServiceUtility.passwordEncoder().encode(password);
		
		if(userService.existByEmail(email)) {											// check for already email exist
			
			ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
			mv.addObject("classfromDatabase", standard);
			
			mv.addObject("FailureP", "E-mail Already Exist");
			mv.addObject("checkedOptionParent", "checked");
			mv.setViewName("Signup");
			
			return mv;
			
		}
		
		if(!password.equals(req.getParameter("Confpassword"))) {						// check for password and confirm password equality
			
			ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
			mv.addObject("classfromDatabase", standard);
			
			mv.addObject("FailureP", "Password & Confirm Password Doesn't match");
			mv.addObject("checkedOptionParent", "checked");
			mv.setViewName("Signup");
			
			return mv;
			
		}
	
	
		RoleDetail role=roleService.findByRoleName("Parent");
		
		User usr=new User();
		
		usr.setEmail(email);
		usr.setPassword(passwordEncrypt);
		usr.setFname(fname);
		usr.setLname(lname);
		usr.setSex(gender);
		usr.setDateAdded(ServiceUtility.getCurrentTime());
		usr.setLastLogin(ServiceUtility.getCurrentTime());
		usr.setRegistered(1);
		
		Set<UserRole> userRoles=new HashSet<UserRole>();
		userRoles.add(new UserRole(usr, role));
		
		
		userService.createUser(usr, userRoles);													// persisting user(Parent)
		
		
		mv.setViewName("redirect:/");
		return mv;
	}
	
	/*------------------------------------------------------END-------------------------------------------------------------------------------------*/
	/*------------------------------------------Registration Task Method (TEACHER)-----------------------------------------------------------------*/
	
	
	@RequestMapping(value = "/newUserT", method = RequestMethod.POST )
	public ModelAndView addNewUserTeacher(@RequestParam("txtTeacherDocument") MultipartFile[] uploadDocument,HttpServletRequest req,ModelAndView mv) throws Exception {
		
		String email=req.getParameter("email");
		String fname=req.getParameter("txtTeacherFirstname");
		String lname=req.getParameter("txtTeacherLastname");
		String password=req.getParameter("password");
		String gender=req.getParameter("gender");
		String passwordEncrypt=ServiceUtility.passwordEncoder().encode(password);
		
		if(!ServiceUtility.checkFileExtensionImage(uploadDocument)) {						// validate against Image file
			
			ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
			mv.addObject("classfromDatabase", standard);
			
			mv.addObject("checkedOptionTeacher", "checked");
			mv.addObject("FailureT", "Please Upload only Image File");
			mv.setViewName("Signup");
			
			return mv;
			
		}
		
		if(userService.existByEmail(email)) {												// check for already email exist
			
			ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
			mv.addObject("classfromDatabase", standard);
			
			mv.addObject("FailureT", "E-mail Already Exist");
			mv.addObject("checkedOptionTeacher", "checked");
			mv.setViewName("Signup");
			
			return mv;
			
		}
		
		if(!password.equals(req.getParameter("Confpassword"))) {							// check for password and confirm password equality
			
			ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
			mv.addObject("classfromDatabase", standard);
			
			mv.addObject("FailureT", "Password & Confirm Password Doesn't match");
			mv.addObject("checkedOptionTeacher", "checked");
			mv.setViewName("Signup");
			
			return mv;
			
		}
		
		String documentLocal=ServiceUtility.uploadFile(uploadDocument, uploadTeacherDirectory);
		
		int indexToStart=documentLocal.indexOf('M');
		
		String document=documentLocal.substring(indexToStart, documentLocal.length());
		
	
		RoleDetail role=roleService.findByRoleName("Teacher");
		
		User usr=new User();
		
		usr.setEmail(email);
		usr.setPassword(passwordEncrypt);
		usr.setFname(fname);
		usr.setLname(lname);
		usr.setSex(gender);
		usr.setDateAdded(ServiceUtility.getCurrentTime());
		usr.setLastLogin(ServiceUtility.getCurrentTime());
		usr.setRegistered(0);
		usr.setDocument(document);

		
		Set<UserRole> userRoles=new HashSet<UserRole>();
		userRoles.add(new UserRole(usr, role));												
		
		
		userService.createUser(usr, userRoles);										// persist user (Teacher)
		
		
		mv.setViewName("redirect:/");
		return mv;
	}
	
	
/*----------------------------------------------------------------------------------END---------------------------------------------------------------*/

	

}

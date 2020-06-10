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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.adminportal.content.Class;
import com.adminportal.content.Subject;
import com.adminportal.domain.RoleDetail;
import com.adminportal.domain.User;
import com.adminportal.domain.UserRole;
import com.adminportal.service.ClassService;
import com.adminportal.service.RoleDetailService;
import com.adminportal.service.SubjectService;
import com.adminportal.service.UserRoleService;
import com.adminportal.service.UserService;
import com.spoken.Utility.ServiceUtility;

@Controller
public class RegistrationController {
	
	public static final String uploadTeacherDirectory="Media/User/";  /* path to which teachers document will get stored */
	public static final long fileSize=10*1024*1024; 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private RoleDetailService roleService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private SubjectService subjectService;
	
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
		
		ArrayList<Subject> subject = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subject);
		
		if(!ServiceUtility.checkEmailValidity(email)) {
			ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
			mv.addObject("classfromDatabase", standard);
			
			mv.addObject("FailureL", "Please Enter valid E-mail");
			mv.addObject("checkedOptionLearner", "checked");
			mv.setViewName("Signup");
			
			return mv;
		}
		
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
		if(!dateOfBirth.before(ServiceUtility.getCurrentTime())) {
			ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
			mv.addObject("classfromDatabase", standard);
			
			mv.addObject("FailureL", "Please enter previous date");
			mv.addObject("checkedOptionLearner", "checked");
			mv.setViewName("Signup");
			
			return mv;
			
		}
	
		RoleDetail role=roleService.findByRoleName("Learner");
		
		User usr=new User();
		usr.setId(userService.countRow()+1);
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
		userRoles.add(new UserRole(userRoleService.countRow()+1,usr, role));												// persisting user(Learner)
			
		
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
		
		ArrayList<Subject> subject = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subject);
		
		if(!ServiceUtility.checkEmailValidity(email)) {
			ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
			mv.addObject("classfromDatabase", standard);
			
			mv.addObject("FailureP", "Please Enter Valid E-mail");
			mv.addObject("checkedOptionParent", "checked");
			mv.setViewName("Signup");
			
			return mv;
		}
		
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
		usr.setId(userService.countRow()+1);
		usr.setEmail(email);
		usr.setPassword(passwordEncrypt);
		usr.setFname(fname);
		usr.setLname(lname);
		usr.setSex(gender);
		usr.setDateAdded(ServiceUtility.getCurrentTime());
		usr.setLastLogin(ServiceUtility.getCurrentTime());
		usr.setRegistered(1);
		
		Set<UserRole> userRoles=new HashSet<UserRole>();
		userRoles.add(new UserRole(userRoleService.countRow()+1,usr, role));
		
		
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
		
		ArrayList<Subject> subject = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subject);
		
		if(!ServiceUtility.checkEmailValidity(email)) {
			ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
			mv.addObject("classfromDatabase", standard);
			
			mv.addObject("FailureT", "Please Enter Valid E-mail");
			mv.addObject("checkedOptionTeacher", "checked");
			mv.setViewName("Signup");
			
			return mv;
		}
		
		if(!ServiceUtility.checkFileExtensionImage(uploadDocument)) {						// validate against Image file
			
			ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
			mv.addObject("classfromDatabase", standard);
			
			mv.addObject("checkedOptionTeacher", "checked");
			mv.addObject("FailureT", "Please Upload only Image File");
			mv.setViewName("Signup");
			
			return mv;
			
		}
		
		if(uploadDocument[0].getSize()>fileSize) {
			
			ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
			mv.addObject("classfromDatabase", standard);
			
			mv.addObject("checkedOptionTeacher", "checked");
			mv.addObject("FailureT", "FileSize must be within 10MB");
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
		
		/***************** creating folder for Storing Teacher data *************************************/
		
		boolean path_creation=ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+uploadTeacherDirectory+email+"/Document");
		
		
		String pathtoUploadteacherData=env.getProperty("spring.applicationexternalPath.name")+uploadTeacherDirectory+email+"/Document";
		
		String documentLocal=ServiceUtility.uploadFile(uploadDocument, pathtoUploadteacherData);
		
		int indexToStart=documentLocal.indexOf("Media");
		
		String document=documentLocal.substring(indexToStart, documentLocal.length());
		
	
		RoleDetail role=roleService.findByRoleName("Teacher");
		
		User usr=new User();
		usr.setId(userService.countRow()+1);
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
		userRoles.add(new UserRole(userRoleService.countRow()+1,usr, role));												
		
		
		userService.createUser(usr, userRoles);										// persist user (Teacher)
		
		
		mv.setViewName("redirect:/");
		return mv;
	}
	
	
/*----------------------------------------------------------------------------------END---------------------------------------------------------------*/

	

}

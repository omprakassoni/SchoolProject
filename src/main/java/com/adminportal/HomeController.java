/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */



package com.adminportal;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Class;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.Events;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Subject;
import com.adminportal.content.SubjectClassMapping;
import com.adminportal.content.Testimonial;
import com.adminportal.content.Topic;
import com.adminportal.content.VideoExternal;
import com.adminportal.domain.RoleDetail;
import com.adminportal.domain.User;
import com.adminportal.domain.UserRole;
import com.adminportal.service.ArticleExternalService;
import com.adminportal.service.ClassService;
import com.adminportal.service.DocumentExternalService;
import com.adminportal.service.EventService;
import com.adminportal.service.LessonPlanService;
import com.adminportal.service.PhetsService;
import com.adminportal.service.QuizQuestionService;
import com.adminportal.service.RoleDetailService;
import com.adminportal.service.SubjectClassService;
import com.adminportal.service.SubjectService;
import com.adminportal.service.TestimonialService;
import com.adminportal.service.TopicService;
import com.adminportal.service.UserService;
import com.adminportal.service.VideoExternalService;
import com.spoken.Utility.ServiceUtility;


@Controller
@SessionAttributes({"UserLogedAdmin","UserNameAdmin","UserLogedUserView","UserNameUserSide"})
public class HomeController {
	
	public static final String uploadDirectory="src/main/resources/static"+"/Media/content/";
	public static final String uploadTeacherDirectory="src/main/resources/static/Media/Teacher/";
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private SubjectClassService subjectClassService;
	
	@Autowired
	private  SubjectService subjectService;
	

	@Autowired
	private TopicService topicService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ArticleExternalService articleService;
	
	@Autowired
	private DocumentExternalService documentService;
	
	@Autowired
	private LessonPlanService lessonService;
	
	@Autowired
	private PhetsService phetService;
	
	@Autowired
	private QuizQuestionService quizService;
	
	@Autowired
	private VideoExternalService videoService;
	
	@Autowired
	private RoleDetailService roleService;
	
	@Autowired
	private TestimonialService testiService;
	
	@Autowired
	private EventService eventService;
	
	////////////////////////
	
	

	
/* ------------------------------------------HOME PAGE-------------------------------------------------*/

	
	
	@RequestMapping("/")
	public ModelAndView home(ModelAndView mv) {
		
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		
		List<Testimonial> testidata=testiService.getAlltestimonial();
		
		List<Events> eventData=eventService.getAllEventdata();
		
		
		mv.addObject("Events", eventData);
		mv.addObject("Testimonial", testidata);
		mv.addObject("classfromDatabase", standard);
		mv.setViewName("Index");
		return mv;
	}
	
	
	
	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public ModelAndView adminHomeGet(HttpServletRequest req,ModelAndView mv) {
		HttpSession session=req.getSession(false);
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			
			
			String userLogged=(String) session.getAttribute("UserLogedAdmin");
			try{
				
				if(userLogged.isEmpty()) {
				 mv.setViewName("redirect:/adminPortal");
				
				}else {
			
						mv.setViewName("home");
				}
			}catch (Exception e) {
				 mv.setViewName("redirect:/adminPortal");
				
			}
		}
		return mv;
	}
	
	@RequestMapping(value = "/logout",method = RequestMethod.POST)
	public String logoutGet(HttpServletRequest req) {
		HttpSession session=req.getSession(false);
		session.invalidate();
		
		return "redirect:/adminPortal";
		
	}
	@RequestMapping(value = "/home",method = RequestMethod.POST)
	public ModelAndView adminHomepost(HttpServletRequest req,ModelAndView mv) {
		
		String username=req.getParameter("username");
		String password=ServiceUtility.passwordEncoder().encode(req.getParameter("password"));
		boolean status=false;
		
		User usr=(User) userService.existsByUser(username, password);
		if(usr!=null) {
			List<UserRole> local=(List<UserRole>) usr.getUserRoles();
			for(UserRole role:local) {
				if(role.getRole().getRoleName().contentEquals("Admin")) {
					status=true;
				}
			}
			
			if(status) {
				HttpSession session=req.getSession();
				session.setAttribute("UserLogedAdmin", usr.getEmail());
				session.setAttribute("UserNameAdmin", usr.getFname());
				
				mv.setViewName("home");
				
			}else {
				mv.setViewName("adminLoginPage");
				
			}
			
		
			
		}else {
			mv.addObject("Error", "Login Credentials are Incorrect");
			mv.setViewName("adminLoginPage");
			System.out.println("vikash");
			
		}
		


		return mv;
		
	}
	
	@RequestMapping(value = "/adminPathUi", method = RequestMethod.GET)
	public ModelAndView adminPortalFromUI(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession();
		String loggedUser=(String) session.getAttribute("UserLogedAdmin");
		if(loggedUser==null) {
			session.setAttribute("UserLogedAdmin", (String)session.getAttribute("UserLogedUserView"));
			session.setAttribute("UserNameAdmin", (String)session.getAttribute("UserNameUserSide"));
			mv.setViewName("redirect:/home");
		}
		
		
		
		
		return mv;
	}
	
	/********************************************** USER SIDE AUTHENTICATION********************************************************/
	
	@RequestMapping(value = "/userLogin",method = RequestMethod.POST)
	public ModelAndView userLoginpost(HttpServletRequest req,ModelAndView mv) {
		
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		
		String username=req.getParameter("username");
		String password=ServiceUtility.passwordEncoder().encode(req.getParameter("password"));
		boolean status=false;
		
		User usr=(User) userService.existsByUser(username, password);
		if(usr!=null) {
			
				HttpSession session=req.getSession();
				session.setAttribute("UserLogedUserView", usr.getEmail());
				session.setAttribute("UserNameUserSide", usr.getFname());

				mv.setViewName("redirect:/");
			
		}else {
			mv.addObject("Error", "Login Credentials are Incorrect");
			mv.setViewName("Login");
			
			
		}
		


		return mv;
		
	}
	
	
/**********************************************************************************************************************/
	
	@RequestMapping(value = "/myAccount",method=RequestMethod.GET)
	public ModelAndView myAccountGet(ModelAndView mv,HttpServletRequest req) {
		
		HttpSession session=req.getSession(false);
		if(session==null) {
			mv.setViewName("redirect:/");
		}else {
			
			
			
			String loggedUser=(String) session.getAttribute("UserLogedUserView");
			User localUser=userService.findByUsername(loggedUser);
			List<UserRole> localUserRole=localUser.getUserRoles();
			for(UserRole temp:localUserRole) {
				mv.addObject("LoggedUserRole", temp.getRole().getRoleName());
				
			}
			
			List<QuizQuestion> localQuiz=quizService.findALlByUser(localUser);
			List<ArticleExternal> localArticle=articleService.findALlByUser(localUser);
			List<DocumentExternal> localDocument=documentService.findALlByUser(localUser);
			List<LessonPlan> localLesson=lessonService.findALlByUser(localUser);
			List<VideoExternal> localvideo=videoService.findALlByUser(localUser);
			List<Phets> localPhets=phetService.findALlByUser(localUser);
			
			if(localQuiz.isEmpty()) {
				mv.addObject("QuizError", "Nothing To Show");
			}
			
			if(localArticle.isEmpty()) {;
				mv.addObject("ArticleError", "Nothing To Show");
			}
			
			if(localDocument.isEmpty()) {
				mv.addObject("DocumentError", "Nothing To Show");
			}
			
			if(localLesson.isEmpty()) {
				mv.addObject("LessonError", "Nothing To Show");
			}
			
			if(localvideo.isEmpty()) {
				mv.addObject("VideoError", "Nothing To Show");
			}
			
			if(localPhets.isEmpty()) {
				mv.addObject("PhetError", "Nothing To Show");
			}
			
			mv.addObject("QuizOnUser", localQuiz);
			mv.addObject("VideoOnUser", localvideo);
			mv.addObject("ArticleOnUser", localArticle);
			mv.addObject("DocumentOnUser", localDocument);
			mv.addObject("LessonOnUser", localLesson);
			mv.addObject("PhetOnUser", localPhets);
			
			
			ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
			mv.addObject("classfromDatabase", standard);
			mv.addObject("LoggedUser",localUser);
			
			
			mv.setViewName("myAccount");
		}
		return mv;
		
	}
	
//---------------------------------------------END--------------------------------------------------------------------
	
//------------------------------------------LOGIN HYPERLINK--------------------------------------------------------------------
	
	
	
	@RequestMapping("/Login")
	public ModelAndView login(ModelAndView mv) {
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		mv.setViewName("Login");
		return mv;
	}

	
	
//---------------------------------------------END--------------------------------------------------------------------
	
	
//-----------------------------------------------SIGNUP HYPERLINK------------------------------------------------------------------	
	

	
	@RequestMapping("/Signup")
	public ModelAndView signup(ModelAndView mv){
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		mv.addObject("checkedOptionLearner", "checked");
		mv.addObject("classfromDatabase", standard);
		mv.setViewName("Signup");
		return mv;

	}
	
	
	
//---------------------------------------------END--------------------------------------------------------------------
	
	@RequestMapping(value = "/adminPortal")
	public String adminHomepage() {
		return "adminLoginPage";
	}
	
	
//	@RequestMapping(value="/adminPortal")
//		public String homepageGet() {
//			return "home";
//	}
	
	
	@RequestMapping(value = "/eventsList")
	public ModelAndView viewEvent(ModelAndView mv) {
		
		List<Events> localEvent=eventService.findAll();
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
	
		
		mv.addObject("classfromDatabase", standard);
		mv.addObject("EventList", localEvent);
		mv.setViewName("events");
		return mv;
	}
	
	

	
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
		
		
		if(userService.existByEmail(email)) {
			
			mv.addObject("FailureL", "E-mail Already Exist");
			mv.addObject("checkedOptionLearner", "checked");
			mv.setViewName("Signup");
			
			return mv;
			
		}
	
		if(!password.equals(req.getParameter("password2"))) {
			mv.addObject("FailureL", "Password & Confirm Password Doesn't match");
			mv.addObject("checkedOptionLearner", "checked");
			mv.setViewName("Signup");
			
			return mv;
			
		}
		
		String date=req.getParameter("txtdate");
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
		userRoles.add(new UserRole(usr, role));
		
		
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
		
		if(userService.existByEmail(email)) {
			
			mv.addObject("FailureP", "E-mail Already Exist");
			mv.addObject("checkedOptionParent", "checked");
			mv.setViewName("Signup");
			
			return mv;
			
		}
		
		if(!password.equals(req.getParameter("Confpassword"))) {
			mv.addObject("FailureP", "Password & Confirm Password Doesn't match");
			mv.addObject("checkedOptionParent", "checked");
			mv.setViewName("Signup");
			
			return mv;
			
		}
	
	
		RoleDetail role=roleService.findByRoleName("Admin");
		
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
		
		
		userService.createUser(usr, userRoles);
		
		
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
		
		if(userService.existByEmail(email)) {
			
			mv.addObject("FailureT", "E-mail Already Exist");
			mv.addObject("checkedOptionTeacher", "checked");
			mv.setViewName("Signup");
			
			return mv;
			
		}
		
		if(!password.equals(req.getParameter("Confpassword"))) {
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
		
		
		userService.createUser(usr, userRoles);
		
		
		mv.setViewName("redirect:/");
		return mv;
	}
	
	
/*----------------------------------------------------------------------------------END---------------------------------------------------------------*/

	

	
	/*------------------------------------------ADD CLASS (ADMIN MODULE)-----------------------------------------------------------------*/
	
	
	@RequestMapping(value="/addClass", method = RequestMethod.GET)
	public ModelAndView addClassget(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		
		ArrayList<String> newNonExistClass=new ArrayList<String>();
		newNonExistClass.add("Class 1");
		newNonExistClass.add("Class 2");
		newNonExistClass.add("Class 3");
		newNonExistClass.add("Class 4");
		newNonExistClass.add("Class 5");
		newNonExistClass.add("Class 6");
		newNonExistClass.add("Class 7");
		newNonExistClass.add("Class 8");
		newNonExistClass.add("Class 9");
		newNonExistClass.add("Class 10");
		newNonExistClass.add("Class 11");
		newNonExistClass.add("Class 12");
		

		
		for(Class s:standard) {
			if(newNonExistClass.contains(s.getClassName()))
				newNonExistClass.remove(s.getClassName());
		}

		mv.addObject("classExist", newNonExistClass);
		mv.setViewName("addClass");
		}
		
		return mv;
	}
	
	
	@RequestMapping(value="/addClass", method = RequestMethod.POST)
	public ModelAndView addClassPost(HttpServletRequest req,@RequestParam(name="classSelected") String classSelected , ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		System.out.println(session.getAttribute("User"));
		
//		Class class_data=new Class();
//		class_data.setClassName(classSelected);
//		
//		classService.save(class_data);
//		mv.addObject("status", "Added Sucessfully");
		
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		
		ArrayList<String> newNonExistClass=new ArrayList<String>();
		newNonExistClass.add("Class 1");
		newNonExistClass.add("Class 2");
		newNonExistClass.add("Class 3");
		newNonExistClass.add("Class 4");
		newNonExistClass.add("Class 5");
		newNonExistClass.add("Class 6");
		newNonExistClass.add("Class 7");
		newNonExistClass.add("Class 8");
		newNonExistClass.add("Class 9");
		newNonExistClass.add("Class 10");
		newNonExistClass.add("Class 11");
		newNonExistClass.add("Class 12");
		
		for(Class s:standard) {
			if(newNonExistClass.contains(s.getClassName()))
				newNonExistClass.remove(s.getClassName());

		}

		mv.addObject("classExist", newNonExistClass);
		
		
		mv.setViewName("addClass");
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/

	
	/*------------------------------------------ADD ARTICLE (ADMIN MODULE)-----------------------------------------------------------------*/
	
	
	@RequestMapping(value="/addArticle",method = RequestMethod.GET)
	public ModelAndView addArtcileGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		mv.setViewName("addArticle");
		
		}
		return mv;
	}
	
	@RequestMapping(value="/addArticle",method = RequestMethod.POST)
	public ModelAndView addArtcilePost(HttpServletRequest req,ModelAndView mv) {
		
		String className=req.getParameter("classSelected");
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("description");
		String url=req.getParameter("url");
		String source=req.getParameter("source");
	
		
		String emailToIdentifyUser;
		
		HttpSession session=req.getSession(false);
		
		 emailToIdentifyUser=(String) session.getAttribute("UserLogedAdmin");
		
		
		
		

		Class localClass=classService.findByClassName(className);
		Subject localSubject=subjectService.findBysubName(subjectName);
		SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
		Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
		
		
		
		User usr=userService.findByUsername(emailToIdentifyUser);
		Set<ArticleExternal> articlemapping=new HashSet<ArticleExternal>();
		articlemapping.add(new ArticleExternal(articleService.countRow()+1, "Article", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, url, 0,  ServiceUtility.getCurrentTime(), localTopic, usr));
		
	
	
		userService.addUserToArticle(usr, articlemapping);
		
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
	
		
		mv.setViewName("addArticle");
		
		
		return mv;
		
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------ADD DOCUMENT (ADMIN MODULE)-----------------------------------------------------------------*/
	
	@RequestMapping(value="/addDocument",method = RequestMethod.GET)
	public ModelAndView addDocumentGet(HttpServletRequest req,ModelAndView mv) {
		
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		mv.setViewName("addDocument");
		}
		return mv;
		
	}
	
	@RequestMapping(value="/addDocument",method = RequestMethod.POST)
	public ModelAndView addDocumentPost(HttpServletRequest req,@RequestParam(name="Question")MultipartFile[] document,ModelAndView mv) {
		
		String path1=null;
		String className=req.getParameter("classSelected");
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("description");
		String source=req.getParameter("source");
		
		
		String emailToIdentifyUser;
		
		HttpSession session=req.getSession(false);
		
		emailToIdentifyUser=(String) session.getAttribute("UserLogedAdmin");
	
		
		String createFolder=uploadDirectory+className+"_"+subjectName+"/"+topicName+"/Document/";
		
		
		path1=ServiceUtility.uploadFile(document, createFolder);
		
		
		
		int indexToStart=path1.indexOf('M');
		String path=path1.substring(indexToStart, path1.length());
		

		Class localClass=classService.findByClassName(className);
		Subject localSubject=subjectService.findBysubName(subjectName);
		SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
		Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
		

		
		User usr=userService.findByUsername(emailToIdentifyUser);
		
		Set<DocumentExternal> documentMapping=new HashSet<DocumentExternal>();
		documentMapping.add(new DocumentExternal(documentService.countRow()+1, "Document", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, path, 0, ServiceUtility.getCurrentTime(), localTopic, usr));
	
	
		userService.addUserToDocument(usr, documentMapping);
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		
		mv.setViewName("addDocument");
		
		
		return mv;
		
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------ADD LESSON PLAN (ADMIN MODULE)-----------------------------------------------------------------*/
	
	@RequestMapping(value="/addLessonPlan",method = RequestMethod.GET)
	public ModelAndView addLessonPlanGet(HttpServletRequest req,ModelAndView mv) {
		
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		mv.setViewName("addLessonPlan");
		}
		return mv;
		
	}
	
	@RequestMapping(value="/addLessonPlan",method = RequestMethod.POST)
	public ModelAndView addLessonPlanPost(HttpServletRequest req,@RequestParam(name="lesson") MultipartFile[] lesson,ModelAndView mv) {
		
		String path1=null;
		String className=req.getParameter("classSelected");
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		
		String emailToIdentifyUser;
		
		HttpSession session=req.getSession(false);
		
		 emailToIdentifyUser=(String) session.getAttribute("UserLogedAdmin");
		 
		
		

		
		String createFolder=uploadDirectory+className+"_"+subjectName+"/"+topicName+"/Lessonplan/";
		
		path1=ServiceUtility.uploadFile(lesson, createFolder);
		
		int indexToStart=path1.indexOf('M');
		String path=path1.substring(indexToStart, path1.length());
		
		
		

		Class localClass=classService.findByClassName(className);
		Subject localSubject=subjectService.findBysubName(subjectName);
		SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
		Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
		
	
		
		User usr=userService.findByUsername(emailToIdentifyUser);
		
		Set<LessonPlan> lessonMapping=new HashSet<LessonPlan>();
		lessonMapping.add(new LessonPlan(lessonService.countRow()+1, "Lesson", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), path, 0, ServiceUtility.getCurrentTime(), localTopic, usr));
		
		
		userService.addUserToLessonplan(usr, lessonMapping);
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		
		
		mv.addObject("lessonStatus", "Added Sucessfully");
		
			
		mv.setViewName("addLessonPlan");
		
		
	
		return mv;
		
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	/*------------------------------------------ADD PHET (ADMIN MODULE)-----------------------------------------------------------------*/
	
	@RequestMapping(value="/addPhets",method = RequestMethod.GET)
	public ModelAndView addPhetsGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		mv.setViewName("addPhets");
		}
		return mv;
	
	}
	
	@RequestMapping(value="/addPhets",method = RequestMethod.POST)
	public ModelAndView addPhetsPost(HttpServletRequest req,ModelAndView mv) {
		

		String className=req.getParameter("classSelected");
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("description");
		String phet=req.getParameter("phet");
		String source=req.getParameter("source");
		String phetPath=null;

		
		String emailToIdentifyUser;
		
		HttpSession session=req.getSession(false);
		
	
		 emailToIdentifyUser=(String) session.getAttribute("UserLogedAdmin");
		
		
		
			if(phet.length()>0) {
			
			if(!phet.startsWith("<iframe")) {
				
			}
			try {
				int indexOfSrc=phet.indexOf("src");
				int lastIndexOfSrc =phet.indexOf(' ', indexOfSrc);
				String temp=phet.substring(indexOfSrc, lastIndexOfSrc);
				int ind=temp.indexOf('=');
				String urlUpload1=temp.substring(ind+2, temp.length()-1);
				phetPath=urlUpload1;
			
			}catch(Exception e) {
				
			}
			
		}else {
			
		}
		

		Class localClass=classService.findByClassName(className);
		Subject localSubject=subjectService.findBysubName(subjectName);
		SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
		Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
		
		
		User usr=userService.findByUsername(emailToIdentifyUser);
		
		
		Set<Phets> phetMapping=new HashSet<Phets>();
		phetMapping.add(new Phets(phetService.countRow()+1, "Phets", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, phetPath, 0, ServiceUtility.getCurrentTime(), localTopic, usr));
	
		
		userService.addUserToPhets(usr, phetMapping);
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		
		
		mv.addObject("phetsStatus", "Added Sucessfully");
		
			
		
			
		mv.setViewName("addPhets");
		
		
		
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	
	/*------------------------------------------ADD TOPIC (ADMIN MODULE)-----------------------------------------------------------------*/
	
	
	@RequestMapping(value="/addTopic",method=RequestMethod.POST)
	public ModelAndView addTopicPost(@RequestParam("posterQ") MultipartFile[] poster,HttpServletRequest req,ModelAndView mv) throws Exception {
		
		String path1=null;
		String uploadDirectoryPoster=null;
		String className=req.getParameter("classSelected");
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topic");
		String topicDesc=req.getParameter("descriptionQ");
		
		
		System.out.println(poster);
		
		boolean b=ServiceUtility.createclassSubjectFolder(className, subjectName,topicName) ;
		

		
			uploadDirectoryPoster=uploadDirectory+className+"_"+subjectName+"/"+topicName+"/";
			System.out.println(uploadDirectoryPoster);
			
			path1=ServiceUtility.uploadFile(poster, uploadDirectoryPoster);
			
 	
		int indexToStart=path1.indexOf('M');
		String path=path1.substring(indexToStart, path1.length());
		
		Class localClass=classService.findByClassName(className);
		Subject localSubject =subjectService.findBysubName(subjectName);
		SubjectClassMapping localsubjectClassMapping=subjectClassService.findBysubAndstandard(localClass,localSubject);
		
		System.out.println(localsubjectClassMapping.getSubClassId());
		
		Topic addTopic=new Topic();
		addTopic.setPoster(path);
		addTopic.setTopicName(topicName);
		addTopic.setDescription(topicDesc);
		addTopic.setDateAdded(ServiceUtility.getCurrentTime());
		addTopic.setDateModified(ServiceUtility.getCurrentTime());
		addTopic.setSubjectClassMapping(localsubjectClassMapping);
		
		subjectClassService.createTopic(addTopic, localsubjectClassMapping);
		
		
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();
		
		mv.addObject("status", "Added Sucessfully");
		mv.addObject("classExist", classExist);
		mv.setViewName("addTopic");
		return mv;
	}
	
	
	@RequestMapping(value="/addTopic",method=RequestMethod.GET)
	public ModelAndView addTopicGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		mv.setViewName("addTopic");
		}
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	/*------------------------------------------ADD SUBJECT (ADMIN MODULE)-----------------------------------------------------------------*/
	
	
	@RequestMapping(value="/addSubject", method=RequestMethod.GET)
	public ModelAndView addSubjectGet(HttpServletRequest req,ModelAndView mv) {
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		ArrayList<Subject> subject=(ArrayList<Subject>) subjectService.findAll();
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();
		
		ArrayList<String> newNonExistSubject =new ArrayList<String>();
		
		newNonExistSubject.add("Maths");
		newNonExistSubject.add("Science");
		newNonExistSubject.add("Physics");
		newNonExistSubject.add("Biology");
		newNonExistSubject.add("Chemistry");
		newNonExistSubject.add("English");
		
		for(Subject sub:subject) {
			if(newNonExistSubject.contains(sub.getSubName()))
				newNonExistSubject.remove(sub.getSubName());
		}
		
		mv.addObject("subjectExist",newNonExistSubject);
		mv.addObject("classExist",classExist);
		
		
		mv.setViewName("addSubject");
		}
		return mv;
	}
	
	@RequestMapping(value="/addSubject", method=RequestMethod.POST)
	public ModelAndView addSubjectPost(@RequestParam(name="SelectedSubject") String selectedSubject,@RequestParam(name="SelectedClasses") List<String> selectedClasses,ModelAndView mv) throws Exception {
			
		
		Subject addsubject=new Subject();
		addsubject.setSubName(selectedSubject);
		
		Set<SubjectClassMapping> subjectClassMapping=new HashSet<SubjectClassMapping>();
	
		for(String classes:selectedClasses) {
			Class om=classService.findByClassName(classes);
			subjectClassMapping.add(new SubjectClassMapping(om,addsubject));
		}
		
		subjectClassService.createSubjectClass(addsubject, subjectClassMapping);
		
		
		ArrayList<Subject> subject=(ArrayList<Subject>) subjectService.findAll();
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();
		
		ArrayList<String> newNonExistSubject =new ArrayList<String>();
		
		newNonExistSubject.add("Maths");
		newNonExistSubject.add("Science");
		newNonExistSubject.add("Physics");
		newNonExistSubject.add("Biology");
		newNonExistSubject.add("Chemistry");
		newNonExistSubject.add("English");
		
		for(Subject sub:subject) {
			if(newNonExistSubject.contains(sub.getSubName()))
				newNonExistSubject.remove(sub.getSubName());
		}
		
		mv.addObject("subjectExist",newNonExistSubject);
		mv.addObject("classExist",classExist);
		mv.addObject("status", "Added Sucessfully");
		
		
		mv.setViewName("addSubject");
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------ADD VIDEO (ADMIN MODULE)-----------------------------------------------------------------*/
	
	
	@RequestMapping(value="/addVideo",method = RequestMethod.GET)
	public ModelAndView addVideoGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		mv.setViewName("addVideo");
		}
		return mv;
		
	}
	
	@RequestMapping(value="/addVideo",method = RequestMethod.POST)
	public ModelAndView addVideoGetPost(HttpServletRequest req,ModelAndView mv) {
		
		
		String className=req.getParameter("classSelected");
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("description");
		String url=req.getParameter("url");
		String source=req.getParameter("source");
		String videourl=null;
	
		
		if(url.length()>0) {
			
			if(!url.startsWith("<iframe")) {
				
			}
			try {
				int indexOfSrc=url.indexOf("src");
				int lastIndexOfSrc =url.indexOf(' ', indexOfSrc);
				String temp=url.substring(indexOfSrc, lastIndexOfSrc);
				int ind=temp.indexOf('=');
				String urlUpload1=temp.substring(ind+2, temp.length()-1);
				videourl=urlUpload1;
			
			}catch(Exception e) {
				
			}
			
		}else {
			
		}
		
		String emailToIdentifyUser;
		
		
		HttpSession session=req.getSession(false);
	
		 emailToIdentifyUser=(String) session.getAttribute("UserLogedAdmin");
		
		

		Class localClass=classService.findByClassName(className);
		Subject localSubject=subjectService.findBysubName(subjectName);
		SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
		Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
		
		
		
		User usr=userService.findByUsername(emailToIdentifyUser);
		
		Set<VideoExternal> videoMapping=new HashSet<VideoExternal>();
		videoMapping.add(new VideoExternal(videoService.countRow()+1, "Video", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, videourl, 0, ServiceUtility.getCurrentTime(), localTopic, usr));
		
	
		
		userService.addUserToVideo(usr, videoMapping);
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		
	
		mv.addObject("videoStatus", "Added Sucessfully");
		
			
		
			
		mv.setViewName("addVideo");
		
		
		
		return mv;
		
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------ADD QUIZ (ADMIN MODULE)-----------------------------------------------------------------*/
	
	@RequestMapping(value="/addQuiz",method = RequestMethod.GET)
	public ModelAndView addQuizGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		mv.setViewName("addQuiz");
		}
		return mv;
		
		
	}
	
	@RequestMapping(value="/addQuiz",method = RequestMethod.POST)
	public ModelAndView addQuizPost(HttpServletRequest req,@RequestParam(name="Question")MultipartFile[] question,@RequestParam(name="Answer")MultipartFile[] answer,ModelAndView mv) {
		
		String questionPath=null;
		String answerPath=null;
		String className=req.getParameter("classSelected");
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String remarks=req.getParameter("remarks");
		
		
		String emailToIdentifyUser;
		
		HttpSession session=req.getSession(false);
		
		 emailToIdentifyUser=(String) session.getAttribute("UserLogedAdmin");
		
		
		
		
		String createFolder=uploadDirectory+className+"_"+subjectName+"/"+topicName+"/Quiz/"+remarks+"/";
		boolean b=ServiceUtility.createFolder(createFolder);
		
		String CreateFolderQuestion=createFolder+"Question/";
		String CreateFolderAnswer=createFolder+"Answer/";
		
		boolean ques=ServiceUtility.createFolder(CreateFolderQuestion);
		boolean ans=ServiceUtility.createFolder(CreateFolderAnswer);
		
		if(ques && ans) {
		
		questionPath=ServiceUtility.uploadFile(question, CreateFolderQuestion);
		answerPath=ServiceUtility.uploadFile(answer, CreateFolderAnswer);
		
	 	
		int indexToStart=questionPath.indexOf('M');
		String pathQuestion=questionPath.substring(indexToStart, questionPath.length());
		
		int indexToStart1=answerPath.indexOf('M');
		String pathAnswer=answerPath.substring(indexToStart1, answerPath.length());
		

		
		User usr=userService.findByUsername(emailToIdentifyUser);
		
		Class localClass=classService.findByClassName(className);
		Subject localSubject=subjectService.findBysubName(subjectName);
		SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
		Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
		

		
		Set<QuizQuestion> quizMapping=new HashSet<QuizQuestion>();
		quizMapping.add(new QuizQuestion(quizService.countRow()+1,"Quiz",ServiceUtility.getCurrentTime(),ServiceUtility.getCurrentTime(),pathQuestion,pathAnswer,0,remarks,ServiceUtility.getCurrentTime(),localTopic,usr));
		
		
		
		userService.addUserToQuizQuestion(usr, quizMapping);
		
		mv.addObject("status", "Added Sucessfully");
		
		}else {
			mv.addObject("status", "Error: Try again Later");
		}
		
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		
		
		mv.addObject("quizStatus", "Added Sucessfully");
		
			
		
			
		mv.setViewName("addQuiz");
		
			
		
		
		return mv;
		
		
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	

	
// Above code was to add all entries into database

	/*------------------------------------------SHOW USER_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	
	@RequestMapping(value = "/userList",method = RequestMethod.GET)
	public ModelAndView userListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		List<User> usr= userService.findAll();
	
		mv.addObject("User", usr);
		mv.setViewName("userList");
		}
		return mv;
	}
	
	@RequestMapping(value = "/deleteUser",method = RequestMethod.GET)
	public ModelAndView userListEnableGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		List<User> usr= userService.findAll();
	
		mv.addObject("User", usr);
		mv.setViewName("userList");
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
	public ModelAndView userListPost(@RequestParam(name="radiocall") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		User usrTemp=userService.findById(id);
		if(usrTemp.getRegistered()==1) {
		 status=userService.disableEnableUser(0, id);
		}else {
		  status=userService.disableEnableUser(1, id);
		}
		if(status) {
			mv.addObject("status", "User Disabled Sucessfully");
		}else {
			mv.addObject("status", "Please try Again");
		}
		

		
		List<User> usr= userService.findAll();
	
		mv.addObject("User", usr);
		mv.setViewName("userList");
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
/*------------------------------------------SHOW SUBJECT_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	
	@RequestMapping(value="/subjectList",method = RequestMethod.GET)
	public ModelAndView subjectListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		List<Subject> subjectList=subjectService.findAll();
		mv.addObject("Subject", subjectList);
		
		mv.setViewName("subjectList");
		}
		return mv;
	}
	
	@RequestMapping(value="/deleteSubject",method = RequestMethod.POST)
	public ModelAndView subjectListPost(@RequestParam(name="radiocall") String subjectId,ModelAndView mv) {
		
		int id=Integer.parseInt(subjectId);
		System.out.println(id);
		
		subjectService.deleteById(id);
		
		mv.addObject("status", "Subject Deleted Sucessfully");
		
		List<Subject> subjectList=subjectService.findAll();
		mv.addObject("Subject", subjectList);
		
		mv.setViewName("subjectList");
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	/*------------------------------------------SHOW TOPIC_LIST (ADMIN MODULE)-----------------------------------------------------------------*/	
	
	@RequestMapping(value="/topicList",method = RequestMethod.GET)
	public ModelAndView topicListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		List<Topic> topicList=topicService.findAll();
		List<Subject> subjectList=subjectService.findAll();
		mv.addObject("Topic", topicList);
		mv.addObject("Subject", subjectList);
		
		mv.setViewName("topicList");
		}
		return mv;
	}
	
	
	@RequestMapping(value="/deleteTopic",method = RequestMethod.POST)
	public ModelAndView topicListPost(@RequestParam(name="radiocall") String topicId,ModelAndView mv) {
		
		int id=Integer.parseInt(topicId);
		System.out.println(id);
		
		topicService.deleteTopicById(id);
		
		
		List<Topic> topicList=topicService.findAll();
		List<Subject> subjectList=subjectService.findAll();
		mv.addObject("Topic", topicList);
		mv.addObject("Subject", subjectList);
		
		mv.setViewName("topicList");
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	/*------------------------------------------SHOW VIDEO_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	
	
	@RequestMapping(value="/videoList",method = RequestMethod.GET)
	public ModelAndView videoListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		List<VideoExternal> videoList=videoService.findAll();
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic",topicList);
		
		mv.addObject("Video",videoList);
		
		mv.setViewName("videoList");
		}
		return mv;
	}
	
	@RequestMapping(value="/deleteVideo",method = RequestMethod.POST)
	public ModelAndView videoListPost(@RequestParam(name="radiocall") String videoId,ModelAndView mv) {
		
		int id=Integer.parseInt(videoId);
		System.out.println(id);
		
	//	videoService.deleteVideoById(id);
		
		List<VideoExternal> videoList=videoService.findAll();
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic",topicList);
		
		mv.addObject("Video",videoList);
		
		mv.setViewName("videoList");
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------SHOW ARTICLE_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	
	@RequestMapping(value="/articleList",method = RequestMethod.GET)
	public ModelAndView articleListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		List<ArticleExternal> articleList=articleService.findAll();
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic",topicList);
		mv.addObject("Article",articleList);
		
		mv.setViewName("articleList");
		}
		return mv;
	}
	
	@RequestMapping(value="/deleteArticle",method = RequestMethod.POST)
	public ModelAndView articleListPost(@RequestParam(name="radiocall") String articleId,ModelAndView mv) {
		
		int id=Integer.parseInt(articleId);
		System.out.println(id);
		
	//	articleService.deleteArticleById(id);
		List<ArticleExternal> articleList=articleService.findAll();
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic",topicList);
		mv.addObject("Article",articleList);
		
		mv.setViewName("articleList");
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	

	
	/*------------------------------------------SHOW DOCUMENT_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	
	@RequestMapping(value="/documentList",method = RequestMethod.GET)
	public ModelAndView documentListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		List<DocumentExternal> documentList=documentService.findAll();
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic",topicList);
		mv.addObject("Document",documentList);
		
		mv.setViewName("documentList");
		}
		return mv;
	}
	

	@RequestMapping(value="/deleteDocument",method = RequestMethod.POST)
	public ModelAndView documentListPost(@RequestParam(name="radiocall") String documentId,ModelAndView mv) {
		
		int id=Integer.parseInt(documentId);
		System.out.println(id);
		
	//	documentService.deleteDocumentById(id);
		
		List<DocumentExternal> documentList=documentService.findAll();
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic",topicList);
		mv.addObject("Document",documentList);
		
		mv.setViewName("documentList");
		return mv;
	}
	
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------SHOW PHET_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	
	@RequestMapping(value="/phetsList",method = RequestMethod.GET)
	public ModelAndView phetsListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {

		List<Phets> phetList=phetService.findAll();
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic",topicList);
		mv.addObject("Phet",phetList);
		
		mv.setViewName("phetsList");
		}
		return mv;
	}
	
	@RequestMapping(value="/deletePhet",method = RequestMethod.POST)
	public ModelAndView phetsListPost(@RequestParam(name="radiocall") String phetId,ModelAndView mv) {

		int id=Integer.parseInt(phetId);
		System.out.println(id);
		
	//	phetService.deletePhetById(id);
		
		List<Phets> phetList=phetService.findAll();
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic",topicList);
		mv.addObject("Phet",phetList);
		
		mv.setViewName("phetsList");
		return mv;
	}
	
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------SHOW LESSONPLAN_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	
	@RequestMapping(value="/lessonPlanList",method = RequestMethod.GET)
	public ModelAndView lessonPlanListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		List<LessonPlan> lessonList=lessonService.findAll();
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic",topicList);
		mv.addObject("Lesson",lessonList);
		mv.setViewName("lessonPlanList");
		}
		return mv;
	}
	
	@RequestMapping(value="/deleteLesson",method = RequestMethod.POST)
	public ModelAndView lessonPlanListPost(@RequestParam(name="radiocall") String lessonId,ModelAndView mv) {
		
		int id=Integer.parseInt(lessonId);
		System.out.println(id);
		
	//	lessonService.deleteLessonById(id);
		
		
		List<LessonPlan> lessonList=lessonService.findAll();
		
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic",topicList);
		mv.addObject("Lesson",lessonList);
		mv.setViewName("lessonPlanList");
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/	
	
	/*------------------------------------------SHOW QUIZ_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	
	@RequestMapping(value="/quizList",method = RequestMethod.GET)
	public ModelAndView quizListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		
		
		List<QuizQuestion> quizList=quizService.findAll();
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic",topicList);
		mv.addObject("Quiz",quizList );
		mv.setViewName("quizList");
		}
		return mv;
	}
	
	@RequestMapping(value="/deleteQuiz",method = RequestMethod.POST)
	public ModelAndView quizListPost(@RequestParam(name="radiocall") String quizId,ModelAndView mv) {
		
		int id=Integer.parseInt(quizId);
		System.out.println(id);
		
	//	quizService.deleteQuizById(id);
		
		List<QuizQuestion> quizList=quizService.findAll();
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic",topicList);
		
		mv.addObject("Quiz",quizList );
		mv.setViewName("quizList");
		return mv;
	}


/*----------------------------------------------------------END----------------------------------------------------------------------------*/


/*-----------------------------------------------START OF APPROVING/REJECT TECAHER---------------------------------------------------------------------*/

	@RequestMapping(value = "/approveRejectTeacher")
	public ModelAndView teacherAprovementGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession();
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			List<User> local=new ArrayList<User>();
		
			List<User> usr= userService.findAll();
			
			for(User a:usr) {
				List<UserRole> userRole=a.getUserRoles();
				for(UserRole x:userRole) {
					if(x.getRole().getRoleName().contentEquals("Teacher") && a.getRegistered()==0) {
						local.add(a);
					}
				}
			}
				
				if(!local.isEmpty()) {
					mv.addObject("UserTeacher", local);
					
				}else {
					mv.addObject("TeacherStatus","No Entries Present To Approve");
				}
			
			
			mv.setViewName("approveRejectTeacher");
			
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/approveTeacher",method =RequestMethod.POST)
	public ModelAndView approveTeacherPost(HttpServletRequest req,@RequestParam(name="radiocall") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		  status=userService.disableEnableUser(1, id);
	
		if(status) {
			mv.addObject("status", "User Enabled Sucessfully");
		}else {
			mv.addObject("status", "Please try Again");
		}
		
		
		HttpSession session=req.getSession();
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			List<User> local=new ArrayList<User>();
		
			List<User> usr= userService.findAll();
			
			for(User a:usr) {
				List<UserRole> userRole=a.getUserRoles();
				for(UserRole x:userRole) {
					if(x.getRole().getRoleName().contentEquals("Teacher") && a.getRegistered()==0) {
						local.add(a);
					}
				}
			}
				
				if(!local.isEmpty()) {
					mv.addObject("UserTeacher", local);
					
				}else {
					mv.addObject("TeacherStatus","No Entries Present To Approve");
				}
			
			
			mv.setViewName("approveRejectTeacher");
			
		}
		
		
		
		return mv;
	}
	




/*--------------------------------------------------------END----------------------------------------------------------------------------------*/

	/*-----------------------------------------------START OF APPROVING/REJECT VIDEO---------------------------------------------------------------------*/


	@RequestMapping(value = "/approveRejectVideo")
	public ModelAndView videoApprovementGet(HttpServletRequest req, ModelAndView mv) {
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			List<VideoExternal> localVideo=new ArrayList<VideoExternal>();
			
			List<VideoExternal> videoList=videoService.findAll();
			for(VideoExternal a:videoList) {
				if(a.isStatus()==0) {
					localVideo.add(a);
				}
			}
			
			if(localVideo.isEmpty()) {
				
				mv.addObject("VideoStatus","No Entries Present To Approve");
			}else {
				mv.addObject("VideoAdded",localVideo);
			}
			
			
			List<Topic> topicList=topicService.findAll();
			
			mv.addObject("Topic",topicList);
			
		
			mv.setViewName("approveRejectVideo");
			
		}
		return mv;
	}
	
	@RequestMapping(value = "/EnableVideo",method = RequestMethod.POST)
	public ModelAndView enbaleVideoPost(HttpServletRequest req,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		int id=Integer.parseInt(userId);
		boolean status;
		
		  status=videoService.EnableVideoContent(1, id);
			
			if(status) {
				mv.addObject("status", "Video Enabled Sucessfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
		
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			List<VideoExternal> localVideo=new ArrayList<VideoExternal>();
			
			List<VideoExternal> videoList=videoService.findAll();
			for(VideoExternal a:videoList) {
				if(a.isStatus()==0) {
					localVideo.add(a);
				}
			}
			
			if(localVideo.isEmpty()) {
				
				mv.addObject("VideoStatus","No Entries Present To Approve");
			}else {
				mv.addObject("VideoAdded",localVideo);
			}
			
			
			List<Topic> topicList=topicService.findAll();
			
			mv.addObject("Topic",topicList);
			
		
			mv.setViewName("approveRejectVideo");
			
		}
		
		
		
		return mv;
	}


/*-----------------------------------------------START OF APPROVING/REJECT DOCUMENT---------------------------------------------------------------------*/
	
	
	@RequestMapping(value = "/approveRejectDocument")
	public ModelAndView documentApprovementGet(HttpServletRequest req, ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			List<DocumentExternal> localdocument=new ArrayList<DocumentExternal>();
			
			List<DocumentExternal> documentList=documentService.findAll();
			for(DocumentExternal a:documentList) {
				if(a.isStatus()==0) {
					localdocument.add(a);
				}
			}
			
			if(localdocument.isEmpty()) {
				
				mv.addObject("documentStatus","No Entries Present To Approve");
			}else {
				mv.addObject("Documentadded",localdocument);
			}
			
			
			List<Topic> topicList=topicService.findAll();
			
			mv.addObject("Topic",topicList);
			
		
			mv.setViewName("approveRejectDocument");
			
		}
		return mv;
	}
	
	@RequestMapping(value = "/EnableDocument",method = RequestMethod.POST)
	public ModelAndView enbaleDocumentPost(HttpServletRequest req,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		  status=documentService.EnableDocumentContent(1, id);
			
			if(status) {
				mv.addObject("status", "Document Enabled Sucessfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
			HttpSession session=req.getSession(false);
			
			if(session==null) {
				mv.setViewName("redirect:/adminPortal");
			}else {
				List<DocumentExternal> localdocument=new ArrayList<DocumentExternal>();
				
				List<DocumentExternal> documentList=documentService.findAll();
				for(DocumentExternal a:documentList) {
					if(a.isStatus()==0) {
						localdocument.add(a);
					}
				}
				
				if(localdocument.isEmpty()) {
					
					mv.addObject("documentStatus","No Entries Present To Approve");
				}else {
					mv.addObject("Documentadded",localdocument);
				}
				
				
				List<Topic> topicList=topicService.findAll();
				
				mv.addObject("Topic",topicList);
				
			
				mv.setViewName("approveRejectDocument");
				
			}
			return mv;
		}
	
	
	
	/*-----------------------------------------------START OF APPROVING/REJECT ARTICLE---------------------------------------------------------------------*/
	
	
	
	@RequestMapping(value = "/approveRejectArticle")
	public ModelAndView articleApprovementGet(HttpServletRequest req, ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			List<ArticleExternal> localarticle=new ArrayList<ArticleExternal>();
			
			List<ArticleExternal> articleList=articleService.findAll();
			for(ArticleExternal a:articleList) {
				if(a.isStatus()==0) {
					localarticle.add(a);
				}
			}
			
			if(localarticle.isEmpty()) {
				
				mv.addObject("articleStatus","No Entries Present To Approve");
			}else {
				mv.addObject("Article",localarticle);
			}
			
			
			List<Topic> topicList=topicService.findAll();
			
			mv.addObject("Topic",topicList);
			
		
			mv.setViewName("approveRejectArticle");
			
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/EnableArticle",method = RequestMethod.POST)
	public ModelAndView enbaleArticlePost(HttpServletRequest req,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		  status=articleService.EnableArticleContent(1, id);
			
			if(status) {
				mv.addObject("status", "Article Enabled Sucessfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
			HttpSession session=req.getSession(false);
			
			if(session==null) {
				mv.setViewName("redirect:/adminPortal");
			}else {
				List<ArticleExternal> localarticle=new ArrayList<ArticleExternal>();
				
				List<ArticleExternal> articleList=articleService.findAll();
				for(ArticleExternal a:articleList) {
					if(a.isStatus()==0) {
						localarticle.add(a);
					}
				}
				
				if(localarticle.isEmpty()) {
					
					mv.addObject("articleStatus","No Entries Present To Approve");
				}else {
					mv.addObject("Article",localarticle);
				}
				
				
				List<Topic> topicList=topicService.findAll();
				
				mv.addObject("Topic",topicList);
				
			
				mv.setViewName("approveRejectArticle");
				
			}
			return mv;
		}
	
	
	/*-----------------------------------------------START OF APPROVING/REJECT PHETS---------------------------------------------------------------------*/
	
	
	@RequestMapping(value = "/approveRejectPhets")
	public ModelAndView phetApprovementGet(HttpServletRequest req, ModelAndView mv) {
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			List<Phets> localphet=new ArrayList<Phets>();
			
			List<Phets> phetList=phetService.findAll();
			for(Phets a:phetList) {
				if(a.isStatus()==0) {
					localphet.add(a);
				}
			}
			
			if(localphet.isEmpty()) {
				
				mv.addObject("phetStatus","No Entries Present To Approve");
			}else {
				mv.addObject("PhetsAdded",localphet);
			}
			
			
			List<Topic> topicList=topicService.findAll();
			
			mv.addObject("Topic",topicList);
			
		
			mv.setViewName("approveRejectPhet");
			
		}
		return mv;
	}
	
	
	
	@RequestMapping(value = "/EnablePhet",method = RequestMethod.POST)
	public ModelAndView enbalePhetPost(HttpServletRequest req,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		  status=phetService.EnablePhetContent(1, id);
			
			if(status) {
				mv.addObject("status", "Phets Enabled Sucessfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
			HttpSession session=req.getSession(false);
			
			if(session==null) {
				mv.setViewName("redirect:/adminPortal");
			}else {
				List<Phets> localphet=new ArrayList<Phets>();
				
				List<Phets> phetList=phetService.findAll();
				for(Phets a:phetList) {
					if(a.isStatus()==0) {
						localphet.add(a);
					}
				}
				
				if(localphet.isEmpty()) {
					
					mv.addObject("phetStatus","No Entries Present To Approve");
				}else {
					mv.addObject("PhetsAdded",localphet);
				}
				
				
				List<Topic> topicList=topicService.findAll();
				
				mv.addObject("Topic",topicList);
				
			
				mv.setViewName("approveRejectPhet");
				
			}
			return mv;
		}
	
	
	/*-----------------------------------------------START OF APPROVING/REJECT QUIZ---------------------------------------------------------------------*/
	
	
	@RequestMapping(value = "/approveRejectQuiz")
	public ModelAndView quizApprovementGet(HttpServletRequest req, ModelAndView mv) {
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			List<QuizQuestion> localQuiz=new ArrayList<QuizQuestion>();
			
			List<QuizQuestion> quizList=quizService.findAll();
			for(QuizQuestion a:quizList) {
				if(a.isStatus()==0) {
					localQuiz.add(a);
				}
			}
			
			if(localQuiz.isEmpty()) {
				
				mv.addObject("QuizStatus","No Entries Present To Approve");
			}else {
				mv.addObject("QuizAdded",localQuiz);
			}
			
			
			List<Topic> topicList=topicService.findAll();
			
			mv.addObject("Topic",topicList);
			
		
			mv.setViewName("approveRejectQuiz");
			
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/EnableQuiz",method = RequestMethod.POST)
	public ModelAndView enbaleQuizPost(HttpServletRequest req,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		  status=quizService.EnableQuizContent(1, id);
			
			if(status) {
				mv.addObject("status", "Quiz Enabled Sucessfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
			HttpSession session=req.getSession(false);
			
			if(session==null) {
				mv.setViewName("redirect:/adminPortal");
			}else {
				List<QuizQuestion> localQuiz=new ArrayList<QuizQuestion>();
				
				List<QuizQuestion> quizList=quizService.findAll();
				for(QuizQuestion a:quizList) {
					if(a.isStatus()==0) {
						localQuiz.add(a);
					}
				}
				
				if(localQuiz.isEmpty()) {
					
					mv.addObject("QuizStatus","No Entries Present To Approve");
				}else {
					mv.addObject("QuizAdded",localQuiz);
				}
				
				
				List<Topic> topicList=topicService.findAll();
				
				mv.addObject("Topic",topicList);
				
			
				mv.setViewName("approveRejectQuiz");
				
			}
			return mv;
		}
	
	
	
	/*-----------------------------------------------START OF APPROVING/REJECT LESSON---------------------------------------------------------------------*/
	
	
	@RequestMapping(value = "/approveRejectLesson")
	public ModelAndView lessonApprovementGet(HttpServletRequest req, ModelAndView mv) {
		HttpSession session=req.getSession(false);
		
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			List<LessonPlan> localLesson=new ArrayList<LessonPlan>();
			
			List<LessonPlan> lessonList=lessonService.findAll();
			for(LessonPlan a:lessonList) {
				if(a.isStatus()==0) {
					localLesson.add(a);
				}
			}
			
			if(localLesson.isEmpty()) {
				
				mv.addObject("LessonStatus","No Entries Present To Approve");
			}else {
				mv.addObject("LessonAdded",localLesson);
			}
			
			
			List<Topic> topicList=topicService.findAll();
			
			mv.addObject("Topic",topicList);
			
		
			mv.setViewName("approveRejectLesson");
			
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/EnableLesson",method = RequestMethod.POST)
	public ModelAndView enbaleLessonPost(HttpServletRequest req,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		  status=lessonService.EnableLessonPlanContent(1, id);
			
			if(status) {
				mv.addObject("status", "Lesson Plan Enabled Sucessfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
			HttpSession session=req.getSession(false);
			
			if(session==null) {
				mv.setViewName("redirect:/adminPortal");
			}else {
				List<LessonPlan> localLesson=new ArrayList<LessonPlan>();
				
				List<LessonPlan> lessonList=lessonService.findAll();
				for(LessonPlan a:lessonList) {
					if(a.isStatus()==0) {
						localLesson.add(a);
					}
				}
				
				if(localLesson.isEmpty()) {
					
					mv.addObject("LessonStatus","No Entries Present To Approve");
				}else {
					mv.addObject("LessonAdded",localLesson);
				}
				
				
				List<Topic> topicList=topicService.findAll();
				
				mv.addObject("Topic",topicList);
				
			
				mv.setViewName("approveRejectLesson");
				
			}
			return mv;
		}
	
	
	
/************************************************************END********************************************************************************/
	
	
/************************************************ START OF CONTROLLER FOR USER SIDE ***********************************************************/
	
	@RequestMapping(value = "/courses", method = RequestMethod.GET)
	public ModelAndView viewCoursesAvailable(@RequestParam(name="subjectSelected") String subject,@RequestParam(name="classSelected") String classSelected,ModelAndView mv) {
		
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		
		Class localClass=classService.findByClassName(classSelected);
		Subject localSubject=subjectService.findBySubjectName(subject);
		SubjectClassMapping localSubjectClassMapping=subjectClassService.findBysubAndstandard(localClass, localSubject);
		
		List<Topic> localTopic=topicService.findBysubjectclassMapping(localSubjectClassMapping);
		
		if(!localTopic.isEmpty()) {
			mv.addObject("TopicListOnSubjectClass", localTopic);
		}else {
			mv.addObject("NoTopicAvailable","No Content Available to Show");
		}
		
		
		mv.addObject("subjectSelected", localSubject.getSubName());
		mv.addObject("classSelected", localClass.getClassName());
		mv.setViewName("Courses");
		
		return mv;
	}
	
	
	
	@RequestMapping(path = "/content/{topicId}", method = RequestMethod.GET)
	public ModelAndView viewContentonTopic(@PathVariable("topicId") int topicId,ModelAndView mv) {
		
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		
		Topic localTopic=topicService.findById(topicId);
		List<QuizQuestion> localQuiz=quizService.findAllByTopic(localTopic);
		List<ArticleExternal> localArticle=articleService.findAllByTopic(localTopic);
		List<DocumentExternal> localDocument=documentService.findAllByTopic(localTopic);
		List<LessonPlan> localLesson=lessonService.findAllByTopic(localTopic);
		List<VideoExternal> localvideo=videoService.findAllByTopic(localTopic);
		List<Phets> localPhets=phetService.findAllByTopic(localTopic);
		
			
		if(localQuiz.isEmpty()) {
			mv.addObject("QuizError", "Nothing To Show");
		}
		
		if(localArticle.isEmpty()) {;
			mv.addObject("ArticleError", "Nothing To Show");
		}
		
		if(localDocument.isEmpty()) {
			mv.addObject("DocumentError", "Nothing To Show");
		}
		
		if(localLesson.isEmpty()) {
			mv.addObject("LessonError", "Nothing To Show");
		}
		
		if(localvideo.isEmpty()) {
			mv.addObject("VideoError", "Nothing To Show");
		}
		
		if(localPhets.isEmpty()) {
			mv.addObject("PhetError", "Nothing To Show");
		}
		
		mv.addObject("QuizOnTopic", localQuiz);
		mv.addObject("VideoOnTopic", localvideo);
		mv.addObject("ArticleOnTopic", localArticle);
		mv.addObject("DocumentOnTopic", localDocument);
		mv.addObject("LessonOnTopic", localLesson);
		mv.addObject("PhetOnTopic", localPhets);
		
		mv.addObject("subjectSelected", localTopic.getSubjectClassMapping().getSub().getSubName());
		mv.addObject("classSelected", localTopic.getSubjectClassMapping().getStandard().getClassName());
		mv.addObject("TopicSelected", localTopic);
		mv.setViewName("content");
		return mv;
	}
	
	
	@RequestMapping(value = "/addTestimonial",method = RequestMethod.GET)
	public ModelAndView addTestimonialGet(HttpServletRequest req,ModelAndView mv) {
		HttpSession session=req.getSession(false);
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			
			
			String userLogged=(String) session.getAttribute("UserLogedAdmin");
			try{
				
				if(userLogged.isEmpty()) {
				 mv.setViewName("redirect:/adminPortal");
				
				}else {
			
						mv.setViewName("addTestimonial");
				}
			}catch (Exception e) {
				 mv.setViewName("redirect:/adminPortal");
				
			}
		}
		return mv;
	
		
	}
	
	@RequestMapping(value = "/addTestimonial",method = RequestMethod.POST)
	public ModelAndView addTestimonialPost(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		String name=req.getParameter("Name");
		String organization=req.getParameter("org");
		String Desc=req.getParameter("description");
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			
			
			String userLogged=(String) session.getAttribute("UserLogedAdmin");
			try{
				
				if(userLogged.isEmpty()) {
				 mv.setViewName("redirect:/adminPortal");
				
				}else {
					
					try {
						Testimonial addtestData=new Testimonial();
						addtestData.setTestimonialId(testiService.getCount());
						addtestData.setDateAdded(ServiceUtility.getCurrentTime());
						addtestData.setName(name);
						addtestData.setDescription(Desc);
						addtestData.setOrganization(organization);
						
						testiService.save(addtestData);
						mv.addObject("returnStatus", "Data Added Sucessfully");
						mv.setViewName("addTestimonial");
					} catch (Exception e) {
						
						mv.addObject("returnStatus", "Please Try Again");
						mv.setViewName("addTestimonial");
						e.printStackTrace();
					}
				}
			}catch (Exception e) {
				 mv.setViewName("redirect:/adminPortal");
				
			}
		}
		return mv;

	}
	
	
	@RequestMapping(value = "/addEvent",method = RequestMethod.GET)
	public ModelAndView addEventGet(HttpServletRequest req,ModelAndView mv) {
		
		
		HttpSession session=req.getSession(false);
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			
			
			String userLogged=(String) session.getAttribute("UserLogedAdmin");
			try{
				
				if(userLogged.isEmpty()) {
				 mv.setViewName("redirect:/adminPortal");
				
				}else {
			
						mv.setViewName("addEvent");
				}
			}catch (Exception e) {
				 mv.setViewName("redirect:/adminPortal");
				
			}
		}
		return mv;

	}
	
	@RequestMapping(value = "/addEvent",method = RequestMethod.POST)
	public ModelAndView addEventPost(HttpServletRequest req,ModelAndView mv) {
		
		String headline=req.getParameter("headline");
		String Desc=req.getParameter("description");
		
	
		
		HttpSession session=req.getSession(false);
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			
			
			String userLogged=(String) session.getAttribute("UserLogedAdmin");
			try{
				
				if(userLogged.isEmpty()) {
				 mv.setViewName("redirect:/adminPortal");
				
				}else {
					
					try {
						String date=req.getParameter("date");
						SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd");
						java.util.Date dateUtil=sd1.parse(date);
						Date dateOfEvent=new Date(dateUtil.getTime());
						
						Events addEvent=new Events();
						addEvent.setEventId(eventService.getCount());
						addEvent.setDateAdded(ServiceUtility.getCurrentTime());
						addEvent.setDescription(Desc);
						addEvent.setHeadline(headline);
						addEvent.setDateToHappen(dateOfEvent);
						
						eventService.save(addEvent);
						
						mv.addObject("returnStatus", "Data Added Sucessfully");
						mv.setViewName("addEvent");
					} catch (Exception e) {
						
						mv.addObject("returnStatus", "Please Try Again");
						mv.setViewName("addEvent");
						e.printStackTrace();
					}
				}
			}catch (Exception e) {
				 mv.setViewName("redirect:/adminPortal");
				
			}
		}
		return mv;

	}
	
	
	@RequestMapping(value = "/testimonialList",method = RequestMethod.GET)
	public ModelAndView testimonialList(HttpServletRequest req,ModelAndView mv) {
		HttpSession session=req.getSession(false);
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			
			
			String userLogged=(String) session.getAttribute("UserLogedAdmin");
			try{
				
				if(userLogged.isEmpty()) {
				 mv.setViewName("redirect:/adminPortal");
				
				}else {
					List<Testimonial> local=testiService.findAll();
					mv.addObject("Testimonial", local);
			
					mv.setViewName("testimonialList");
				}
			}catch (Exception e) {
				 mv.setViewName("redirect:/adminPortal");
				
			}
		}
		return mv;
	
		
	}
	
	@RequestMapping(value = "/eventList",method = RequestMethod.GET)
	public ModelAndView eventList(HttpServletRequest req,ModelAndView mv) {
		HttpSession session=req.getSession(false);
		if(session==null) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			
			
			String userLogged=(String) session.getAttribute("UserLogedAdmin");
			try{
				
				if(userLogged.isEmpty()) {
				 mv.setViewName("redirect:/adminPortal");
				
				}else {
					List<Events> local=eventService.findAll();
					mv.addObject("Events", local);
			
					mv.setViewName("eventList");
				}
			}catch (Exception e) {
				 mv.setViewName("redirect:/adminPortal");
				
			}
		}
		return mv;
	
		
	}
	
	



/*--------------------------------------------------------END----------------------------------------------------------------------------------*/
}


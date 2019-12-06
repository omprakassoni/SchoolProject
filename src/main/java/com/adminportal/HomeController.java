/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 *  Description   : This HomeController Class mainly deal with url which will caught from USer side like
 *  				1. initial Page or index page
 *  				2. Login Page
 *  				3. Singup Page
 *  				4. User Authentication (Not for Admin)
 *  				5. course information
 *  				6. content information
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.hibernate.annotations.Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpRequest;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.adminportal.config.MailConstructor;
import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Class;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.Events;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Subject;
import com.adminportal.content.SubjectClassMapping;
import com.adminportal.content.Testimonial;
import com.adminportal.content.Topic;
import com.adminportal.content.Tutorial;
import com.adminportal.content.VideoExternal;
import com.adminportal.domain.RoleDetail;
import com.adminportal.domain.User;
import com.adminportal.domain.UserRole;
import com.adminportal.service.ArticleExternalService;
import com.adminportal.service.ClassService;
import com.adminportal.service.ConceptMapService;
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
import com.adminportal.service.TutorialService;
import com.adminportal.service.UserService;
import com.adminportal.service.VideoExternalService;
import com.spoken.Utility.ServiceUtility;
import com.spoken.Utility.TutorialList;

@Controller
@SessionAttributes({"UserLogedUsername","UserLogedName","UserLogedRole"})  // Session Variable
public class HomeController {
	
	public static final String uploadDirectory="src/main/resources/static"+"/Media/content/";   // Path to save resources
	public static final String uploadTeacherDirectory="src/main/resources/static/Media/Teacher/";	// path to save teacher Document
	
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
	
	@Autowired
	private ConceptMapService conceptMapService;
	
	@Autowired
	private TutorialService tutorialService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConstructor mailConstructor;
	

	

	////////////////////////
	
	

	
/* ------------------------------------------HOME PAGE-------------------------------------------------*/

	

	
	@RequestMapping("/")
	public ModelAndView home(ModelAndView mv) {
		
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		
		List<Testimonial> testidata=testiService.getAlltestimonial();
		
		List<Events> eventData=eventService.getAllEventdata();
		

		
		if(testidata.size()>0) {
			
			Testimonial temp1=testidata.get(0);
			mv.addObject("TestimonialFirst", temp1);
			
			List<Testimonial> temp2=new ArrayList<Testimonial>();
			for(int i=1;i<testidata.size();i++) {
				temp2.add(testidata.get(i));
			}
			
			mv.addObject("TestimonialRest", temp2);
			
			
		}
		
		if(eventData.size()>0) {
			
			List<Events> eventTemp=new ArrayList<Events>();
			eventTemp.add(eventData.get(0));
			for(int i=1;i<eventData.size();i++) {
				eventTemp.add(eventData.get(i));
				break;
			}
	
			mv.addObject("Events", eventTemp);
		}
		
		

		mv.addObject("classfromDatabase", standard);
		mv.setViewName("Index");
		return mv;
	}
	
	
	
	/********************************************** USER SIDE AUTHENTICATION********************************************************/
	
	@RequestMapping(value = "/userLogin",method = RequestMethod.POST)
	public ModelAndView userLoginpost(HttpServletRequest req,ModelAndView mv) {
		
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		
		String userRole=null;
		String username=req.getParameter("username");
		//String password=ServiceUtility.passwordEncoder().encode(req.getParameter("password"));
		boolean statusPassword=false;
		
		//User usr1=(User) userService.existsByUser(username, password);			// check for existence of User
		User usr=userService.findByUsername(username);
		statusPassword =ServiceUtility.passwordEncoder().matches(req.getParameter("password"), usr.getPassword());
		System.out.println(statusPassword);
		if(statusPassword) {
		List<UserRole> tempuserRole=usr.getUserRoles();
		for(UserRole temp:tempuserRole) {
			userRole=temp.getRole().getRoleName();
			
		}
		}
		if(statusPassword) {
				
				if(userRole.contentEquals("Teacher") ) {
					
					if(usr.getRegistered()==1) {
			
						HttpSession session=req.getSession();
						session.setAttribute("UserLogedUsername", usr.getEmail());		// setting Session variable 
						session.setAttribute("UserLogedName", usr.getFname());
						session.setAttribute("UserLogedRole", userRole);
						
						usr.setLastLogin(ServiceUtility.getCurrentTime());
						userService.save(usr);
		
						mv.setViewName("redirect:/");
					}else {
						mv.addObject("Error", "Not verified yet..Please Try later");
						mv.setViewName("Login");
						
					}
				}else {
					
					HttpSession session=req.getSession();
					session.setAttribute("UserLogedUsername", usr.getEmail());		// setting Session variable 
					session.setAttribute("UserLogedName", usr.getFname());
					session.setAttribute("UserLogedRole", userRole);
					
					usr.setLastLogin(ServiceUtility.getCurrentTime());
					userService.save(usr);
	
					mv.setViewName("redirect:/");
					
				}
			
		}else {
			
			mv.addObject("Error", "Login Credentials are Incorrect");
			mv.setViewName("Login");
			
			
		}
		


		return mv;
		
	}
	
	
/***********************************************END***********************************************************************/
	
/******************************* MY ACCOUNT PAGE OF USER TO PERFORM OPERATION ***********************************/
	
	@RequestMapping(value = "/myAccount",method=RequestMethod.GET)
	public ModelAndView myAccountGet(ModelAndView mv,HttpServletRequest req) {
		
		HttpSession session=req.getSession(false);						// CHECK FOR EXISTING ALIVE SESSION	
		if(!ServiceUtility.chechExistSessionUser(session)) {			// CHECK FOR USER ALIVE SESSION
			mv.setViewName("redirect:/Login");
		}else {
			
			
			
			String loggedUser=(String) session.getAttribute("UserLogedUsername");
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
			List<ConceptMap> localConcept=conceptMapService.findALlByUser(localUser);
			
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
			
			if(localPhets.isEmpty()) {
				mv.addObject("ConceptError", "Nothing To Show");
			}
			
			mv.addObject("QuizOnUser", localQuiz);
			mv.addObject("VideoOnUser", localvideo);
			mv.addObject("ArticleOnUser", localArticle);
			mv.addObject("DocumentOnUser", localDocument);
			mv.addObject("LessonOnUser", localLesson);
			mv.addObject("PhetOnUser", localPhets);
			mv.addObject("ConceptOnUser", localConcept);
			
			
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
	
//------------------------------------------- FORGET PASSWORD --------------------------------------------------------
	
	@RequestMapping("/forgetPassword")
	public ModelAndView forgetPasswordGet(ModelAndView mv){
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		mv.setViewName("forgetPassword");
		return mv;

	}
	
	/*------------------- FORGET PASSWORD TAKIN REQUEST FROM VIEW -----------------------------------------*/
	
	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public ModelAndView forgetPasswordPost(HttpServletRequest req,ModelAndView mv){
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		
		String userEmail=req.getParameter("username");
		
		User usr=userService.findByUsername(userEmail);
		if(usr==null) {
			mv.addObject("Error", "E-mail doesn't Exist");
			mv.setViewName("forgetPassword");
			return mv;
		}
		
		String token=UUID.randomUUID().toString();
		usr.setToken(token);
		
		userService.save(usr);
		
		String appUrl = "http://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath();
		System.out.println(appUrl);
		SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, req.getLocale(), token, usr);
		
		mailSender.send(newEmail);
		
		
	
	
		
		mv.addObject("Success", "Link to reset password has been sent to your E-mail ID");
		
		mv.setViewName("forgetPassword");
		return mv;

	}
	
	/*--------------------------- LINK TO WHICH USER WILL SET NEW PASSWORD ----------------------------------*/
	
	@RequestMapping(value = "/reset",method = RequestMethod.GET)
	public ModelAndView resetPasswordGet(ModelAndView mv,@RequestParam("token") String token){
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		
		User usr=userService.findByToken(token);
		if(usr==null) {
			mv.setViewName("redirect:/");
			return mv;
		}
		
		System.out.println(token);
		mv.addObject("resetToken",usr.getToken());
		mv.setViewName("resetPassword");
		return mv;

	}
	
	
	/*---------------------------- PERSISTING NEW PASSWORD OF USER ----------------------------------*/
	
	@RequestMapping(value = "/resetPassword",method = RequestMethod.POST)
	public ModelAndView resetPasswordPost(ModelAndView mv,HttpServletRequest req){
		
		String newPassword=req.getParameter("Password");
		String confNewPassword=req.getParameter("Confirm");
		String token=req.getParameter("token");
		
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		
		User usr=userService.findByToken(token);
		if(usr==null) {
			mv.addObject("Error","Invalid request");
			return mv;
		}
		
		if(!newPassword.contentEquals(confNewPassword)) {
			mv.addObject("Error","Both password doesn't match");
			return mv;
		}
		
		usr.setPassword(ServiceUtility.passwordEncoder().encode(newPassword));
		usr.setToken(null);
		userService.save(usr);
	
		mv.setViewName("resetPassword");
		return mv;

	}
	
//-----------------------------------------END-------------------------------------------------------
	
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
	
/*********************************** ADMIN PORTAL HYPERLINK ******************************************/	
	
//	@RequestMapping(value = "/adminPortal")
//	public String adminHomepage() {
//		return "adminLoginPage";
//	}
	
	
/************************************************************************************************************************/
	
// ---------------------------------- Event List on USer Side ------------------------------------------
	
	@RequestMapping(value = "/eventsList")
	public ModelAndView viewEvent(ModelAndView mv) {
		
		List<Events> localEvent=eventService.findAll();
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
	
		
		mv.addObject("classfromDatabase", standard);
		mv.addObject("EventList", localEvent);
		mv.setViewName("events");
		return mv;
	}
	


	
/************************************************ START OF CONTROLLER FOR USER SIDE ***********************************************************/
	
	// COURSES PAGE BASED ON SELECTION OF CLASS AND SUBJECT
	
	@RequestMapping(value = "/courses", method = RequestMethod.GET)
	public ModelAndView viewCoursesAvailable(@RequestParam(name="subjectSelected") String subject,@RequestParam(name="classSelected") String classSelected,ModelAndView mv) {
		
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		
		Class localClass=classService.findByClassName(classSelected);
		Subject localSubject=subjectService.findBySubjectName(subject);
		SubjectClassMapping localSubjectClassMapping=subjectClassService.findBysubAndstandard(localClass, localSubject);
		
		List<Topic> localTopictemp=topicService.findBysubjectclassMapping(localSubjectClassMapping);
		
		List<Topic> localTopic=new ArrayList<Topic>();
		for(Topic temp:localTopictemp) {
			if(temp.getStatus()==1) {
				localTopic.add(temp);
			}
		}
		
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
	
	
	/******              CONTENT PAGE BASED ON CLASS SUBJECT AND TOPIC-------------------------------*/
	
	@RequestMapping(path = "/content/{topicId}", method = RequestMethod.GET)
	public ModelAndView viewContentonTopic(HttpServletRequest req,@PathVariable("topicId") int topicId,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		
		Topic localTopic=topicService.findById(topicId);
		List<QuizQuestion> localQuiz=quizService.findAllByTopicAndStatus(localTopic);
		List<ArticleExternal> localArticle=articleService.findAllByTopicAndStatus(localTopic);
		List<DocumentExternal> localDocument=documentService.findAllByTopicAndStatus(localTopic);
		List<LessonPlan> localLesson=lessonService.findAllByTopicAndStatus(localTopic);
		List<VideoExternal> localvideo=videoService.findAllByTopicAndStatus(localTopic);
		List<Phets> localPhets=phetService.findAllByTopicAndStatus(localTopic);
		List<ConceptMap> localConcept=conceptMapService.findAllByTopicAndStatus(localTopic);
		List<Tutorial> localTutorial=tutorialService.findAllByTopicAndStatus(localTopic);
		
		if(ServiceUtility.chechExistSessionUser(session)) {			// CHECK FOR USER ALIVE SESSION
			
		String loggedUser=(String) session.getAttribute("UserLogedUsername");
		User localUser=userService.findByUsername(loggedUser);
		List<UserRole> localUserRole=localUser.getUserRoles();
		for(UserRole temp:localUserRole) {
			mv.addObject("LoggedUserRole", temp.getRole().getRoleName());
			
		}
		}
		
		
		
		List<TutorialList> tutorialListData=new ArrayList<TutorialList>();
		
		
		for(Tutorial localTemp:localTutorial) {
			
			try {
				String url="https://spoken-tutorial.org/api/get_tutorialdetails/"+localTemp.getStVideoId()+"/";
				RestTemplate restTemp=new RestTemplate();
				TutorialList localTut=restTemp.getForObject(url, TutorialList.class);
				
				System.out.println(localTut.getTut_name());
				
				tutorialListData.add(localTut);
			} catch (RestClientException e) {
				
				e.printStackTrace();
			}
		}
		
			
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
		
		if(localConcept.isEmpty()) {
			mv.addObject("ConceptError", "Nothing To Show");
		}
		
		if(tutorialListData.isEmpty()) {
			mv.addObject("TutorialError", "Nothing To Show");
		}
		
		mv.addObject("QuizOnTopic", localQuiz);
		mv.addObject("VideoOnTopic", localvideo);
		mv.addObject("ArticleOnTopic", localArticle);
		mv.addObject("DocumentOnTopic", localDocument);
		mv.addObject("LessonOnTopic", localLesson);
		mv.addObject("PhetOnTopic", localPhets);
		mv.addObject("ConceptOnTopic",localConcept);
		mv.addObject("TutorialOnTopic", tutorialListData);
		
		mv.addObject("subjectSelected", localTopic.getSubjectClassMapping().getSub().getSubName());
		mv.addObject("classSelected", localTopic.getSubjectClassMapping().getStandard().getClassName());
		mv.addObject("TopicSelected", localTopic);
		mv.setViewName("content");
		return mv;
	}
	
	/*********************** LOGIN HYPERLINK FROM CONTENT PAGE *********************************/
	
	@RequestMapping(path = "/content/Login", method = RequestMethod.GET)
	public ModelAndView pathtoLogin(ModelAndView mv) {
		mv.setViewName("redirect:/Login");
		return mv;
	}
	
	/*********************** SIGNUP HYPERLINK FROM CONTENT PAGE *********************************/
	
	@RequestMapping(path = "/content/Signup", method = RequestMethod.GET)
	public ModelAndView pathtoRegister(ModelAndView mv) {
		mv.setViewName("redirect:/Signup");
		return mv;
	}
	
	
	
	// ------------------ MY MIND HYPERLINK***********************************************
	
	@RequestMapping(path = "/my-mind", method = RequestMethod.GET)
	public ModelAndView mindMapGet(ModelAndView mv) {
		mv.setViewName("my-mind");
		return mv;
	}
	
	
	
	
	

	
	



/*--------------------------------------------------------END----------------------------------------------------------------------------------*/
}


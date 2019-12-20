/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : This Class is a Controller Class(All request related to admin task like adding resources into an App.)
 * 					All Admin related Url for adding resources will get called into this layer.
 * 					This can be considered as middleware ie, create a link among view and modal.
 */


package com.adminportal;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.adminportal.content.ArticleExternal;

import com.adminportal.content.Class;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.ContactForm;
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
import com.adminportal.domain.User;
import com.adminportal.domain.UserRole;
import com.adminportal.service.ArticleExternalService;

import com.adminportal.service.ClassService;
import com.adminportal.service.ConceptMapService;
import com.adminportal.service.ContactFormService;
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

/* Annotation used to declare class to accept url passed from view  */
@Controller

/* these are the Session variable to be used under session management  */
@SessionAttributes({"UserLogedUsername","UserLogedName","UserLogedRole"})

public class AdminController {
	
	public static final String uploadDirectory="Media/content/";  /* path to which content will get stored */
	//public static final String uploadTeacherDirectory="src/main/resources/static/Media/Teacher/"; /* path to which teachers document will get stored */;
	/* Dependency Injection via creating reference to service class */
	
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
	private ConceptMapService conceptService;
	
	@Autowired
	private TestimonialService testiService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private TutorialService tutorialService;
	
	@Autowired
	private ContactFormService contactService;
	
	@Autowired
	private Environment env;
	

	
	
	
	
	/* url to redirect page to homepage of Admin Module */
	
	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public ModelAndView adminHomeGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);  // checking the last alive session
		if(!ServiceUtility.chechExistSessionAdmin(session)) {     // checking for admin alive session
			
			mv.setViewName("redirect:/");    // if no admin session is active--- redirect to login page.
		}else {
						
			mv.setViewName("home");   // if admin session alive the, redirect to homepage.
			
		}
		return mv;
	}
	
	// Authentication Process under the admin module 
	// only admin role access user can access adminn module.
	
//	@RequestMapping(value = "/home",method = RequestMethod.POST)
//	public ModelAndView adminHomepost(HttpServletRequest req,ModelAndView mv) {
//		
//		String username=req.getParameter("username");   // geeting username from the view
//		
//		boolean status=false;
//		
//		User usr=userService.findByUsername(username);   // check for user existence in database
//		boolean passwordStatus=ServiceUtility.passwordEncoder().matches(req.getParameter("password"), usr.getPassword());   
//		
//		if(passwordStatus) {
//			List<UserRole> local=(List<UserRole>) usr.getUserRoles();
//			for(UserRole role:local) {
//				if(role.getRole().getRoleName().contentEquals("Admin")) {  // checking for admin role in user 
//					status=true;
//				}
//			}
//			
//			if(status) {
//				HttpSession session=req.getSession();
//				session.setAttribute("UserLogedAdmin", usr.getEmail());  // Setting session variable for session management.
//				session.setAttribute("UserNameAdmin", usr.getFname());
//				
//				usr.setLastLogin(ServiceUtility.getCurrentTime());
//				userService.save(usr);
//				
//				mv.setViewName("home");                         // redirecting to Homepage
//				
//			}else {
//				mv.setViewName("adminLoginPage");              // redirecting to login page if anything goes wrong.
//				
//			}
//			
//		
//			
//		}else {
//			mv.addObject("Error", "Login Credentials are Incorrect");  // Setting error Status for View.
//			mv.setViewName("adminLoginPage");
//
//			
//		}
//		
//
//
//		return mv;
//		
//	}
	
	// Entry to Admin Module from User side Under the MyAccount Section.
	
//	@RequestMapping(value = "/adminPathUi", method = RequestMethod.GET)
//	public ModelAndView adminPortalFromUI(HttpServletRequest req,ModelAndView mv) {
//		
//		mv.setViewName("redirect:/home");
//		
//		return mv;
//	}
	
	
/*------------------------------------------ADD CLASS (ADMIN MODULE)-----------------------------------------------------------------*/
	
	// method to redirect addClass View 
	
	@RequestMapping(value="/addClass", method = RequestMethod.GET)
	public ModelAndView addClassget(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);                             // checking the last alive session
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {                  // checking for admin alive session
			mv.setViewName("redirect:/");
		}else {
		
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.
			
			mv.addObject("classExist",classExist);
			mv.setViewName("addClass");
		
		}
		
		return mv;
	}
	
	
	// method to add Class entry into database
	
	@RequestMapping(value="/addClass", method = RequestMethod.POST)
	public ModelAndView addClassPost(HttpServletRequest req,@RequestParam(name="classSelected") String classSelected , ModelAndView mv) {
		
		HttpSession session=req.getSession(false);									// checking the last alive session
		if(!ServiceUtility.chechExistSessionAdmin(session)) {						// checking for admin alive session
			mv.addObject("sessionTimeOut", "Session TimeOut");
			
		}else {
			
			boolean classExist=false;
			ArrayList<Class> classTemp=(ArrayList<Class>) classService.findAll();
			for(Class classtemp1:classTemp) {
				if(classSelected.equalsIgnoreCase(classtemp1.getClassName())) {
					classExist=true;
				}
			}
			
			if(!classExist) {
			Class tempClass=new Class();											// creating object of class modal to add entry into database.
			tempClass.setClass_id(classService.countRow()+1);
			tempClass.setClassName(classSelected);
			classService.save(tempClass);											// pushing data into database 
			mv.addObject("status", "Added Successfully");
			}else {
				mv.addObject("sessionTimeOut", "Data Already Exist");
			}
										
		}
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.
		
		mv.addObject("classExist",classExist);
			
		mv.setViewName("addClass");													// returns to addClass View
		
		
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------ADD ARTICLE (ADMIN MODULE)-----------------------------------------------------------------*/
	
	// method to redirect addArticle View 
	
	@RequestMapping(value="/addArticle",method = RequestMethod.GET)
	public ModelAndView addArtcileGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);										// checking the last alive session
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {							// checking for admin alive session
			mv.setViewName("redirect:/");
		}else {
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);											// setting variable for view for displaying purpose
		mv.setViewName("addArticle");													// setting view name
		
		List<ArticleExternal> articleListTemp=articleService.findAll();
		List<ArticleExternal> articleList=new ArrayList<ArticleExternal>();
		for(ArticleExternal temp:articleListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				articleList.add(temp);
			}
		}
		
	
		mv.addObject("Article",articleList);
		}
		mv.addObject("viewActive","active");
		return mv;
	}
	
	
	// method to add Article entry into database
	
	@RequestMapping(value="/addArticle",method = RequestMethod.POST)
	public ModelAndView addArtcilePost(HttpServletRequest req,ModelAndView mv) {
		
		String className=req.getParameter("classSelected");					// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("description");
		String url=req.getParameter("url");
		String source=req.getParameter("source");
		
		if(!url.startsWith("http")) {												// validation against proper Url given against artcile file.
			
			mv.addObject("fileError","Path specified isn't correct");
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

			mv.addObject("classExist", classExist);
			
			mv.addObject("addActive","active");
			
			List<ArticleExternal> articleListTemp=articleService.findAll();
			List<ArticleExternal> articleList=new ArrayList<ArticleExternal>();
			for(ArticleExternal temp:articleListTemp) {
				if(temp.getAcceptedByAdmin()==1) {
					articleList.add(temp);
				}
			}
			
		
			mv.addObject("Article",articleList);
		
			mv.setViewName("addArticle");
			
			return mv;
			
		}
	
		
		String emailToIdentifyUser;
		
		HttpSession session=req.getSession(false);											// checking the last alive session
		
		if(ServiceUtility.chechExistSessionAdmin(session)) {								// checking for admin alive session
		
		try {
			emailToIdentifyUser=(String) session.getAttribute("UserLogedUsername");			// fetching e-mail of user logged in.
			
			Class localClass=classService.findByClassName(className);									// fetching class tuple based on given data
			Subject localSubject=subjectService.findBysubName(subjectName);								// fetching Subject tuple based on given data
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);			// fetching Subject Class mapping tuple based on class and subject
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);					// fetching topic tuple based on given data subject class mapping.
			
			
			
			User usr=userService.findByUsername(emailToIdentifyUser);						// logged in user details.
			Set<ArticleExternal> articlemapping=new HashSet<ArticleExternal>();
			articlemapping.add(new ArticleExternal(articleService.countRow()+1, "Article", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, url, 0,0,  ServiceUtility.getCurrentTime(), localTopic, usr));
			


			userService.addUserToArticle(usr, articlemapping);								// saving the article information into database.
			mv.addObject("statusAdd", "Added Sucessfully");									// setting status for view(success)
		} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please try Again Later");								// setting status for view(failure)
			
		}
		}else {
			
			mv.addObject("sessionTimeOut", "Session TimeOut");
			
		}
		
		List<ArticleExternal> articleListTemp=articleService.findAll();
		List<ArticleExternal> articleList=new ArrayList<ArticleExternal>();
		for(ArticleExternal temp:articleListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				articleList.add(temp);
			}
		}
		
	
		mv.addObject("Article",articleList);
		mv.addObject("addActive","active");
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();				// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);												// setting variable for view for displaying purpose

		
		mv.setViewName("addArticle");														// setting view name
		
		
		return mv;
		
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------ADD DOCUMENT (ADMIN MODULE)-----------------------------------------------------------------*/
	
	// method to redirect addDocument View 
	
	@RequestMapping(value="/addDocument",method = RequestMethod.GET)
	public ModelAndView addDocumentGet(HttpServletRequest req,ModelAndView mv) {
		
		
		HttpSession session=req.getSession(false);								// checking the last alive session
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {					// checking for admin alive session
			mv.setViewName("redirect:/");
		}else {
			
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();	// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);									// setting variable for view for displaying purpose
		
		List<DocumentExternal> documentListTemp=documentService.findAll();
		List<DocumentExternal> documentList=new ArrayList<DocumentExternal>();
		for(DocumentExternal temp:documentListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				documentList.add(temp);
			}
		}
		
		mv.addObject("Document",documentList);
		
		mv.setViewName("addDocument");											// setting view name
		}
		mv.addObject("viewActive","active");
		return mv;
		
	}
	
	
	// method to add Document into database
	@RequestMapping(value="/addDocument",method = RequestMethod.POST)
	public ModelAndView addDocumentPost(HttpServletRequest req,@RequestParam(name="Question")MultipartFile[] document,ModelAndView mv) {
		
		String path1=null;
		String className=req.getParameter("classSelected");						// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("description");
		String source=req.getParameter("source");
		
		String emailToIdentifyUser;
		
		if(!ServiceUtility.checkFileExtensionPDF(document)) {                 	// validation against PDF document 
			mv.addObject("fileError", "File must be a PDF File");
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.

			mv.addObject("classExist", classExist);								// setting variable for view for displaying purpose
			
			mv.addObject("addActive","active");
			
			List<DocumentExternal> documentListTemp=documentService.findAll();
			List<DocumentExternal> documentList=new ArrayList<DocumentExternal>();
			for(DocumentExternal temp:documentListTemp) {
				if(temp.getAcceptedByAdmin()==1) {
					documentList.add(temp);
				}
			}
			
			mv.addObject("Document",documentList);
			
			mv.setViewName("addDocument");										// setting view name
			
			return mv;
			
		}
		
		HttpSession session=req.getSession(false);									// checking the last alive session
		if(ServiceUtility.chechExistSessionAdmin(session)) {						// checking for admin alive session
		
		try {
			emailToIdentifyUser=(String) session.getAttribute("UserLogedUsername");						// fetching e-mail of user logged in.

			String createFolder=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+subjectName+"/"+topicName+"/Document/";	// PATH TO SAVE DOCUMENT UNDER TOPIC
			
			path1=ServiceUtility.uploadFile(document, createFolder);									// 	upload file to path mentioned
			
			int indexToStart=path1.indexOf("Media");														// extracting path starting from MEDIA to save in database
			String path=path1.substring(indexToStart, path1.length());
			

			Class localClass=classService.findByClassName(className);									// retrieving class modal
			Subject localSubject=subjectService.findBysubName(subjectName);								// retrieving subject modal
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject); // retrieving subject class mapping from class and subject
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);			// retrieving topic from sucject class mapping
			

			
			User usr=userService.findByUsername(emailToIdentifyUser);						// retrieving the logged USer
			
			Set<DocumentExternal> documentMapping=new HashSet<DocumentExternal>();
			documentMapping.add(new DocumentExternal(documentService.countRow()+1, "Document", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, path, 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));


			userService.addUserToDocument(usr, documentMapping);					// saving document data into database.		
			mv.addObject("statusAdd", "Document Added Successfully");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again");
		}
		}else {
			
			mv.addObject("sessionTimeOut", "Session TimeOut");
			
		}
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);											// setting variable for view for displaying purpose
		
		mv.addObject("addActive","active");
		
		List<DocumentExternal> documentListTemp=documentService.findAll();
		List<DocumentExternal> documentList=new ArrayList<DocumentExternal>();
		for(DocumentExternal temp:documentListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				documentList.add(temp);
			}
		}
		
		mv.addObject("Document",documentList);
		
		mv.setViewName("addDocument");													// setting view name
		
		
		return mv;
		
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------ADD LESSON PLAN (ADMIN MODULE)-----------------------------------------------------------------*/
	
	// method to redirect addLessonPlan View 
	@RequestMapping(value="/addLessonPlan",method = RequestMethod.GET)
	public ModelAndView addLessonPlanGet(HttpServletRequest req,ModelAndView mv) {
		
		
		HttpSession session=req.getSession(false);									// checking the last alive session
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {						// checking for admin alive session
			mv.setViewName("redirect:/");
		}else {
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.
		
		mv.addObject("classExist", classExist);										// setting variable for view for displaying purpose
		
		List<LessonPlan> lessonListTemp=lessonService.findAll();
		List<LessonPlan> lessonList=new ArrayList<LessonPlan>();
		for(LessonPlan temp:lessonListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				lessonList.add(temp);
			}
		}
	
		mv.addObject("Lesson",lessonList);
		
		mv.setViewName("addLessonPlan");											// setting view name
		}
		
		mv.addObject("viewActive","active");
		return mv;
		
	}
	
	// method to add Lesson Plan into database
	@RequestMapping(value="/addLessonPlan",method = RequestMethod.POST)
	public ModelAndView addLessonPlanPost(HttpServletRequest req,@RequestParam(name="lesson") MultipartFile[] lesson,ModelAndView mv) {
		
		String path1=null;
		String className=req.getParameter("classSelected");						// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		
		
		String emailToIdentifyUser;
		
		if(!ServiceUtility.checkFileExtensionPDF(lesson)) {							// validation against PDF document 
			
			mv.addObject("fileError", "Please Select PDF file");
			
			List<LessonPlan> lessonListTemp=lessonService.findAll();
			List<LessonPlan> lessonList=new ArrayList<LessonPlan>();
			for(LessonPlan temp:lessonListTemp) {
				if(temp.getAcceptedByAdmin()==1) {
					lessonList.add(temp);
				}
			}
		
			mv.addObject("Lesson",lessonList);
			
			mv.addObject("addActive","active");
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

			mv.addObject("classExist", classExist);										// setting variable for view for displaying purpose
			
			mv.setViewName("addLessonPlan");											// setting view name
			return mv;
			
		}
		
		HttpSession session=req.getSession(false);									// checking the last alive session
		if(ServiceUtility.chechExistSessionAdmin(session)) {						// checking for admin alive session
		
		
		try {
			emailToIdentifyUser=(String) session.getAttribute("UserLogedUsername");   
			

			String createFolder=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+subjectName+"/"+topicName+"/Lessonplan/";  // path to store lesson Plan
			
			path1=ServiceUtility.uploadFile(lesson, createFolder);					// uploading lesson plan to path given
			
			int indexToStart=path1.indexOf("Media");									// extract path starting from MEDIA to persist .
			String path=path1.substring(indexToStart, path1.length());
			
			
			

			Class localClass=classService.findByClassName(className);						
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			

			
			User usr=userService.findByUsername(emailToIdentifyUser);				// retrive logged in user
			
			Set<LessonPlan> lessonMapping=new HashSet<LessonPlan>();
			lessonMapping.add(new LessonPlan(lessonService.countRow()+1, "Lesson", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), path, 0, 0,ServiceUtility.getCurrentTime(), localTopic, usr));
			
			
			userService.addUserToLessonplan(usr, lessonMapping);					// saving lessonPaln data
			mv.addObject("statusAdd", "Added Sucessfully");
			
		} catch (Exception e) {
		
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again");
			
		}
		}else {
			mv.addObject("sessionTimeOut", "Session TimeOut");
			
		}
		
		
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);											// setting variable for view for displaying purpose
		
		
		mv.addObject("lessonStatus", "Added Sucessfully");	
		
		List<LessonPlan> lessonListTemp=lessonService.findAll();
		List<LessonPlan> lessonList=new ArrayList<LessonPlan>();
		for(LessonPlan temp:lessonListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				lessonList.add(temp);
			}
		}
	
		mv.addObject("Lesson",lessonList);
		
		mv.addObject("addActive","active");
			
		mv.setViewName("addLessonPlan");												// setting view name
			
		
	
		return mv;
		
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	/*------------------------------------------ADD PHET (ADMIN MODULE)-----------------------------------------------------------------*/
	
	// method to redirect addPhets View 
	@RequestMapping(value="/addPhets",method = RequestMethod.GET)
	public ModelAndView addPhetsGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);								// checking the last alive session
			
		if(!ServiceUtility.chechExistSessionAdmin(session)) {					// checking for admin alive session
			mv.setViewName("redirect:/");
		}else {
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();	// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);									// setting variable for view for displaying purpose
		
		List<Phets> phetListTemp=phetService.findAll();
		List<Phets> phetList=new ArrayList<Phets>();
		for(Phets temp:phetListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				phetList.add(temp);
			}
		}
		
		mv.addObject("Phet",phetList);
		
		mv.setViewName("addPhets");												// setting view name
		}
		
		mv.addObject("viewActive","active");
		return mv;
	
	}
	
	// method to add Phets into database
	@RequestMapping(value="/addPhets",method = RequestMethod.POST)
	public ModelAndView addPhetsPost(HttpServletRequest req,ModelAndView mv) {
		

		String className=req.getParameter("classSelected");						// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("description");
		String phet=req.getParameter("phet");
		String source=req.getParameter("source");
		String phetPath=null;

		
		String emailToIdentifyUser;
		
		HttpSession session=req.getSession(false);							// checking the last alive session
		
		
			if(phet.length()>0) {											// checking out whether phet link proper or not
			
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
				
				e.printStackTrace();
				ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

				mv.addObject("classExist", classExist);
				
				List<Phets> phetListTemp=phetService.findAll();
				List<Phets> phetList=new ArrayList<Phets>();
				for(Phets temp:phetListTemp) {
					if(temp.getAcceptedByAdmin()==1) {
						phetList.add(temp);
					}
				}
				
				mv.addObject("Phet",phetList);
				
				mv.addObject("fileError", "Please specify Embed Code");
				
				mv.addObject("addActive","active");
				
				mv.setViewName("addPhets");
				
				return mv;
				
				
			}
			
		}else {
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

			mv.addObject("classExist", classExist);
			
			mv.addObject("failure", "Please Try Again");
			
			List<Phets> phetListTemp=phetService.findAll();
			List<Phets> phetList=new ArrayList<Phets>();
			for(Phets temp:phetListTemp) {
				if(temp.getAcceptedByAdmin()==1) {
					phetList.add(temp);
				}
			}
			
			mv.addObject("Phet",phetList);
			
			mv.addObject("addActive","active");
			
			mv.setViewName("addPhets");
			
			return mv;
			
			
		}
			
			
			if(ServiceUtility.chechExistSessionAdmin(session)) {					// checking for admin alive session
		
				try {
			
			emailToIdentifyUser=(String) session.getAttribute("UserLogedUsername");

			Class localClass=classService.findByClassName(className);
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			
			
			User usr=userService.findByUsername(emailToIdentifyUser);				// retrive Logged In User
			
			
			Set<Phets> phetMapping=new HashSet<Phets>();
			phetMapping.add(new Phets(phetService.countRow()+1, "Phets", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, phetPath, 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));

			
			userService.addUserToPhets(usr, phetMapping);							// persist Phet data 
			mv.addObject("statusAdd", "Added Sucessfully");
			
			
				} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again");
			
			
				}
				
			}else {
				
				mv.addObject("sessionTimeOut", "Session TimeOut");
			}
		
		
		
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);											// setting variable for view for displaying purpose
		
		mv.addObject("addActive","active");
		
		List<Phets> phetListTemp=phetService.findAll();
		List<Phets> phetList=new ArrayList<Phets>();
		for(Phets temp:phetListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				phetList.add(temp);
			}
		}
		
		mv.addObject("Phet",phetList);
		
		mv.setViewName("addPhets");														// setting view name
		
		
		
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	
	/*------------------------------------------ADD TOPIC (ADMIN MODULE)-----------------------------------------------------------------*/
	
	// method to add Topic into database
	@RequestMapping(value="/addTopic",method=RequestMethod.POST)
	public ModelAndView addTopicPost(@RequestParam("posterQ") MultipartFile[] poster,HttpServletRequest req,ModelAndView mv) throws Exception {
		
		String path1=null;
		String uploadDirectoryPoster=null;
		String className=req.getParameter("classSelected");							// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topic");
		String topicDesc=req.getParameter("descriptionQ");
		
		System.out.println(poster);
		
		
		if(!ServiceUtility.checkFileExtensionImage(poster)) {						// validating Image file of given data
			
			mv.addObject("fileError", "Invalid File Extension");
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();
			
			mv.addObject("classExist", classExist);
			mv.setViewName("addTopic");
			List<Topic> topicList=topicService.findAll();
			mv.addObject("Topic", topicList);
			mv.addObject("addActive","active");
			return mv;
			
			
		}
		
		try {
			boolean b=ServiceUtility.createclassSubjectFolder(className, subjectName,topicName) ;
			

			
			uploadDirectoryPoster=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+subjectName+"/"+topicName+"/"; // path to upload Quiz File
			System.out.println(uploadDirectoryPoster);
				
			path1=ServiceUtility.uploadFile(poster, uploadDirectoryPoster);
				
	
			int indexToStart=path1.indexOf("Media");						// extract path starting from MEDIA to persist .
			String path=path1.substring(indexToStart, path1.length());
			
			Class localClass=classService.findByClassName(className);
			Subject localSubject =subjectService.findBysubName(subjectName);
			SubjectClassMapping localsubjectClassMapping=subjectClassService.findBysubAndstandard(localClass,localSubject);
			
			System.out.println(localsubjectClassMapping.getSubClassId());
			
			Topic addTopic=new Topic();
			addTopic.setTopicId(topicService.countRow()+1);
			addTopic.setPoster(path);
			addTopic.setTopicName(topicName);
			addTopic.setDescription(topicDesc);
			addTopic.setDateAdded(ServiceUtility.getCurrentTime());
			addTopic.setDateModified(ServiceUtility.getCurrentTime());
			addTopic.setSubjectClassMapping(localsubjectClassMapping);
			addTopic.setStatus(1);
			
			subjectClassService.createTopic(addTopic, localsubjectClassMapping);
			mv.addObject("statusAdd", "Added Sucessfully");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			mv.addObject("failure", "Please Try Again");
		}
		
		
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic", topicList);
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.
		
		
		mv.addObject("classExist", classExist);											// setting variable for view for displaying purpose
		
		mv.setViewName("addTopic");
		mv.addObject("addActive","active");
		return mv;
	}
	
	// redirect to addTopic View
	@RequestMapping(value="/addTopic",method=RequestMethod.GET)
	public ModelAndView addTopicGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);								// checking the last alive session
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {					// checking for admin alive session
			mv.setViewName("redirect:/");
		}else {
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();	// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);									// setting variable for view for displaying purpose
		mv.setViewName("addTopic");												// setting view name
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic", topicList);
		}
		mv.addObject("viewActive","active");
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	/*------------------------------------------ADD SUBJECT (ADMIN MODULE)-----------------------------------------------------------------*/
	
	
	// method to redirect addSubject View 
	@RequestMapping(value="/addSubject", method=RequestMethod.GET)
	public ModelAndView addSubjectGet(HttpServletRequest req,ModelAndView mv) {
		HttpSession session=req.getSession(false);						// checking the last alive session
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {			// checking for admin alive session
			mv.setViewName("redirect:/");
		}else {
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.
		
		mv.addObject("classExist",classExist);
		
		mv.setViewName("addSubject");										// setting view name
		
		List<Subject> subjectList=subjectService.findAll();
		mv.addObject("Subject", subjectList);
		}
		
		mv.addObject("viewActive","active");
		return mv;
	}
	
	
	// method to add Subject into database
	@RequestMapping(value="/addSubject", method=RequestMethod.POST)
	public ModelAndView addSubjectPost(HttpServletRequest req,@RequestParam(name="SelectedSubject") String selectedSubject,ModelAndView mv) {
			
		int i=0;     
		boolean subjectExist=false;
		ArrayList<Subject> subject=(ArrayList<Subject>) subjectService.findAll();
		for(Subject temp:subject) {
			if(selectedSubject.equalsIgnoreCase(temp.getSubName())) {
				subjectExist=true;
			}
		}
		String[] selectedClasses=req.getParameterValues("SelectedClasses");
		if(!subjectExist) {
		
			
			Subject addsubject=new Subject();
			addsubject.setSubId(subjectService.countRow()+1);
			addsubject.setSubName(selectedSubject);
			
			
			try {
				Set<SubjectClassMapping> subjectClassMapping=new HashSet<SubjectClassMapping>();

				for(String classes:selectedClasses) {
					Class om=classService.findByClassName(classes);
					subjectClassMapping.add(new SubjectClassMapping(subjectClassService.countRow()+i++,om,addsubject));
				}


				subjectClassService.createSubjectClass(addsubject, subjectClassMapping);

				mv.addObject("statusAdd", "Added Sucessfully");
			} catch (Exception e) {
				
				subjectService.save(addsubject);
				mv.addObject("statusAdd", "Added Sucessfully");
			}
			
				
			
		}else {
	
			mv.addObject("statusAdd", "Subject Exist");
		}
		
		
		
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.
		
		mv.addObject("classExist",classExist);										// setting variable for view for displaying purpose								
		mv.addObject("addActive","active");
		
		List<Subject> subjectList=subjectService.findAll();
		mv.addObject("Subject", subjectList);
		
		
		mv.setViewName("addSubject");												// setting view name
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------ADD VIDEO (ADMIN MODULE)-----------------------------------------------------------------*/
	
	// method to redirect addVideo View 
	@RequestMapping(value="/addVideo",method = RequestMethod.GET)
	public ModelAndView addVideoGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);					// checking the last alive session
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {		// checking for admin alive session
			mv.setViewName("redirect:/");
		}else {
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();	// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);									// setting variable for view for displaying purpose
		mv.setViewName("addVideo");												// setting view name
		List<VideoExternal> videoListtemp=videoService.findAll();
		List<VideoExternal> videoList=new ArrayList<VideoExternal>();
		for(VideoExternal temp:videoListtemp) {
			if(temp.getAcceptedByAdmin()==1) {
				videoList.add(temp);
			}
		}
	
		
		
		
		mv.addObject("Video",videoList);
		
		}
		
		mv.addObject("viewActive","active");
		return mv;
		
	}
	
	// method to add Video into database
	@RequestMapping(value="/addVideo",method = RequestMethod.POST)
	public ModelAndView addVideoGetPost(HttpServletRequest req,ModelAndView mv) {
		
		
		String className=req.getParameter("classSelected");						// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("description");
		String url=req.getParameter("url");
		String source=req.getParameter("source");
		String videourl=null;
	
		
		if(url.length()>0) {													// check for proper Video Url 
			
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
				mv.addObject("fileError","File path is not proper");
				
				ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

				mv.addObject("classExist", classExist);
				mv.addObject("addActive","active");
				mv.setViewName("addVideo");
				
				List<VideoExternal> videoListtemp=videoService.findAll();
				List<VideoExternal> videoList=new ArrayList<VideoExternal>();
				for(VideoExternal temp:videoListtemp) {
					if(temp.getAcceptedByAdmin()==1) {
						videoList.add(temp);
					}
				}
			
				
				
				
				mv.addObject("Video",videoList);
				
				return mv;
				
				
				
			}
			
		}else {
			
		}
		
		String emailToIdentifyUser;
		
		
		HttpSession session=req.getSession(false);							// checking the last alive session
		
		if(ServiceUtility.chechExistSessionAdmin(session)) {				// checking for admin alive session
	
		try {
			emailToIdentifyUser=(String) session.getAttribute("UserLogedUsername");
			
			

			Class localClass=classService.findByClassName(className);
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			
			
			
			User usr=userService.findByUsername(emailToIdentifyUser);
			
			Set<VideoExternal> videoMapping=new HashSet<VideoExternal>();
			videoMapping.add(new VideoExternal(videoService.countRow()+1, "Video", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, videourl, 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));
			

			
			userService.addUserToVideo(usr, videoMapping);							// persist Video Information
			mv.addObject("statusAdd", "Added Sucessfully");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again Later");
		}
		}else {
			
			mv.addObject("sessionTimeOut", "Session TimeOut");
		}
		
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);										// setting variable for view for displaying purpose
		
		List<VideoExternal> videoListtemp=videoService.findAll();
		List<VideoExternal> videoList=new ArrayList<VideoExternal>();
		for(VideoExternal temp:videoListtemp) {
			if(temp.getAcceptedByAdmin()==1) {
				videoList.add(temp);
			}
		}
	
		
		mv.addObject("Video",videoList);
		mv.addObject("addActive","active");
		
		mv.setViewName("addVideo");													// setting view name
			
		
		
		return mv;
		
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------ADD QUIZ (ADMIN MODULE)-----------------------------------------------------------------*/
	
	// method to redirect addQuiz View 
	@RequestMapping(value="/addQuiz",method = RequestMethod.GET)
	public ModelAndView addQuizGet(HttpServletRequest req,ModelAndView mv) {
			
		HttpSession session=req.getSession(false);							// checking the last alive session
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {				// checking for admin alive session
			mv.setViewName("redirect:/");
		}else {
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();	// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);									// setting variable for view for displaying purpose
		mv.setViewName("addQuiz");												// setting view name
		List<QuizQuestion> quizListTemp=quizService.findAll();
		List<QuizQuestion> quizList=new ArrayList<QuizQuestion>();
		for(QuizQuestion temp:quizListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				quizList.add(temp);
			}
		}
		
		mv.addObject("Quiz",quizList );
		}
		mv.addObject("viewActive","active");
		return mv;
		
		
	}
	
	// method to add Quiz into database
	@RequestMapping(value="/addQuiz",method = RequestMethod.POST)
	public ModelAndView addQuizPost(HttpServletRequest req,@RequestParam(name="Question")MultipartFile[] question,@RequestParam(name="Answer")MultipartFile[] answer,ModelAndView mv) {
		
		String questionPath=null;
		String answerPath=null;
		String className=req.getParameter("classSelected");						// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String remarks=req.getParameter("remarks");
		
		
		String emailToIdentifyUser;
		
		HttpSession session=req.getSession(false);							// checking the last alive session
		
		
		
		if(!ServiceUtility.checkFileExtensionPDF(question)) {					// check for PDF file
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

			mv.addObject("classExist", classExist);

			mv.addObject("fileError", "Please Add only Pdf File");
				
			mv.setViewName("addQuiz");
			mv.addObject("addActive","active");
			
			List<QuizQuestion> quizListTemp=quizService.findAll();
			List<QuizQuestion> quizList=new ArrayList<QuizQuestion>();
			for(QuizQuestion temp:quizListTemp) {
				if(temp.getAcceptedByAdmin()==1) {
					quizList.add(temp);
				}
			}
			
			mv.addObject("Quiz",quizList );
			
			return mv;
			
		
			
		}
		
		if(!ServiceUtility.checkFileExtensionPDF(answer)) {								// check for PDF file
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

			mv.addObject("classExist", classExist);

			mv.addObject("fileError", "Please Add only Pdf File");
				
			mv.setViewName("addQuiz");
			mv.addObject("addActive","active");
			
			
			List<QuizQuestion> quizListTemp=quizService.findAll();
			List<QuizQuestion> quizList=new ArrayList<QuizQuestion>();
			for(QuizQuestion temp:quizListTemp) {
				if(temp.getAcceptedByAdmin()==1) {
					quizList.add(temp);
				}
			}
			
			mv.addObject("Quiz",quizList );
			
			return mv;
			
			
		}
		
		
		
		
		String createFolder=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+subjectName+"/"+topicName+"/Quiz/"+remarks+"/";  // path to save question and answer
		boolean b=ServiceUtility.createFolder(createFolder);
		
		String CreateFolderQuestion=createFolder+"Question/";
		String CreateFolderAnswer=createFolder+"Answer/";
		
		boolean ques=ServiceUtility.createFolder(CreateFolderQuestion);
		boolean ans=ServiceUtility.createFolder(CreateFolderAnswer);
		
		if(ServiceUtility.chechExistSessionAdmin(session)) {			// checking for admin alive session
		
		try {
			if(ques && ans) {
			
			questionPath=ServiceUtility.uploadFile(question, CreateFolderQuestion);
			answerPath=ServiceUtility.uploadFile(answer, CreateFolderAnswer);
			
			
			int indexToStart=questionPath.indexOf("Media");
			String pathQuestion=questionPath.substring(indexToStart, questionPath.length());
			
			int indexToStart1=answerPath.indexOf("Media");
			String pathAnswer=answerPath.substring(indexToStart1, answerPath.length());
			
			emailToIdentifyUser=(String) session.getAttribute("UserLogedUsername");
			
			User usr=userService.findByUsername(emailToIdentifyUser);
			
			Class localClass=classService.findByClassName(className);
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			

			
			Set<QuizQuestion> quizMapping=new HashSet<QuizQuestion>();
			quizMapping.add(new QuizQuestion(quizService.countRow()+1,"Quiz",ServiceUtility.getCurrentTime(),ServiceUtility.getCurrentTime(),pathQuestion,pathAnswer,0,0,remarks,ServiceUtility.getCurrentTime(),localTopic,usr));
			
			
			
			userService.addUserToQuizQuestion(usr, quizMapping);   // persist Quiz details
			
			mv.addObject("statusAdd", "Added Sucessfully");
			
			}else {
				mv.addObject("failure", " Try again Later");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			
			mv.addObject("failure", " Try again Later");
			
		}
		}else {
			
			mv.addObject("sessionTimeOut", "Session TimeOut");
			
		}
		
		
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);										// setting variable for view for displaying purpose
		
		mv.addObject("quizStatus", "Added Sucessfully");
		mv.addObject("addActive","active");
		
		List<QuizQuestion> quizListTemp=quizService.findAll();
		List<QuizQuestion> quizList=new ArrayList<QuizQuestion>();
		for(QuizQuestion temp:quizListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				quizList.add(temp);
			}
		}
		
		mv.addObject("Quiz",quizList );
		
		mv.setViewName("addQuiz");													// setting view name
		
	
		return mv;
		
		
	}
	/**********************************************************************************************/
	
	// method to redirect addConcept-map View 
	@RequestMapping(value = "/addConceptMap",method = RequestMethod.GET)
	public ModelAndView addConceptMapGet(HttpServletRequest req,ModelAndView mv) {
		HttpSession session=req.getSession();							// checking the last alive session
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {			// checking for admin alive session
			mv.setViewName("redirect:/");
		}else {
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);										// setting variable for view for displaying purpose
		
		List<ConceptMap> ConceptMapListTemp=conceptService.findAll();
		List<ConceptMap> ConceptMapList=new ArrayList<ConceptMap>();
		for(ConceptMap temp:ConceptMapListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				ConceptMapList.add(temp);
			}
		}
		
		mv.addObject("ConceptMapList",ConceptMapList );
		mv.addObject("viewActive","active");
		mv.setViewName("addConcpetMap");											// setting view name
		}
		return mv;
	}
	
	// method to add Concept -MAp into database
	@RequestMapping(value = "/addConceptMap",method = RequestMethod.POST)
	public ModelAndView addConceptMapPost(HttpServletRequest req,@RequestParam(name="conceptMapImage")MultipartFile[] conceptMapImage,ModelAndView mv) {
		
		String path1=null;
		String className=req.getParameter("classSelected");				// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("descriptionConceptMap");
		String remark=req.getParameter("headlineConceptMap");
		
		String emailToIdentifyUser;
		
		if(!ServiceUtility.checkFileExtensionImage(conceptMapImage)) {    // check Image file
			mv.addObject("fileError", "File must be a Image File");
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

			mv.addObject("classExist", classExist);
			
			List<ConceptMap> ConceptMapListTemp=conceptService.findAll();
			List<ConceptMap> ConceptMapList=new ArrayList<ConceptMap>();
			for(ConceptMap temp:ConceptMapListTemp) {
				if(temp.getAcceptedByAdmin()==1) {
					ConceptMapList.add(temp);
				}
			}
			
			mv.addObject("ConceptMapList",ConceptMapList );
			
			mv.addObject("addActive","active");
			
			mv.setViewName("addConcpetMap");
			
			return mv;
			
		}
		
		HttpSession session=req.getSession(false);							// checking the last alive session
		if(ServiceUtility.chechExistSessionAdmin(session)) {				// checking for admin alive session
		
		try {
			emailToIdentifyUser=(String) session.getAttribute("UserLogedUsername");

			String createFolder=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+subjectName+"/"+topicName+"/ConceptMap/";  // path to store Concept-map
			
			path1=ServiceUtility.uploadFile(conceptMapImage, createFolder);
			
			int indexToStart=path1.indexOf("Media");										// extracting proper Path from Actual path
			String path=path1.substring(indexToStart, path1.length());
			

			Class localClass=classService.findByClassName(className);
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			

			
			User usr=userService.findByUsername(emailToIdentifyUser);
			
			Set<ConceptMap> conceptMapping=new HashSet<ConceptMap>();
			conceptMapping.add(new ConceptMap(conceptService.countRow()+1, "ConceptMap", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), path, desc, 0,0, remark, localTopic, usr));
			
			

			userService.addUserToConceptMap(usr, conceptMapping);						// persist Concept-map
			mv.addObject("statusAdd", "Concept-Map Added Successfully");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again");
		}
		}else {
			
			mv.addObject("sessionTimeOut", "Session TimeOut");
			
		}
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);											// setting variable for view for displaying purpose
		
		List<ConceptMap> ConceptMapListTemp=conceptService.findAll();
		List<ConceptMap> ConceptMapList=new ArrayList<ConceptMap>();
		for(ConceptMap temp:ConceptMapListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				ConceptMapList.add(temp);
			}
		}
		
		mv.addObject("ConceptMapList",ConceptMapList );
		
		mv.addObject("addActive","active");
		
		mv.setViewName("addConcpetMap");												// setting view name
		
		
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	/**************************************** ADDING TUTORIAL  ********************************************************************************/
	// method to redirect addTutorial View 
	
	@RequestMapping(value="/addTutorial",method = RequestMethod.GET)
	public ModelAndView addTutorialGet(HttpServletRequest req,ModelAndView mv) {
		
		
		HttpSession session=req.getSession(false);							// checking the last alive session
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {				// checking for admin alive session
			mv.setViewName("redirect:/");
		}else {
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);										// setting variable for view for displaying purpose
		mv.addObject("viewActive","active");
		
		List<Tutorial> tempTutorial=tutorialService.getAllTutorial();
		
		List<TutorialList> tutorialListData=new ArrayList<TutorialList>();
		
		
		for(Tutorial localTemp:tempTutorial) {
			
			try {
				String url="https://spoken-tutorial.org/api/get_tutorialdetails/"+localTemp.getStVideoId()+"/";
				RestTemplate restTemp=new RestTemplate();
				TutorialList localTutorial=restTemp.getForObject(url, TutorialList.class);
				
				localTutorial.setTutorialId(localTemp.getTutorialId());
				localTutorial.setTopicNAme(localTemp.getTopic().getTopicName());
				localTutorial.setContributedBy(localTemp.getUser().getFname());
				localTutorial.setStatus(localTemp.getStatus());
				System.out.println(localTutorial.getOutline());
				
				tutorialListData.add(localTutorial);
			} catch (RestClientException e) {
				
				e.printStackTrace();
			}
		}
		
	
		
		mv.addObject("Tutorials", tutorialListData);
		
		
		mv.setViewName("addTutorial");												// setting view name
		}
		return mv;
		
	}
	
	// method to add Tutorial into database
	@RequestMapping(value = "/addTutorial",method = RequestMethod.POST)
	public ModelAndView addTutorialPost(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);							// checking the last alive session
		if(!ServiceUtility.chechExistSessionAdmin(session)) {				// checking for admin alive session
			mv.setViewName("redirect:/adminPortal");
		}else {
			
			String className=req.getParameter("classSelected");					// taking out various information given by user in view.
			String subjectName=req.getParameter("subjectSelected");
			String topicName=req.getParameter("topicSelected");
			String [] tutorialList=req.getParameterValues("fossTutorialSelected");
			int fossid=Integer.parseInt(req.getParameter("fossSelected"));
			int stLanguageId=Integer.parseInt(req.getParameter("fossLanguageSelected"));
			
			String emailToIdentifyUser=(String) session.getAttribute("UserLogedUsername");
			
			User usr=userService.findByUsername(emailToIdentifyUser);
			
			
			Class localClass=classService.findByClassName(className);
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			
			Set<Tutorial> tempTutorial=new HashSet<Tutorial>();
			int tutorialCount=tutorialService.countRow();
			tutorialCount++;
			
			for(int i=0;i<tutorialList.length;i++) {
				int videoId=Integer.parseInt(tutorialList[i]);
				tempTutorial.add(new Tutorial(tutorialCount+i, fossid, stLanguageId, videoId, 1, localTopic, usr));
			}
			
			
			userService.addUserToTutorial(usr, tempTutorial);				// persisting Tutorial
			mv.addObject("statusAdd", "Tutorial Added Successfully");
			
		
			
		}
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);											// setting variable for view for displaying purpose
		
		mv.addObject("addActive","active");
		
		List<Tutorial> tempTutorial=tutorialService.getAllTutorial();
		
		List<TutorialList> tutorialListData=new ArrayList<TutorialList>();
		
		
		for(Tutorial localTemp:tempTutorial) {
			
			try {
				String url="https://spoken-tutorial.org/api/get_tutorialdetails/"+localTemp.getStVideoId()+"/";
				RestTemplate restTemp=new RestTemplate();
				TutorialList localTutorial=restTemp.getForObject(url, TutorialList.class);
				
				localTutorial.setTutorialId(localTemp.getTutorialId());
				localTutorial.setTopicNAme(localTemp.getTopic().getTopicName());
				localTutorial.setContributedBy(localTemp.getUser().getFname());
				localTutorial.setStatus(localTemp.getStatus());
				System.out.println(localTutorial.getOutline());
				
				tutorialListData.add(localTutorial);
			} catch (RestClientException e) {
				
				e.printStackTrace();
			}
		}
		
	
		
		mv.addObject("Tutorials", tutorialListData);
		
		mv.setViewName("addTutorial");													// setting view name
		
		return mv;
	}
	
	
	
	
	/*******************************************************************************************************************************************/

	// method to redirect addTestimonial View 
	@RequestMapping(value = "/addTestimonial",method = RequestMethod.GET)
	public ModelAndView addTestimonialGet(HttpServletRequest req,ModelAndView mv) {
		HttpSession session=req.getSession(false);						// checking the last alive session
		if(!ServiceUtility.chechExistSessionAdmin(session)) {			// checking for admin alive session
			mv.setViewName("redirect:/");
		}else {
				mv.addObject("viewActive","active");
				List<Testimonial> local=testiService.findAll();
				mv.addObject("Testimonial", local);
				mv.setViewName("addTestimonial");							// setting view name
				
		}
		
		return mv;
	
		
	}
	
	
	// method to add Testimonial into database
	@RequestMapping(value = "/addTestimonial",method = RequestMethod.POST)
	public ModelAndView addTestimonialPost(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);					// checking the last alive session
		String name=req.getParameter("Name");						// taking out various information given by user in view.
		String organization=req.getParameter("org");
		String Desc=req.getParameter("description");
		if(!ServiceUtility.chechExistSessionAdmin(session)) {		// checking for admin alive session
			mv.setViewName("redirect:/");
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
						mv.addObject("addActive","active");
						List<Testimonial> local=testiService.findAll();
						mv.addObject("Testimonial", local);
						mv.setViewName("addTestimonial");									// setting view name
					} catch (Exception e) {
						
						mv.addObject("returnStatus", "Please Try Again");
						mv.addObject("addActive","active");
						List<Testimonial> local=testiService.findAll();
						mv.addObject("Testimonial", local);
						mv.setViewName("addTestimonial");
						e.printStackTrace();
					}
			}
			
		
		return mv;

	}
	
	
	
	// method to redirect addEvent View 
	@RequestMapping(value = "/addEvent",method = RequestMethod.GET)
	public ModelAndView addEventGet(HttpServletRequest req,ModelAndView mv) {
		
		
		HttpSession session=req.getSession(false);						// checking the last alive session
		if(!ServiceUtility.chechExistSessionAdmin(session)) {			// checking for admin alive session
			mv.setViewName("redirect:/");
		}else {
				List<Events> local=eventService.findAll();
				mv.addObject("Events", local);
				mv.addObject("viewActive","active");
				mv.setViewName("addEvent");								// setting view name
		}
		
		return mv;

	}
	
	// method to add event into database
	@RequestMapping(value = "/addEvent",method = RequestMethod.POST)
	public ModelAndView addEventPost(HttpServletRequest req,ModelAndView mv) {
		
		String headline=req.getParameter("headline");									// taking out various information given by user in view.
		String Desc=req.getParameter("description");
		
	
		
		HttpSession session=req.getSession(false);										// checking the last alive session
		if(!ServiceUtility.chechExistSessionAdmin(session)) {							// checking for admin alive session
			mv.setViewName("redirect:/");
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
						
						List<Events> local=eventService.findAll();
						mv.addObject("Events", local);
						mv.addObject("addActive","active");
						
						mv.addObject("returnStatus", "Data Added Sucessfully");
						mv.setViewName("addEvent");											// setting view name
					} catch (Exception e) {
						
						mv.addObject("returnStatus", "Please Try Again");
						
						List<Events> local=eventService.findAll();
						mv.addObject("Events", local);
						mv.addObject("addActive","active");
						mv.setViewName("addEvent");											// setting view name
						e.printStackTrace();
					}
			}
			
		
		return mv;

	}
	
	
	//method to redirect page to Message of User
	@RequestMapping(value = "/contactInformation",method = RequestMethod.GET)
	public ModelAndView getUserMessageget(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);										// checking the last alive session
		if(!ServiceUtility.chechExistSessionAdmin(session)) {							// checking for admin alive session
			mv.setViewName("redirect:/");
		}else {
			
			List<ContactForm> tempContact=contactService.getAllMessages();
			mv.addObject("contactMessages", tempContact);
			mv.setViewName("message");	
			
			
		}
		
		return mv;
		
	}
	

}

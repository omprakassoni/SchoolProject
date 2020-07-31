/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description   : This Class is a Controller Class(All request related to admin task like adding resources into an App.)
 * 					All Admin related Url for adding resources will get called into this layer.
 * 					This can be considered as middleware ie, create a link among view and modal.
 */


package com.adminportal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.security.Provider.Service;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.ContainerType;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.IContext;

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
import com.xuggle.xuggler.IContainer;


/* Annotation used to declare class to accept url passed from view  */
@Controller
public class AdminController {
	
	public static final String uploadDirectory="Media/content/";  /* path to which content will get stored */
	public static final String uploadEvent="Media/Event/";
	public static final long videoSize=50*1024*1024;                     // 50 MB vidoe File max
	public static final long fileSize=10*1024*1024;                      // 10 MB max file   max
	public static final String uploadTestimonial="Media/Testimonial/";
	public static final long videoDuration=300000000L;
	
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
	
	@RequestMapping(value = "/admin/addView",method = RequestMethod.GET)
	public ModelAndView adminHomeView(Principal principal,ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
			
		mv.setViewName("addView");   
	
		return mv;
	}
	
	@RequestMapping(value = "/admin/approve",method = RequestMethod.GET)
	public ModelAndView adminHomeAprove(Principal principal,ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
			
		mv.setViewName("approveReject");   
	
		return mv;
	}
	
	
/*------------------------------------------ADD CLASS (ADMIN MODULE)-----------------------------------------------------------------*/
	
	// method to redirect addClass View 
	
	@RequestMapping(value="/admin/addView/addClass", method = RequestMethod.GET)
	public ModelAndView addClassget(Principal principal,ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.
			
		mv.addObject("classExist",classExist);
		mv.setViewName("addClass");
		
		return mv;
	}
	
	
	// method to add Class entry into database
	
	@RequestMapping(value="/admin/addView/addClass", method = RequestMethod.POST)
	public ModelAndView addClassPost(HttpServletRequest req,@RequestParam(name="classSelected") int classSelected ,Principal principal, ModelAndView mv) {
			
			boolean classExist=false;
			ArrayList<Class> classTemp=(ArrayList<Class>) classService.findAll();
			for(Class classtemp1:classTemp) {
				if(classSelected==classtemp1.getClassName()) {
					classExist=true;
				}
			}
			
			if(!classExist) {
			User usr=userService.findByUsername(principal.getName());	
				
			Class tempClass=new Class();											// creating object of class modal to add entry into database.
			tempClass.setClass_id(classService.countRow()+1);
			tempClass.setClassName(classSelected);
			tempClass.setDateAdded(ServiceUtility.getCurrentTime());
			tempClass.setUser(usr);
			userService.addUserToClass(usr, tempClass);											// pushing data into database 
			mv.addObject("status", "Added Successfully");
			}else {
				mv.addObject("dataAvailable", "Class Already Exist");
			}
										

		
		ArrayList<Class> classExistTemp=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.
		
		mv.addObject("classExist",classExistTemp);
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		mv.setViewName("addClass");													// returns to addClass View
		
		
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------ADD ARTICLE (ADMIN MODULE)-----------------------------------------------------------------*/
	
	// method to redirect addArticle View 
	
	@RequestMapping(value="/admin/addView/addArticle",method = RequestMethod.GET)
	public ModelAndView addArtcileGet(Principal principal,ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
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
		mv.addObject("viewActive","active");
		return mv;
	}
	
	
	// method to add Article entry into database
	
	@RequestMapping(value="/admin/addView/addArticle",method = RequestMethod.POST)
	public ModelAndView addArtcilePost(HttpServletRequest req,Principal principal,ModelAndView mv) {
		
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
			
			User localUser=userService.findByUsername(principal.getName());
			
			mv.addObject("LoggedUser",localUser);
			
			return mv;
			
		}
		
		try {
		
			Class localClass=classService.findByClassName(Integer.parseInt(className));									// fetching class tuple based on given data
			Subject localSubject=subjectService.findBysubName(subjectName);								// fetching Subject tuple based on given data
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);			// fetching Subject Class mapping tuple based on class and subject
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);					// fetching topic tuple based on given data subject class mapping.
			
			User usr=userService.findByUsername(principal.getName());						// logged in user details.
			Set<ArticleExternal> articlemapping=new HashSet<ArticleExternal>();
			articlemapping.add(new ArticleExternal(articleService.countRow()+1, "Article", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, url, 0,0,  ServiceUtility.getCurrentTime(), localTopic, usr));
			
			userService.addUserToArticle(usr, articlemapping);								// saving the article information into database.
			mv.addObject("statusAdd", "Added Successfully");									// setting status for view(success)
		} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please try Again Later");								// setting status for view(failure)
			
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

		
		mv.setViewName("addArticle");	// setting view name
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		return mv;
		
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------ADD DOCUMENT (ADMIN MODULE)-----------------------------------------------------------------*/
	
	// method to redirect addDocument View 
	
	@RequestMapping(value="/admin/addView/addDocument",method = RequestMethod.GET)
	public ModelAndView addDocumentGet(Principal principal,ModelAndView mv) {
	
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
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

		mv.addObject("viewActive","active");
		return mv;
		
	}
	
	
	// method to add Document into database
	@RequestMapping(value="/admin/addView/addDocument",method = RequestMethod.POST)
	public ModelAndView addDocumentPost(HttpServletRequest req,@RequestParam(name="Question")MultipartFile[] document,Principal principal,ModelAndView mv) {
		
		String path1=null;
		String className=req.getParameter("classSelected");						// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("description");
		String source=req.getParameter("source");
		
		if(!ServiceUtility.checkFileExtensionPDF(document) && !ServiceUtility.checkFileExtensionImage(document)) {                 	// validation against PDF document 
			mv.addObject("fileError", "File must be a PDF or JPEG/JPG/PNG");
			
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
			
			User localUser=userService.findByUsername(principal.getName());
			
			mv.addObject("LoggedUser",localUser);
			
			return mv;
			
		}
		
		if(document[0].getSize()>fileSize) {
			
			mv.addObject("fileError", "FileSize must be within 10MB");
			
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
			
			User localUser=userService.findByUsername(principal.getName());
			
			mv.addObject("LoggedUser",localUser);
			
			return mv;
		}
				
		try {
			
			Class localClass=classService.findByClassName(Integer.parseInt(className));									// retrieving class modal
			Subject localSubject=subjectService.findBysubName(subjectName);								// retrieving subject modal
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject); // retrieving subject class mapping from class and subject
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);			// retrieving topic from sucject class mapping
			
			String createFolder=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+localSubject.getSubId()+"/"+localTopic.getTopicId()+"/Document/";	// PATH TO SAVE DOCUMENT UNDER TOPIC
			
			path1=ServiceUtility.uploadFile(document, createFolder);									// 	upload file to path mentioned
			
			int indexToStart=path1.indexOf("Media");														// extracting path starting from MEDIA to save in database
			String path=path1.substring(indexToStart, path1.length());
			
			User usr=userService.findByUsername(principal.getName());						// retrieving the logged USer
			
			Set<DocumentExternal> documentMapping=new HashSet<DocumentExternal>();
			documentMapping.add(new DocumentExternal(documentService.countRow()+1, "Document", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, path, 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));


			userService.addUserToDocument(usr, documentMapping);					// saving document data into database.		
			mv.addObject("statusAdd", "Document Added Successfully");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again");
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
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		return mv;
		
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------ADD LESSON PLAN (ADMIN MODULE)-----------------------------------------------------------------*/
	
	// method to redirect addLessonPlan View 
	@RequestMapping(value="/admin/addView/addLessonPlan",method = RequestMethod.GET)
	public ModelAndView addLessonPlanGet(Principal principal,ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
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
		
		mv.addObject("viewActive","active");
		return mv;
		
	}
	
	// method to add Lesson Plan into database
	@RequestMapping(value="/admin/addView/addLessonPlan",method = RequestMethod.POST)
	public ModelAndView addLessonPlanPost(HttpServletRequest req,@RequestParam(name="lesson") MultipartFile[] lesson,Principal principal,ModelAndView mv) {
		
		String path1=null;
		String className=req.getParameter("classSelected");						// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		
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
			
			User localUser=userService.findByUsername(principal.getName());
			
			mv.addObject("LoggedUser",localUser);
			
			return mv;
			
		}
		
		if(lesson[0].getSize()>fileSize) {
			
			mv.addObject("fileError", "FileSize must be within 10MB");
			
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
			
			User localUser=userService.findByUsername(principal.getName());
			
			mv.addObject("LoggedUser",localUser);
			
			return mv;
		}
		
		try {
			
			Class localClass=classService.findByClassName(Integer.parseInt(className));						
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			
			String createFolder=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+localSubject.getSubId()+"/"+localTopic.getTopicId()+"/Lessonplan/";  // path to store lesson Plan
			
			path1=ServiceUtility.uploadFile(lesson, createFolder);					// uploading lesson plan to path given
			
			int indexToStart=path1.indexOf("Media");									// extract path starting from MEDIA to persist .
			String path=path1.substring(indexToStart, path1.length());
			
			
			User usr=userService.findByUsername(principal.getName());				// retrive logged in user
			
			Set<LessonPlan> lessonMapping=new HashSet<LessonPlan>();
			lessonMapping.add(new LessonPlan(lessonService.countRow()+1, "Lesson", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), path, 0, 0,ServiceUtility.getCurrentTime(), localTopic, usr));
			
			
			userService.addUserToLessonplan(usr, lessonMapping);					// saving lessonPaln data
			mv.addObject("statusAdd", "Added Successfully");
			
		} catch (Exception e) {
		
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again");
			
		}

		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);											// setting variable for view for displaying purpose
		
		mv.addObject("lessonStatus", "Added Successfully");	
		
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
			
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
	
		return mv;
		
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	/*------------------------------------------ADD PHET (ADMIN MODULE)-----------------------------------------------------------------*/
	
	// method to redirect addPhets View 
	@RequestMapping(value="/admin/addView/addPhets",method = RequestMethod.GET)
	public ModelAndView addPhetsGet(Principal principal,ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
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
		
		mv.addObject("viewActive","active");
		return mv;
	
	}
	
	// method to add Phets into database
	@RequestMapping(value="/admin/addView/addPhets",method = RequestMethod.POST)
	public ModelAndView addPhetsPost(HttpServletRequest req,Principal principal,ModelAndView mv) {
		

		String className=req.getParameter("classSelected");						// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("description");
		String phet=req.getParameter("phet");
		String source=req.getParameter("source");
		String phetPath=null;

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
				
				User localUser=userService.findByUsername(principal.getName());
				
				mv.addObject("LoggedUser",localUser);
				
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
			
			User localUser=userService.findByUsername(principal.getName());
			
			mv.addObject("LoggedUser",localUser);
			
			return mv;
			
			
		}
		
		try {

			Class localClass=classService.findByClassName(Integer.parseInt(className));
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			
			
			User usr=userService.findByUsername(principal.getName());				// retrive Logged In User
			
			
			Set<Phets> phetMapping=new HashSet<Phets>();
			phetMapping.add(new Phets(phetService.countRow()+1, "Phets", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, phetPath, 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));

			
			userService.addUserToPhets(usr, phetMapping);							// persist Phet data 
			mv.addObject("statusAdd", "Added Successfully");
			
			
			} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again");
			
			
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
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	
	/*------------------------------------------ADD TOPIC (ADMIN MODULE)-----------------------------------------------------------------*/
	
	// method to add Topic into database
	@Transactional
	@RequestMapping(value="/admin/addView/addTopic",method=RequestMethod.POST)
	public ModelAndView addTopicPost(@RequestParam("posterQ") MultipartFile[] poster,HttpServletRequest req,ModelAndView mv,Principal principal) throws Exception {
		
		String path1=null;
		String uploadDirectoryPoster=null;
		String className=req.getParameter("classSelected");							// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topic");
		String topicDesc=req.getParameter("descriptionQ");
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		User usr=userService.findByUsername(principal.getName());
		
		System.out.println(poster);
		Subject localSubjectTemp=subjectService.findBySubjectName(subjectName);
		
		
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
		
		if(poster[0].getSize()>fileSize) {
			
			mv.addObject("fileError", "FileSize must be within 10MB ");
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();
			
			mv.addObject("classExist", classExist);
			mv.setViewName("addTopic");
			List<Topic> topicList=topicService.findAll();
			mv.addObject("Topic", topicList);
			mv.addObject("addActive","active");
			return mv;
			
		}
		
		try {
			
			
			Class localClass=classService.findByClassName(Integer.parseInt(className));
			SubjectClassMapping localsubjectClassMapping=subjectClassService.findBysubAndstandard(localClass,localSubjectTemp);
			
			System.out.println(localsubjectClassMapping.getSubClassId());
			
			int newTopicId=topicService.countRow()+1;
			
			Topic addTopic=new Topic();
			addTopic.setTopicId(newTopicId);
			addTopic.setPoster("null");
			addTopic.setTopicName(topicName);
			addTopic.setDescription(topicDesc);
			addTopic.setDateAdded(ServiceUtility.getCurrentTime());
			addTopic.setDateModified(ServiceUtility.getCurrentTime());
			addTopic.setSubjectClassMapping(localsubjectClassMapping);
			addTopic.setStatus(1);
			addTopic.setUserId(usr);
			
			subjectClassService.createTopic(addTopic, localsubjectClassMapping);
			
			
			
			boolean b=ServiceUtility.createclassSubjectFolder(className, Integer.toString(localSubjectTemp.getSubId()),Integer.toString(newTopicId)) ;
			
			
			uploadDirectoryPoster=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+localSubjectTemp.getSubId()+"/"+newTopicId+"/"; // path to upload Quiz File
			System.out.println(uploadDirectoryPoster);
				
			path1=ServiceUtility.uploadFile(poster, uploadDirectoryPoster);
				
	
			int indexToStart=path1.indexOf("Media");						// extract path starting from MEDIA to persist .
			String path=path1.substring(indexToStart, path1.length());
			
			if(!topicService.updateTopicPoster(path, newTopicId)){
				throw new Exception();
			}
			
			
			mv.addObject("statusAdd", "Added Successfully");
			
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
	@RequestMapping(value="/admin/addView/addTopic",method=RequestMethod.GET)
	public ModelAndView addTopicGet(Principal principal,ModelAndView mv) {
	
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();	// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);									// setting variable for view for displaying purpose
		mv.setViewName("addTopic");												// setting view name
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic", topicList);
		mv.addObject("viewActive","active");
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	/*------------------------------------------ADD SUBJECT (ADMIN MODULE)-----------------------------------------------------------------*/
	
	
	// method to redirect addSubject View 
	@RequestMapping(value="/admin/addView/addSubject", method=RequestMethod.GET)
	public ModelAndView addSubjectGet(Principal principal,ModelAndView mv) {
	
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.
		
		mv.addObject("classExist",classExist);
		
		mv.setViewName("addSubject");										// setting view name
		
		List<Subject> subjectList=subjectService.findAll();
		mv.addObject("Subject", subjectList);
		
		mv.addObject("viewActive","active");
		return mv;
	}
	
	
	// method to add Subject into database
	@RequestMapping(value="/admin/addView/addSubject", method=RequestMethod.POST)
	public ModelAndView addSubjectPost(HttpServletRequest req,@RequestParam(name="SelectedSubject") String selectedSubject,Principal principal,ModelAndView mv) {
			
		int i=0;     
		boolean subjectExist=false;
		
		User localUser=userService.findByUsername(principal.getName());
		mv.addObject("LoggedUser",localUser);
		
		if(!ServiceUtility.checkContainNumeralInString(selectedSubject)) {
			mv.addObject("statusError", "Please Enter only Charcter value");
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.
			
			mv.addObject("classExist",classExist);										// setting variable for view for displaying purpose								
			mv.addObject("addActive","active");
			
			List<Subject> subjectList=subjectService.findAll();
			mv.addObject("Subject", subjectList);
			
			
			mv.setViewName("addSubject");												// setting view name
			return mv;
			
		}
		ArrayList<Subject> subject=(ArrayList<Subject>) subjectService.findAll();
		for(Subject temp:subject) {
			if(selectedSubject.equalsIgnoreCase(temp.getSubName())) {
				subjectExist=true;
			}
		}
		String[] selectedClasses=req.getParameterValues("SelectedClasses");
		if(!subjectExist) {
			
			User usr=userService.findByUsername(principal.getName());
			
			Subject addsubject=new Subject();
			addsubject.setSubId(subjectService.countRow()+1);
			addsubject.setSubName(selectedSubject);
			addsubject.setDateAdded(ServiceUtility.getCurrentTime());
			addsubject.setUser(usr);
			
			userService.adduserToSubject(usr, addsubject);
			
			
			try {
				Set<SubjectClassMapping> subjectClassMapping=new HashSet<SubjectClassMapping>();

				for(String classes:selectedClasses) {
					System.out.println(classes);
					Class localClass=classService.findByClassName(Integer.parseInt(classes));
					subjectClassMapping.add(new SubjectClassMapping(subjectClassService.countRow()+i++,localClass,addsubject));
				}


				subjectClassService.createSubjectClass(addsubject, subjectClassMapping);

				mv.addObject("statusAdd", "Added Successfully");
			} catch (Exception e) {
				
				subjectService.save(addsubject);
				mv.addObject("statusAdd", "Added Successfully");
			}
			
				
			
		}else {
	
			mv.addObject("statusError", "Subject Exist");
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
	@RequestMapping(value="/admin/addView/addVideo",method = RequestMethod.GET)
	public ModelAndView addVideoGet(Principal principal,ModelAndView mv) {
			
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
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
		
		mv.addObject("viewActive","active");
		return mv;
		
	}
	
	// method to add Video into database
	@RequestMapping(value="/admin/addView/addVideo",method = RequestMethod.POST)
	public ModelAndView addVideoGetPost(HttpServletRequest req,Principal principal,ModelAndView mv) {
		
		
		String className=req.getParameter("classSelected");						// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("description");
		String url=req.getParameter("url");
		String source=req.getParameter("source");
		String videourl=null;
	
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
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

		try {

			Class localClass=classService.findByClassName(Integer.parseInt(className));
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			
			User usr=userService.findByUsername(principal.getName());
			
			Set<VideoExternal> videoMapping=new HashSet<VideoExternal>();
			videoMapping.add(new VideoExternal(videoService.countRow()+1, "Video", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, videourl, 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));
			
			userService.addUserToVideo(usr, videoMapping);							// persist Video Information
			mv.addObject("statusAdd", "Added Successfully");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again Later");
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
	
	
	@RequestMapping(value="/admin/addView/addVideoUpload",method = RequestMethod.POST)
	public ModelAndView addVideoUploadPost(@RequestParam(name="videoUpload") MultipartFile video,HttpServletRequest req,Principal principal,ModelAndView mv) {
		
		
		String className=req.getParameter("classSelected");						// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("description");
		String source=req.getParameter("source");
		String videourl=null;
		String path1=null;
	
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		if(!ServiceUtility.checkFileExtensionVideo(video)) {
			
			mv.addObject("failure", "Please upload mp4 or mov file");
			
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
		
		
		if(video.getSize()>videoSize) {
			
			mv.addObject("failure", "Please upload file size within 50MB");
			
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
		
		try {
			
			int videoId=videoService.countRow()+1;
			

			Class localClass=classService.findByClassName(Integer.parseInt(className));
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			
			User usr=userService.findByUsername(principal.getName());
			
			String createFolder=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+localSubject.getSubId()+"/"+localTopic.getTopicId()+"/Video/"+videoId+"/";  // path to save video
			
			boolean ques=ServiceUtility.createFolder(createFolder);
			
			if(ques) {
			
			path1=ServiceUtility.uploadVideoFile(video, createFolder);
			
			int indexToStart=path1.indexOf("Media");										// extracting proper Path from Actual path
			String path=path1.substring(indexToStart, path1.length());
			
			
			Set<VideoExternal> videoMapping=new HashSet<VideoExternal>();
			videoMapping.add(new VideoExternal(videoId, "Video", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, path, 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));
			
			userService.addUserToVideo(usr, videoMapping);							// persist Video Information
			mv.addObject("statusAdd", "Added Successfully");
			}else {
				mv.addObject("failure", "Please Try Again Later");
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again Later");
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
	@RequestMapping(value="/admin/addView/addQuiz",method = RequestMethod.GET)
	public ModelAndView addQuizGet(Principal principal,ModelAndView mv) {
				
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
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

		mv.addObject("viewActive","active");
		return mv;
		
		
	}
	
	// method to add Quiz into database
	@RequestMapping(value="/admin/addView/addQuiz",method = RequestMethod.POST)
	public ModelAndView addQuizPost(HttpServletRequest req,@RequestParam(name="Question")MultipartFile[] question,Principal principal,@RequestParam(name="Answer")MultipartFile[] answer,ModelAndView mv) {
		
		String questionPath=null;
		String answerPath=null;
		String className=req.getParameter("classSelected");						// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String remarks=req.getParameter("remarks");
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		
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
		
		if(question[0].getSize()>fileSize || answer[0].getSize()>fileSize) {
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

			mv.addObject("classExist", classExist);

			mv.addObject("fileError", "FileSize must be within 10MB");
				
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
		try {
		
		Class localClass=classService.findByClassName(Integer.parseInt(className));
		Subject localSubject=subjectService.findBysubName(subjectName);
		SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
		Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
		
		
		String createFolder=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+localSubject.getSubId()+"/"+localTopic.getTopicId()+"/Quiz/"+remarks+"/";  // path to save question and answer
		boolean b=ServiceUtility.createFolder(createFolder);
		
		String CreateFolderQuestion=createFolder+"Question/";
		String CreateFolderAnswer=createFolder+"Answer/";
		
		boolean ques=ServiceUtility.createFolder(CreateFolderQuestion);
		boolean ans=ServiceUtility.createFolder(CreateFolderAnswer);
		
		
			if(ques && ans) {
			
			questionPath=ServiceUtility.uploadFile(question, CreateFolderQuestion);
			answerPath=ServiceUtility.uploadFile(answer, CreateFolderAnswer);
			
			
			int indexToStart=questionPath.indexOf("Media");
			String pathQuestion=questionPath.substring(indexToStart, questionPath.length());
			
			int indexToStart1=answerPath.indexOf("Media");
			String pathAnswer=answerPath.substring(indexToStart1, answerPath.length());
			
			User usr=userService.findByUsername(principal.getName());
			
			Set<QuizQuestion> quizMapping=new HashSet<QuizQuestion>();
			quizMapping.add(new QuizQuestion(quizService.countRow()+1,"Quiz",ServiceUtility.getCurrentTime(),ServiceUtility.getCurrentTime(),pathQuestion,pathAnswer,0,0,remarks,ServiceUtility.getCurrentTime(),localTopic,usr));
			
			userService.addUserToQuizQuestion(usr, quizMapping);   // persist Quiz details
			
			mv.addObject("statusAdd", "Added Successfully");
			
			}else {
				mv.addObject("failure", " Try again Later");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			
			mv.addObject("failure", " Try again Later");
			
		}
		
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);										// setting variable for view for displaying purpose
		
		mv.addObject("quizStatus", "Added Successfully");
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
	@RequestMapping(value = "/admin/addView/addConceptMap",method = RequestMethod.GET)
	public ModelAndView addConceptMapGet(Principal principal,ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
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

		return mv;
	}
	
	// method to add Concept -MAp into database
	@RequestMapping(value = "/admin/addView/addConceptMap",method = RequestMethod.POST)
	public ModelAndView addConceptMapPost(HttpServletRequest req,Principal principal,@RequestParam(name="conceptMapImage")MultipartFile[] conceptMapImage,ModelAndView mv) {
		
		String path1=null;
		String className=req.getParameter("classSelected");				// taking out various information given by user in view.
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("descriptionConceptMap");
		String remark=req.getParameter("headlineConceptMap");
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		if(!ServiceUtility.checkFileExtensionImage(conceptMapImage) && !ServiceUtility.checkFileExtensionHtml(conceptMapImage)) {    // check Image file
			mv.addObject("fileError", "File must be a Image File or Html Page");
			
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
		
		if(conceptMapImage[0].getSize()>fileSize) {
			
			mv.addObject("fileError", "FileSize must be within 10MB");
			
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
		
		
		try {
			
			Class localClass=classService.findByClassName(Integer.parseInt(className));
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);

			String createFolder=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+localSubject.getSubId()+"/"+localTopic.getTopicId()+"/ConceptMap/";  // path to store Concept-map
			
			path1=ServiceUtility.uploadFile(conceptMapImage, createFolder);
			
			int indexToStart=path1.indexOf("Media");										// extracting proper Path from Actual path
			String path=path1.substring(indexToStart, path1.length());
			

			User usr=userService.findByUsername(principal.getName());
			
			Set<ConceptMap> conceptMapping=new HashSet<ConceptMap>();
			conceptMapping.add(new ConceptMap(conceptService.countRow()+1, "ConceptMap", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), path, desc, 0,0, remark, localTopic, usr));
			
			userService.addUserToConceptMap(usr, conceptMapping);						// persist Concept-map
			mv.addObject("statusAdd", "Concept-Map Added Successfully");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again");
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
	
	@RequestMapping(value="/admin/addView/addTutorial",method = RequestMethod.GET)
	public ModelAndView addTutorialGet(Principal principal,ModelAndView mv) {
		
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
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
		return mv;
		
	}
	
	// method to add Tutorial into database
	@RequestMapping(value = "/admin/addView/addTutorial",method = RequestMethod.POST)
	public ModelAndView addTutorialPost(HttpServletRequest req,Principal principal,ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
			
			String className=req.getParameter("classSelected");					// taking out various information given by user in view.
			String subjectName=req.getParameter("subjectSelected");
			String topicName=req.getParameter("topicSelected");
			String [] tutorialList=req.getParameterValues("fossTutorialSelected");
			int fossid=Integer.parseInt(req.getParameter("fossSelected"));
			int stLanguageId=Integer.parseInt(req.getParameter("fossLanguageSelected"));
			
			User usr=userService.findByUsername(principal.getName());
			
			try {
				Class localClass=classService.findByClassName(Integer.parseInt(className));
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
			} catch (Exception e1) {
				
				e1.printStackTrace();
				
				ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

				mv.addObject("classExist", classExist);											// setting variable for view for displaying purpose
				
				mv.addObject("addActive","active");
				
				List<Tutorial> tempTutorialLocal=tutorialService.getAllTutorial();
				
				List<TutorialList> tutorialListData=new ArrayList<TutorialList>();
				
				
				for(Tutorial localTemp:tempTutorialLocal) {
					
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
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);											// setting variable for view for displaying purpose
		
		mv.addObject("addActive","active");
		
		List<Tutorial> tempTutorialLocal=tutorialService.getAllTutorial();
		
		List<TutorialList> tutorialListData=new ArrayList<TutorialList>();
		
		
		for(Tutorial localTemp:tempTutorialLocal) {
			
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
	@RequestMapping(value = "/admin/addView/addTestimonial",method = RequestMethod.GET)
	public ModelAndView addTestimonialGet(Principal principal,ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		mv.addObject("viewActive","active");
		List<Testimonial> local=testiService.findAll();
		mv.addObject("Testimonial", local);
		mv.setViewName("addTestimonial");							// setting view name

		
		return mv;
	
		
	}
	
	
	// method to add Testimonial into database
	@RequestMapping(value = "/admin/addView/addTestimonial",method = RequestMethod.POST)
	public ModelAndView addTestimonialPost(HttpServletRequest req,ModelAndView mv,Principal principal) {
		
							
		String name=req.getParameter("Name");						// taking out various information given by user in view.
		String organization=req.getParameter("org");
		String Desc=req.getParameter("description");

			
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
					
					try {
						Testimonial addtestData=new Testimonial();
						addtestData.setTestimonialId(testiService.getCount());
						addtestData.setDateAdded(ServiceUtility.getCurrentTime());
						addtestData.setName(name);
						addtestData.setDescription(Desc);
						addtestData.setOrganization(organization);
						
						testiService.save(addtestData);
						mv.addObject("returnStatus", "Data Added Successfully");
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
			
		
		return mv;

	}
	
	@RequestMapping(value = "/admin/addView/addVideoTestimonial",method = RequestMethod.POST)
	public ModelAndView addVideoTestimonialPost(@RequestParam(name="videoTesti") MultipartFile video ,HttpServletRequest req,ModelAndView mv,Principal principal) throws Exception {
		
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		if(!ServiceUtility.checkFileExtensionVideo(video)) {
			
			if(ServiceUtility.checkFileExtensiononeFilePDF(video)) {
				
				int testiId=testiService.getCount();
				Testimonial addtestData=new Testimonial();
				addtestData.setTestimonialId(testiId);
				addtestData.setDateAdded(ServiceUtility.getCurrentTime());
				addtestData.setName(req.getParameter("Name"));
				addtestData.setDescription(req.getParameter("description"));
				addtestData.setOrganization(req.getParameter("org"));
				
				testiService.save(addtestData);
				
				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+uploadTestimonial+"/"+testiId);
				
				String pathtoUploadTestData=env.getProperty("spring.applicationexternalPath.name")+uploadTestimonial+"/"+testiId;
				
				String documentLocal=ServiceUtility.uploadVideoFile(video, pathtoUploadTestData);
				
				int indexToStart=documentLocal.indexOf("Media");
				
				String document=documentLocal.substring(indexToStart, documentLocal.length());
				
				Testimonial localTest=testiService.getbyId(testiId);
				localTest.setFilePath(document);
				
				testiService.save(localTest);
				
				mv.addObject("returnStatus", "Testimonial added Successfully");
				mv.addObject("addVideoActive","active");
				List<Testimonial> local=testiService.findAll();
				mv.addObject("Testimonial", local);
				mv.setViewName("addTestimonial");
				return mv;
				
				
			}else {
			
			mv.addObject("returnStatus", "Video format not Supported (only MP4 and MOV)");
			mv.addObject("addVideoActive","active");
			List<Testimonial> local=testiService.findAll();
			mv.addObject("Testimonial", local);
			mv.setViewName("addTestimonial");
			return mv;
			}
			
		}
		
		if(video.getSize()>videoSize) {
			
			mv.addObject("returnStatus", "Video size must be less than 50MB");
			mv.addObject("addVideoActive","active");
			List<Testimonial> local=testiService.findAll();
			mv.addObject("Testimonial", local);
			mv.setViewName("addTestimonial");
			return mv;
		}
		
		String pathSampleVideo=ServiceUtility.uploadVideoFile(video, env.getProperty("spring.applicationexternalPath.name")+uploadTestimonial);
		
		IContainer container = IContainer.make();
		int result=10;
		result = container.open(pathSampleVideo,IContainer.Type.READ,null);
		
		if(result<0) {
			
			mv.addObject("returnStatus", "Please Try again");
			mv.addObject("addVideoActive","active");
			List<Testimonial> local=testiService.findAll();
			mv.addObject("Testimonial", local);
			mv.setViewName("addTestimonial");
			return mv;
			
		}else {
			
			if(container.getDuration()>videoDuration) {
				
				mv.addObject("returnStatus", "duration of file must be 5 minutes or less");
				mv.addObject("addVideoActive","active");
				List<Testimonial> local=testiService.findAll();
				mv.addObject("Testimonial", local);
				mv.setViewName("addTestimonial");
				return mv;
			}else {
				
				Path deletePreviousPath=Paths.get(pathSampleVideo);
				Files.delete(deletePreviousPath);
				
				int testiId=testiService.getCount();
				Testimonial addtestData=new Testimonial();
				addtestData.setTestimonialId(testiId);
				addtestData.setDateAdded(ServiceUtility.getCurrentTime());
				addtestData.setName(req.getParameter("Name"));
				addtestData.setDescription(req.getParameter("description"));
				addtestData.setOrganization(req.getParameter("org"));
				
				testiService.save(addtestData);
				
				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+uploadTestimonial+"/"+testiId);
				
				String pathtoUploadTestData=env.getProperty("spring.applicationexternalPath.name")+uploadTestimonial+"/"+testiId;
				
				String documentLocal=ServiceUtility.uploadVideoFile(video, pathtoUploadTestData);
				
				int indexToStart=documentLocal.indexOf("Media");
				
				String document=documentLocal.substring(indexToStart, documentLocal.length());
				
				Testimonial localTest=testiService.getbyId(testiId);
				localTest.setVideoPath(document);
				
				testiService.save(localTest);
				
				mv.addObject("returnStatus", "Testimonial added Successfully");
				mv.addObject("addVideoActive","active");
				List<Testimonial> local=testiService.findAll();
				mv.addObject("Testimonial", local);
				mv.setViewName("addTestimonial");
				return mv;
				
			}
			
		}
		
	/*	long duration = container.getDuration();
		long fileSize = container.getFileSize();
		
		System.out.println(video.getOriginalFilename());
		System.out.println(result);
		System.out.println(duration);
		System.out.println(fileSize);
		System.out.println(video.getSize());*/
		
		/*
		 * String om = "/home/om/Desktop/VID_20191227_153237.mp4"; IContainer container
		 * = IContainer.make(); int result = container.open(om, IContainer.Type.READ,
		 * null); long duration = container.getDuration(); long fileSize =
		 * container.getFileSize(); System.out.println(result);
		 * System.out.println(duration); System.out.println(fileSize);
		 */
		
		/*
		 * String name=req.getParameter("Name"); // taking out various information given
		 * by user in view. String organization=req.getParameter("org"); String
		 * Desc=req.getParameter("description");
		 * 
		 * 
		 * User localUser=userService.findByUsername(principal.getName());
		 * 
		 * mv.addObject("LoggedUser",localUser);
		 * 
		 * try { Testimonial addtestData=new Testimonial();
		 * addtestData.setTestimonialId(testiService.getCount());
		 * addtestData.setDateAdded(ServiceUtility.getCurrentTime());
		 * addtestData.setName(name); addtestData.setDescription(Desc);
		 * addtestData.setOrganization(organization);
		 * 
		 * testiService.save(addtestData); mv.addObject("returnStatus",
		 * "Data Added Successfully"); mv.addObject("addVideoActive","active");
		 * List<Testimonial> local=testiService.findAll(); mv.addObject("Testimonial",
		 * local); mv.setViewName("addTestimonial"); // setting view name } catch
		 * (Exception e) {
		 * 
		 * mv.addObject("returnStatus", "Please Try Again");
		 * mv.addObject("addVideoActive","active"); List<Testimonial>
		 * local=testiService.findAll(); mv.addObject("Testimonial", local);
		 * mv.setViewName("addTestimonial"); e.printStackTrace(); }
		 */
			

	}
	
	
	
	// method to redirect addEvent View 
	@RequestMapping(value = "/admin/addView/addEvent",method = RequestMethod.GET)
	public ModelAndView addEventGet(Principal principal,ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		List<Events> local=eventService.findAll();
		mv.addObject("Events", local);
		mv.addObject("viewActive","active");
		mv.setViewName("addEvent");								// setting view name
		
		return mv;

	}
	
	// method to add event into database
	@RequestMapping(value = "/admin/addView/addEvent",method = RequestMethod.POST)
	public ModelAndView addEventPost(@RequestParam("poster") MultipartFile[] poster,HttpServletRequest req,ModelAndView mv,Principal principal) {
		
		String headline=req.getParameter("headline");									// taking out various information given by user in view.
		String Desc=req.getParameter("description");
		String location=req.getParameter("location");
		String mode=req.getParameter("modeEvent");
		String modeOfEvent;
		String coordName=req.getParameter("cordinatorName");
		String coordMobile=req.getParameter("cordinatorMobile");
		
		
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		if(!ServiceUtility.checkFileExtensionImage(poster)) {						// validating Image file of given data
				
			mv.addObject("returnStatus", "Please upload only Images");
			
			List<Events> local=eventService.findAll();
			mv.addObject("Events", local);
			mv.addObject("addActive","active");
			mv.setViewName("addEvent");
			return mv;
				
			}
		
		if(poster[0].getSize()>fileSize) {
			
			mv.addObject("returnStatus", "FileSize must be within 10MB");
			
			List<Events> local=eventService.findAll();
			mv.addObject("Events", local);
			mv.addObject("addActive","active");
			mv.setViewName("addEvent");
			return mv;
			
		}
		
		if(mode.equals("1")) {
			modeOfEvent="Remote";
		}else if(mode.equals("2")){
			modeOfEvent="Virtual";
		}else {
			
			mv.addObject("returnStatus", "Please Try Again");
			
			List<Events> local=eventService.findAll();
			mv.addObject("Events", local);
			mv.addObject("addActive","active");
			mv.setViewName("addEvent");
			return mv;
		}
			
					try {
						String date=req.getParameter("startDate");
						SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd");
						java.util.Date dateUtil=sd1.parse(date);
						Date dateOfEventStart=new Date(dateUtil.getTime());
						
						String datetemp1=req.getParameter("endDate");
						dateUtil=sd1.parse(datetemp1);
						Date dateOfEventend=new Date(dateUtil.getTime());
						
						String datetemp2=req.getParameter("RegStartDate");
						dateUtil=sd1.parse(datetemp2);
						Date registStart=new Date(dateUtil.getTime());
						
						
						String datetemp3=req.getParameter("RegEndDate");
						dateUtil=sd1.parse(datetemp3);
						Date registEnd=new Date(dateUtil.getTime());
						
						
						if(dateOfEventStart.before(ServiceUtility.getCurrentTime())) {
							mv.addObject("returnStatus", "Date of Event Must be Future Date");
							
							List<Events> local=eventService.findAll();
							mv.addObject("Events", local);
							mv.addObject("addActive","active");
							mv.setViewName("addEvent");
							return mv;
							
						} 
						
						if(dateOfEventend.before(dateOfEventStart)) {
							mv.addObject("returnStatus", "End Date of Event Must be After Start Date");
							
							List<Events> local=eventService.findAll();
							mv.addObject("Events", local);
							mv.addObject("addActive","active");
							mv.setViewName("addEvent");
							return mv;
							
						} 
						
						if(registStart.before(ServiceUtility.getCurrentTime()) ) {
							mv.addObject("returnStatus", "Regsitration Date of Event Must be Future Date");
							
							List<Events> local=eventService.findAll();
							mv.addObject("Events", local);
							mv.addObject("addActive","active");
							mv.setViewName("addEvent");
							return mv;
							
						} 
						
						if(registStart.after(dateOfEventStart) || registEnd.after(dateOfEventStart)  ) {
							mv.addObject("returnStatus", "Regsitration Date of Event Must be Earlier Date");
							
							List<Events> local=eventService.findAll();
							mv.addObject("Events", local);
							mv.addObject("addActive","active");
							mv.setViewName("addEvent");
							return mv;
							
						}
						
						if(registEnd.before(registStart) ) {
							mv.addObject("returnStatus", "Regsitration End Date of Event Must be Future Date");
							
							List<Events> local=eventService.findAll();
							mv.addObject("Events", local);
							mv.addObject("addActive","active");
							mv.setViewName("addEvent");
							return mv;
							
						}
						
						int eventId=eventService.getCount();
						Events addEvent=new Events();
						addEvent.setEventId(eventId);
						addEvent.setDateAdded(ServiceUtility.getCurrentTime());
						addEvent.setDescription(Desc);
						addEvent.setHeadline(headline);
						addEvent.setDateToHappenStart(dateOfEventStart);
						addEvent.setDateToHappenStart(dateOfEventend);
						addEvent.setRegistStart(registStart);
						addEvent.setRegistEnd(registEnd);
						addEvent.setCoordName(coordName);
						addEvent.setCoordContactNo(Integer.parseInt(coordMobile));
						addEvent.setLocation(location);
						addEvent.setMode(modeOfEvent);
						addEvent.setPotser_path("null");
						
						eventService.save(addEvent);
						
						boolean path_creation=ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+uploadEvent+"/"+eventId);
						
						
						String pathtoUploadEventData=env.getProperty("spring.applicationexternalPath.name")+uploadEvent+"/"+eventId;
						
						String documentLocal=ServiceUtility.uploadFile(poster, pathtoUploadEventData);
						
						int indexToStart=documentLocal.indexOf("Media");
						
						String document=documentLocal.substring(indexToStart, documentLocal.length());
						
						Events localEvent=eventService.getbyid(eventId);
						localEvent.setPotser_path(document);
						
						eventService.save(localEvent);
						
						List<Events> local=eventService.findAll();
						mv.addObject("Events", local);
						mv.addObject("addActive","active");
						
						mv.addObject("returnStatus", "Data Added Successfully");
						mv.setViewName("addEvent");											// setting view name
					} catch (Exception e) {
						
						mv.addObject("returnStatus", "Please Try Again");
						
						List<Events> local=eventService.findAll();
						mv.addObject("Events", local);
						mv.addObject("addActive","active");
						mv.setViewName("addEvent");											// setting view name
						e.printStackTrace();
					}
			
		
		return mv;

	}
	
	
	//method to redirect page to Message of User
	@RequestMapping(value = "/admin/approve/contactInformation",method = RequestMethod.GET)
	public ModelAndView getUserMessageget(Principal principal,ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		List<ContactForm> tempContact=contactService.getAllMessages();
		mv.addObject("contactMessages", tempContact);
		mv.setViewName("message");	
			
		
		return mv;
		
	}
	

}

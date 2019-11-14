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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
import com.adminportal.content.VideoExternal;
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
import com.adminportal.service.UserService;
import com.adminportal.service.VideoExternalService;
import com.spoken.Utility.ServiceUtility;

@Controller
@SessionAttributes({"UserLogedAdmin","UserNameAdmin","UserLogedUserView","UserNameUserSide"})
public class AdminController {
	
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
	private ConceptMapService conceptService;
	
	@Autowired
	private TestimonialService testiService;
	
	@Autowired
	private EventService eventService;
	
	
	
	
	/************************* HomePage *******************************************************/
	
	@RequestMapping(value = "/home",method = RequestMethod.GET)
	public ModelAndView adminHomeGet(HttpServletRequest req,ModelAndView mv) {
		HttpSession session=req.getSession(false);
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			
			mv.setViewName("redirect:/adminPortal");
		}else {
						
			mv.setViewName("home");
			
		}
		return mv;
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
	
	/************** admin Portal from User Side Entry *****************************************/
	
	@RequestMapping(value = "/adminPathUi", method = RequestMethod.GET)
	public ModelAndView adminPortalFromUI(HttpServletRequest req,ModelAndView mv) {
		
		mv.setViewName("redirect:/home");
		
		return mv;
	}
	
	
/*------------------------------------------ADD CLASS (ADMIN MODULE)-----------------------------------------------------------------*/
	
	
	@RequestMapping(value="/addClass", method = RequestMethod.GET)
	public ModelAndView addClassget(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
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
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.addObject("sessionTimeOut", "Session TimeOut");
			
		}else {
			Class tempClass=new Class();
			tempClass.setClassName(classSelected);
			classService.save(tempClass);
			mv.addObject("status", "Added Successfully");
		}
			
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
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
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
		
		if(!url.startsWith("http")) {
			
			mv.addObject("fileError","Path specified isn't correct");
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

			mv.addObject("classExist", classExist);
		
			mv.setViewName("addArticle");
			
			return mv;
			
		}
	
		
		String emailToIdentifyUser;
		
		HttpSession session=req.getSession(false);
		
		if(ServiceUtility.chechExistSessionAdmin(session)) {
		
		try {
			emailToIdentifyUser=(String) session.getAttribute("UserLogedAdmin");
			
			Class localClass=classService.findByClassName(className);
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			
			
			
			User usr=userService.findByUsername(emailToIdentifyUser);
			Set<ArticleExternal> articlemapping=new HashSet<ArticleExternal>();
			articlemapping.add(new ArticleExternal(articleService.countRow()+1, "Article", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, url, 0,  ServiceUtility.getCurrentTime(), localTopic, usr));
			


			userService.addUserToArticle(usr, articlemapping);
			mv.addObject("status", "Added Sucessfully");
		} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please try Again Later");
			
		}
		}else {
			
			mv.addObject("sessionTimeOut", "Session TimeOut");
			
		}
		
		
		
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
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
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
		
		if(!ServiceUtility.checkFileExtensionPDF(document)) {
			mv.addObject("fileError", "File must be a PDF File");
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

			mv.addObject("classExist", classExist);
			
			mv.setViewName("addDocument");
			
			return mv;
			
		}
		
		HttpSession session=req.getSession(false);
		if(ServiceUtility.chechExistSessionAdmin(session)) {
		
		try {
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
			mv.addObject("status", "Document Added Successfully");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again");
		}
		}else {
			
			mv.addObject("sessionTimeOut", "Session TimeOut");
			
		}
		
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
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
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
		
		if(!ServiceUtility.checkFileExtensionPDF(lesson)) {
			
			mv.addObject("fileError", "Please Select PDF file");
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

			mv.addObject("classExist", classExist);
			
			mv.setViewName("addLessonPlan");
			return mv;
			
		}
		
		HttpSession session=req.getSession(false);
		if(ServiceUtility.chechExistSessionAdmin(session)) {
		
		
		try {
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
			mv.addObject("status", "Added Sucessfully");
			
		} catch (Exception e) {
		
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again");
			
		}
		}else {
			mv.addObject("sessionTimeOut", "Session TimeOut");
			
		}
		
		
		
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
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
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
				
				e.printStackTrace();
				ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

				mv.addObject("classExist", classExist);
				
				mv.addObject("fileError", "Please specify Embed Code");
				
				mv.setViewName("addPhets");
				
				return mv;
				
				
			}
			
		}else {
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

			mv.addObject("classExist", classExist);
			
			mv.addObject("failure", "Please Try Again");
			
			mv.setViewName("addPhets");
			
			return mv;
			
			
		}
			
			
			if(ServiceUtility.chechExistSessionAdmin(session)) {
		
				try {
			
			emailToIdentifyUser=(String) session.getAttribute("UserLogedAdmin");

			Class localClass=classService.findByClassName(className);
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			
			
			User usr=userService.findByUsername(emailToIdentifyUser);
			
			
			Set<Phets> phetMapping=new HashSet<Phets>();
			phetMapping.add(new Phets(phetService.countRow()+1, "Phets", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, phetPath, 0, ServiceUtility.getCurrentTime(), localTopic, usr));

			
			userService.addUserToPhets(usr, phetMapping);
			mv.addObject("status", "Added Sucessfully");
			
			
				} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again");
			
			
				}
				
			}else {
				
				mv.addObject("sessionTimeOut", "Session TimeOut");
			}
		
		
		
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		
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
		
		
		if(!ServiceUtility.checkFileExtensionImage(poster)) {
			
			mv.addObject("fileError", "Invalid File Extension");
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();
			
			mv.addObject("classExist", classExist);
			mv.setViewName("addTopic");
			return mv;
			
			
		}
		
		try {
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
			addTopic.setStatus(1);
			
			subjectClassService.createTopic(addTopic, localsubjectClassMapping);
			mv.addObject("status", "Added Sucessfully");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			mv.addObject("failure", "Please Try Again");
		}
		
		
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();
		
		
		mv.addObject("classExist", classExist);
		mv.setViewName("addTopic");
		return mv;
	}
	
	
	@RequestMapping(value="/addTopic",method=RequestMethod.GET)
	public ModelAndView addTopicGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
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
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
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
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
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
				mv.addObject("fileError","File path is not proper");
				
				ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

				mv.addObject("classExist", classExist);
				
				mv.setViewName("addVideo");
				
				return mv;
				
				
				
			}
			
		}else {
			
		}
		
		String emailToIdentifyUser;
		
		
		HttpSession session=req.getSession(false);
		
		if(ServiceUtility.chechExistSessionAdmin(session)) {
	
		try {
			emailToIdentifyUser=(String) session.getAttribute("UserLogedAdmin");
			
			

			Class localClass=classService.findByClassName(className);
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			
			
			
			User usr=userService.findByUsername(emailToIdentifyUser);
			
			Set<VideoExternal> videoMapping=new HashSet<VideoExternal>();
			videoMapping.add(new VideoExternal(videoService.countRow()+1, "Video", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, videourl, 0, ServiceUtility.getCurrentTime(), localTopic, usr));
			

			
			userService.addUserToVideo(usr, videoMapping);
			mv.addObject("Status", "Added Sucessfully");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again Later");
		}
		}else {
			
			mv.addObject("sessionTimeOut", "Session TimeOut");
		}
		
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		
		mv.setViewName("addVideo");
		
		
		
		return mv;
		
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------ADD QUIZ (ADMIN MODULE)-----------------------------------------------------------------*/
	
	@RequestMapping(value="/addQuiz",method = RequestMethod.GET)
	public ModelAndView addQuizGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
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
		
		
		
		if(!ServiceUtility.checkFileExtensionPDF(question)) {
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

			mv.addObject("classExist", classExist);

			mv.addObject("fileError", "Please Add only Pdf File");
				
			mv.setViewName("addQuiz");
			
			return mv;
			
		
			
		}
		
		if(!ServiceUtility.checkFileExtensionPDF(answer)) {
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

			mv.addObject("classExist", classExist);

			mv.addObject("fileError", "Please Add only Pdf File");
				
			mv.setViewName("addQuiz");
			
			return mv;
			
			
		}
		
		
		
		
		String createFolder=uploadDirectory+className+"_"+subjectName+"/"+topicName+"/Quiz/"+remarks+"/";
		boolean b=ServiceUtility.createFolder(createFolder);
		
		String CreateFolderQuestion=createFolder+"Question/";
		String CreateFolderAnswer=createFolder+"Answer/";
		
		boolean ques=ServiceUtility.createFolder(CreateFolderQuestion);
		boolean ans=ServiceUtility.createFolder(CreateFolderAnswer);
		
		if(ServiceUtility.chechExistSessionAdmin(session)) {
		
		try {
			if(ques && ans) {
			
			questionPath=ServiceUtility.uploadFile(question, CreateFolderQuestion);
			answerPath=ServiceUtility.uploadFile(answer, CreateFolderAnswer);
			
			
			int indexToStart=questionPath.indexOf('M');
			String pathQuestion=questionPath.substring(indexToStart, questionPath.length());
			
			int indexToStart1=answerPath.indexOf('M');
			String pathAnswer=answerPath.substring(indexToStart1, answerPath.length());
			
			emailToIdentifyUser=(String) session.getAttribute("UserLogedAdmin");
			
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
				mv.addObject("failure", " Try again Later");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			
			mv.addObject("failure", " Try again Later");
			
		}
		}else {
			
			mv.addObject("sessionTimeOut", "Session TimeOut");
			
		}
		
		
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		
		mv.addObject("quizStatus", "Added Sucessfully");
		
		mv.setViewName("addQuiz");
		
	
		return mv;
		
		
	}
	
	
	@RequestMapping(value = "/addConceptMap",method = RequestMethod.GET)
	public ModelAndView addConceptMapGet(HttpServletRequest req,ModelAndView mv) {
		HttpSession session=req.getSession();
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		mv.setViewName("addConcpetMap");
		}
		return mv;
	}
	
	@RequestMapping(value = "/addConceptMap",method = RequestMethod.POST)
	public ModelAndView addConceptMapPost(HttpServletRequest req,@RequestParam(name="conceptMapImage")MultipartFile[] conceptMapImage,ModelAndView mv) {
		
		String path1=null;
		String className=req.getParameter("classSelected");
		String subjectName=req.getParameter("subjectSelected");
		String topicName=req.getParameter("topicSelected");
		String desc=req.getParameter("descriptionConceptMap");
		String remark=req.getParameter("headlineConceptMap");
		
		String emailToIdentifyUser;
		
		if(!ServiceUtility.checkFileExtensionImage(conceptMapImage)) {
			mv.addObject("fileError", "File must be a Image File");
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

			mv.addObject("classExist", classExist);
			
			mv.setViewName("addConcpetMap");
			
			return mv;
			
		}
		
		HttpSession session=req.getSession(false);
		if(ServiceUtility.chechExistSessionAdmin(session)) {
		
		try {
			emailToIdentifyUser=(String) session.getAttribute("UserLogedAdmin");

			String createFolder=uploadDirectory+className+"_"+subjectName+"/"+topicName+"/ConceptMap/";
			
			path1=ServiceUtility.uploadFile(conceptMapImage, createFolder);
			
			int indexToStart=path1.indexOf('M');
			String path=path1.substring(indexToStart, path1.length());
			

			Class localClass=classService.findByClassName(className);
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			

			
			User usr=userService.findByUsername(emailToIdentifyUser);
			
			Set<ConceptMap> conceptMapping=new HashSet<ConceptMap>();
			conceptMapping.add(new ConceptMap(conceptService.countRow()+10, "ConceptMap", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), path, desc, 0, remark, localTopic, usr));
			
			

			userService.addUserToConceptMap(usr, conceptMapping);
			mv.addObject("status", "Concept-Map Added Successfully");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			mv.addObject("failure", "Please Try Again");
		}
		}else {
			
			mv.addObject("sessionTimeOut", "Session TimeOut");
			
		}
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		
		mv.setViewName("addConcpetMap");
		
		
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	/**************************************** ADDING TUTORIAL  ********************************************************************************/
	
	@RequestMapping(value="/addTutorial",method = RequestMethod.GET)
	public ModelAndView addTutorialGet(HttpServletRequest req,ModelAndView mv) {
		
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/adminPortal");
		}else {
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		mv.setViewName("addTutorial");
		}
		return mv;
		
	}
	
	
	
	
	/*******************************************************************************************************************************************/

	@RequestMapping(value = "/addTestimonial",method = RequestMethod.GET)
	public ModelAndView addTestimonialGet(HttpServletRequest req,ModelAndView mv) {
		HttpSession session=req.getSession(false);
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/adminPortal");
		}else {
				mv.setViewName("addTestimonial");
				
		}
		
		return mv;
	
		
	}
	
	@RequestMapping(value = "/addTestimonial",method = RequestMethod.POST)
	public ModelAndView addTestimonialPost(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		String name=req.getParameter("Name");
		String organization=req.getParameter("org");
		String Desc=req.getParameter("description");
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
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
			
		
		return mv;

	}
	
	
	@RequestMapping(value = "/addEvent",method = RequestMethod.GET)
	public ModelAndView addEventGet(HttpServletRequest req,ModelAndView mv) {
		
		
		HttpSession session=req.getSession(false);
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/adminPortal");
		}else {
			
				mv.setViewName("addEvent");
		}
		
		return mv;

	}
	
	@RequestMapping(value = "/addEvent",method = RequestMethod.POST)
	public ModelAndView addEventPost(HttpServletRequest req,ModelAndView mv) {
		
		String headline=req.getParameter("headline");
		String Desc=req.getParameter("description");
		
	
		
		HttpSession session=req.getSession(false);
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
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
			
		
		return mv;

	}
	

}

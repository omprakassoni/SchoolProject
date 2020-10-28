package com.adminportal;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
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

/**
 * This Controller makes changes in resource attributes done by Admin role based USer
 * @author om prakash
 * 
 */
@Controller
public class AdminViewController {
	
	/**
	 * Path to store resource
	 */
	public static final String uploadDirectory="Media/content/";
	
	/**
	 * path to store Teacher's role user data
	 */
	public static final String uploadTeacherDirectory="Media/Teacher/";

	
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
	private ConceptMapService conceptService;
	
	@Autowired
	private TutorialService tutorialService;
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private SubjectClassService subjectClassService;
	
/*------------------------------------------SHOW USER_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	
	/**
	 * redirect admin user to view all the user list
	 * @param principal principal Object
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/userList",method = RequestMethod.GET)
	public ModelAndView userListGet(Principal principal,ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		List<User> usr= userService.findAll();
	
		mv.addObject("User", usr);
		mv.setViewName("userList");

		return mv;
	}
	
	
	/**
	 * Enable/Disable User By Admin User
	 * @param principal principal Object
	 * @param userId User ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/deleteUser",method = RequestMethod.POST)
	public ModelAndView userListPost(Principal principal,@RequestParam(name="radiocall") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		String enableDisable;

		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		User usrTemp=userService.findById(id);
		if(usrTemp.getRegistered()==1) {
		 status=userService.disableEnableUser(0, id);
		 enableDisable="Disabled";
		}else {
		  status=userService.disableEnableUser(1, id);
		  enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "User "+enableDisable+ " Successfully");
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
	
	/**
	 * Enable/Disable Subject By Admin User
	 * @param principal principal Object
	 * @param subjectId subject ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value="/admin/addView/disableSubject",method = RequestMethod.POST)
	public ModelAndView subjectListPost(Principal principal,@RequestParam(name="radioSubject") String subjectId,ModelAndView mv) {
		
		int id=Integer.parseInt(subjectId);
		System.out.println(id);
		Subject subTemp=null;
		List<SubjectClassMapping> subjectClassTemp=null;
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		try {
			subTemp=subjectService.findById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mv.addObject("status", "Please Try Again");
			Map<Subject, List<Integer>>	SubjectEntry=new HashMap<Subject, List<Integer>>();
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.
			
			mv.addObject("classExist",classExist);
			
			mv.setViewName("addSubject");										// setting view name
			
			List<Subject> subjectList=subjectService.findAll();
			for(Subject temp:subjectList) {
				List<Integer> classWithSubject=new ArrayList<>();
				for(SubjectClassMapping temp1:temp.getSubClasMapp()) {
					classWithSubject.add(temp1.getStandard().getClassName());
				}
				Collections.sort(classWithSubject);
				
				SubjectEntry.put(temp, classWithSubject);
			}
			
			TreeMap<Subject, List<Integer>> sortedSubjectEntry = new TreeMap<>();
			sortedSubjectEntry.putAll(SubjectEntry);
			
			mv.addObject("Subject", sortedSubjectEntry);
			
			mv.addObject("viewActive","active");
			return mv;
		}
		
		if(subTemp.isStatus()) {
			subTemp.setStatus(false);
			subjectService.save(subTemp);
			
			try {
				subjectClassTemp=subjectClassService.getClassFromSubject(subTemp);
				subjectClassService.updateSubjectinAllField(false, subTemp);
				if(!subjectClassTemp.isEmpty()) {
					topicService.disableEnableAllByClassStandard(0, subjectClassTemp);
				}
				mv.addObject("status", "Subject Disabled Successfully");
			} catch (Exception e) {
				
				subjectClassService.updateSubjectinAllField(true, subTemp);
				if(!subjectClassTemp.isEmpty()) {
					topicService.disableEnableAllByClassStandard(1, subjectClassTemp);
				}
				subTemp.setStatus(true);
				subjectService.save(subTemp);
				mv.addObject("status", "Please Try Again");
				e.printStackTrace();
				
			}
			
			
		}else {
			subTemp.setStatus(true);
			subjectService.save(subTemp);
			try {
				subjectClassTemp=subjectClassService.getClassFromSubject(subTemp);
				subjectClassService.updateSubjectinAllField(true, subTemp);
				if(!subjectClassTemp.isEmpty()) {
					topicService.disableEnableAllByClassStandard(1, subjectClassTemp);
				}
				mv.addObject("status", "Subject Enabled Successfully");
			} catch (Exception e) {
				
				subjectClassService.updateSubjectinAllField(false, subTemp);
				if(!subjectClassTemp.isEmpty()) {
					topicService.disableEnableAllByClassStandard(0, subjectClassTemp);
				}
				subTemp.setStatus(false);
				subjectService.save(subTemp);
				mv.addObject("status", "Please Try Again");
				e.printStackTrace();
				
			}
			
		}
		
		Map<Subject, List<Integer>>	SubjectEntry=new HashMap<Subject, List<Integer>>();
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.
		
		mv.addObject("classExist",classExist);
		
		mv.setViewName("addSubject");										// setting view name
		
		List<Subject> subjectList=subjectService.findAll();
		for(Subject temp:subjectList) {
			List<Integer> classWithSubject=new ArrayList<>();
			for(SubjectClassMapping temp1:temp.getSubClasMapp()) {
				classWithSubject.add(temp1.getStandard().getClassName());
			}
			Collections.sort(classWithSubject);
			
			SubjectEntry.put(temp, classWithSubject);
		}
		
		TreeMap<Subject, List<Integer>> sortedSubjectEntry = new TreeMap<>();
		sortedSubjectEntry.putAll(SubjectEntry);
		
		mv.addObject("Subject", sortedSubjectEntry);
		
		mv.addObject("viewActive","active");
		return mv;
		
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	/*------------------------------------------SHOW TOPIC_LIST (ADMIN MODULE)-----------------------------------------------------------------*/	
	
	/**
	 * Enable/Disable Topic Resource By Admin User
	 * @param principal principal Object
	 * @param topicId topic ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value="/admin/addView/deleteTopic",method = RequestMethod.POST)
	public ModelAndView topicListPost(Principal principal,@RequestParam(name="radioTopic") String topicId,ModelAndView mv) {
		
		int id=Integer.parseInt(topicId);
		boolean status;
		String enableDisable;
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		Topic topicTemp=topicService.findById(id);
		if(topicTemp.getStatus()==1) {
		 status=topicService.disableEnableTopicById(0, id);
		 enableDisable="Disabled";
		}else {
		  status=topicService.disableEnableTopicById(1, id);
		  enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Topic "+enableDisable+ " Successfully");
		}else {
			mv.addObject("status", "Please try Again");
		}
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();	// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);	
		
		List<Topic> topicList=topicService.findAll();

		mv.addObject("Topic", topicList);
		mv.addObject("viewActive","active");
		mv.setViewName("addTopic");
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	/*------------------------------------------SHOW VIDEO_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	
	
	/**
	 * Enable/Disable Video Resource By Admin User
	 * @param principal principal Object
	 * @param videoId Video ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value="/admin/addView/deleteVideo",method = RequestMethod.POST)
	public ModelAndView videoListPost(Principal principal,@RequestParam(name="radioVideo") String videoId,ModelAndView mv) {
		
		int id=Integer.parseInt(videoId);
		boolean status;
		String enableDisable;
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		VideoExternal videoTemp=videoService.findById(id);
		if(videoTemp.isStatus()==1) {
		 status=videoService.EnableVideoContent(0, id);
		 enableDisable="Disabled";
		 
		}else {
			status=videoService.EnableVideoContent(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Video "+enableDisable+ " Successfully");
		}else {
			mv.addObject("status", "Please try Again");
		}
		

		
		List<VideoExternal> videoListtemp=videoService.findAll();
		List<VideoExternal> videoList=new ArrayList<VideoExternal>();
		for(VideoExternal temp:videoListtemp) {
			if(temp.getAcceptedByAdmin()==1) {
				videoList.add(temp);
			}
		}
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();	// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);	
	
		
		mv.addObject("Video",videoList);
		mv.addObject("viewActive","active");
		
		mv.setViewName("addVideo");
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------SHOW ARTICLE_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	
	/**
	 * Enable/Disable article Resource By Admin User
	 * @param principal principal Object
	 * @param articleId article ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value="/admin/addView/deleteArticle",method = RequestMethod.POST)
	public ModelAndView articleListPost(Principal principal,@RequestParam(name="radioArticle") String articleId,ModelAndView mv) {
		
		int id=Integer.parseInt(articleId);
		boolean status;
		String enableDisable;
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		ArticleExternal articleTemp=articleService.findByid(id);
		if(articleTemp.isStatus()==1) {
		 status=articleService.EnableArticleContent(0, id);
		 enableDisable="Disabled";
		}else {
			status=articleService.EnableArticleContent(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Article "+enableDisable+ " Successfully");
		}else {
			mv.addObject("status", "Please try Again");
		}
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);	
		
		List<ArticleExternal> articleListTemp=articleService.findAll();
		List<ArticleExternal> articleList=new ArrayList<ArticleExternal>();
		for(ArticleExternal temp:articleListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				articleList.add(temp);
			}
		}

		mv.addObject("Article",articleList);
		mv.addObject("viewActive","active");
		mv.setViewName("addArticle");
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	

	
	/*------------------------------------------SHOW DOCUMENT_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	

	/**
	 * Enable/Disable Document Resource By Admin User
	 * @param principal principal Object
	 * @param documentId Document ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value="/admin/addView/deleteDocument",method = RequestMethod.POST)
	public ModelAndView documentListPost(Principal principal,@RequestParam(name="radioDocument") String documentId,ModelAndView mv) {
		
		int id=Integer.parseInt(documentId);
		boolean status;
		String enableDisable;
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		DocumentExternal documentTemp=documentService.findByid(id);
		if(documentTemp.isStatus()==1) {
		 status=documentService.EnableDocumentContent(0, id);
		 enableDisable="Disabled";
		}else {
			status=documentService.EnableDocumentContent(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Document "+enableDisable+ " Successfully");
		}else {
			mv.addObject("status", "Please try Again");
		}
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);	
		
		List<DocumentExternal> documentListTemp=documentService.findAll();
		List<DocumentExternal> documentList=new ArrayList<DocumentExternal>();
		for(DocumentExternal temp:documentListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				documentList.add(temp);
			}
		}
		
		mv.addObject("Document",documentList);
		mv.addObject("viewActive","active");
		mv.setViewName("addDocument");
		return mv;
	}
	
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------SHOW PHET_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	
	/**
	 * Enable/Disable Phet Resource By Admin User
	 * @param principal principal Object
	 * @param phetId Phet ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value="/admin/addView/deletePhet",method = RequestMethod.POST)
	public ModelAndView phetsListPost(Principal principal,@RequestParam(name="radioPhet") String phetId,ModelAndView mv) {

		int id=Integer.parseInt(phetId);
		boolean status;
		String enableDisable;
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		Phets phetTemp=phetService.findByid(id);
		if(phetTemp.isStatus()==1) {
		 status=phetService.EnablePhetContent(0, id);
		 enableDisable="Disabled";
		}else {
			status=phetService.EnablePhetContent(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Phet "+enableDisable+ " Successfully");
		}else {
			mv.addObject("status", "Please try Again");
		}
		
		List<Phets> phetListTemp=phetService.findAll();
		List<Phets> phetList=new ArrayList<Phets>();
		for(Phets temp:phetListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				phetList.add(temp);
			}
		}
		
		mv.addObject("Phet",phetList);
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

		mv.addObject("classExist", classExist);
		
		mv.addObject("viewActive","active");
		
		mv.setViewName("addPhets");
		return mv;
	}
	
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*------------------------------------------SHOW LESSONPLAN_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	

	/**
	 * Enable/Disable LessonPlan Resource By Admin User
	 * @param principal principal Object
	 * @param lessonId lessonPlan ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value="/admin/addView/deleteLesson",method = RequestMethod.POST)
	public ModelAndView lessonPlanListPost(Principal principal,@RequestParam(name="radioLesson") String lessonId,ModelAndView mv) {
		
		int id=Integer.parseInt(lessonId);
		boolean status;
		String enableDisable;
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		LessonPlan lessonTemp=lessonService.findById(id);
		if(lessonTemp.isStatus()==1) {
		 status=lessonService.EnableLessonPlanContent(0, id);
		 enableDisable="Disabled";
		}else {
			status=lessonService.EnableLessonPlanContent(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Lesson "+enableDisable+ " Successfully");
		}else {
			mv.addObject("status", "Please try Again");
		}
		
		
		List<LessonPlan> lessonListTemp=lessonService.findAll();
		List<LessonPlan> lessonList=new ArrayList<LessonPlan>();
		for(LessonPlan temp:lessonListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				lessonList.add(temp);
			}
		}
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.
		
		mv.addObject("classExist", classExist);	
		
		mv.addObject("viewActive","active");
	
		mv.addObject("Lesson",lessonList);
		mv.setViewName("addLessonPlan");
		return mv;
	}
	
	/*----------------------------------------------------------END----------------------------------------------------------------------------*/	
	
	/*------------------------------------------SHOW QUIZ_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	

	/**
	 * Enable/Disable Quiz Resource By Admin User
	 * @param principal principal Object
	 * @param quizId Quiz ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value="/admin/addView/deleteQuiz",method = RequestMethod.POST)
	public ModelAndView quizListPost(Principal principal,@RequestParam(name="radioQuiz") String quizId,ModelAndView mv) {
		
		int id=Integer.parseInt(quizId);
		boolean status;
		String enableDisable;
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		QuizQuestion quizTemp=quizService.findById(id);
		if(quizTemp.isStatus()==1) {
		 status=quizService.EnableQuizContent(0, id);
		 enableDisable="Disabled";
		}else {
			status=quizService.EnableQuizContent(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Quiz "+enableDisable+ " Successfully");
		}else {
			mv.addObject("status", "Please try Again");
		}
		
		List<QuizQuestion> quizListTemp=quizService.findAll();
		List<QuizQuestion> quizList=new ArrayList<QuizQuestion>();
		for(QuizQuestion temp:quizListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				quizList.add(temp);
			}
		}
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();	// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);	
		
		mv.addObject("Quiz",quizList );
		mv.addObject("viewActive","active");
		mv.setViewName("addQuiz");
		return mv;
	}

/*-------------------------------------------CONCEPT-MAP---------------------------------------*/
	
	
	/**
	 * Enable/Disable ConcepMap Resource By Admin User
	 * @param principal principal Object
	 * @param conceptId conceptMap ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value="/admin/addView/deleteConcept",method = RequestMethod.POST)
	public ModelAndView conceptListPost(Principal principal,@RequestParam(name="radioConcept") String conceptId,ModelAndView mv) {
		
		int id=Integer.parseInt(conceptId);
		boolean status;
		String enableDisable;
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		ConceptMap conceptTemp=conceptService.findByid(id);
		if(conceptTemp.getStatus()==1) {
		 status=conceptService.EnableConceptContent(0, id);
		 enableDisable="Disabled";
		}else {
			status=conceptService.EnableConceptContent(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Concept "+enableDisable+ " Successfully");
		}else {
			mv.addObject("status", "Please try Again");
		}
		
		List<ConceptMap> ConceptMapListTemp=conceptService.findAll();
		List<ConceptMap> ConceptMapList=new ArrayList<ConceptMap>();
		for(ConceptMap temp:ConceptMapListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				ConceptMapList.add(temp);
			}
		}
	
		
		mv.addObject("ConceptMapList",ConceptMapList );
		mv.addObject("viewActive","active");
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);		
		
		mv.setViewName("addConcpetMap");
		return mv;
	}


/*----------------------------------------------------------END----------------------------------------------------------------------------*/


/*-----------------------------------------------START OF APPROVING/REJECT TECAHER---------------------------------------------------------------------*/

	/**
	 * redirect admin user to approve reject Teacher page
	 * @param principal principal Object
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/approveRejectTeacher")
	public ModelAndView teacherAprovementGet(Principal principal,ModelAndView mv) {

		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
			List<User> local=new ArrayList<User>();
		
			List<User> usr= userService.findAll();
			
			for(User a:usr) {
				List<UserRole> userRole=a.getUserRoles();
				for(UserRole x:userRole) {
					if(x.getRole().getRoleName().contentEquals("Teacher") && a.getApproveTeacherFlag()==0) {
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
		
		return mv;
	}
	
	/**
	 * Accept User's Teacher Role By Admin User
	 * @param principal principal Object
	 * @param userId User ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/approveTeacher",method =RequestMethod.POST)
	public ModelAndView approveTeacherPost(Principal principal,@RequestParam(name="radiocall") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean statusReg,statusApprove;
		
		User usrApprove=userService.findById(id);
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		statusReg=userService.disableEnableUser(1, id);
		
		statusApprove=userService.enableApproveTeacher(1, id);
	
		if(statusReg && statusApprove) {
			try {
				SimpleMailMessage emailSend=mailConstructor.confirmOnApproveTeacher(usrApprove);
				mailSender.send(emailSend);
			} catch (MailException e) {
				
				e.printStackTrace();
				mv.addObject("status", "User Enabled Successfully");
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
				
			
				return mv;
			}
			
			mv.addObject("status", "User Enabled Successfully");
		}else {
			mv.addObject("status", "Please try Again");
		}
		
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
			
		
		return mv;
	}
	




/*--------------------------------------------------------END----------------------------------------------------------------------------------*/

	/*-----------------------------------------------START OF APPROVING/REJECT VIDEO---------------------------------------------------------------------*/


	/**
	 * redirect admin user to approve Reject Video page
	 * @param principal principal Object
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/approveRejectVideo")
	public ModelAndView videoApprovementGet(Principal principal, ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
			List<VideoExternal> localVideo=new ArrayList<VideoExternal>();
			
			List<VideoExternal> videoList=videoService.findAll();
			for(VideoExternal a:videoList) {
				if(a.getAcceptedByAdmin()==0) {
					localVideo.add(a);
				}
			}
			
			if(localVideo.isEmpty()) {
				
				mv.addObject("VideoStatus","No Entries Present To Approve");
			}else {
				mv.addObject("VideoAdded",localVideo);
			}
			
		
			
		
			mv.setViewName("approveRejectVideo");
			
		return mv;
	}
	
	/**
	 * Accept Video Resource By Admin User
	 * @param principal principal Object
	 * @param userId Video ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/EnableVideo",method = RequestMethod.POST)
	public ModelAndView enbaleVideoPost(Principal principal,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		int id=Integer.parseInt(userId);
		boolean status;
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		  status=videoService.EnableAcceptedByAdminVideoContent(1, id);
			
			if(status) {
				mv.addObject("status", "Video Enabled Successfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
		
			List<VideoExternal> localVideo=new ArrayList<VideoExternal>();
			
			List<VideoExternal> videoList=videoService.findAll();
			for(VideoExternal a:videoList) {
				if(a.getAcceptedByAdmin()==0) {
					localVideo.add(a);
				}
			}
			
			if(localVideo.isEmpty()) {
				
				mv.addObject("VideoStatus","No Entries Present To Approve");
			}else {
				mv.addObject("VideoAdded",localVideo);
			}
			
			
		
			
		
			mv.setViewName("approveRejectVideo");
		
		return mv;
	}


/*-----------------------------------------------START OF APPROVING/REJECT DOCUMENT---------------------------------------------------------------------*/
	
	/**
	 * redirects admin User to approveReject Document Page
	 * @param principal principal Object
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/approveRejectDocument")
	public ModelAndView documentApprovementGet(Principal principal, ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
			List<DocumentExternal> localdocument=new ArrayList<DocumentExternal>();
			
			List<DocumentExternal> documentList=documentService.findAll();
			for(DocumentExternal a:documentList) {
				if(a.getAcceptedByAdmin()==0) {
					localdocument.add(a);
				}
			}
			
			if(localdocument.isEmpty()) {
				
				mv.addObject("documentStatus","No Entries Present To Approve");
			}else {
				mv.addObject("Documentadded",localdocument);
			}
			
			
			mv.setViewName("approveRejectDocument");
			

		return mv;
	}
	
	/**
	 * Accept Document Resource By Admin User
	 * @param principal principal Object
	 * @param userId Document ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/EnableDocument",method = RequestMethod.POST)
	public ModelAndView enbaleDocumentPost(Principal principal,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		  status=documentService.EnableAcceptedByAdminDocumentContent(1, id);
			
			if(status) {
				mv.addObject("status", "Document Enabled Successfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
				List<DocumentExternal> localdocument=new ArrayList<DocumentExternal>();
				
				List<DocumentExternal> documentList=documentService.findAll();
				for(DocumentExternal a:documentList) {
					if(a.getAcceptedByAdmin()==0) {
						localdocument.add(a);
					}
				}
				
				if(localdocument.isEmpty()) {
					
					mv.addObject("documentStatus","No Entries Present To Approve");
				}else {
					mv.addObject("Documentadded",localdocument);
				}
				
				
			
				
			
				mv.setViewName("approveRejectDocument");

			return mv;
		}
	
	
	
	/*-----------------------------------------------START OF APPROVING/REJECT ARTICLE---------------------------------------------------------------------*/
	
	
	/**
	 * redirects admin user to ApproveReject Article page
	 * @param principal principal Object
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/approveRejectArticle")
	public ModelAndView articleApprovementGet(Principal principal, ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
			List<ArticleExternal> localarticle=new ArrayList<ArticleExternal>();
			
			List<ArticleExternal> articleList=articleService.findAll();
			for(ArticleExternal a:articleList) {
				if(a.getAcceptedByAdmin()==0) {
					localarticle.add(a);
				}
			}
			
			if(localarticle.isEmpty()) {
				
				mv.addObject("articleStatus","No Entries Present To Approve");
			}else {
				mv.addObject("Article",localarticle);
			}
			
			
			mv.setViewName("approveRejectArticle");
			
		return mv;
	}
	
	
	/**
	 * Accept Article Resource By Admin User
	 * @param principal principal Object
	 * @param userId Article ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/EnableArticle",method = RequestMethod.POST)
	public ModelAndView enbaleArticlePost(Principal principal,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		  status=articleService.EnableAcceptedByAdminArticleContent(1, id);
			
			if(status) {
				mv.addObject("status", "Article Enabled Successfully");
			}else {
				mv.addObject("status", "Please try Again");
			}

				List<ArticleExternal> localarticle=new ArrayList<ArticleExternal>();
				
				List<ArticleExternal> articleList=articleService.findAll();
				for(ArticleExternal a:articleList) {
					if(a.getAcceptedByAdmin()==0) {
						localarticle.add(a);
					}
				}
				
				if(localarticle.isEmpty()) {
					
					mv.addObject("articleStatus","No Entries Present To Approve");
				}else {
					mv.addObject("Article",localarticle);
				}
				
				
				mv.setViewName("approveRejectArticle");

			return mv;
		}
	
	
	/*-----------------------------------------------START OF APPROVING/REJECT PHETS---------------------------------------------------------------------*/
	
	/**
	 * redirects admin user to ApproveReject Phet Page
	 * @param principal principal Object
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/approveRejectPhets")
	public ModelAndView phetApprovementGet(Principal principal, ModelAndView mv) {

		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
			List<Phets> localphet=new ArrayList<Phets>();
			
			List<Phets> phetList=phetService.findAll();
			for(Phets a:phetList) {
				if(a.getAcceptedByAdmin()==0) {
					localphet.add(a);
				}
			}
			
			if(localphet.isEmpty()) {
				
				mv.addObject("phetStatus","No Entries Present To Approve");
			}else {
				mv.addObject("PhetsAdded",localphet);
			}
			
			mv.setViewName("approveRejectPhet");
			

		return mv;
	}
	
	
	/**
	 * Accept Phet Resource By Admin User
	 * @param principal principal Object
	 * @param userId Phet ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/EnablePhet",method = RequestMethod.POST)
	public ModelAndView enbalePhetPost(Principal principal,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		  status=phetService.EnableAcceptedByAdminPhetContent(1, id);
			
			if(status) {
				mv.addObject("status", "Phets Enabled Successfully");
			}else {
				mv.addObject("status", "Please try Again");
			}

				List<Phets> localphet=new ArrayList<Phets>();
				
				List<Phets> phetList=phetService.findAll();
				for(Phets a:phetList) {
					if(a.getAcceptedByAdmin()==0) {
						localphet.add(a);
					}
				}
				
				if(localphet.isEmpty()) {
					
					mv.addObject("phetStatus","No Entries Present To Approve");
				}else {
					mv.addObject("PhetsAdded",localphet);
				}
			
			
				mv.setViewName("approveRejectPhet");
				

			return mv;
		}
	
	
	/*-----------------------------------------------START OF APPROVING/REJECT QUIZ---------------------------------------------------------------------*/
	
	/**
	 * redirects Admin User to ApproveReject Quiz Page
	 * @param principal principal Object
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/approveRejectQuiz")
	public ModelAndView quizApprovementGet(Principal principal, ModelAndView mv) {

		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
			List<QuizQuestion> localQuiz=new ArrayList<QuizQuestion>();
			
			List<QuizQuestion> quizList=quizService.findAll();
			for(QuizQuestion a:quizList) {
				if(a.getAcceptedByAdmin()==0) {
					localQuiz.add(a);
				}
			}
			
			if(localQuiz.isEmpty()) {
				
				mv.addObject("QuizStatus","No Entries Present To Approve");
			}else {
				mv.addObject("QuizAdded",localQuiz);
			}
		
			mv.setViewName("approveRejectQuiz");
			

		return mv;
	}
	
	/**
	 * Accept Quiz Resource By Admin User
	 * @param principal principal Object
	 * @param userId Quiz ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/EnableQuiz",method = RequestMethod.POST)
	public ModelAndView enbaleQuizPost(Principal principal,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		  status=quizService.EnableAcceptedByAdminQuizContent(1, id);
			
			if(status) {
				mv.addObject("status", "Quiz Enabled Successfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
				List<QuizQuestion> localQuiz=new ArrayList<QuizQuestion>();
				
				List<QuizQuestion> quizList=quizService.findAll();
				for(QuizQuestion a:quizList) {
					if(a.getAcceptedByAdmin()==0) {
						localQuiz.add(a);
					}
				}
				
				if(localQuiz.isEmpty()) {
					
					mv.addObject("QuizStatus","No Entries Present To Approve");
				}else {
					mv.addObject("QuizAdded",localQuiz);
				}
			
				mv.setViewName("approveRejectQuiz");
				
			return mv;
		}
	
	
	
	/*-----------------------------------------------START OF APPROVING/REJECT LESSON---------------------------------------------------------------------*/
	
	/**
	 * redirects admin User to ApproveReject Lesson Page
	 * @param principal principal Object
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/approveRejectLesson")
	public ModelAndView lessonApprovementGet(Principal principal, ModelAndView mv) {

		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
			List<LessonPlan> localLesson=new ArrayList<LessonPlan>();
			
			List<LessonPlan> lessonList=lessonService.findAll();
			for(LessonPlan a:lessonList) {
				if(a.getAcceptedByAdmin()==0) {
					localLesson.add(a);
				}
			}
			
			if(localLesson.isEmpty()) {
				
				mv.addObject("LessonStatus","No Entries Present To Approve");
			}else {
				mv.addObject("LessonAdded",localLesson);
			}
			
			mv.setViewName("approveRejectLesson");
		
		return mv;
	}
	
	/**
	 * Accept LessonPlan Resource By Admin User
	 * @param principal principal Object
	 * @param userId LessonPlan ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/EnableLesson",method = RequestMethod.POST)
	public ModelAndView enbaleLessonPost(Principal principal,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		  status=lessonService.EnableAcceptedByAdminLessonPlanContent(1, id);
			
			if(status) {
				mv.addObject("status", "Lesson Plan Enabled Successfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
				List<LessonPlan> localLesson=new ArrayList<LessonPlan>();
				
				List<LessonPlan> lessonList=lessonService.findAll();
				for(LessonPlan a:lessonList) {
					if(a.getAcceptedByAdmin()==0) {
						localLesson.add(a);
					}
				}
				
				if(localLesson.isEmpty()) {
					
					mv.addObject("LessonStatus","No Entries Present To Approve");
				}else {
					mv.addObject("LessonAdded",localLesson);
				}
		
				mv.setViewName("approveRejectLesson");

			return mv;
		}
	
	
	/*----------------------------------- APPROVE REJECT CONCEPTS-MAP ------------------------------------------------*/
	
	/**
	 * redirects Admin User to ApproveReject Concept Map Page
	 * @param principal principal Object
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/approveRejectConcept")
	public ModelAndView conceptApprovementGet(Principal principal, ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
			List<ConceptMap> localconcept=new ArrayList<ConceptMap>();
			
			List<ConceptMap> conceptList=conceptService.findAll();
			for(ConceptMap a:conceptList) {
				if(a.getAcceptedByAdmin()==0) {
					localconcept.add(a);
				}
			}
			
			if(localconcept.isEmpty()) {
				
				mv.addObject("conceptStatus","No Entries Present To Approve");
			}else {
				mv.addObject("Conceptadded",localconcept);
			}
			
			mv.setViewName("approveRejectConcept");

		return mv;
	}
	
	/**
	 * Accept ConcepMap Resource By Admin User
	 * @param principal principal Object
	 * @param userId ConceptMap ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/EnableConcept",method = RequestMethod.POST)
	public ModelAndView enbaleConceptPost(Principal principal,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		  status=conceptService.EnableAcceptedByAdminConceptContent(1, id);
			
			if(status) {
				mv.addObject("status", "Concept-Map Enabled Successfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
				List<ConceptMap> localconcept=new ArrayList<ConceptMap>();
				
				List<ConceptMap> conceptList=conceptService.findAll();
				for(ConceptMap a:conceptList) {
					if(a.getAcceptedByAdmin()==0) {
						localconcept.add(a);
					}
				}
				
				if(localconcept.isEmpty()) {
					
					mv.addObject("conceptStatus","No Entries Present To Approve");
				}else {
					mv.addObject("Conceptadded",localconcept);
				}
				
				mv.setViewName("approveRejectConcept");
			return mv;
		}
	/***********************************************************************************************************************/
	
	/**
	 * redirects admin user to view all the Testimonial data exist in application
	 * @param principal principal Object
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/testimonialList",method = RequestMethod.GET)
	public ModelAndView testimonialList(Principal principal,ModelAndView mv) {
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
			try{
				
					List<Testimonial> local=testiService.findAll();
					mv.addObject("Testimonial", local);
					
					mv.setViewName("testimonialList");
				}
			catch (Exception e) {
				   mv.setViewName("redirect:/");
				
			}

		return mv;
	
		
	}
	
	/**
	 * Redirects Admin user to view all the Event data exist in application.
	 * @param principal principal Object
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value = "/admin/approve/eventList",method = RequestMethod.GET)
	public ModelAndView eventList(Principal principal,ModelAndView mv) {

			
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
			try{
					List<Events> local=eventService.findAll();
					mv.addObject("Events", local);
			
					mv.setViewName("eventList");
				}
			catch (Exception e) {
				 mv.setViewName("redirect:/");
				
			}

		return mv;
	
		
	}
	
	
	// not in use 
	@RequestMapping(value = "/admin/approve/tutorialList",method = RequestMethod.GET)
	public ModelAndView TutorialList(Principal principal,ModelAndView mv) {

		
			User localUser=userService.findByUsername(principal.getName());
			
			mv.addObject("LoggedUser",localUser);
			
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
			
			mv.setViewName("tutorialList");
			
		return mv;
	
		
	}
	
	/**
	 * Changes Tutorial's attributes value in database
	 * @param principal principal object
	 * @param req HttpServletRequest
	 * @param tutorialId Tutorial Resource ID
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	@RequestMapping(value="/admin/addView/disableEnableTutorial",method = RequestMethod.POST)
	public ModelAndView enableDisableTutorialListPost(Principal principal,HttpServletRequest req,@RequestParam(name="radioTutorial") String tutorialId,ModelAndView mv) {
		
		int id=Integer.parseInt(tutorialId);
		boolean status;
		String enableDisable;
		
		User localUser=userService.findByUsername(principal.getName());
		
		mv.addObject("LoggedUser",localUser);
		
		Tutorial tutorialTemp=tutorialService.getById(id);
		if(tutorialTemp.getStatus()==1) {
		 status=tutorialService.enableDisableTutorial(0, id);
		 enableDisable="Disabled";
		 
		}else {
			status=tutorialService.enableDisableTutorial(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Tutorial "+enableDisable+ " Successfully");
		}else {
			mv.addObject("status", "Please try Again");
		}
		
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
				
				tutorialListData.add(localTutorial);
			} catch (RestClientException e) {
				
				e.printStackTrace();
			}
		}
		
		
		mv.addObject("Tutorials", tutorialListData);
		mv.addObject("viewActive","active");
		
		ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

		mv.addObject("classExist", classExist);	
		
		mv.setViewName("addTutorial");
		return mv;
	}
	
	
	
	
/************************************************************END********************************************************************************/
	
	
	
	

}

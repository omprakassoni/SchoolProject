package com.adminportal;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
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

@Controller
@SessionAttributes({"UserLogedUsername","UserLogedName","UserLogedRole"})
public class AdminViewController {
	
	public static final String uploadDirectory="src/main/resources/static"+"/Media/content/";
	public static final String uploadTeacherDirectory="src/main/resources/static/Media/Teacher/";

	
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
	
/*------------------------------------------SHOW USER_LIST (ADMIN MODULE)-----------------------------------------------------------------*/
	
	@RequestMapping(value = "/userList",method = RequestMethod.GET)
	public ModelAndView userListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
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
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
		
		List<User> usr= userService.findAll();
	
		mv.addObject("User", usr);
		mv.setViewName("userList");
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
	public ModelAndView userListPost(HttpServletRequest req,@RequestParam(name="radiocall") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		String enableDisable;
		HttpSession session=req.getSession();
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
			return mv;
			
		}
		
		User usrTemp=userService.findById(id);
		if(usrTemp.getRegistered()==1) {
		 status=userService.disableEnableUser(0, id);
		 enableDisable="Disabled";
		}else {
		  status=userService.disableEnableUser(1, id);
		  enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "User "+enableDisable+ " Sucessfully");
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
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
		
		List<Subject> subjectList=subjectService.findAll();
		mv.addObject("Subject", subjectList);
		
		mv.setViewName("subjectList");
		}
		return mv;
	}
	
	@RequestMapping(value="/deleteSubject",method = RequestMethod.POST)
	public ModelAndView subjectListPost(HttpServletRequest req,@RequestParam(name="radiocall") String subjectId,ModelAndView mv) {
		
		int id=Integer.parseInt(subjectId);
		System.out.println(id);
		
		HttpSession session=req.getSession();
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
			return mv;
			
		}
		
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
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
		
		List<Topic> topicList=topicService.findAll();
		
		mv.addObject("Topic", topicList);
		mv.setViewName("topicList");
		}
		return mv;
	}
	
	
	@RequestMapping(value="/deleteTopic",method = RequestMethod.POST)
	public ModelAndView topicListPost(HttpServletRequest req,@RequestParam(name="radioTopic") String topicId,ModelAndView mv) {
		
		int id=Integer.parseInt(topicId);
		boolean status;
		String enableDisable;
		
		HttpSession session=req.getSession();
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
			return mv;
			
		}
		
		Topic topicTemp=topicService.findById(id);
		if(topicTemp.getStatus()==1) {
		 status=topicService.disableEnableTopicById(0, id);
		 enableDisable="Disabled";
		}else {
		  status=topicService.disableEnableTopicById(1, id);
		  enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Topic "+enableDisable+ " Sucessfully");
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
	
	
	@RequestMapping(value="/videoList",method = RequestMethod.GET)
	public ModelAndView videoListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
		
		List<VideoExternal> videoListtemp=videoService.findAll();
		List<VideoExternal> videoList=new ArrayList<VideoExternal>();
		for(VideoExternal temp:videoListtemp) {
			if(temp.getAcceptedByAdmin()==1) {
				videoList.add(temp);
			}
		}
	
		
		
		
		mv.addObject("Video",videoList);
		
		mv.setViewName("videoList");
		}
		return mv;
	}
	
	@RequestMapping(value="/deleteVideo",method = RequestMethod.POST)
	public ModelAndView videoListPost(HttpServletRequest req,@RequestParam(name="radioVideo") String videoId,ModelAndView mv) {
		
		int id=Integer.parseInt(videoId);
		boolean status;
		String enableDisable;
		
		HttpSession session=req.getSession();
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
			return mv;
			
		}
		
		VideoExternal videoTemp=videoService.findById(id);
		if(videoTemp.isStatus()==1) {
		 status=videoService.EnableVideoContent(0, id);
		 enableDisable="Disabled";
		 
		}else {
			status=videoService.EnableVideoContent(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Video "+enableDisable+ " Sucessfully");
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
	
	@RequestMapping(value="/articleList",method = RequestMethod.GET)
	public ModelAndView articleListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
		
		List<ArticleExternal> articleListTemp=articleService.findAll();
		List<ArticleExternal> articleList=new ArrayList<ArticleExternal>();
		for(ArticleExternal temp:articleListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				articleList.add(temp);
			}
		}
		
	
		mv.addObject("Article",articleList);
		
		mv.setViewName("articleList");
		}
		return mv;
	}
	
	@RequestMapping(value="/deleteArticle",method = RequestMethod.POST)
	public ModelAndView articleListPost(HttpServletRequest req,@RequestParam(name="radioArticle") String articleId,ModelAndView mv) {
		
		int id=Integer.parseInt(articleId);
		boolean status;
		String enableDisable;
		
		HttpSession session=req.getSession();
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
			return mv;
			
		}
		
		ArticleExternal articleTemp=articleService.findByid(id);
		if(articleTemp.isStatus()==1) {
		 status=articleService.EnableArticleContent(0, id);
		 enableDisable="Disabled";
		}else {
			status=articleService.EnableArticleContent(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Article "+enableDisable+ " Sucessfully");
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
	
	@RequestMapping(value="/documentList",method = RequestMethod.GET)
	public ModelAndView documentListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
		
		List<DocumentExternal> documentListTemp=documentService.findAll();
		List<DocumentExternal> documentList=new ArrayList<DocumentExternal>();
		for(DocumentExternal temp:documentListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				documentList.add(temp);
			}
		}
		
		mv.addObject("Document",documentList);
		
		mv.setViewName("documentList");
		}
		return mv;
	}
	

	@RequestMapping(value="/deleteDocument",method = RequestMethod.POST)
	public ModelAndView documentListPost(HttpServletRequest req,@RequestParam(name="radioDocument") String documentId,ModelAndView mv) {
		
		int id=Integer.parseInt(documentId);
		boolean status;
		String enableDisable;
		
		HttpSession session=req.getSession();
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
			return mv;
			
		}
		
		DocumentExternal documentTemp=documentService.findByid(id);
		if(documentTemp.isStatus()==1) {
		 status=documentService.EnableDocumentContent(0, id);
		 enableDisable="Disabled";
		}else {
			status=documentService.EnableDocumentContent(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Document "+enableDisable+ " Sucessfully");
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
	
	@RequestMapping(value="/phetsList",method = RequestMethod.GET)
	public ModelAndView phetsListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {

		List<Phets> phetListTemp=phetService.findAll();
		List<Phets> phetList=new ArrayList<Phets>();
		for(Phets temp:phetListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				phetList.add(temp);
			}
		}
		
		mv.addObject("Phet",phetList);
		
		mv.setViewName("phetsList");
		}
		return mv;
	}
	
	@RequestMapping(value="/deletePhet",method = RequestMethod.POST)
	public ModelAndView phetsListPost(HttpServletRequest req,@RequestParam(name="radioPhet") String phetId,ModelAndView mv) {

		int id=Integer.parseInt(phetId);
		boolean status;
		String enableDisable;
		
		HttpSession session=req.getSession();
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
			return mv;
			
		}
		
		Phets phetTemp=phetService.findByid(id);
		if(phetTemp.isStatus()==1) {
		 status=phetService.EnablePhetContent(0, id);
		 enableDisable="Disabled";
		}else {
			status=phetService.EnablePhetContent(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Phet "+enableDisable+ " Sucessfully");
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
	
	@RequestMapping(value="/lessonPlanList",method = RequestMethod.GET)
	public ModelAndView lessonPlanListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
		
		List<LessonPlan> lessonListTemp=lessonService.findAll();
		List<LessonPlan> lessonList=new ArrayList<LessonPlan>();
		for(LessonPlan temp:lessonListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				lessonList.add(temp);
			}
		}
	
		mv.addObject("Lesson",lessonList);
		mv.setViewName("lessonPlanList");
		}
		return mv;
	}
	
	@RequestMapping(value="/deleteLesson",method = RequestMethod.POST)
	public ModelAndView lessonPlanListPost(HttpServletRequest req,@RequestParam(name="radioLesson") String lessonId,ModelAndView mv) {
		
		int id=Integer.parseInt(lessonId);
		boolean status;
		String enableDisable;
		
		HttpSession session=req.getSession();
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
			return mv;
			
		}
		
		LessonPlan lessonTemp=lessonService.findById(id);
		if(lessonTemp.isStatus()==1) {
		 status=lessonService.EnableLessonPlanContent(0, id);
		 enableDisable="Disabled";
		}else {
			status=lessonService.EnableLessonPlanContent(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Lesson "+enableDisable+ " Sucessfully");
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
	
	@RequestMapping(value="/quizList",method = RequestMethod.GET)
	public ModelAndView quizListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
		
		
		List<QuizQuestion> quizListTemp=quizService.findAll();
		List<QuizQuestion> quizList=new ArrayList<QuizQuestion>();
		for(QuizQuestion temp:quizListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				quizList.add(temp);
			}
		}
		
		mv.addObject("Quiz",quizList );
		mv.setViewName("quizList");
		}
		return mv;
	}
	
	@RequestMapping(value="/deleteQuiz",method = RequestMethod.POST)
	public ModelAndView quizListPost(HttpServletRequest req,@RequestParam(name="radioQuiz") String quizId,ModelAndView mv) {
		
		int id=Integer.parseInt(quizId);
		boolean status;
		String enableDisable;
		
		HttpSession session=req.getSession();
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
			return mv;
			
		}
		
		QuizQuestion quizTemp=quizService.findById(id);
		if(quizTemp.isStatus()==1) {
		 status=quizService.EnableQuizContent(0, id);
		 enableDisable="Disabled";
		}else {
			status=quizService.EnableQuizContent(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Quiz "+enableDisable+ " Sucessfully");
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
	
	@RequestMapping(value="/conceptList",method = RequestMethod.GET)
	public ModelAndView ConceptListGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
		
		
		List<ConceptMap> ConceptMapListTemp=conceptService.findAll();
		List<ConceptMap> ConceptMapList=new ArrayList<ConceptMap>();
		for(ConceptMap temp:ConceptMapListTemp) {
			if(temp.getAcceptedByAdmin()==1) {
				ConceptMapList.add(temp);
			}
		}
		
		mv.addObject("ConceptMapList",ConceptMapList );
		mv.setViewName("concepMapList");
		}
		return mv;
	}
	
	
	@RequestMapping(value="/deleteConcept",method = RequestMethod.POST)
	public ModelAndView conceptListPost(HttpServletRequest req,@RequestParam(name="radioConcept") String conceptId,ModelAndView mv) {
		
		int id=Integer.parseInt(conceptId);
		boolean status;
		String enableDisable;
		
		HttpSession session=req.getSession();
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
			return mv;
			
		}
		
		ConceptMap conceptTemp=conceptService.findByid(id);
		if(conceptTemp.getStatus()==1) {
		 status=conceptService.EnableConceptContent(0, id);
		 enableDisable="Disabled";
		}else {
			status=conceptService.EnableConceptContent(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Concept "+enableDisable+ " Sucessfully");
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

	@RequestMapping(value = "/approveRejectTeacher")
	public ModelAndView teacherAprovementGet(HttpServletRequest req,ModelAndView mv) {
		
		HttpSession session=req.getSession();
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
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
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
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
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
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
			
		}
		return mv;
	}
	
	@RequestMapping(value = "/EnableVideo",method = RequestMethod.POST)
	public ModelAndView enbaleVideoPost(HttpServletRequest req,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		int id=Integer.parseInt(userId);
		boolean status;
		
		  status=videoService.EnableAcceptedByAdminVideoContent(1, id);
			
			if(status) {
				mv.addObject("status", "Video Enabled Sucessfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
		
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
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
			
		}
		
		
		
		return mv;
	}


/*-----------------------------------------------START OF APPROVING/REJECT DOCUMENT---------------------------------------------------------------------*/
	
	
	@RequestMapping(value = "/approveRejectDocument")
	public ModelAndView documentApprovementGet(HttpServletRequest req, ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
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
			
		}
		return mv;
	}
	
	@RequestMapping(value = "/EnableDocument",method = RequestMethod.POST)
	public ModelAndView enbaleDocumentPost(HttpServletRequest req,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		  status=documentService.EnableAcceptedByAdminDocumentContent(1, id);
			
			if(status) {
				mv.addObject("status", "Document Enabled Sucessfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
			HttpSession session=req.getSession(false);
			
			if(!ServiceUtility.chechExistSessionAdmin(session)) {
				mv.setViewName("redirect:/");
			}else {
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
				
			}
			return mv;
		}
	
	
	
	/*-----------------------------------------------START OF APPROVING/REJECT ARTICLE---------------------------------------------------------------------*/
	
	
	
	@RequestMapping(value = "/approveRejectArticle")
	public ModelAndView articleApprovementGet(HttpServletRequest req, ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
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
			
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/EnableArticle",method = RequestMethod.POST)
	public ModelAndView enbaleArticlePost(HttpServletRequest req,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		  status=articleService.EnableAcceptedByAdminArticleContent(1, id);
			
			if(status) {
				mv.addObject("status", "Article Enabled Sucessfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
			HttpSession session=req.getSession(false);
			
			if(!ServiceUtility.chechExistSessionAdmin(session)) {
				mv.setViewName("redirect:/");
			}else {
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
				
			}
			return mv;
		}
	
	
	/*-----------------------------------------------START OF APPROVING/REJECT PHETS---------------------------------------------------------------------*/
	
	
	@RequestMapping(value = "/approveRejectPhets")
	public ModelAndView phetApprovementGet(HttpServletRequest req, ModelAndView mv) {
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
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
			
		}
		return mv;
	}
	
	
	
	@RequestMapping(value = "/EnablePhet",method = RequestMethod.POST)
	public ModelAndView enbalePhetPost(HttpServletRequest req,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		  status=phetService.EnableAcceptedByAdminPhetContent(1, id);
			
			if(status) {
				mv.addObject("status", "Phets Enabled Sucessfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
			HttpSession session=req.getSession(false);
			
			if(!ServiceUtility.chechExistSessionAdmin(session)) {
				mv.setViewName("redirect:/");
			}else {
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
				
			}
			return mv;
		}
	
	
	/*-----------------------------------------------START OF APPROVING/REJECT QUIZ---------------------------------------------------------------------*/
	
	
	@RequestMapping(value = "/approveRejectQuiz")
	public ModelAndView quizApprovementGet(HttpServletRequest req, ModelAndView mv) {
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
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
			
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/EnableQuiz",method = RequestMethod.POST)
	public ModelAndView enbaleQuizPost(HttpServletRequest req,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		  status=quizService.EnableAcceptedByAdminQuizContent(1, id);
			
			if(status) {
				mv.addObject("status", "Quiz Enabled Sucessfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
			HttpSession session=req.getSession(false);
			
			if(!ServiceUtility.chechExistSessionAdmin(session)) {
				mv.setViewName("redirect:/");
			}else {
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
				
			}
			return mv;
		}
	
	
	
	/*-----------------------------------------------START OF APPROVING/REJECT LESSON---------------------------------------------------------------------*/
	
	
	@RequestMapping(value = "/approveRejectLesson")
	public ModelAndView lessonApprovementGet(HttpServletRequest req, ModelAndView mv) {
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
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
			
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/EnableLesson",method = RequestMethod.POST)
	public ModelAndView enbaleLessonPost(HttpServletRequest req,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		  status=lessonService.EnableAcceptedByAdminLessonPlanContent(1, id);
			
			if(status) {
				mv.addObject("status", "Lesson Plan Enabled Sucessfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
			HttpSession session=req.getSession(false);
			
			if(!ServiceUtility.chechExistSessionAdmin(session)) {
				mv.setViewName("redirect:/");
			}else {
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
				
			}
			return mv;
		}
	
	
	/*----------------------------------- APPROVE REJECT CONCEPTS-MAP ------------------------------------------------*/
	
	@RequestMapping(value = "/approveRejectConcept")
	public ModelAndView conceptApprovementGet(HttpServletRequest req, ModelAndView mv) {
		
		HttpSession session=req.getSession(false);
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
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
			
		}
		return mv;
	}
	
	@RequestMapping(value = "/EnableConcept",method = RequestMethod.POST)
	public ModelAndView enbaleConceptPost(HttpServletRequest req,@RequestParam(name="selectionRadio") String userId, ModelAndView mv) {
		
		int id=Integer.parseInt(userId);
		boolean status;
		
		  status=conceptService.EnableAcceptedByAdminConceptContent(1, id);
			
			if(status) {
				mv.addObject("status", "Concept-Map Enabled Sucessfully");
			}else {
				mv.addObject("status", "Please try Again");
			}
		
			HttpSession session=req.getSession(false);
			
			if(!ServiceUtility.chechExistSessionAdmin(session)) {
				mv.setViewName("redirect:/");
			}else {
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
			}
			return mv;
		}
	/***********************************************************************************************************************/
	
	@RequestMapping(value = "/testimonialList",method = RequestMethod.GET)
	public ModelAndView testimonialList(HttpServletRequest req,ModelAndView mv) {
		HttpSession session=req.getSession(false);
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
			
			try{
				
					List<Testimonial> local=testiService.findAll();
					mv.addObject("Testimonial", local);
					
					mv.setViewName("testimonialList");
				}
			catch (Exception e) {
				   mv.setViewName("redirect:/");
				
			}
		}
		return mv;
	
		
	}
	
	@RequestMapping(value = "/eventList",method = RequestMethod.GET)
	public ModelAndView eventList(HttpServletRequest req,ModelAndView mv) {
		HttpSession session=req.getSession(false);
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
			
			
		
			try{
					List<Events> local=eventService.findAll();
					mv.addObject("Events", local);
			
					mv.setViewName("eventList");
				}
			catch (Exception e) {
				 mv.setViewName("redirect:/");
				
			}
		}
		return mv;
	
		
	}
	
	
	@RequestMapping(value = "/tutorialList",method = RequestMethod.GET)
	public ModelAndView TutorialList(HttpServletRequest req,ModelAndView mv) {
		HttpSession session=req.getSession(false);
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
		}else {
			
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
			
		
		}
		return mv;
	
		
	}
	
	@RequestMapping(value="/disableEnableTutorial",method = RequestMethod.POST)
	public ModelAndView enableDisableTutorialListPost(HttpServletRequest req,@RequestParam(name="radioTutorial") String tutorialId,ModelAndView mv) {
		
		int id=Integer.parseInt(tutorialId);
		boolean status;
		String enableDisable;
		
		HttpSession session=req.getSession();
		
		if(!ServiceUtility.chechExistSessionAdmin(session)) {
			mv.setViewName("redirect:/");
			return mv;
			
		}
		
		Tutorial tutorialTemp=tutorialService.getById(id);
		if(tutorialTemp.getStatus()==1) {
		 status=tutorialService.enableDisableTutorial(0, id);
		 enableDisable="Disabled";
		 
		}else {
			status=tutorialService.enableDisableTutorial(1, id);
			enableDisable="Enabled";
		}
		if(status) {
			mv.addObject("status", "Tutorial "+enableDisable+ " Sucessfully");
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

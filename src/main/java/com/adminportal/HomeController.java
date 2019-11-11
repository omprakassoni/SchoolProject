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

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.hibernate.annotations.Loader;
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
		if(!ServiceUtility.chechExistSessionUser(session)) {
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
	
	

	
	@RequestMapping(value = "/eventsList")
	public ModelAndView viewEvent(ModelAndView mv) {
		
		List<Events> localEvent=eventService.findAll();
		ArrayList<Class> standard=(ArrayList<Class>) classService.findAll();
	
		
		mv.addObject("classfromDatabase", standard);
		mv.addObject("EventList", localEvent);
		mv.setViewName("events");
		return mv;
	}
	
	

	


	
	
	
// Above code was to add all entries into database

	
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
	
	
	
	
	

	
	



/*--------------------------------------------------------END----------------------------------------------------------------------------------*/
}


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
import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
import com.adminportal.repository.EventsRepository;
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
public class HomeController {

	public static final String uploadDirectory = "Media/content/"; // Path to save resources
	public static final String uploadTeacherDirectory = "Media/User/"; // path to save teacher Document
	public static final String uploadEvent="Media/Event/";             // path to sabe event images
	public static final String uploadTestimonial="Media/Testimonial/";

	@Autowired
	private ClassService classService;

	@Autowired
	private SubjectClassService subjectClassService;

	@Autowired
	private SubjectService subjectService;

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

	@Autowired
	private EventsRepository evenRepo;

	/*
	 * @Autowired HttpServletRequest req;
	 */
	/*
	 * ------------------------------------------HOME
	 * PAGE-------------------------------------------------
	 */

	@RequestMapping("/")
	public ModelAndView home(@RequestHeader Map<String ,String> headers,ModelAndView mv) {
		
		/*
		 * headers.forEach((key,value)->{
		 * System.out.println("Header Name: "+key+" Header Value: "+value); });
		 * 
		 * System.out.println(req.getRemoteAddr());
		 */

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();

		ArrayList<Subject> subject = (ArrayList<Subject>) subjectService.findAll();

		List<Testimonial> testidata = testiService.getAlltestimonial();

		List<Events> eventData = eventService.getAllEventdata();

		if (testidata.size() > 0) {

			Testimonial temp1 = testidata.get(0);
			mv.addObject("TestimonialFirst", temp1);

			List<Testimonial> temp2 = new ArrayList<Testimonial>();
			for (int i = 1; i < testidata.size(); i++) {
				temp2.add(testidata.get(i));
			}

			mv.addObject("TestimonialRest", temp2);

		}

		if (eventData.size() > 0) {

			List<Events> eventTemp = new ArrayList<Events>();
			eventTemp.add(eventData.get(0));
			for (int i = 1; i < eventData.size(); i++) {
				eventTemp.add(eventData.get(i));
				if(i==5) {
					break;
				}
			}

			mv.addObject("Events", eventTemp);
		}

		mv.addObject("classfromDatabase", standard);
		mv.addObject("subjectfromDatabase", subject);

		mv.setViewName("Index");
		return mv;
	}


	/*******************************
	 * dASHBOARD FOR CONTRIBUTOR
	 ********************************************/

	@RequestMapping(value = "/dashBoard", method = RequestMethod.GET)
	public ModelAndView contributorDashBoard(ModelAndView mv, Principal principal) {

		User localUser = userService.findByUsername(principal.getName());

		mv.addObject("LoggedUser", localUser);

		mv.setViewName("user");

		return mv;
	}

	@RequestMapping(value = "/conVideo", method = RequestMethod.GET)
	public ModelAndView contributorDashBoardVideo(ModelAndView mv, Principal principal) {

		User localUser = userService.findByUsername(principal.getName());

		mv.addObject("LoggedUser", localUser);
		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);

		mv.setViewName("video");

		return mv;
	}

	@RequestMapping(value = "/conConceptMap", method = RequestMethod.GET)
	public ModelAndView contributorDashBoardConceptMap(ModelAndView mv, Principal principal) {

		User localUser = userService.findByUsername(principal.getName());

		mv.addObject("LoggedUser", localUser);

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);

		mv.setViewName("conceptMap");

		return mv;
	}

	@RequestMapping(value = "/conQuiz", method = RequestMethod.GET)
	public ModelAndView contributorDashBoardQuiz(ModelAndView mv, Principal principal) {

		User localUser = userService.findByUsername(principal.getName());

		mv.addObject("LoggedUser", localUser);

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		mv.setViewName("quiz");

		return mv;
	}

	@RequestMapping(value = "/conArticle", method = RequestMethod.GET)
	public ModelAndView contributorDashBoardArticle(ModelAndView mv, Principal principal) {

		User localUser = userService.findByUsername(principal.getName());

		mv.addObject("LoggedUser", localUser);

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		mv.setViewName("article");

		return mv;
	}

	@RequestMapping(value = "/conLessonPlan", method = RequestMethod.GET)
	public ModelAndView contributorDashBoardLessonPlan(ModelAndView mv, Principal principal) {

		User localUser = userService.findByUsername(principal.getName());

		mv.addObject("LoggedUser", localUser);

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		mv.setViewName("lessonPlan");

		return mv;
	}

	@RequestMapping(value = "/conDocument", method = RequestMethod.GET)
	public ModelAndView contributorDashBoardDocument(ModelAndView mv, Principal principal) {

		User localUser = userService.findByUsername(principal.getName());

		mv.addObject("LoggedUser", localUser);

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		mv.setViewName("document");

		return mv;
	}

	@RequestMapping(value = "/conPhet", method = RequestMethod.GET)
	public ModelAndView contributorDashBoardPhet(ModelAndView mv, Principal principal) {

		User localUser = userService.findByUsername(principal.getName());

		mv.addObject("LoggedUser", localUser);

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);
		mv.setViewName("phets");

		return mv;
	}

	@RequestMapping(value = "/conView", method = RequestMethod.GET)
	public ModelAndView contributorDashBoardStatus(ModelAndView mv, Principal principal) {

		User localUser = userService.findByUsername(principal.getName());

		mv.addObject("LoggedUser", localUser);

		List<QuizQuestion> localQuiz = quizService.findALlByUser(localUser);
		List<ArticleExternal> localArticle = articleService.findALlByUser(localUser);
		List<DocumentExternal> localDocument = documentService.findALlByUser(localUser);
		List<LessonPlan> localLesson = lessonService.findALlByUser(localUser);
		List<VideoExternal> localvideo = videoService.findALlByUser(localUser);
		List<Phets> localPhets = phetService.findALlByUser(localUser);
		List<ConceptMap> localConcept = conceptMapService.findALlByUser(localUser);

		if (localQuiz.isEmpty()) {
			mv.addObject("QuizError", "Nothing To Show");
		}

		if (localArticle.isEmpty()) {
			;
			mv.addObject("ArticleError", "Nothing To Show");
		}

		if (localDocument.isEmpty()) {
			mv.addObject("DocumentError", "Nothing To Show");
		}

		if (localLesson.isEmpty()) {
			mv.addObject("LessonError", "Nothing To Show");
		}

		if (localvideo.isEmpty()) {
			mv.addObject("VideoError", "Nothing To Show");
		}

		if (localPhets.isEmpty()) {
			mv.addObject("PhetError", "Nothing To Show");
		}

		if (localPhets.isEmpty()) {
			mv.addObject("ConceptError", "Nothing To Show");
		}

		mv.addObject("QuizOnUser", localQuiz);
		mv.addObject("VideoOnUser", localvideo);
		mv.addObject("ArticleOnUser", localArticle);
		mv.addObject("DocumentOnUser", localDocument);
		mv.addObject("LessonOnUser", localLesson);
		mv.addObject("PhetOnUser", localPhets);
		mv.addObject("ConceptOnUser", localConcept);

		mv.setViewName("contentView");

		return mv;
	}

	/****************************************
	 * END
	 **************************************************/

	

//------------------------------------------LOGIN HYPERLINK--------------------------------------------------------------------

	@RequestMapping("/Login")
	public ModelAndView login(ModelAndView mv) {
		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);

		ArrayList<Subject> subjectData = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subjectData);

		mv.setViewName("Login");
		return mv;
	}

//---------------------------------------------END--------------------------------------------------------------------

//------------------------------------------- FORGET PASSWORD --------------------------------------------------------

	@RequestMapping("/forgetPassword")
	public ModelAndView forgetPasswordGet(ModelAndView mv) {
		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);

		ArrayList<Subject> subjectData = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subjectData);

		mv.setViewName("forgetPassword");
		return mv;

	}

	/*------------------- FORGET PASSWORD TAKIN REQUEST FROM VIEW -----------------------------------------*/

	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public ModelAndView forgetPasswordPost(HttpServletRequest req, ModelAndView mv) {
		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);

		ArrayList<Subject> subjectData = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subjectData);

		String userEmail = req.getParameter("username");

		User usr = userService.findByUsername(userEmail);
		if (usr == null) {
			mv.addObject("Error", "E-mail doesn't Exist");
			mv.setViewName("forgetPassword");
			return mv;
		}

		String token = UUID.randomUUID().toString();
		usr.setToken(token);

		userService.save(usr);

		String appUrl = "http://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
		System.out.println(appUrl);
		SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, req.getLocale(), token, usr);

		mailSender.send(newEmail);

		mv.addObject("Success", "Link to reset password has been sent to your E-mail ID");

		mv.setViewName("forgetPassword");
		return mv;

	}

	/*--------------------------- LINK TO WHICH USER WILL SET NEW PASSWORD ----------------------------------*/

	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView resetPasswordGet(ModelAndView mv, @RequestParam("token") String token) {
		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);

		ArrayList<Subject> subjectData = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subjectData);

		User usr = userService.findByToken(token);
		if (usr == null) {
			mv.setViewName("redirect:/");
			return mv;
		}

		System.out.println(token);
		mv.addObject("resetToken", usr.getToken());
		mv.setViewName("resetPassword");
		return mv;

	}

	/*---------------------------- PERSISTING NEW PASSWORD OF USER ----------------------------------*/

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ModelAndView resetPasswordPost(ModelAndView mv, HttpServletRequest req) {

		String newPassword = req.getParameter("Password");
		String confNewPassword = req.getParameter("Confirm");
		String token = req.getParameter("token");

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);

		ArrayList<Subject> subjectData = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subjectData);

		User usr = userService.findByToken(token);
		if (usr == null) {
			mv.addObject("Error", "Invalid request");
			return mv;
		}

		if (!newPassword.contentEquals(confNewPassword)) {
			mv.addObject("Error", "Both password doesn't match");
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
	public ModelAndView signup(ModelAndView mv) {
		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("checkedOptionLearner", "checked");
		ArrayList<Subject> subject = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subject);
		mv.addObject("classfromDatabase", standard);
		mv.setViewName("Signup");
		return mv;

	}

//---------------------------------------------END--------------------------------------------------------------------

// ---------------------------------- Event List on USer Side ------------------------------------------

	@RequestMapping(value = "/eventsList")
	public ModelAndView viewEvent(ModelAndView mv, @RequestParam(defaultValue = "0") int page) {

		// List<Events> localEvent=eventService.findAll();

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();

		mv.addObject("classfromDatabase", standard);

		ArrayList<Subject> subjectData = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subjectData);

		mv.addObject("EventList", evenRepo.findAll(new PageRequest(page, 6)));
		mv.addObject("current", page);
		mv.setViewName("events");
		return mv;
	}

	/************************************************
	 * START OF CONTROLLER FOR USER SIDE
	 ***********************************************************/

	// COURSES PAGE BASED ON SELECTION OF CLASS AND SUBJECT

	@RequestMapping(value = "/courses", method = RequestMethod.GET)
	public ModelAndView viewCoursesAvailable(HttpServletRequest req,
			@RequestParam(name = "subjectSelected") String subject,
			@RequestParam(name = "classSelected") String classSelected, ModelAndView mv) {
		/* System.out.println("test"+subject); */
		Class localClass = null;

		Subject localSubject = null;
		SubjectClassMapping localSubjectClassMapping;
		List<Topic> localTopictemp = null;

		if (subject.contentEquals("Select Subject") && classSelected.contentEquals("Select Class")) {
			localTopictemp = topicService.findAll();
			System.out.println("all value");

		}

		else if (subject.contentEquals("Select Subject") && !classSelected.contentEquals("Select Class")) {
			System.out.println("wow");
			localClass = classService.findByClassName(Integer.parseInt(classSelected.substring(6)));
			List<SubjectClassMapping> tempLocalSubjectClassMapping = subjectClassService
					.getClassFromMapping(localClass);
			localTopictemp = topicService.findBySubjectClassMppaing(tempLocalSubjectClassMapping);

		} else if (!subject.contentEquals("Select Subject") && classSelected.contentEquals("Select Class")) {
			localSubject = subjectService.findBySubjectName(subject);
			List<SubjectClassMapping> tempLocalSubjectClassMapping = subjectClassService
					.getClassFromSubject(localSubject);
			localTopictemp = topicService.findBySubjectClassMppaing(tempLocalSubjectClassMapping);

		} else if (!subject.contentEquals("Select Subject") && !classSelected.contentEquals("Select Class")) {
			localClass = classService.findByClassName(Integer.parseInt(classSelected.substring(6)));

			localSubject = subjectService.findBySubjectName(subject);

			localSubjectClassMapping = subjectClassService.findBysubAndstandard(localClass, localSubject);

			localTopictemp = topicService.findBysubjectclassMapping(localSubjectClassMapping);

		}

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);

		ArrayList<Subject> subjectData = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subjectData);

		List<Topic> localTopic = new ArrayList<Topic>();
		for (Topic temp : localTopictemp) {
			if (temp.getStatus() == 1) {
				localTopic.add(temp);
			}
		}

		if (!localTopic.isEmpty()) {
			mv.addObject("TopicListOnSubjectClass", localTopic);
		} else {
			mv.addObject("NoTopicAvailable", "No Content Available to Show");
		}

		if (localSubject != null) {
			mv.addObject("subjectSelected", localSubject.getSubName());
		}

		if (localClass != null) {
			mv.addObject("classSelected", localClass.getClassName());
		}

		mv.setViewName("Courses");

		return mv;
	}

	/******
	 * CONTENT PAGE BASED ON CLASS SUBJECT AND TOPIC-------------------------------
	 */

	@RequestMapping(path = "/contentTutorial/{topicId}", method = RequestMethod.GET)
	public ModelAndView viewTutorialOnTopic(@PathVariable("topicId") int topicId, ModelAndView mv) {

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);

		ArrayList<Subject> subjectData = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subjectData);

		Topic localTopic = topicService.findById(topicId);

		List<Tutorial> localTutorial = tutorialService.findAllByTopicAndStatus(localTopic);

		List<TutorialList> tutorialListData = new ArrayList<TutorialList>();

		for (Tutorial localTemp : localTutorial) {

			try {
				String url = "https://spoken-tutorial.org/api/get_tutorialdetails/" + localTemp.getStVideoId() + "/";
				RestTemplate restTemp = new RestTemplate();
				TutorialList localTut = restTemp.getForObject(url, TutorialList.class);

				System.out.println(localTut.getTut_name());

				tutorialListData.add(localTut);
			} catch (RestClientException e) {

				e.printStackTrace();
			}
		}

		if (tutorialListData.isEmpty()) {
			mv.addObject("TutorialError", "Nothing To Show");
		}

		mv.addObject("TutorialOnTopic", tutorialListData);

		mv.addObject("subjectSelected", localTopic.getSubjectClassMapping().getSub().getSubName());
		mv.addObject("classSelected", localTopic.getSubjectClassMapping().getStandard().getClassName());
		mv.addObject("TopicSelected", localTopic);
		mv.setViewName("contentTutorial");
		return mv;
	}

	@RequestMapping(path = "/contentLink/{topicId}", method = RequestMethod.GET)
	public ModelAndView viewLinksOnTopic(@PathVariable("topicId") int topicId, ModelAndView mv) {

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);

		ArrayList<Subject> subjectData = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subjectData);

		Topic localTopic = topicService.findById(topicId);

		List<ArticleExternal> localArticle = articleService.findAllByTopicAndStatus(localTopic);
		List<DocumentExternal> localDocument = documentService.findAllByTopicAndStatus(localTopic);
		List<VideoExternal> localvideo = videoService.findAllByTopicAndStatus(localTopic);

		if (localArticle.isEmpty()) {
			;
			mv.addObject("ArticleError", "Nothing To Show");
		}

		if (localDocument.isEmpty()) {
			mv.addObject("DocumentError", "Nothing To Show");
		}

		if (localvideo.isEmpty()) {
			mv.addObject("VideoError", "Nothing To Show");
		}

		mv.addObject("VideoOnTopic", localvideo);
		mv.addObject("ArticleOnTopic", localArticle);
		mv.addObject("DocumentOnTopic", localDocument);

		mv.addObject("subjectSelected", localTopic.getSubjectClassMapping().getSub().getSubName());
		mv.addObject("classSelected", localTopic.getSubjectClassMapping().getStandard().getClassName());
		mv.addObject("TopicSelected", localTopic);
		mv.setViewName("contentLinks");
		return mv;
	}

	@RequestMapping(path = "/contentLesson/{topicId}", method = RequestMethod.GET)
	public ModelAndView viewLessonPlanlOnTopic(@PathVariable("topicId") int topicId, ModelAndView mv) {

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);

		ArrayList<Subject> subjectData = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subjectData);

		Topic localTopic = topicService.findById(topicId);

		List<LessonPlan> localLesson = lessonService.findAllByTopicAndStatus(localTopic);

		if (localLesson.isEmpty()) {
			mv.addObject("LessonError", "Nothing To Show");
		}

		mv.addObject("LessonOnTopic", localLesson);

		mv.addObject("subjectSelected", localTopic.getSubjectClassMapping().getSub().getSubName());
		mv.addObject("classSelected", localTopic.getSubjectClassMapping().getStandard().getClassName());
		mv.addObject("TopicSelected", localTopic);
		mv.setViewName("contentLessonPlan");
		return mv;
	}

	@RequestMapping(path = "/contentPhet/{topicId}", method = RequestMethod.GET)
	public ModelAndView viewPhetOnTopic(@PathVariable("topicId") int topicId, ModelAndView mv) {

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);

		ArrayList<Subject> subjectData = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subjectData);

		Topic localTopic = topicService.findById(topicId);

		List<Phets> localPhets = phetService.findAllByTopicAndStatus(localTopic);

		if (localPhets.isEmpty()) {
			mv.addObject("PhetError", "Nothing To Show");
		}

		mv.addObject("PhetOnTopic", localPhets);

		mv.addObject("subjectSelected", localTopic.getSubjectClassMapping().getSub().getSubName());
		mv.addObject("classSelected", localTopic.getSubjectClassMapping().getStandard().getClassName());
		mv.addObject("TopicSelected", localTopic);
		mv.setViewName("contentPhet");
		return mv;
	}

	@RequestMapping(path = "/contentQuiz/{topicId}", method = RequestMethod.GET)
	public ModelAndView viewQuizOnTopic(@PathVariable("topicId") int topicId, ModelAndView mv) {

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);

		ArrayList<Subject> subjectData = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subjectData);

		Topic localTopic = topicService.findById(topicId);
		List<QuizQuestion> localQuiz = quizService.findAllByTopicAndStatus(localTopic);

		if (localQuiz.isEmpty()) {
			mv.addObject("QuizError", "Nothing To Show");
		}

		mv.addObject("QuizOnTopic", localQuiz);

		mv.addObject("subjectSelected", localTopic.getSubjectClassMapping().getSub().getSubName());
		mv.addObject("classSelected", localTopic.getSubjectClassMapping().getStandard().getClassName());
		mv.addObject("TopicSelected", localTopic);
		mv.setViewName("contentQuiz");
		return mv;
	}

	@RequestMapping(path = "/contentConcept/{topicId}", method = RequestMethod.GET)
	public ModelAndView viewContentonTopic(@PathVariable("topicId") int topicId, ModelAndView mv) {

		ArrayList<Class> standard = (ArrayList<Class>) classService.findAll();
		mv.addObject("classfromDatabase", standard);

		ArrayList<Subject> subjectData = (ArrayList<Subject>) subjectService.findAll();
		mv.addObject("subjectfromDatabase", subjectData);

		Topic localTopic = topicService.findById(topicId);

		List<ConceptMap> localConcept = conceptMapService.findAllByTopicAndStatus(localTopic);

		if (localConcept.isEmpty()) {
			mv.addObject("ConceptError", "Nothing To Show");
		}

		mv.addObject("ConceptOnTopic", localConcept);

		mv.addObject("subjectSelected", localTopic.getSubjectClassMapping().getSub().getSubName());
		mv.addObject("classSelected", localTopic.getSubjectClassMapping().getStandard().getClassName());
		mv.addObject("TopicSelected", localTopic);
		mv.setViewName("contentConceptMap");
		return mv;
	}


	@RequestMapping(path = "/eventsList/home", method = RequestMethod.GET)
	public ModelAndView pathtoAdminDashboardEvent(ModelAndView mv) {
		mv.setViewName("redirect:/home");
		return mv;
	}

	// ------------------ MY MIND
	// HYPERLINK***********************************************

	@RequestMapping(path = "/my-mind", method = RequestMethod.GET)
	public ModelAndView mindMapGet(ModelAndView mv) {
		mv.setViewName("my-mind");
		return mv;
	}

	/************************************** Testimonial Page *****************************************************/
	
	@RequestMapping(path = "testimonials", method = RequestMethod.GET)
	public ModelAndView testimonialPage(ModelAndView mv) {
		
		List<Testimonial> textTestimonial=new ArrayList<>();
		List<Testimonial> mediaTestimonial=new ArrayList<>();
		List<Testimonial> fileTestimonial=new ArrayList<>();
		List<Testimonial> localTestimonial=testiService.findAll();
		
		for(Testimonial temp:localTestimonial) {
			if(temp.getVideoPath() != null) {
				
				mediaTestimonial.add(temp);
			}else if(temp.getFilePath() !=null){
				
				fileTestimonial.add(temp);
			}else {
				textTestimonial.add(temp);
			}
		}
		
		mv.addObject("textTestimonial",textTestimonial);
		mv.addObject("mediaTestimonial",mediaTestimonial);
		mv.addObject("fileTestimonial",fileTestimonial);
		mv.addObject("textActive","active");
		mv.setViewName("testimonial");
		return mv;
	}
	/*--------------------------------------------------------END----------------------------------------------------------------------------------*/
}

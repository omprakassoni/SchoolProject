package com.adminportal;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Class;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Subject;
import com.adminportal.content.SubjectClassMapping;
import com.adminportal.content.Topic;
import com.adminportal.content.VideoExternal;
import com.adminportal.domain.User;
import com.adminportal.service.ArticleExternalService;
import com.adminportal.service.ClassService;
import com.adminportal.service.ConceptMapService;
import com.adminportal.service.ContactFormService;
import com.adminportal.service.DocumentExternalService;
import com.adminportal.service.EventService;
import com.adminportal.service.LessonPlanService;
import com.adminportal.service.PhetsService;
import com.adminportal.service.QuizQuestionService;
import com.adminportal.service.SubjectClassService;
import com.adminportal.service.SubjectService;
import com.adminportal.service.TestimonialService;
import com.adminportal.service.TopicService;
import com.adminportal.service.TutorialService;
import com.adminportal.service.UserService;
import com.adminportal.service.VideoExternalService;
import com.spoken.Utility.ServiceUtility;

/**
 * This Controller add all the resources from Teacher Role's User
 * @author om prakash
 *
 */
@Controller
public class ContributorController {
	
	/**
	 * path to store resources
	 */
	public static final String uploadDirectory="Media/content/";  /* path to which content will get stored */
	/**
	 * Path to store Event data
	 */
	public static final String uploadEvent="Media/Event/";
	/**
	 * SIze of Video file
	 */
	public static final long videoSize=50*1024*1024;                     // 50 MB vidoe File max
	/**
	 * SIze of normal file
	 */
	public static final long fileSize=10*1024*1024;                      // 10 MB max file   max
	/**
	 * Path to store Testimonial data
	 */
	public static final String uploadTestimonial="Media/Testimonial/";
	/**
	 * Duration of Video
	 */
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
	
	
	/**
	 * add video resource to app (iframe data) from Teacher's Role
	 * @param req HttpServletRequest object
	 * @param principal A principal object
	 * @param mv ModelAndView object
	 * @return ModelAndView object
	 */
	// method to add Video into database
		@RequestMapping(value="/conVideo/addVideo",method = RequestMethod.POST)
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
					
					mv.setViewName("video");
					
					return mv;
					
					
					
				}
				
			}else {
				
			}

			int videoId=videoService.countRow()+1;
			try {

				Class localClass=classService.findByClassName(Integer.parseInt(className));
				Subject localSubject=subjectService.findBysubName(subjectName);
				SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
				Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
				
				User usr=userService.findByUsername(principal.getName());
				
				Set<VideoExternal> videoMapping=new HashSet<VideoExternal>();
				videoMapping.add(new VideoExternal(videoId, "Video", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, videourl, 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));
				
				userService.addUserToVideo(usr, videoMapping);							// persist Video Information
				mv.addObject("statusAdd", "Added Successfully");
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
				VideoExternal videoTemp=videoService.findById(videoId);
				videoService.deleteVideo(videoTemp);
				mv.addObject("failure", "Please Try Again Later");
			}

			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.

			mv.addObject("classExist", classExist);										// setting variable for view for displaying purpose
			
			mv.setViewName("video");													// setting view name
				
			
			
			return mv;
			
		}
		
		/**
		 * add Video resource to app from Teacher's Role
		 * @param video video file to be added
		 * @param req HttpServletRequest object
		 * @param principal A principal object
		 * @param mv ModelAndView object
		 * @return ModelAndView object
		 */
		@RequestMapping(value="/conVideo/addVideoUpload",method = RequestMethod.POST)
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
				
				mv.setViewName("video");													// setting view name
					
				
				
				return mv;
				
			}
			
			
			if(video.getSize()>videoSize) {
				
				mv.addObject("failure", "Please upload file size within 50MB");
				
				ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.

				mv.addObject("classExist", classExist);										// setting variable for view for displaying purpose
				
				mv.setViewName("video");													// setting view name
					
				
				
				return mv;
				
			}
			
			int videoId=videoService.countRow()+1;
			try {
				
				Class localClass=classService.findByClassName(Integer.parseInt(className));
				Subject localSubject=subjectService.findBysubName(subjectName);
				SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
				Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
				
				User usr=userService.findByUsername(principal.getName());
				
				Set<VideoExternal> videoMapping=new HashSet<VideoExternal>();
				videoMapping.add(new VideoExternal(videoId, "Video", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, "null", 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));
				
				userService.addUserToVideo(usr, videoMapping);							// persist Video Information
				
				String createFolder=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+localSubject.getSubId()+"/"+localTopic.getTopicId()+"/Video/"+videoId+"/";  // path to save video
				
				boolean ques=ServiceUtility.createFolder(createFolder);
				
				if(ques) {
				
				path1=ServiceUtility.uploadVideoFile(video, createFolder);
				
				int indexToStart=path1.indexOf("Media");										// extracting proper Path from Actual path
				String path=path1.substring(indexToStart, path1.length());
				
				VideoExternal videoTemp=videoService.findById(videoId);
				videoTemp.setUrl(path);
				videoService.save(videoTemp);
				
				mv.addObject("statusAdd", "Added Successfully");
				}else {
					VideoExternal videoTemp=videoService.findById(videoId);
					videoService.deleteVideo(videoTemp);
					mv.addObject("failure", "Please Try Again Later");
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
				VideoExternal videoTemp=videoService.findById(videoId);
				videoService.deleteVideo(videoTemp);
				mv.addObject("failure", "Please Try Again Later");
			}

			
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.

			mv.addObject("classExist", classExist);										// setting variable for view for displaying purpose
			
			mv.setViewName("video");													// setting view name
				
			
			
			return mv;
			
		}
		
		/*----------------------------------------------------------END----------------------------------------------------------------------------*/
		/**
		 * add Quiz data to app from Teacher's Role
		 * @param req HttpServletRequest object
		 * @param question question file to be added
		 * @param answer answer file to be added
		 * @param principal A principal object
		 * @param mv ModelAndView object
		 * @return ModelAndView object
		 */
		// method to add Quiz into database
		@RequestMapping(value="/conQuiz/addQuiz",method = RequestMethod.POST)
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
					
				mv.setViewName("quiz");
				
				return mv;
				
			
				
			}
			
			if(!ServiceUtility.checkFileExtensionPDF(answer)) {								// check for PDF file
				
				ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

				mv.addObject("classExist", classExist);

				mv.addObject("fileError", "Please Add only Pdf File");
					
				mv.setViewName("quiz");
				
				return mv;
				
				
			}
			
			if(question[0].getSize()>fileSize || answer[0].getSize()>fileSize) {
				
				ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

				mv.addObject("classExist", classExist);

				mv.addObject("fileError", "FileSize must be within 10MB");
					
				mv.setViewName("quiz");
				
				return mv;
			}
			int quizId=quizService.countRow()+1;
			try {
			
			Class localClass=classService.findByClassName(Integer.parseInt(className));
			Subject localSubject=subjectService.findBysubName(subjectName);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
			
			
			User usr=userService.findByUsername(principal.getName());
			
			Set<QuizQuestion> quizMapping=new HashSet<QuizQuestion>();
			quizMapping.add(new QuizQuestion(quizId,"Quiz",ServiceUtility.getCurrentTime(),ServiceUtility.getCurrentTime(),"null","null",0,0,remarks,ServiceUtility.getCurrentTime(),localTopic,usr));
			
			userService.addUserToQuizQuestion(usr, quizMapping);   // persist Quiz details
			
			String createFolder=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+localSubject.getSubId()+"/"+localTopic.getTopicId()+"/Quiz/"+quizId+"/";  // path to save question and answer
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
				
				QuizQuestion quizTemp=quizService.findById(quizId);
				quizTemp.setAnswer(pathAnswer);
				quizTemp.setQuestion(pathQuestion);
				
				quizService.save(quizTemp);
				
				
				
				mv.addObject("statusAdd", "Added Successfully");
				
				}else {
					
					QuizQuestion quizTemp=quizService.findById(quizId);
					quizService.deleteQuiz(quizTemp);
					mv.addObject("failure", " Try again Later");
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				QuizQuestion quizTemp=quizService.findById(quizId);
				quizService.deleteQuiz(quizTemp);
				
				mv.addObject("failure", " Try again Later");
				
			}
			
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.

			mv.addObject("classExist", classExist);										// setting variable for view for displaying purpose
			
			mv.addObject("quizStatus", "Added Successfully");
			
			mv.setViewName("quiz");													// setting view name
			
			return mv;
			
			
		}
		
		/**
		 * add conceptMap data to app from Teacher's Role
		 * @param req HttpServletRequest object
		 * @param conceptMapImage file to be added
		 * @param principal A principal object
		 * @param mv ModelAndView object
		 * @return ModelAndView object
		 */
		// method to add Concept -MAp into database
		@RequestMapping(value = "/conConceptMap/addConceptMap",method = RequestMethod.POST)
		public ModelAndView addConceptMapPost(HttpServletRequest req,Principal principal,@RequestParam(name="conceptMapImage")MultipartFile[] conceptMapImage,ModelAndView mv) {
			
			String path1=null;
			String className=req.getParameter("classSelected");				// taking out various information given by user in view.
			String subjectName=req.getParameter("subjectSelected");
			String topicName=req.getParameter("topicSelected");
			String desc=req.getParameter("descriptionConceptMap");
			String remark=req.getParameter("headlineConceptMap");
			
			User localUser=userService.findByUsername(principal.getName());
			
			mv.addObject("LoggedUser",localUser);
			
			if(!ServiceUtility.checkFileExtensionImage(conceptMapImage) && !ServiceUtility.checkFileExtensionZip(conceptMapImage)) {    // check Image file
				mv.addObject("fileError", "File must be a Image File or Html Page");
				
				ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

				mv.addObject("classExist", classExist);
				
				mv.setViewName("conceptMap");
				
				return mv;
				
			}
			
			if(conceptMapImage[0].getSize()>fileSize) {
				
				mv.addObject("fileError", "FileSize must be within 10MB");
				
				ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

				mv.addObject("classExist", classExist);
				
				mv.setViewName("conceptMap");
				
				return mv;
			}
			
			
			int conceptID=conceptService.countRow()+1;
			try {
				
				Class localClass=classService.findByClassName(Integer.parseInt(className));
				Subject localSubject=subjectService.findBysubName(subjectName);
				SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
				Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
				
				
				User usr=userService.findByUsername(principal.getName());
				
				Set<ConceptMap> conceptMapping=new HashSet<ConceptMap>();
				conceptMapping.add(new ConceptMap(conceptID, "ConceptMap", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "null", desc, 0,0, remark, localTopic, usr));
				
				userService.addUserToConceptMap(usr, conceptMapping);						// persist Concept-map

				String createFolder=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+localSubject.getSubId()+"/"+localTopic.getTopicId()+"/ConceptMap/"+conceptID+"/";  // path to store Concept-map
				
				ServiceUtility.createFolder(createFolder);
				
				if(ServiceUtility.checkFileExtensionImage(conceptMapImage)) {
					path1=ServiceUtility.uploadFile(conceptMapImage, createFolder);
				}else {
					path1=ServiceUtility.uploadZipFile(conceptMapImage, createFolder);
				}
				
				int indexToStart=path1.indexOf("Media");										// extracting proper Path from Actual path
				String path=path1.substring(indexToStart, path1.length());
				
				ConceptMap concepTemp=conceptService.findByid(conceptID);
				concepTemp.setUrl(path);
				
				conceptService.save(concepTemp);
				

				mv.addObject("statusAdd", "Concept-Map Added Successfully");
				
			} catch (Exception e) {
				
				e.printStackTrace();
				ConceptMap conceptTemp=conceptService.findByid(conceptID);
				conceptService.deleteConceptMap(conceptTemp);
				mv.addObject("failure", "Please Try Again");
			}

			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

			mv.addObject("classExist", classExist);											// setting variable for view for displaying purpose
			
			mv.setViewName("conceptMap");												// setting view name
			
			return mv;
		}
		
		/**
		 * Add phet resource to app from Teacher's Role
		 * @param req HttpServletRequest object
		 * @param principal A principal object
		 * @param mv ModelAndView object
		 * @return ModelAndView object
		 */
		// method to add Phets into database
		@RequestMapping(value="/conPhet/addPhets",method = RequestMethod.POST)
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
					
					mv.addObject("fileError", "Please specify Embed Code");
					
					mv.setViewName("phets");
					
					User localUser=userService.findByUsername(principal.getName());
					
					mv.addObject("LoggedUser",localUser);
					
					return mv;
					
					
				}
				
			}else {
				
				ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

				mv.addObject("classExist", classExist);
				
				mv.addObject("failure", "Please Try Again");
				
				mv.setViewName("phets");
				
				User localUser=userService.findByUsername(principal.getName());
				
				mv.addObject("LoggedUser",localUser);
				
				return mv;
				
				
			}
			
				int phetId=phetService.countRow()+1;
				try {

					Class localClass=classService.findByClassName(Integer.parseInt(className));
					Subject localSubject=subjectService.findBysubName(subjectName);
					SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
					Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
					
					
					User usr=userService.findByUsername(principal.getName());				// retrive Logged In User
					
					
					Set<Phets> phetMapping=new HashSet<Phets>();
					phetMapping.add(new Phets(phetId, "Phets", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, phetPath, 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));

					
					userService.addUserToPhets(usr, phetMapping);							// persist Phet data 
					mv.addObject("statusAdd", "Added Successfully");
					
					
					} catch (Exception e) {
					
					e.printStackTrace();
					Phets phetTemp=phetService.findByid(phetId);
					phetService.deletePhet(phetTemp);
					mv.addObject("failure", "Please Try Again");
					
					
					}
					
			
			
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

			mv.addObject("classExist", classExist);											// setting variable for view for displaying purpose
			
			mv.setViewName("phets");														// setting view name
			
			User localUser=userService.findByUsername(principal.getName());
			
			mv.addObject("LoggedUser",localUser);
			
			return mv;
		}
		
		/**
		 * adds Lesson resource to app from Teacher's Role
		 * @param req HttpServletRequest object
		 * @param lesson file to be added
		 * @param principal A principal object
		 * @param mv ModelAndView object
		 * @return ModelAndView object
		 */
		// method to add Lesson Plan into database
		@RequestMapping(value="/conLessonPlan/addLessonPlan",method = RequestMethod.POST)
		public ModelAndView addLessonPlanPost(HttpServletRequest req,@RequestParam(name="lesson") MultipartFile[] lesson,Principal principal,ModelAndView mv) {
			
			String path1=null;
			String className=req.getParameter("classSelected");						// taking out various information given by user in view.
			String subjectName=req.getParameter("subjectSelected");
			String topicName=req.getParameter("topicSelected");
			
			if(!ServiceUtility.checkFileExtensionPDF(lesson)) {							// validation against PDF document 
				
				mv.addObject("fileError", "Please Select PDF file");
					
				ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

				mv.addObject("classExist", classExist);										// setting variable for view for displaying purpose
				
				mv.setViewName("lessonPlan");											// setting view name
				
				User localUser=userService.findByUsername(principal.getName());
				
				mv.addObject("LoggedUser",localUser);
				
				return mv;
				
			}
			
			if(lesson[0].getSize()>fileSize) {
				
				mv.addObject("fileError", "FileSize must be within 10MB");
				
				ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();

				mv.addObject("classExist", classExist);										// setting variable for view for displaying purpose
				
				mv.setViewName("lessonPlan");											// setting view name
				
				User localUser=userService.findByUsername(principal.getName());
				
				mv.addObject("LoggedUser",localUser);
				
				return mv;
			}
			
			int lessonId=lessonService.countRow()+1;
			try {
				
				Class localClass=classService.findByClassName(Integer.parseInt(className));						
				Subject localSubject=subjectService.findBysubName(subjectName);
				SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
				Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);
				
				
				
				User usr=userService.findByUsername(principal.getName());				// retrive logged in user
				
				Set<LessonPlan> lessonMapping=new HashSet<LessonPlan>();
				lessonMapping.add(new LessonPlan(lessonId, "Lesson", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), "null", 0, 0,ServiceUtility.getCurrentTime(), localTopic, usr));
				
				
				userService.addUserToLessonplan(usr, lessonMapping);					// saving lessonPaln data
				
				String createFolder=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+localSubject.getSubId()+"/"+localTopic.getTopicId()+"/Lessonplan/"+lessonId+"/";  // path to store lesson Plan
				
				ServiceUtility.createFolder(createFolder);
				
				path1=ServiceUtility.uploadFile(lesson, createFolder);					// uploading lesson plan to path given
				
				int indexToStart=path1.indexOf("Media");									// extract path starting from MEDIA to persist .
				String path=path1.substring(indexToStart, path1.length());
				
				LessonPlan lessonTemp=lessonService.findById(lessonId);
				lessonTemp.setLessonPlan(path);
				
				lessonService.save(lessonTemp);
				
				mv.addObject("statusAdd", "Added Successfully");
				
			} catch (Exception e) {
			
				e.printStackTrace();
				LessonPlan lessonTemp=lessonService.findById(lessonId);
				lessonService.deleteLessonPlan(lessonTemp);
				mv.addObject("failure", "Please Try Again");
				
			}

			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

			mv.addObject("classExist", classExist);											// setting variable for view for displaying purpose
			
			mv.addObject("lessonStatus", "Added Successfully");	
				
			mv.setViewName("lessonPlan");												// setting view name
				
			User localUser=userService.findByUsername(principal.getName());
			
			mv.addObject("LoggedUser",localUser);
			
		
			return mv;
			
		}
		

		/**
		 * Adds Document resource to app from Teacher's Role
		 * @param req HttpServletRequest object
		 * @param document File to be uploaded
		 * @param principal A principal object
		 * @param mv ModelAndView object
		 * @return ModelAndView object
		 */
		// method to add Document into database
		@RequestMapping(value="/conDocument/addDocument",method = RequestMethod.POST)
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
				
				mv.setViewName("document");										// setting view name
				
				User localUser=userService.findByUsername(principal.getName());
				
				mv.addObject("LoggedUser",localUser);
				
				return mv;
				
			}
			
			if(document[0].getSize()>fileSize) {
				
				mv.addObject("fileError", "FileSize must be within 10MB");
				
				ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();		// fetching out the available list of class from database.

				mv.addObject("classExist", classExist);								// setting variable for view for displaying purpose
				
				mv.setViewName("document");										// setting view name
				
				User localUser=userService.findByUsername(principal.getName());
				
				mv.addObject("LoggedUser",localUser);
				
				return mv;
			}
					
			int documentId=documentService.countRow()+1;	
			try {
				
				Class localClass=classService.findByClassName(Integer.parseInt(className));									// retrieving class modal
				Subject localSubject=subjectService.findBysubName(subjectName);								// retrieving subject modal
				SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject); // retrieving subject class mapping from class and subject
				Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);	// retrieving topic from sucject class mapping
				
				
				User usr=userService.findByUsername(principal.getName());						// retrieving the logged USer
				
				Set<DocumentExternal> documentMapping=new HashSet<DocumentExternal>();
				documentMapping.add(new DocumentExternal(documentId, "Document", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, "null", 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));

				userService.addUserToDocument(usr, documentMapping);					// saving document data into database.		
				
				String createFolder=env.getProperty("spring.applicationexternalPath.name")+uploadDirectory+className+"_"+localSubject.getSubId()+"/"+localTopic.getTopicId()+"/Document/"+documentId+"/";	// PATH TO SAVE DOCUMENT UNDER TOPIC
				
				ServiceUtility.createFolder(createFolder);
				
				path1=ServiceUtility.uploadFile(document, createFolder);									// 	upload file to path mentioned
				
				int indexToStart=path1.indexOf("Media");														// extracting path starting from MEDIA to save in database
				String path=path1.substring(indexToStart, path1.length());
				
				DocumentExternal docuTemp=documentService.findByid(documentId);
				docuTemp.setUrl(path);
				
				documentService.save(docuTemp);
				
				mv.addObject("statusAdd", "Document Added Successfully");
				
			} catch (Exception e) {
				
				e.printStackTrace();
				DocumentExternal documentTemp=documentService.findByid(documentId);
				documentService.deleteDocuemnt(documentTemp);
				mv.addObject("failure", "Please Try Again");
			}


			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();			// fetching out the available list of class from database.

			mv.addObject("classExist", classExist);											// setting variable for view for displaying purpose
			
			mv.setViewName("document");													// setting view name
			
			User localUser=userService.findByUsername(principal.getName());
			
			mv.addObject("LoggedUser",localUser);
			
			return mv;
			
		}
		
		
		// method to add Article entry into database
		/**
		 * Adds Article resource to app from Teacher's Role
		 * @param req HttpServletRequest object
		 * @param principal A principal object
		 * @param mv ModelAndView object
		 * @return ModelAndView object
		 */
		@RequestMapping(value="/conArticle/addArticle",method = RequestMethod.POST)
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
				
				mv.setViewName("article");
				
				User localUser=userService.findByUsername(principal.getName());
				
				mv.addObject("LoggedUser",localUser);
				
				return mv;
				
			}
			
			int articleId=articleService.countRow()+1;
			try {
			
				Class localClass=classService.findByClassName(Integer.parseInt(className));									// fetching class tuple based on given data
				Subject localSubject=subjectService.findBysubName(subjectName);								// fetching Subject tuple based on given data
				SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);			// fetching Subject Class mapping tuple based on class and subject
				Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicName);					// fetching topic tuple based on given data subject class mapping.
				
				User usr=userService.findByUsername(principal.getName());						// logged in user details.
				Set<ArticleExternal> articlemapping=new HashSet<ArticleExternal>();
				articlemapping.add(new ArticleExternal(articleId, "Article", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, url, 0,0,  ServiceUtility.getCurrentTime(), localTopic, usr));
				
				userService.addUserToArticle(usr, articlemapping);								// saving the article information into database.
				
				mv.addObject("statusAdd", "Added Successfully");									// setting status for view(success)
				
			} catch (Exception e) {
				
				e.printStackTrace();
				ArticleExternal tempArticle=articleService.findByid(articleId);
				articleService.deleteArticle(tempArticle);
				mv.addObject("failure", "Please try Again Later");								// setting status for view(failure)
				
			}
			
			
			ArrayList<Class> classExist=(ArrayList<Class>) classService.findAll();				// fetching out the available list of class from database.

			mv.addObject("classExist", classExist);												// setting variable for view for displaying purpose

			
			mv.setViewName("article");	// setting view name
			
			User localUser=userService.findByUsername(principal.getName());
			
			mv.addObject("LoggedUser",localUser);
			
			return mv;
			
		}
		
		
}

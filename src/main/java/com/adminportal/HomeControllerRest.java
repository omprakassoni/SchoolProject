/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.tomcat.jni.File;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.ls.LSInput;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Class;
import com.adminportal.content.Comment;
import com.adminportal.content.CommentReply;
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
import com.adminportal.domain.RoleDetail;
import com.adminportal.domain.User;
import com.adminportal.domain.UserRole;
import com.adminportal.repository.ClassRepository;
import com.adminportal.repository.ContactFormRepository;
import com.adminportal.repository.SubjectClassMappingRepository;
import com.adminportal.repository.SubjectRepository;
import com.adminportal.repository.TopicRepository;
import com.adminportal.service.ArticleExternalService;
import com.adminportal.service.ClassService;
import com.adminportal.service.CommentReplyService;
import com.adminportal.service.CommentService;
import com.adminportal.service.ConceptMapService;
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
import com.spoken.Utility.CommentAjaxQueryResolver;
import com.spoken.Utility.CommentReplyAjaxQueryResolver;
import com.spoken.Utility.ServiceUtility;
import com.spoken.Utility.SubjectAjaxQueryResolver;
import com.spoken.Utility.SubjectClassAjaxQueryResolver;
import com.spoken.Utility.passwordUpdateAjaxQueryResolver;

import antlr.debug.Event;

@RestController
public class HomeControllerRest {
	
	public static String uploadDirectory="src/main/resources/static"+"/Media/content/";
	
	public static String deleteDirectory="src/main/resources/static/";
		
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
	private QuizQuestionService quizService;
	
	@Autowired	
	private VideoExternalService videoService;
	
	@Autowired
	private ArticleExternalService articleService;
	
	@Autowired
	private DocumentExternalService docuService;
	
	@Autowired
	private PhetsService phetServcie;
	
	@Autowired
	private CommentService comService;
	
	@Autowired
	private LessonPlanService lessonService;
	
	@Autowired
	private CommentReplyService comReplyService;
	
	@Autowired
	private ContactFormRepository contactFormService;
	
	@Autowired
	private TestimonialService testiService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private ConceptMapService concepMapService;
	
	@Autowired
	private TutorialService tutorialService;
	
	/*--------------------------------------TAKING CONTACT FORM DATA FROM INDEX PAGE ------------------------------------------------------*/
	@PostMapping("/addContactForm")
	public @ResponseBody List<String> addContactData(@Valid @RequestBody ContactForm contactData){
		List<String> status=new ArrayList<String>();
		
		
		
		try {
			ContactForm addLocal=new ContactForm();
			addLocal.setContactId((int) (contactFormService.count()+1));
			addLocal.setDataAdded(ServiceUtility.getCurrentTime());
			addLocal.setEmail(contactData.getEmail());
			addLocal.setMessage(contactData.getMessage());
			addLocal.setName(contactData.getName());
			
			contactFormService.save(addLocal);
			status.add("Success");
			
		} catch (Exception e) {
			
			status.add("Failure");
			e.printStackTrace();
		}
				
				
				
		return status;
	}
	
	
	
	
	/*-----------------------------------------------------END------------------------------------------------------------------------------*/
	
	/*---------------------------------------------LOADING DATA INTO MODAL FROM TESTIMIONIAL ID -----------------------------------------------*/
	
	@PostMapping("/loadByTestimonialID")
	public @ResponseBody Testimonial loadByTestimonial(@Valid @RequestBody Testimonial localdata) {
		
		Testimonial temp=testiService.getbyId(localdata.getTestimonialId());
		
		return temp;
		
	}
	
	@PostMapping("/loadByEventID")
	public @ResponseBody Events loadByevent(@Valid @RequestBody Events localdata) {
		
		Events temp=eventService.getbyid(localdata.getEventId());
		
		return temp;
		
	}
	
	@PostMapping("/updateTestimonial")
	public @ResponseBody List<String> updateTestimonial(@Valid @RequestBody Testimonial localdata){
		
		List<String> status=new ArrayList<String>();
		
		Testimonial tempTest=testiService.getbyId(localdata.getTestimonialId());
		String name;
		String desc;
		String org;
		
		if(localdata.getName().isEmpty()) {
			name=tempTest.getName();
		}else {
			name=localdata.getName();
		}
		
		if(localdata.getOrganization().isEmpty()) {
			org=tempTest.getOrganization();
		}else {
			org=localdata.getOrganization();
		}
		
		if(localdata.getDescription().isEmpty()) {
			desc=tempTest.getDescription();
		}else {
			desc=localdata.getDescription();
		}
		
		try {
			boolean result=testiService.updateTestimonial(name, desc, org, tempTest.getTestimonialId());
					
			if(result) {
				status.add("Success");
			}else {
				status.add("Failure");
			}
			
		}catch (Exception e) {
			status.add("Failure");
			
		}
		
		return status;
		
	}
	
	
	
	@PostMapping("/updateEvent")
	public @ResponseBody List<String> updateEvent(@Valid @RequestBody Events localdata){
		
		List<String> status=new ArrayList<String>();
		
		Events tempTest=eventService.getbyid(localdata.getEventId());
		String headline;
		String desc;
		java.sql.Date date;
		
		
		if(localdata.getHeadline().isEmpty()) {
			headline=tempTest.getHeadline();
		}else {
			headline=localdata.getHeadline();
		}
		
		if(localdata.getDateToHappen() == null) {
			date=tempTest.getDateToHappen();
		}else {
			date=localdata.getDateToHappen();
			
		}
		
		if(localdata.getDescription().isEmpty()) {
			desc=tempTest.getDescription();
		}else {
			desc=localdata.getDescription();
		}
		
		try {
			boolean result=eventService.updateEvent(headline, desc, date, tempTest.getEventId());
			if(result) {
				status.add("Success");
			}else {
				status.add("Failure");
			}		
			
			
		}catch (Exception e) {
			status.add("Failure");
			
		}
		
		return status;
		
	}
	
	
	
	
	/**************************************************END*********************************************************************************/
	
	
	/********************************************** UPDATE PASSWORD ***************************************************************/
	@PostMapping("/updatePassword")
	public @ResponseBody List<String> updateUserPassword(@Valid @RequestBody passwordUpdateAjaxQueryResolver localpass){
		List<String> status=new ArrayList<String>();
		boolean statusPassword=false;
		
		User usr=userService.findById(localpass.getUserId());
		statusPassword =ServiceUtility.passwordEncoder().matches(localpass.getCurrentPassword(), usr.getPassword());
		
		if(statusPassword) {
		if(userService.updateUserPassword(ServiceUtility.passwordEncoder().encode(localpass.getPassword()), localpass.getUserId())) {
			status.add("Success");
		}else {
			status.add("failure");
		}
		}else {
			status.add("failure");
		}
		
		
		return status;
		
	}
	
	/*******************************************************END*********************************************************************/
	

	
	/*------------------------------------------------VALIDATE EMAIL---------------------------------------------------------------*/
	@PostMapping("/validateEmail")
	public @ResponseBody List<String> validateNewEmail(@Valid @RequestBody List<String> email){
		List<String> status=new ArrayList<String>();
		
		if(userService.existByEmail(email.get(0))) {
			status.add("TRUE");
		}else {
			status.add("FALSE");
		}
		
		
		return status;
		
	}
	
	
	
	
	/*--------------------------------------------------END-------------------------------------------------------------------------*/
	
/*--------------------------------------------------LOAD BY CLASS NAME----------------------------------------------------------------------*/

	@PostMapping("/loadByClassName")
	public @ResponseBody List<String> loadByClassName(@Valid @RequestBody Class classSelected,Model om1 ){
		
		List<String> subjectName=new ArrayList<String>();
		
		Class tempClass=classService.findByClassName(classSelected.getClassName());
		
		System.out.println(tempClass.getClass_id());
		
		List<SubjectClassMapping> temp=(ArrayList<SubjectClassMapping>) subjectClassService.getSubjectFromClass(tempClass);
		
		for(SubjectClassMapping s:temp) {
			System.out.println(s.getSub().getSubName());
			subjectName.add(s.getSub().getSubName());
		}
		
		
		return subjectName;
		
	}
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	
	/*--------------------------------------------------LOAD BY CLASS NAME AND SUBJECT NAME----------------------------------------------------------------------*/
	
	@PostMapping("/loadByClassnameAndSubject")
	public @ResponseBody List<String> loadByClassnameAndSubject(@Valid @RequestBody SubjectClassAjaxQueryResolver subjectClassSelected){
		List<String> topicName=new ArrayList<String>();
		
		
		Class tempClass=classService.findByClassName(subjectClassSelected.getClassName());
	
		Subject tempSubject=subjectService.findBySubjectName(subjectClassSelected.getSubject());
		
		
		SubjectClassMapping tempSubjectClassmapping=subjectClassService.findBysubAndstandard(tempClass,tempSubject);
		
		
		
		List<Topic> tempTopic= topicService.findBysubjectclassMapping(tempSubjectClassmapping);
		
		for(Topic local:tempTopic) {
			topicName.add(local.getTopicName());
		}
	
		
		return topicName;
		
		
		
		
	}
	
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------LOADING USER DETIAILS----------------------------------------------------------------------*/
	
	@PostMapping("/loadByUser")
	public User loadByUser(@Valid @RequestBody User usr){
	
		User localUser=userService.findById(usr.getId());

		localUser.setPassword("0");
		localUser.setUserRoles(null);
		return localUser;
		
	
	}
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------LOAD BY VALIDITY OF USER----------------------------------------------------------------------*/
	
	@PostMapping("/loadByValidity")
	public List<Integer> loadByValidity(@Valid @RequestBody User usr){
		List<Integer> data=new ArrayList<Integer>();
		
		User local=userService.findById(usr.getId());
		data.add(local.getRegistered());
		
		return data;
	}
	
	@PostMapping("/loadByValidityTopic")
	public List<Integer> loadByValidityTopic(@Valid @RequestBody Topic topic){
		List<Integer> data=new ArrayList<Integer>();
		
		Topic local=topicService.findById(topic.getTopicId());
		data.add(local.getStatus());
		
		return data;
	}
	
	@PostMapping("/loadByValidityPhet")
	public List<Integer> loadByValidityPhet(@Valid @RequestBody Phets phet){
		List<Integer> data=new ArrayList<Integer>();
		
		Phets local=phetServcie.findByid(phet.getPhetId());
		data.add(local.isStatus());
		
		return data;
	}
	
	@PostMapping("/loadByValidityDocument")
	public List<Integer> loadByValidityDocument(@Valid @RequestBody DocumentExternal document){
		List<Integer> data=new ArrayList<Integer>();
		
		DocumentExternal local=docuService.findByid(document.getDocumentId());
		data.add(local.isStatus());
		
		return data;
	}
	
	@PostMapping("/loadByValidityLesson")
	public List<Integer> loadByValidityLesson(@Valid @RequestBody LessonPlan lesson){
		List<Integer> data=new ArrayList<Integer>();
		
		LessonPlan local=lessonService.findById(lesson.getLessonPlanId());
		data.add(local.isStatus());
		
		return data;
	}
	
	@PostMapping("/loadByValidityArticle")
	public List<Integer> loadByValidityArticle(@Valid @RequestBody ArticleExternal article){
		List<Integer> data=new ArrayList<Integer>();
		
		ArticleExternal local=articleService.findByid(article.getArticleId());
		data.add(local.isStatus());
		
		return data;
	}
	
	@PostMapping("/loadByValidityQuiz")
	public List<Integer> loadByValidityQuiz(@Valid @RequestBody QuizQuestion quiz){
		List<Integer> data=new ArrayList<Integer>();
		
		QuizQuestion local=quizService.findById(quiz.getQuizQuestionId());
		data.add(local.isStatus());
		
		return data;
	}
	
	
	@PostMapping("/loadByValidityVideo")
	public List<Integer> loadByValidityVideo(@Valid @RequestBody VideoExternal video){
		List<Integer> data=new ArrayList<Integer>();
		
		VideoExternal local=videoService.findById(video.getVideoId());
		data.add(local.isStatus());
		
		return data;
	}
	
	@PostMapping("/loadByValidityConcept")
	public List<Integer> loadByValidityConcept(@Valid @RequestBody ConceptMap concept){
		List<Integer> data=new ArrayList<Integer>();
		
		ConceptMap local=concepMapService.findByid(concept.getConcepMapid());
		data.add(local.getStatus());
		
		return data;
	}
	
	@PostMapping("/loadByValidityTutorial")
	public List<Integer> loadByValidityTutorial(@Valid @RequestBody Tutorial tutorial){
		List<Integer> data=new ArrayList<Integer>();
		
		Tutorial local=tutorialService.getById(tutorial.getTutorialId());
		data.add(local.getStatus());
		
		return data;
	}
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------LOAD BY SUBJECT----------------------------------------------------------------------*/
	
	@PostMapping("/loadBySubject")
	public SubjectAjaxQueryResolver loadBySubject(@Valid @RequestBody Subject sub) {
		
		SubjectAjaxQueryResolver local=new SubjectAjaxQueryResolver();
		Subject localSub=subjectService.findById(sub.getSubId());
		local.setId(localSub.getSubId());
		local.setSubName(localSub.getSubName());
		return local;	
	}
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------LOAD BY CLASS NAME----------------------------------------------------------------------*/
		
	@PostMapping("/loadByClass")
	public List<String> loadByClass() {
		List< String> local=new ArrayList<String>();
		
		List<Class> localClass=classService.findAll();
		
		for(Class a:localClass) {
			local.add(a.getClassName());
		}
		return local;
	}
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------LOAD BY SUBJECT CLASS----------------------------------------------------------------------*/
	
	@PostMapping("/loadBySubjectClass")
	public Set<String> loadBySubjectClass(@Valid @RequestBody Subject sub){
		Set<String> subName= new HashSet<String>();
		
		Subject localSub=subjectService.findById(sub.getSubId());
		
		Set<SubjectClassMapping> local=localSub.getSubClasMapp();	/*--------------------------------------------------LOAD ----------------------------------------------------------------------*/
		for(SubjectClassMapping a:local) {
			subName.add(a.getStandard().getClassName());
		}
		return subName;
	}
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------LOADING TOPIC DETAILS----------------------------------------------------------------------*/
	
	@PostMapping("/loadByTopic")
	public List<String> loadByTopicTopicName(@Valid @RequestBody Topic topic) {
		
		List<String> topicdata=new ArrayList<String>();
		
		Topic localTopic=topicService.findById(topic.getTopicId());
		 
		topicdata.add(localTopic.getTopicName());
		
		return topicdata;
	}
	

	
	
	@PostMapping("/loadByTopicDesc")
	public List<String> loadByTopicDesc(@Valid @RequestBody Topic topic) {
	
		List<String> topicdata=new ArrayList<String>();
		
		Topic localTopic=topicService.findById(topic.getTopicId());
		 
		topicdata.add(localTopic.getDescription());
		
		return topicdata;
	}
	
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------LOADING QUIZ DETAILS----------------------------------------------------------------------*/
	
	
	@PostMapping("/loadByQuizQuestionID")
	public List<String> loadByQuizQuestionId(@Valid @RequestBody QuizQuestion quiz){
		
		List<String> quizdata=new ArrayList<String>();
		
		QuizQuestion quiz1=quizService.findById(quiz.getQuizQuestionId());
		
		quizdata.add(quiz1.getRemark());
		return quizdata; 
	}
	
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	/*--------------------------------------------------LOAD ----------------------------------------------------------------------*/
	/*--------------------------------------------------LOADING VIDEO DETAILS----------------------------------------------------------------------*/
	
	@PostMapping("/loadByVideoID")
	public List<String> loadByVideoID(@Valid @RequestBody VideoExternal video){
		
		List<String> videodata=new ArrayList<String>();
		
		VideoExternal local=videoService.findById(video.getVideoId());
		
		videodata.add(local.getDescription());
		
		return videodata;
	}
	

	
	@PostMapping("/loadByVideoIDSource")
	public List<String> loadByVideoIDSource(@Valid @RequestBody VideoExternal video){
		
		List<String> videodata=new ArrayList<String>();	/*--------------------------------------------------LOAD ----------------------------------------------------------------------*/
		
		VideoExternal local=videoService.findById(video.getVideoId());
		
		videodata.add(local.getSource());
		
		return videodata;
	}
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	/***************************************** LOADING CONCEPT-MAP DETAILS ********************************************************/
	
	
	@PostMapping("/loadByConceptID")
	public List<String> loadByConceptID(@Valid @RequestBody ConceptMap concept){
		
		List<String> conceptdata=new ArrayList<String>();
		
		ConceptMap local=concepMapService.findByid(concept.getConcepMapid());
		
		conceptdata.add(local.getDescription());
		conceptdata.add(local.getRemark());
		
		return conceptdata;
	}
	
	/****************************************************END****************************************************************************/
	
	/*--------------------------------------------------LOADING ARTICLE DETAILS----------------------------------------------------------------------*/
	
	@PostMapping("/loadByArtcileID")
	public List<String> loadByArtcileID(@Valid @RequestBody ArticleExternal article){
		
		List<String> articledata=new ArrayList<String>();
		
		ArticleExternal local=articleService.findByid(article.getArticleId());
		
		articledata.add(local.getSource());
		
		return articledata;
	}
	
	
	
	@PostMapping("/loadByArtcileIDDesc")
	public List<String> loadByArtcileIDDesc(@Valid @RequestBody ArticleExternal article){
		
		List<String> articledata=new ArrayList<String>();
		
		ArticleExternal local=articleService.findByid(article.getArticleId());
		
		articledata.add(local.getDescription());
		
		return articledata;
	}
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------LOADING DOCUMENT DETAILS----------------------------------------------------------------------*/
	
	@PostMapping("/loadByDocumentID")
	public List<String> loadByDocumentID(@Valid @RequestBody DocumentExternal document){
		
		List<String> articledata=new ArrayList<String>();
		
		DocumentExternal local=docuService.findByid(document.getDocumentId());
		
		articledata.add(local.getSource());
		
		return articledata;
	}	/*--------------------------------------------------LOAD ----------------------------------------------------------------------*/
	

	
	@PostMapping("/loadByDocumentIDDesc")
	public List<String> loadByDocumentIDDesc(@Valid @RequestBody DocumentExternal document){
		
		List<String> articledata=new ArrayList<String>();
		
		DocumentExternal local=docuService.findByid(document.getDocumentId());
		
		articledata.add(local.getDescription());
		
		return articledata;
	}
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------LOADING PHET DETAILS----------------------------------------------------------------------*/
	
	
	
	@PostMapping("/loadByphetID")
	public List<String> loadByphetID(@Valid @RequestBody Phets phet){
		
		List<String> articledata=new ArrayList<String>();
		
		Phets local=phetServcie.findByid(phet.getPhetId());
		
		articledata.add(local.getSource());	/*--------------------------------------------------LOAD ----------------------------------------------------------------------*/
		
		return articledata;
	}
	

	
	@PostMapping("/loadByphetIDDesc")
	public List<String> loadByphetIDDesc(@Valid @RequestBody Phets phet){
		
		List<String> articledata=new ArrayList<String>();
		
		Phets local=phetServcie.findByid(phet.getPhetId());
		
		articledata.add(local.getDescription());
		
		return articledata;
	}
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------UPDATING SUBJECT----------------------------------------------------------------------*/
	
	@PostMapping("/updateSubject")
	public List<String> updateSubject(@Valid @RequestBody List<String> data) throws Exception {
		int index=0;
		
		List<String> msg=new ArrayList<String>();
		int size=data.size();
		Set<SubjectClassMapping> subjectClassMapping=new HashSet<SubjectClassMapping>();
		
		if(size>1) {
			String sub=data.get(size-1);
			int idsub=Integer.parseInt(sub);
			System.out.println(sub);
			Subject subject=subjectService.findById(idsub);
			
			for(int i=0;i<size-1;i++) {
				Class classTemp=classService.findByClassName(data.get(i));
				subjectClassMapping.add(new SubjectClassMapping(subjectClassService.countRow()+index++,classTemp,subject));
				
			}
			
			subjectClassService.createSubjectClass(subject, subjectClassMapping);
			
			msg.add("Success");
			return msg;
			
		}else {
			msg.add("failure");
			return msg;
			
		}
	

	}	/*--------------------------------------------------LOAD ----------------------------------------------------------------------*/
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------UPDATING TOPIC----------------------------------------------------------------------*/
	
	@PostMapping("/updateTopic")
	public @ResponseBody List<String> updateTopic(@RequestParam("posterQ") MultipartFile[] uploadfiles, @RequestParam("topicDesc") String desc, @RequestParam("TopicId") String TopicID){
		List<String> msg=new ArrayList<String>();
		
	
		Topic temptopic=topicService.findById(Integer.parseInt(TopicID));
		
		if(!ServiceUtility.checkFileExtensionImage(uploadfiles)) {
			msg.add("invalid-data'");
			return msg;
		}

		
		if(!(desc.length()>0)) {
			desc=temptopic.getDescription();
		}
			
			String previousPath=deleteDirectory+temptopic.getPoster();
			
			Path fileToDeletePath=Paths.get(previousPath);
			
			String pathToUpload=uploadDirectory+temptopic.getSubjectClassMapping().getStandard().getClassName()+"_"+temptopic.getSubjectClassMapping().getSub().getSubName()+"/"+temptopic.getTopicName()+"/";
			System.out.println(pathToUpload);
			String newFilePath=ServiceUtility.uploadFile(uploadfiles, pathToUpload);
			
			int indexToStart=newFilePath.indexOf('M');
			String posterPath=newFilePath.substring(indexToStart, newFilePath.length());
			
			boolean status=topicService.updateTopic(desc, posterPath, ServiceUtility.getCurrentTime(), temptopic.getTopicId());
			
			if(status) {
				
				try {
					Files.delete(fileToDeletePath);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				msg.add("Success");
				return msg;
				
			}else {
				
				msg.add("failure");
				return msg;
				
			}
			
			
			
		
		
	}
	
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------UPDATING QUIZ----------------------------------------------------------------------*/
		
		
	
	
	@PostMapping("/updateQuiz")
	public @ResponseBody List<String> updateQuiz(@RequestParam("question") MultipartFile[] uploadQuestion, @RequestParam("answer") MultipartFile[] uploadAnswer, @RequestParam("quizId") String quizID){
		List<String> msg=new ArrayList<String>();
		
		if(!ServiceUtility.checkFileExtensionPDF(uploadAnswer)) {
			msg.add("invalid-data");
			return msg;
		}
		
		if(!ServiceUtility.checkFileExtensionPDF(uploadQuestion)) {
			msg.add("invalid-data");
			return msg;
		}
		
		
		QuizQuestion tempQuiz=quizService.findById(Integer.parseInt(quizID));
		
		String previousQuestion=deleteDirectory+tempQuiz.getQuestion();
		String previousAnswer=deleteDirectory+tempQuiz.getAnswer();
		
		Path deletepreviousQuestion=Paths.get(previousQuestion);
		
		Path deletepreviousAnswer=Paths.get(previousAnswer);
		
		String pathToUploadQuestion=uploadDirectory+tempQuiz.getTopic().getSubjectClassMapping().getStandard().getClassName()+"_"+tempQuiz.getTopic().getSubjectClassMapping().getSub().getSubName()+"/"+tempQuiz.getTopic().getTopicName()+"/"+"Quiz/"+tempQuiz.getRemark()+"/Question/";
		
		String pathToUploadAnswer=uploadDirectory+tempQuiz.getTopic().getSubjectClassMapping().getStandard().getClassName()+"_"+tempQuiz.getTopic().getSubjectClassMapping().getSub().getSubName()+"/"+tempQuiz.getTopic().getTopicName()+"/"+"Quiz/"+tempQuiz.getRemark()+"/Answer/";
		
		String questiontemp=ServiceUtility.uploadFile(uploadQuestion, pathToUploadQuestion);
		String answertemp=ServiceUtility.uploadFile(uploadAnswer, pathToUploadAnswer);
		
		System.out.println(previousAnswer);
		System.out.println(previousQuestion);
		System.out.println(pathToUploadAnswer);
		System.out.println(pathToUploadQuestion);
		
		int indexToStart=questiontemp.indexOf('M');
		String question=questiontemp.substring(indexToStart, questiontemp.length());
		
		indexToStart=answertemp.indexOf('M');
		String answer=answertemp.substring(indexToStart, answertemp.length());
		
		
		boolean status=quizService.updateQuiz(question, answer, ServiceUtility.getCurrentTime(), tempQuiz.getQuizQuestionId());
		
		if(status) {
			try {
				Files.delete(deletepreviousQuestion);
				Files.delete(deletepreviousAnswer);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			msg.add("Success");
			return msg;
			
		}else {
			
			msg.add("failure");
			return msg;
		}
		
		
		
	}
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------UPDATING DOCUMENT----------------------------------------------------------------------*/
	
	
	@PostMapping("/updateDocument")
	public @ResponseBody List<String> updateDocument(@RequestParam("document") MultipartFile[] uploadQuestion,@RequestParam("documentsource") String dSource,@RequestParam("documentId") String documentId,@RequestParam("documentDesc") String desc){
		List<String> msg=new ArrayList<String>();
		
		if(!ServiceUtility.checkFileExtensionPDF(uploadQuestion)) {
			msg.add("invalid-data");
			return msg;
		}
		
		DocumentExternal docuTemp=docuService.findByid(Integer.parseInt(documentId));
		
		String previousPath=deleteDirectory+docuTemp.getUrl();
		Path deletePreviousPath=Paths.get(previousPath);
		
		String uploadDocument=uploadDirectory+docuTemp.getTopic().getSubjectClassMapping().getStandard().getClassName()+"_"+docuTemp.getTopic().getSubjectClassMapping().getSub().getSubName()+"/"+docuTemp.getTopic().getTopicName()+"/"+"Document/";
		
		String document=ServiceUtility.uploadFile(uploadQuestion, uploadDocument);
		
		int indexToStart=document.indexOf('M');
		String documentToUpload=document.substring(indexToStart, document.length());
		
		if(desc.length()>0) {
			boolean done=docuService.updateDocument(desc, dSource, documentToUpload, ServiceUtility.getCurrentTime(), docuTemp.getDocumentId());
			
			if(done) {
				try {
					Files.delete(deletePreviousPath);
					
					
				} catch (IOException e) {
					
					
				}
				msg.add("Success");
				return msg;
				
			}else {
				msg.add("failure");
				return msg;
			}
		}else {
			boolean done=docuService.updateDocument(docuTemp.getDescription(), dSource, documentToUpload, ServiceUtility.getCurrentTime(), docuTemp.getDocumentId());
			
			if(done) {
				try {
					Files.delete(deletePreviousPath);
					
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				msg.add("Success");
				return msg;
			}else {
				msg.add("failure");
				return msg;
			}
		}
		
		
	}
	
	/*------------------------------------------------------------END--------------------------------------------------------------------*/
	
	/*--------------------------------------------------UPDATING CONCEPT-MAP-------------------------------------------------------------*/
	
	@PostMapping("/updateConcept")
	public @ResponseBody List<String> updateConcept(@RequestParam("conceptImage") MultipartFile[] conceptImage,@RequestParam("conceptDesc") String desc,@RequestParam("conceptId") String conceptId,@RequestParam("conceptHeadline") String remark){
		List<String> msg=new ArrayList<String>();
		
		if(!ServiceUtility.checkFileExtensionImage(conceptImage)) {
			msg.add("invalid-data");
			return msg;
		}
		
		ConceptMap conceptTemp=concepMapService.findByid(Integer.parseInt(conceptId));
		
		String previousPath=deleteDirectory+conceptTemp.getUrl();
		Path deletePreviousPath=Paths.get(previousPath);
		
		String uploadconceptImage=uploadDirectory+conceptTemp.getTopic().getSubjectClassMapping().getStandard().getClassName()+"_"+conceptTemp.getTopic().getSubjectClassMapping().getSub().getSubName()+"/"+conceptTemp.getTopic().getTopicName()+"/"+"ConceptMap/";
		
		String document=ServiceUtility.uploadFile(conceptImage, uploadconceptImage);
		
		int indexToStart=document.indexOf('M');
		String documentToUpload=document.substring(indexToStart, document.length());
		
		if(desc.length()>0) {
			boolean done=concepMapService.updateConcept(desc, documentToUpload, remark, ServiceUtility.getCurrentTime(), conceptTemp.getConcepMapid());
			if(done) {
				try {
					Files.delete(deletePreviousPath);
					
					
				} catch (IOException e) {
					
					
				}
				msg.add("Success");
				return msg;
				
			}else {
				msg.add("failure");
				return msg;
			}
		}else {
			
			boolean done=concepMapService.updateConcept(conceptTemp.getDescription(), documentToUpload, remark, ServiceUtility.getCurrentTime(), conceptTemp.getConcepMapid());
			if(done) {
				try {
					Files.delete(deletePreviousPath);
					
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				msg.add("Success");
				return msg;
			}else {
				msg.add("failure");
				return msg;
			}
		}
		
		
	}
	
	
	/*--------------------------------------------------------------END-----------------------------------------------------------------*
	 * 
	 */
	
	/*--------------------------------------------------UPDATING VIDEO----------------------------------------------------------------------*/
	
	
	@PostMapping("/updateVideo")
	public @ResponseBody List<String> updateVideo(@RequestParam("videosource") String VSource,@RequestParam("videoId") String videoId,@RequestParam("videoDesc") String desc,@RequestParam("videourl") String url){
		List<String> msg=new ArrayList<String>();
		
		String description=null;
		String urlUpload=null;
		
		VideoExternal videotemp=videoService.findById(Integer.parseInt(videoId));
		
		if(url.length()>0) {
			
			if(!url.startsWith("<iframe")) {
				msg.add("Invalid Data");
				return msg;
			}
			try {
				int indexOfSrc=url.indexOf("src");
				int lastIndexOfSrc =url.indexOf(' ', indexOfSrc);
				String temp=url.substring(indexOfSrc, lastIndexOfSrc);
				int ind=temp.indexOf('=');
				String urlUpload1=temp.substring(ind+2, temp.length()-1);
				urlUpload=urlUpload1;
			
			}catch(Exception e) {
				msg.add("Invalid data");
				return msg;
			}
			
		}else {
			urlUpload=videotemp.getUrl();
		}
		
		if(desc.length()>0) {
			description=desc;
			
		}else {
			description=videotemp.getDescription();
		}
		
		boolean status=phetServcie.updatePhet(VSource, urlUpload, ServiceUtility.getCurrentTime(), description, videotemp.getVideoId());
		
		if(status) {
			msg.add("Success");
			return msg;
		}else {
			msg.add("failure");
			return msg;
		}
	}
	
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------UPDATING PHETS----------------------------------------------------------------------*/	
	
	@PostMapping("/updatePhet")
	public @ResponseBody List<String> updatePhet(@RequestParam("phetsource") String PSource,@RequestParam("phetId") String phetId,@RequestParam("phetDesc") String desc,@RequestParam("phetUrl") String url){
		List<String> msg=new ArrayList<String>();
		
		String description=null;
		String urlUpload=null;
		
		Phets phetemp=phetServcie.findByid(Integer.parseInt(phetId));
		
		if(url.length()>0) {
			
			if(!url.startsWith("<iframe")) {
				msg.add("Invalid Data");
				return msg;
			}
			try {
				int indexOfSrc=url.indexOf("src");
				int lastIndexOfSrc =url.indexOf(' ', indexOfSrc);
				String temp=url.substring(indexOfSrc, lastIndexOfSrc);
				int ind=temp.indexOf('=');
				String urlUpload1=temp.substring(ind+2, temp.length()-1);
				urlUpload=urlUpload1;
			
			}catch(Exception e) {
				msg.add("Invalid data");
				return msg;
			}
			
		}else {
			urlUpload=phetemp.getUrl();
		}
		
		if(desc.length()>0) {
			description=desc;
			
		}else {
			description=phetemp.getDescription();
		}
		
		boolean status=phetServcie.updatePhet(PSource, urlUpload, ServiceUtility.getCurrentTime(), description, phetemp.getPhetId());
		
		if(status) {
			msg.add("Success");
			return msg;
		}else {
			msg.add("failure");
			return msg;
		}
	
		
	}
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------UPDATING Lesson Plan----------------------------------------------------------------------*/
	
	@PostMapping("/updateLesson")
	public @ResponseBody List<String> updateLesson(@RequestParam("lessonPlan") MultipartFile[] uploadLessonPlan,@RequestParam("lessonId") String documentId){
		List<String> msg=new ArrayList<String>();
		
		if(!ServiceUtility.checkFileExtensionPDF(uploadLessonPlan)) {
			msg.add("invalid-data");
			return msg;
		}
		
		LessonPlan lessonTemp=lessonService.findById(Integer.parseInt(documentId));
		
		String previousPath=deleteDirectory+lessonTemp.getLessonPlan();
		Path deletePreviousPath=Paths.get(previousPath);
		
		String uploadDocument=uploadDirectory+lessonTemp.getTopic().getSubjectClassMapping().getStandard().getClassName()+"_"+lessonTemp.getTopic().getSubjectClassMapping().getSub().getSubName()+"/"+lessonTemp.getTopic().getTopicName()+"/"+"Lessonplan/";
		
		String document=ServiceUtility.uploadFile(uploadLessonPlan, uploadDocument);
		
		int indexToStart=document.indexOf('M');
		String documentToUpload=document.substring(indexToStart, document.length());
		
		System.out.println(lessonTemp.getLessonPlanId());
		
		
			boolean done=lessonService.updateLessonPlan(ServiceUtility.getCurrentTime(), documentToUpload, lessonTemp.getLessonPlanId());
			
			if(done) {
				try {
					Files.delete(deletePreviousPath);
					
					
				} catch (IOException e) {
					
					
				}
				msg.add("Success");
				return msg;
				
			}else {
				msg.add("failure");
				return msg;
			}
		
		
		
	}
	
	
	/*------------------------------------------------------------END------------------------------------------------------------------*/
	
	/*--------------------------------------------------UPDATING ARTICLE----------------------------------------------------------------------*/
	
	@PostMapping("/updateArticle")
	public @ResponseBody List<String> updateArticle(@RequestParam("articlesource") String ASource,@RequestParam("artcileId") String articleId,@RequestParam("articleDesc") String desc,@RequestParam("articleurl") String url){
	List<String> msg=new ArrayList<String>();
		
		String description=null;
		String urlUpload=null;
		
		ArticleExternal articletemp=articleService.findByid(Integer.parseInt(articleId));
		
		if(url.length()>0) {
			
			if(!url.startsWith("http")) {
				msg.add("Invalid Data");
				return msg;
			}
			urlUpload=url;
			
		
		}else {
			urlUpload=articletemp.getUrl();
		}
		
		if(desc.length()>0) {
			description=desc;
			
		}else {
			description=articletemp.getDescription();
		}
		
		boolean status=articleService.updateArticle(description, ASource, urlUpload, ServiceUtility.getCurrentTime(), articletemp.getArticleId());
		
		if(status) {
			msg.add("Success");
			return msg;
		}else {
			msg.add("failure");
			return msg;
		}
	}
	
	
	
	/*****************************************************************END************************************************************************/
	
	
	/*---------------------------------------------------------COMMENT SECTION FOR VIDEO---------------------------------------------------------*/
	
	@PostMapping("/loadByVideoComment")
	public @ResponseBody List<CommentAjaxQueryResolver> fetchCommentOnVideo(@Valid @RequestBody VideoExternal video) {
		
		VideoExternal localVideo=videoService.findById(video.getVideoId());
		
		List<Comment> localComment=comService.getCommentByVideoId(localVideo);
		
		List<CommentAjaxQueryResolver> temp=new ArrayList<CommentAjaxQueryResolver>();
		
		for(Comment data:localComment) {
			
			String date=ServiceUtility.daysDifference(data.getDateAdded())+" day Ago";
			
			temp.add(new CommentAjaxQueryResolver(data.getCommentid(), data.getComment(), data.getUser().getFname(), date));
			
		}
		
		
		
		return temp;
	}
	
	
	@PostMapping("/uploadCommentOnVideo")
	public List<String> uploadCommentonVideo(HttpServletRequest req,@Valid @RequestBody CommentReplyAjaxQueryResolver data) throws Exception {
		
		List<String> msg=new ArrayList<String>();
		
		
		HttpSession session=req.getSession(false);
		String emailToIdentifyUser;
		
		emailToIdentifyUser=(String) session.getAttribute("UserLogedUsername");
		
		User usr=userService.findByUsername(emailToIdentifyUser);
		
		try {
			if(data.isReply()) {
				if(data.getComment().isEmpty()) {
					msg.add("failure");
					return msg;
					
				}
				
				Comment localComment=comService.findById(data.getId());
				
				Set<CommentReply> reply= new HashSet<CommentReply>();
				reply.add(new CommentReply(comReplyService.countRow()+1, data.getComment(), ServiceUtility.getCurrentTime(), localComment, usr));
				
				userService.addUserToCommentReply(usr, reply);
				
				msg.add("Success");
				
			}else {
				
				if(data.getComment().isEmpty()) {
					msg.add("failure");
					return msg;
					
				}
				
				int idVideo=data.getId();
				VideoExternal localVideo=videoService.findById(idVideo);
				
						
				Set<Comment> comment=new HashSet<Comment>();
				comment.add(new Comment(comService.countRow()+1, ServiceUtility.getCurrentTime(), data.getComment(), usr, localVideo));
						
				userService.addUserToComment(usr, comment);
				
				msg.add("Success");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			msg.add("failure");
		}
		
		
		return msg;
			
		
}
	
/*****************************************************************END****************************************************************/
	
	/*----------------------------------------------COLLECTION OF REPLY ON COMMENT--------------------------------------------------------------*/
	
	
	@PostMapping("/loadReplyOnComment")
	public List<CommentAjaxQueryResolver> fetchReplyOnComment(@Valid @RequestBody Comment coment){
		List<CommentAjaxQueryResolver> replyOnComment=new ArrayList<CommentAjaxQueryResolver>();
		
		Comment localComment=comService.findById(coment.getCommentid());
		
		List<CommentReply> commentReplyInstance=comReplyService.getReplyOnComment(localComment);
		
		for(CommentReply x:commentReplyInstance) {
			
			String date=ServiceUtility.daysDifference(x.getDateAdded())+" day Ago";
			replyOnComment.add(new CommentAjaxQueryResolver(x.getComment().getCommentid(), x.getCommentReply(), x.getUser().getFname(), date));
		}
		
		return replyOnComment;
		
	}
	
	/*---------------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	/*---------------------------------------------------------COMMENT SECTION FOR ARTICLE---------------------------------------------------------*/
	
	@PostMapping("/loadByArticleComment")
	public @ResponseBody List<CommentAjaxQueryResolver> fetchCommentOnArtcile(@Valid @RequestBody ArticleExternal article) {
		
		ArticleExternal localArticle=articleService.findByid(article.getArticleId());
		
		List<Comment> localComment=comService.getCommentByArticleId(localArticle);
		
		List<CommentAjaxQueryResolver> temp=new ArrayList<CommentAjaxQueryResolver>();
		
		for(Comment data:localComment) {
			
			String date=ServiceUtility.daysDifference(data.getDateAdded())+" day Ago";
			
			temp.add(new CommentAjaxQueryResolver(data.getCommentid(), data.getComment(), data.getUser().getFname(), date));
			
		}
		
		
		
		return temp;
	}
	
	
	@PostMapping("/uploadCommentOnArticle")
	public List<String> uploadCommentonArtcile(HttpServletRequest req,@Valid @RequestBody CommentReplyAjaxQueryResolver data) throws Exception {
		
//		List<String> msg=new ArrayList<String>();
//		int size=data.size();
//		
//		HttpSession session=req.getSession(false);
//		String emailToIdentifyUser=(String) session.getAttribute("UserLoged");
//		User usr=userService.findByUsername(emailToIdentifyUser);
//		
//		String sub=data.get(size-1);
//		int idArticle=Integer.parseInt(sub);
//		ArticleExternal localArticle=articleService.findByid(idArticle);
//
//		
//		Set<Comment> comment=new HashSet<Comment>();
//		comment.add(new Comment(comService.countRow()+1, ServiceUtility.getCurrentTime(), data.get(0), usr, localArticle));
//		
//		userService.addUserToComment(usr, comment);
//		
//		
//		msg.add("Success");
//		return msg;
		
		List<String> msg=new ArrayList<String>();
		
		
		HttpSession session=req.getSession(false);
		String emailToIdentifyUser;
	
		emailToIdentifyUser=(String) session.getAttribute("UserLogedUsername");
	
		
		User usr=userService.findByUsername(emailToIdentifyUser);
		
		try {
			if(data.isReply()) {
				if(data.getComment().isEmpty()) {
					msg.add("failure");
					return msg;
					
				}
				
				Comment localComment=comService.findById(data.getId());
				
				Set<CommentReply> reply= new HashSet<CommentReply>();
				reply.add(new CommentReply(comReplyService.countRow()+1, data.getComment(), ServiceUtility.getCurrentTime(), localComment, usr));
				
				userService.addUserToCommentReply(usr, reply);
				
				msg.add("Success");
				
			}else {
				
				if(data.getComment().isEmpty()) {
					msg.add("failure");
					return msg;
					
				}
				
				int idArticle=data.getId();
				ArticleExternal localArticle=articleService.findByid(idArticle);
				
						
				Set<Comment> comment=new HashSet<Comment>();
				comment.add(new Comment(comService.countRow()+1, ServiceUtility.getCurrentTime(), data.getComment(), usr, localArticle));
						
				userService.addUserToComment(usr, comment);
				
				msg.add("Success");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			msg.add("failure");
		}
		
		
		return msg;
			
			
		
}
	
	
	/*---------------------------------------------------------------END----------------------------------------------------------------------------*/
	
	
	
	/*---------------------------------------------------------COMMENT SECTION FOR DOCUMENT---------------------------------------------------------*/
	
	@PostMapping("/loadByDocumentComment")
	public @ResponseBody List<CommentAjaxQueryResolver> fetchCommentOnDocument(@Valid @RequestBody DocumentExternal document) {
		System.out.println(document.getDocumentId());
		
		DocumentExternal localDocument=docuService.findByid(document.getDocumentId());
		
		List<Comment> localComment=comService.getCommentByDocumentId(localDocument);
		
		List<CommentAjaxQueryResolver> temp=new ArrayList<CommentAjaxQueryResolver>();
		
		for(Comment data:localComment) {
			
			String date=ServiceUtility.daysDifference(data.getDateAdded())+" day Ago";
			
			temp.add(new CommentAjaxQueryResolver(data.getCommentid(), data.getComment(), data.getUser().getFname(), date));
			
		}
		
		
		
		return temp;
	}
	
	@PostMapping("/uploadCommentOnDocument")
	public List<String> uploadCommentonDocument(HttpServletRequest req,@Valid @RequestBody CommentReplyAjaxQueryResolver data) throws Exception {
		
		
		List<String> msg=new ArrayList<String>();
		
		
		HttpSession session=req.getSession(false);
		String emailToIdentifyUser;
		
		emailToIdentifyUser=(String) session.getAttribute("UserLogedUsername");
	
		
		User usr=userService.findByUsername(emailToIdentifyUser);
		
		try {
			if(data.isReply()) {
				if(data.getComment().isEmpty()) {
					msg.add("failure");
					return msg;
					
				}
				
				Comment localComment=comService.findById(data.getId());
				
				Set<CommentReply> reply= new HashSet<CommentReply>();
				reply.add(new CommentReply(comReplyService.countRow()+1, data.getComment(), ServiceUtility.getCurrentTime(), localComment, usr));
				
				userService.addUserToCommentReply(usr, reply);
				
				msg.add("Success");
				
			}else {
				
				if(data.getComment().isEmpty()) {
					msg.add("failure");
					return msg;
					
				}
				
				int idDocument=data.getId();
				DocumentExternal localDocument=docuService.findByid(idDocument);
				
						
				Set<Comment> comment=new HashSet<Comment>();
				comment.add(new Comment(comService.countRow()+1, ServiceUtility.getCurrentTime(), data.getComment(), usr, localDocument));
				
				userService.addUserToComment(usr, comment);
				
				msg.add("Success");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			msg.add("failure");
		}
		
		
		return msg;
			
	
		
		
}
	
	/*---------------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*---------------------------------------------------------COMMENT SECTION FOR QUIZ---------------------------------------------------------*/
	
	
	
	@PostMapping("/loadByQuizComment")
	public @ResponseBody List<CommentAjaxQueryResolver> fetchCommentOnQuiz(@Valid @RequestBody QuizQuestion quiz) {
		
		QuizQuestion localquiz=quizService.findById(quiz.getQuizQuestionId());
		
		List<Comment> localComment=comService.getCommentByQuizId(localquiz);
		
		List<CommentAjaxQueryResolver> temp=new ArrayList<CommentAjaxQueryResolver>();
		
		for(Comment data:localComment) {
			
			String date=ServiceUtility.daysDifference(data.getDateAdded())+" day Ago";
			
			temp.add(new CommentAjaxQueryResolver(data.getCommentid(), data.getComment(), data.getUser().getFname(), date));
			
		}
		
		
		
		return temp;
	}
	
	
	@PostMapping("/uploadCommentOnQuiz")
	public List<String> uploadCommentonQuiz(HttpServletRequest req,@Valid @RequestBody  CommentReplyAjaxQueryResolver data) throws Exception {
		
//		List<String> msg=new ArrayList<String>();
//		int size=data.size();
//		
//		HttpSession session=req.getSession(false);
//		String emailToIdentifyUser=(String) session.getAttribute("UserLoged");
//		User usr=userService.findByUsername(emailToIdentifyUser);
//		
//		String sub=data.get(size-1);
//		int idQuiz=Integer.parseInt(sub);
//		QuizQuestion localQuiz=quizService.findById(idQuiz);
//
//		
//		Set<Comment> comment=new HashSet<Comment>();
//		comment.add(new Comment(comService.countRow()+1, ServiceUtility.getCurrentTime(), data.get(0), usr, localQuiz));
//		
//		userService.addUserToComment(usr, comment);
//		
//		
//		msg.add("Success");
//		return msg;
		
		List<String> msg=new ArrayList<String>();
		String emailToIdentifyUser;
		
		HttpSession session=req.getSession(false);
		
	
		emailToIdentifyUser=(String) session.getAttribute("UserLogedUsername");
		
			
		
		User usr=userService.findByUsername(emailToIdentifyUser);
		
		try {
			if(data.isReply()) {
				if(data.getComment().isEmpty()) {
					msg.add("failure");
					return msg;
					
				}
				
				Comment localComment=comService.findById(data.getId());
				
				Set<CommentReply> reply= new HashSet<CommentReply>();
				reply.add(new CommentReply(comReplyService.countRow()+1, data.getComment(), ServiceUtility.getCurrentTime(), localComment, usr));
				
				userService.addUserToCommentReply(usr, reply);
				
				msg.add("Success");
				
			}else {
				
				if(data.getComment().isEmpty()) {
					msg.add("failure");
					return msg;
					
				}
				
				int idQuiz=data.getId();
				QuizQuestion localQuiz=quizService.findById(idQuiz);
				
						
				Set<Comment> comment=new HashSet<Comment>();
				comment.add(new Comment(comService.countRow()+1, ServiceUtility.getCurrentTime(), data.getComment(), usr, localQuiz));
				
				userService.addUserToComment(usr, comment);
				
				msg.add("Success");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			msg.add("failure");
		}
		
		
		return msg;
					
		
}
	
	/*---------------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/*---------------------------------------------------------COMMENT SECTION FOR PHET---------------------------------------------------------*/
	
	@PostMapping("/loadByPhetComment")
	public @ResponseBody List<CommentAjaxQueryResolver> fetchCommentOnPhet(@Valid @RequestBody Phets phet) {
		
		Phets localphet=phetServcie.findByid(phet.getPhetId());
		
		List<Comment> localComment=comService.getCommentByPhetId(localphet);
		
		List<CommentAjaxQueryResolver> temp=new ArrayList<CommentAjaxQueryResolver>();
		
		for(Comment data:localComment) {
			
			String date=ServiceUtility.daysDifference(data.getDateAdded())+" day Ago";
			
			temp.add(new CommentAjaxQueryResolver(data.getCommentid(), data.getComment(), data.getUser().getFname(), date));
			
		}
		
		
		
		return temp;
	}
	
	
	@PostMapping("/uploadCommentOnPhet")
	public List<String> uploadCommentonPhet(HttpServletRequest req,@Valid @RequestBody CommentReplyAjaxQueryResolver data) throws Exception {
		
//		List<String> msg=new ArrayList<String>();
//		int size=data.size();
//		
//		HttpSession session=req.getSession(false);
//		String emailToIdentifyUser=(String) session.getAttribute("UserLoged");
//		User usr=userService.findByUsername(emailToIdentifyUser);
//		
//		String sub=data.get(size-1);
//		int idphet=Integer.parseInt(sub);
//		Phets localphet=phetServcie.findByid(idphet);
//
//		
//		Set<Comment> comment=new HashSet<Comment>();
//		comment.add(new Comment(comService.countRow()+1, ServiceUtility.getCurrentTime(), data.get(0), usr, localphet));
//		
//		userService.addUserToComment(usr, comment);
//		
//		
//		msg.add("Success");
//		return msg;
		
		List<String> msg=new ArrayList<String>();
		String emailToIdentifyUser;
		
		
		HttpSession session=req.getSession(false);
		
		
		emailToIdentifyUser=(String) session.getAttribute("UserLogedUsername");
	
			
		User usr=userService.findByUsername(emailToIdentifyUser);
		
		try {
			if(data.isReply()) {
				if(data.getComment().isEmpty()) {
					msg.add("failure");
					return msg;
					
				}
				
				Comment localComment=comService.findById(data.getId());
				
				Set<CommentReply> reply= new HashSet<CommentReply>();
				reply.add(new CommentReply(comReplyService.countRow()+1, data.getComment(), ServiceUtility.getCurrentTime(), localComment, usr));
				
				userService.addUserToCommentReply(usr, reply);
				
				msg.add("Success");
				
			}else {
				
				if(data.getComment().isEmpty()) {
					msg.add("failure");
					return msg;
					
				}
				
				int idPhet=data.getId();
				Phets localphet=phetServcie.findByid(idPhet);
				
						
				Set<Comment> comment=new HashSet<Comment>();
				comment.add(new Comment(comService.countRow()+1, ServiceUtility.getCurrentTime(), data.getComment(), usr, localphet));
				
				userService.addUserToComment(usr, comment);
				
				msg.add("Success");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			msg.add("failure");
		}
		
		
		return msg;
			
		
}
	
	/*---------------------------------------------------------------END----------------------------------------------------------------------------*/
	
	/**************************************** COMMENT SECTION FOR CONCEPTS-MAP********************************************/
	
	@PostMapping("/loadByConceptComment")
	public @ResponseBody List<CommentAjaxQueryResolver> fetchCommentOnLesson(@Valid @RequestBody ConceptMap conceptMap) {
		
		ConceptMap localConcept=concepMapService.findByid(conceptMap.getConcepMapid());
		
		List<Comment> localComment=comService.getCommentByConceptMap(localConcept);
		
		List<CommentAjaxQueryResolver> temp=new ArrayList<CommentAjaxQueryResolver>();
		
		for(Comment data:localComment) {
			
			String date=ServiceUtility.daysDifference(data.getDateAdded())+" day Ago";
			
			temp.add(new CommentAjaxQueryResolver(data.getCommentid(), data.getComment(), data.getUser().getFname(), date));
			
		}
		
		
		
		return temp;
	}
	
	@PostMapping("/uploadCommentOnConcept")
	public List<String> uploadCommentonConcept(HttpServletRequest req,@Valid @RequestBody CommentReplyAjaxQueryResolver data) throws Exception {
		
		
		List<String> msg=new ArrayList<String>();
		String emailToIdentifyUser;
		
		
		HttpSession session=req.getSession(false);
		
		 
		emailToIdentifyUser=(String) session.getAttribute("UserLogedUserView");
		
		
		User usr=userService.findByUsername(emailToIdentifyUser);
		
		try {
			if(data.isReply()) {
				if(data.getComment().isEmpty()) {
					msg.add("failure");
					return msg;
					
				}
				
				Comment localComment=comService.findById(data.getId());
				
				Set<CommentReply> reply= new HashSet<CommentReply>();
				reply.add(new CommentReply(comReplyService.countRow()+1, data.getComment(), ServiceUtility.getCurrentTime(), localComment, usr));
				
				userService.addUserToCommentReply(usr, reply);
				
				msg.add("Success");
				
			}else {
				
				if(data.getComment().isEmpty()) {
					msg.add("failure");
					return msg;
					
				}
				
				int idConcept=data.getId();
				ConceptMap localConcept=concepMapService.findByid(idConcept);
				
						
				Set<Comment> comment=new HashSet<Comment>();
				comment.add(new Comment(comService.countRow()+1, ServiceUtility.getCurrentTime(), data.getComment(), usr, localConcept));
				
				userService.addUserToComment(usr, comment);
				
				msg.add("Success");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			msg.add("failure");
		}
		
		
		return msg;
			
		
}
	
	
	/*---------------------------------------------------------COMMENT SECTION FOR LESSON---------------------------------------------------------*/
	
	
	@PostMapping("/loadByLessonComment")
	public @ResponseBody List<CommentAjaxQueryResolver> fetchCommentOnLesson(@Valid @RequestBody LessonPlan lesson) {
		
		LessonPlan locallesson=lessonService.findById(lesson.getLessonPlanId());
		
		List<Comment> localComment=comService.getCommentByLessonId(locallesson);
		
		List<CommentAjaxQueryResolver> temp=new ArrayList<CommentAjaxQueryResolver>();
		
		for(Comment data:localComment) {
			
			String date=ServiceUtility.daysDifference(data.getDateAdded())+" day Ago";
			
			temp.add(new CommentAjaxQueryResolver(data.getCommentid(), data.getComment(), data.getUser().getFname(), date));
			
		}
		
		
		
		return temp;
	}
	
	
	
	
	@PostMapping("/uploadCommentOnLesson")
	public List<String> uploadCommentonLesson(HttpServletRequest req,@Valid @RequestBody CommentReplyAjaxQueryResolver data) throws Exception {
		
//		List<String> msg=new ArrayList<String>();
//		int size=data.size();
//		
//		HttpSession session=req.getSession(false);
//		String emailToIdentifyUser=(String) session.getAttribute("UserLoged");
//		User usr=userService.findByUsername(emailToIdentifyUser);
//		
//		String sub=data.get(size-1);
//		int idLesson=Integer.parseInt(sub);
//		LessonPlan locallesson=lessonService.findById(idLesson);
//
//		
//		Set<Comment> comment=new HashSet<Comment>();
//		comment.add(new Comment(comService.countRow()+1, ServiceUtility.getCurrentTime(), data.get(0), usr, locallesson));
//		
//		userService.addUserToComment(usr, comment);
//		
//		
//		msg.add("Success");
//		return msg;
			
		String emailToIdentifyUser;
		List<String> msg=new ArrayList<String>();
		
		
		HttpSession session=req.getSession(false);
		
	
		emailToIdentifyUser=(String) session.getAttribute("UserLogedUsername");
	

		User usr=userService.findByUsername(emailToIdentifyUser);
		
		try {
			if(data.isReply()) {
				if(data.getComment().isEmpty()) {
					msg.add("failure");
					return msg;
					
				}
				
				Comment localComment=comService.findById(data.getId());
				
				Set<CommentReply> reply= new HashSet<CommentReply>();
				reply.add(new CommentReply(comReplyService.countRow()+1, data.getComment(), ServiceUtility.getCurrentTime(), localComment, usr));
				
				userService.addUserToCommentReply(usr, reply);
				
				msg.add("Success");
				
			}else {
				
				if(data.getComment().isEmpty()) {
					msg.add("failure");
					return msg;
					
				}
				
				int idLesson=data.getId();
				LessonPlan locallesson=lessonService.findById(idLesson);
				
						
				Set<Comment> comment=new HashSet<Comment>();
				comment.add(new Comment(comService.countRow()+1, ServiceUtility.getCurrentTime(), data.getComment(), usr, locallesson));
				
				userService.addUserToComment(usr, comment);
				
				msg.add("Success");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			msg.add("failure");
		}
		
		
		return msg;
		
}
	
	
	@PostMapping("/addArticleFromUser")
	public @ResponseBody List<String> addArticleFromUser(@RequestParam("classSelected") String classSelected,@RequestParam("subjectSelected") String subSelected,
										   @RequestParam("topicSelected") String topicSelected,@RequestParam("UserIdUserEnd") String userID,
										   @RequestParam("description") String desc,@RequestParam("source") String source,@RequestParam("url") String url){
		List<String> status=new ArrayList<String>();
		
		
		
		try {
			Class localClass=classService.findByClassName(classSelected);
			Subject localSubject=subjectService.findBysubName(subSelected);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicSelected);
			
			
			
			User usr=userService.findByUsername(userID);
			Set<ArticleExternal> articlemapping=new HashSet<ArticleExternal>();
			articlemapping.add(new ArticleExternal(articleService.countRow()+1, "Article", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, url, 0,0,  ServiceUtility.getCurrentTime(), localTopic, usr));
			


			userService.addUserToArticle(usr, articlemapping);
			status.add("Success");
		} catch (Exception e) {
			
			status.add("failure");
		}
		
		
		
		return status;
	}
	
	@PostMapping("/addPhetFromUser")
	public @ResponseBody List<String> addPhetFromUser(@RequestParam("classSelected") String classSelected,@RequestParam("subjectSelected") String subSelected,
										   @RequestParam("topicSelected") String topicSelected,@RequestParam("UserIdUserEnd") String userID,
										   @RequestParam("description") String desc,@RequestParam("source") String source,@RequestParam("phet") String phet){
		
		List<String> status=new ArrayList<String>();
		String phetPath=null;
		
		try {
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
			

			Class localClass=classService.findByClassName(classSelected);
			Subject localSubject=subjectService.findBysubName(subSelected);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicSelected);
			
			
			User usr=userService.findByUsername(userID);
			
			
			Set<Phets> phetMapping=new HashSet<Phets>();
			phetMapping.add(new Phets(phetServcie.countRow()+1, "Phets", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, phetPath, 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));

			
			userService.addUserToPhets(usr, phetMapping);
			status.add("Success");
			
		} catch (Exception e) {
			
			status.add("failure");
		}
		
		
		
		return status;
	}
	
	@PostMapping("/addVideoFromUser")
	public @ResponseBody List<String> addVideoFromUser(@RequestParam("classSelected") String classSelected,@RequestParam("subjectSelected") String subSelected,
										   @RequestParam("topicSelected") String topicSelected,@RequestParam("UserIdUserEnd") String userID,
										   @RequestParam("description") String desc,@RequestParam("source") String source,@RequestParam("url") String url){
		List<String> status=new ArrayList<String>();
		
		String videourl=null;
	
		
		try {
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
					status.add("failure");
					e.printStackTrace();
					
				}
				
			}else {
				
			}
			

			

			Class localClass=classService.findByClassName(classSelected);
			Subject localSubject=subjectService.findBysubName(subSelected);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicSelected);
			
			
			
			User usr=userService.findByUsername(userID);
			
			Set<VideoExternal> videoMapping=new HashSet<VideoExternal>();
			videoMapping.add(new VideoExternal(videoService.countRow()+1, "Video", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, videourl, 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));
			

			
			userService.addUserToVideo(usr, videoMapping);
			status.add("Success");
			
		} catch (Exception e) {
			
			status.add("failure");
			e.printStackTrace();
		}
		
		
		
	
		
		
		
		return status;
	}
	
	@PostMapping("/addLessonFromUser")
	public @ResponseBody List<String> addLessonFromUser(@RequestParam("classSelected") String classSelected,@RequestParam("subjectSelected") String subSelected,
										   @RequestParam("topicSelected") String topicSelected,@RequestParam("UserIdUserEnd") String userID,
										   @RequestParam("lesson") MultipartFile[] uploadLessonPlan){
		
		List<String> status=new ArrayList<String>();
		
		if(!ServiceUtility.checkFileExtensionPDF(uploadLessonPlan)) {
			
			status.add("failure");
			return status;
			
		}
		
		String createFolder=uploadDirectory+classSelected+"_"+subSelected+"/"+topicSelected+"/Lessonplan/";
		
		try {
			String path1=ServiceUtility.uploadFile(uploadLessonPlan, createFolder);
			
			int indexToStart=path1.indexOf('M');
			String path=path1.substring(indexToStart, path1.length());
			
			
			

			Class localClass=classService.findByClassName(classSelected);
			Subject localSubject=subjectService.findBysubName(subSelected);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicSelected);
			

			
			User usr=userService.findByUsername(userID);
			
			Set<LessonPlan> lessonMapping=new HashSet<LessonPlan>();
			lessonMapping.add(new LessonPlan(lessonService.countRow()+1, "Lesson", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), path, 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));
			
			
			userService.addUserToLessonplan(usr, lessonMapping);
			status.add("Success");
			
		} catch (Exception e) {
			
			status.add("failure");
		}
		
		
		
		
		return status;
	}
	
	@PostMapping("/addQuizFromUser")
	public @ResponseBody List<String> addQuizFromUser(@RequestParam("classSelected") String classSelected,@RequestParam("subjectSelected") String subSelected,
										   @RequestParam("topicSelected") String topicSelected,@RequestParam("UserIdUserEnd") String userID,
										   @RequestParam("remarks") String remark,@RequestParam("Question") MultipartFile[] uploadQuestion,
										   @RequestParam("Answer") MultipartFile[] uploadAnswer){
		
		List<String> status=new ArrayList<String>();
		
		String questionPath=null;
		String answerPath=null;
		
		if(!ServiceUtility.checkFileExtensionPDF(uploadAnswer)) {
			System.out.println("file error");
			status.add("failure");
			return status;
			
		}
		
		if(!ServiceUtility.checkFileExtensionPDF(uploadQuestion)) {
			
			status.add("failure");
			return status;
		}
		
		try {
			String createFolder=uploadDirectory+classSelected+"_"+subSelected+"/"+topicSelected+"/Quiz/"+remark+"/";
			boolean b=ServiceUtility.createFolder(createFolder);
			
			String CreateFolderQuestion=createFolder+"Question/";
			String CreateFolderAnswer=createFolder+"Answer/";
			
			boolean ques=ServiceUtility.createFolder(CreateFolderQuestion);
			boolean ans=ServiceUtility.createFolder(CreateFolderAnswer);
			
			if(ques && ans) {
			
			questionPath=ServiceUtility.uploadFile(uploadQuestion, CreateFolderQuestion);
			answerPath=ServiceUtility.uploadFile(uploadAnswer, CreateFolderAnswer);
			
			
			int indexToStart=questionPath.indexOf('M');
			String pathQuestion=questionPath.substring(indexToStart, questionPath.length());
			
			int indexToStart1=answerPath.indexOf('M');
			String pathAnswer=answerPath.substring(indexToStart1, answerPath.length());
			

			
			User usr=userService.findByUsername(userID);
			
			Class localClass=classService.findByClassName(classSelected);
			Subject localSubject=subjectService.findBysubName(subSelected);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicSelected);
			

			
			Set<QuizQuestion> quizMapping=new HashSet<QuizQuestion>();
			quizMapping.add(new QuizQuestion(quizService.countRow()+1,"Quiz",ServiceUtility.getCurrentTime(),ServiceUtility.getCurrentTime(),pathQuestion,pathAnswer,0,0,remark,ServiceUtility.getCurrentTime(),localTopic,usr));
			
			
			
			userService.addUserToQuizQuestion(usr, quizMapping);
			
			status.add("Success");
			
			}else {
				System.out.println("status error");
				status.add("failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			status.add("failure");
			
		}
		
		
		
		return status;
	}
	
	@PostMapping("/addDocumentFromUser")
	public @ResponseBody List<String> addDocumentFromUser(@RequestParam("classSelected") String classSelected,@RequestParam("subjectSelected") String subSelected,
										   @RequestParam("topicSelected") String topicSelected,@RequestParam("UserIdUserEnd") String userID,
										   @RequestParam("description") String desc,@RequestParam("source") String source,@RequestParam("Question") MultipartFile[] uploadDocument){
		
		List<String> status=new ArrayList<String>();
		
		if(!ServiceUtility.checkFileExtensionPDF(uploadDocument)) {
			
			status.add("failure");
			return status;
			
		}
		
		String createFolder=uploadDirectory+classSelected+"_"+subSelected+"/"+topicSelected+"/Document/";
		
		
		try {
			String path1=ServiceUtility.uploadFile(uploadDocument, createFolder);
			
			
			
			int indexToStart=path1.indexOf('M');
			String path=path1.substring(indexToStart, path1.length());
			

			Class localClass=classService.findByClassName(classSelected);
			Subject localSubject=subjectService.findBysubName(subSelected);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicSelected);
			

			
			User usr=userService.findByUsername(userID);
			
			Set<DocumentExternal> documentMapping=new HashSet<DocumentExternal>();
			documentMapping.add(new DocumentExternal(docuService.countRow()+1, "Document", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), desc, source, path, 0,0, ServiceUtility.getCurrentTime(), localTopic, usr));


			userService.addUserToDocument(usr, documentMapping);
			status.add("Success");
			
		} catch (Exception e) {
			
			status.add("failure");
			
		}
		
		
		
		return status;
	}
				
	
	@PostMapping("/addConceptFromUser")
	public @ResponseBody List<String> addConceptFromUser(@RequestParam("classSelected") String classSelected,@RequestParam("subjectSelected") String subSelected,
										   @RequestParam("topicSelected") String topicSelected,@RequestParam("UserIdUserEnd") String userID,
										   @RequestParam("descriptionConceptMap") String desc,@RequestParam("headlineConceptMap") String remark,@RequestParam("conceptMapImage") MultipartFile[] uploadDocument){
		
		List<String> status=new ArrayList<String>();
		
		if(!ServiceUtility.checkFileExtensionImage(uploadDocument)) {
			
			status.add("failure");
			return status;
			
		}
		
		String createFolder=uploadDirectory+classSelected+"_"+subSelected+"/"+topicSelected+"/ConceptMap/";
		
		
		try {
			String path1=ServiceUtility.uploadFile(uploadDocument, createFolder);
			
			
			
			int indexToStart=path1.indexOf('M');
			String path=path1.substring(indexToStart, path1.length());
			

			Class localClass=classService.findByClassName(classSelected);
			Subject localSubject=subjectService.findBysubName(subSelected);
			SubjectClassMapping localSubjectClass=subjectClassService.findBysubAndstandard( localClass,localSubject);
			Topic localTopic=topicService.findBysubjectClassMappingAndtopicName(localSubjectClass, topicSelected);
			

			
			User usr=userService.findByUsername(userID);
			
			Set<ConceptMap> conceptMapping=new HashSet<ConceptMap>();
			conceptMapping.add(new ConceptMap(concepMapService.countRow()+1, "ConceptMap", ServiceUtility.getCurrentTime(), ServiceUtility.getCurrentTime(), path, desc, 0,0, remark, localTopic, usr));
			
			userService.addUserToConceptMap(usr, conceptMapping);
			status.add("Success");
			
		} catch (Exception e) {
			
			status.add("failure");
			
		}
		
		
		
		return status;
	}
	
	/*---------------------------------------------------------------END----------------------------------------------------------------------------*/
}



/*------------------------------------------------------------END------------------------------------------------------------------*/

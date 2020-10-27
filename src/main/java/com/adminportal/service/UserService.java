/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for USer interacting with its repository for database operation
 */

package com.adminportal.service;

import java.util.List;
import java.util.Set;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Class;
import com.adminportal.content.Comment;
import com.adminportal.content.CommentReply;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.Subject;
import com.adminportal.content.Tutorial;
import com.adminportal.content.VideoExternal;
import com.adminportal.domain.User;
import com.adminportal.domain.UserRole;

/**
 * This interface has all the method declaration related to User database operation
 * @author om prakash
 *
 */

public interface UserService {
	
	/**
	 * Find User object given user email address
	 * @param username email address
	 * @return User object
	 */
	User findByUsername(String username);
	
	/**
	 * Find User object given user's token
	 * @param token random token
	 * @return User object
	 */
	User findByToken(String token);
	
	/**
	 * Persist user object in database
	 * @param user A user object
	 * @return user object
	 */
	User save(User user);
	
	/**
	 * Find User object given user email and password
	 * @param username email of user
	 * @param password password of user
	 * @return user object
	 * @throws Exception
	 */
	User existsByUser(String username,String password) throws Exception;
	
	/**
	 * Persist User object with relationship given in list of userRole object
	 * @param user User object
	 * @param userRoles list of UserRole object
	 * @return User object
	 * @throws Exception
	 */
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	/**
	 * Find all the user record from database
	 * @return list of user object
	 */
	List<User> findAll();
	
	/**
	 * Find USer object using User ID
	 * @param id User ID
	 * @return User object
	 */
	User findById(int id);
	
	/**
	 * Delete User object from database given user ID
	 * @param id User ID
	 */
	void deleteById(int id);
	
	/**
	 * Update User's visibility in database
	 * @param isvalid value to be set
	 * @param id User ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean disableEnableUser(int isvalid,int id);
	
	/**
	 * Update User's TeacherApprove field in database
	 * @param isvalid value to be set
	 * @param id User ID
	 * @return boolean value based on successful operation or not ?
	 */
	boolean enableApproveTeacher(int isvalid,int id);
	
	/**
	 * Checks if User exist given email address
	 * @param Email Email address
	 * @return boolean value based on successful operation or not ?
	 */
	boolean existByEmail(String Email);
	
	/**
	 * Add user data in Article's user field and persist Article object into database
	 * @param usr User object
	 * @param article Set of Article resource object
	 * @return User object
	 */
	User addUserToArticle(User usr,Set<ArticleExternal> article);
	
	/**
	 * Add user data in DocumentExternal's user field and persist DocumentExternal object into database
	 * @param usr User object
	 * @param document Set of DocumentExternal resource object
	 * @return User object
	 */
	User addUserToDocument(User usr,Set<DocumentExternal> document);
	
	/**
	 * Add user data in LessonPlan's user field and persist LessonPlan object into database
	 * @param usr User object
	 * @param lesson Set of LessonPlan resource object
	 * @return User object
	 */
	User addUserToLessonplan(User usr,Set<LessonPlan> lesson);
	
	/**
	 * Add user data in Phets's user field and persist Phets object into database
	 * @param usr User object
	 * @param phet Set of Phets resource object
	 * @return User object
	 */
	User addUserToPhets(User usr,Set<Phets> phet);
	
	/**
	 * Add user data in QuizQuestion's user field and persist QuizQuestion object into database
	 * @param usr User object
	 * @param quiz Set of QuizQuestion resource object
	 * @return User object
	 */
	User addUserToQuizQuestion(User usr,Set<QuizQuestion> quiz);
	
	/**
	 * Add user data in ConceptMap's user field and persist ConceptMap object into database
	 * @param usr User object
	 * @param concept Set of ConceptMap resource object
	 * @return User object
	 */
	User addUserToConceptMap(User usr,Set<ConceptMap> concept);
	
	/**
	 * Add user data in VideoExternal's user field and persist VideoExternal object into database
	 * @param usr User object
	 * @param video Set of VideoExternal resource object
	 * @return User object
	 */
	User addUserToVideo(User usr,Set<VideoExternal> video);
	
	/**
	 * Add user data in Tutorial's user field and persist Tutorial object into database
	 * @param usr User object
	 * @param tutorial Set of Tutorial resource object
	 * @return User object
	 */
	User addUserToTutorial(User usr,Set<Tutorial> tutorial);
	
	/**
	 * Add user data in Comment's user field and persist Comment object into database 
	 * @param usr User object
	 * @param comment Set of Comment object
	 * @return User object
	 */
	User addUserToComment(User usr,Set<Comment> comment);
	
	/**
	 * Add user data in commentReply's user field and persist CommentReply object into database
	 * @param usr User Object
	 * @param comReply Set of CommentReply object
	 * @return
	 */
	User addUserToCommentReply(User usr,Set<CommentReply> comReply);
	
	/**
	 * Add user data in class's user field and persist class object into database
	 * @param usr User object
	 * @param clas class object
	 * @return User object
	 */
	User addUserToClass(User usr,Class clas);
	
	/**
	 * Add user data in Subject's user field and persist subject object into database
	 * @param usr User object
	 * @param sub subject object
	 * @return User object
	 */
	User adduserToSubject(User usr, Subject sub);
	
	/**
	 * update User's Password given USer Id
	 * @param password
	 * @param id User ID
	 * @return  boolean value based on successful operation or not ?
	 */
	boolean updateUserPassword(String password,int id);
	
	/**
	 * Update user's fname,lname given User ID
	 * @param fname first name of user
	 * @param lname last name of user
	 * @param id user ID
	 * @return  boolean value based on successful operation or not ?
	 */
	boolean updateUserDetails(String fname,String lname,int id);
	
	/**
	 * COunt total number of user record in database
	 * @return integer
	 */
	int countRow();
	

}

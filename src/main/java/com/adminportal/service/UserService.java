/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.service;

import java.util.List;
import java.util.Set;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Comment;
import com.adminportal.content.CommentReply;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.VideoExternal;
import com.adminportal.domain.User;
import com.adminportal.domain.UserRole;



public interface UserService {
	
	User findByUsername(String username);
	User save(User user);
	
	User existsByUser(String username,String password);
	
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	List<User> findAll();
	
	User findById(int id);
	
	void deleteById(int id);
	
	boolean disableEnableUser(int isvalid,int id);
	
	boolean existByEmail(String Email);
	

	User addUserToArticle(User usr,Set<ArticleExternal> article);
	
	User addUserToDocument(User usr,Set<DocumentExternal> document);
	
	User addUserToLessonplan(User usr,Set<LessonPlan> lesson);
	
	User addUserToPhets(User usr,Set<Phets> phet);
	
	User addUserToQuizQuestion(User usr,Set<QuizQuestion> quiz);
	
	User addUserToConceptMap(User usr,Set<ConceptMap> concept);
	
	User addUserToVideo(User usr,Set<VideoExternal> video);
	
	User addUserToComment(User usr,Set<Comment> comment);
	
	User addUserToCommentReply(User usr,Set<CommentReply> comReply);
	
	boolean updateUserPassword(String password,int id);
	

}

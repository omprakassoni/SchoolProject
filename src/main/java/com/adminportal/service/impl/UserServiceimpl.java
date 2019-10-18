/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */

package com.adminportal.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Comment;
import com.adminportal.content.CommentReply;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.VideoExternal;
import com.adminportal.domain.User;
import com.adminportal.domain.UserRole;
import com.adminportal.repository.UserRepository;
import com.adminportal.repository.UserRoleRepository;
import com.adminportal.service.UserService;




@Service
public class UserServiceimpl implements UserService {

	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	
	@Override
	public User save(User user) {
		
		return userRepository.save(user);
	}


	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		
		user.getUserRoles().addAll(userRoles);
		User localuser=userRepository.save(user);
			
		return localuser;
	}


	@Override
	public User existsByUser(String username, String password) {
		
		User local=userRepository.existsByUser(username, password);
		
		return local;
	}


	@Override
	public List<User> findAll() {
		
		List<User> localusr=(List<User>) userRepository.findAll();
		return localusr;
		
	}


	@Override
	public User findById(int id) {
		
		Optional<User> local=userRepository.findById(id);
		
		User localUsr=local.get();
		
		return localUsr;
	}


	@Override
	public void deleteById(int id) {
		
		Optional<User> temp=userRepository.findById(id);
		
		User localUsr=temp.get();
		System.out.println(localUsr);
		
		userRoleRepository.deleteByuser(localUsr);
		
		userRepository.deleteById(id);
		
	}


	@Override
	@Transactional
	public boolean disableEnableUser(int isvalid, int id) {
		
		int status=userRepository.disableuser(isvalid, id);
		
		if(status>0) {
			return true;
		}else {
			return false;
		}
	
	}


	@Override
	public boolean existByEmail(String Email) {
		
		return userRepository.existsByemail(Email);
	}


	@Override
	public User addUserToArticle(User usr, Set<ArticleExternal> article) {
		usr.getArticleExternal().addAll(article);
		userRepository.save(usr);
		
		return null;
	}


	@Override
	public User addUserToDocument(User usr, Set<DocumentExternal> document) {
	
		usr.getDocumentExternal().addAll(document);
		userRepository.save(usr);
		return null;
	}


	@Override
	public User addUserToLessonplan(User usr, Set<LessonPlan> lesson) {
		
		usr.getLessonPlan().addAll(lesson);
		userRepository.save(usr);
		return null;
	}


	@Override
	public User addUserToPhets(User usr, Set<Phets> phet) {
	
		usr.getPhet().addAll(phet);
		userRepository.save(usr);
		return null;
	}


	@Override
	public User addUserToQuizQuestion(User usr, Set<QuizQuestion> quiz) {
		
		usr.getQuiz().addAll(quiz);
		userRepository.save(usr);
		return null;
	}


	@Override
	public User addUserToVideo(User usr, Set<VideoExternal> video) {
		
		usr.getVideoExternal().addAll(video);
		userRepository.save(usr);
		return null;
	}


	@Override
	public User findByUsername(String username) {
		
		
		return userRepository.findByemail(username);
	}


	@Override
	public User addUserToComment(User usr, Set<Comment> comment) {
		
		usr.getComment().addAll(comment);
		userRepository.save(usr);
		
		return null;
	}


	@Override
	public User addUserToCommentReply(User usr, Set<CommentReply> comReply) {
		usr.getCommentReply().addAll(comReply);
		userRepository.save(usr);
		return null;
	}


	@Override
	@Transactional
	public boolean updateUserPassword(String password, int id) {
		int status=userRepository.updateUserPassword(password, id);
		if(status>0)
			return true;
		else
			return false;
		
	}




	
}

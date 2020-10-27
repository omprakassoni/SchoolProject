/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * 	Description	  : Service class for Comment interacting with its repository for database operation
 */
package com.adminportal.service;

import java.util.List;
import java.util.Set;

import javax.swing.text.Document;

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

/**
 * This interface has all the method declaration related to Comment database operation
 * @author om prakash
 *
 */
public interface CommentService {
	/**
	 * Count total number comment record from database
	 * @return integer
	 */
	int  countRow();
	
	/**
	 * Find list of Comment object from database based on Video object given as input parameter
	 * @param video video Object
	 * @return List of Comment object
	 */
	List<Comment> getCommentByVideoId(VideoExternal video);
	
	/**
	 * Find list of Comment object from database based on Article object given as input parameter
	 * @param article A article object
	 * @return List of Comment object
	 */
	List<Comment> getCommentByArticleId(ArticleExternal article);
	
	/**
	 * Find list of Comment object from database based on Quiz object given as input parameter
	 * @param quiz A quiz object
	 * @return List of Comment object
	 */
	List<Comment> getCommentByQuizId(QuizQuestion quiz);
	
	/**
	 * Find list of Comment object from database based on document object given as input parameter
	 * @param document document object
	 * @return List of Comment object
	 */
	List<Comment> getCommentByDocumentId(DocumentExternal document);
	
	/**
	 * Find list of Comment object from database based on Phet object given as input parameter
	 * @param phet Phet object
	 * @return List of Comment object
	 */
	List<Comment> getCommentByPhetId(Phets phet);
	
	/**
	 * Find list of Comment object from database based on Lesson object given as input parameter
	 * @param lesson Lesson Object
	 * @return List of Comment object
	 */
	List<Comment> getCommentByLessonId(LessonPlan lesson);
	
	/**
	 * Find list of Comment object from database based on ConcepMap object given as input parameter
	 * @param conceptMap ConcepMap object
	 * @return List of Comment object
	 */
	List<Comment> getCommentByConceptMap(ConceptMap conceptMap);
	
	/**
	 * Persist Comment reply to database using comment object
	 * @param comment
	 * @param commentReply
	 * @return Comment object
	 */
	Comment addReplyComment(Comment comment,Set<CommentReply> commentReply);
	
	/**
	 * Find comment object from database based on comment ID
	 * @param id Comment ID
	 * @return Comment Object
	 */
	Comment findById(int id);

}

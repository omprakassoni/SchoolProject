/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 */
package com.adminportal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Comment;
import com.adminportal.content.ConceptMap;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.VideoExternal;

/**
 * This Interface Extend CrudRepository to handle all database operation related to Comment
 * @author om prakash
 *
 */
public interface CommentRepository extends CrudRepository<Comment, Integer>{
	
	/**
	 * Find list of comment made on Video Resource
	 * @param video Video resource Object
	 * @return List of Comment object 
	 */
	List<Comment> findAllByvideo(VideoExternal video);			// listing list of comment based on Video type
	
	/**
	 * Find list of comment made on Article Resource
	 * @param article Article Object
	 * @return List of Comment object
	 */
	List<Comment> findAllByarticle(ArticleExternal article);	// listing list of comment based on article type
	
	/**
	 * Find list of comment made on Quiz Resource
	 * @param quiz Quiz Object
	 * @return List of Comment object
	 */
	List<Comment> findAllByquiz(QuizQuestion quiz);				// listing list of comment based on Quiz type
	
	/**
	 * Find list of comment made on LessonPlan Resource
	 * @param lesson Lessonplan Object
	 * @return List of Comment object
	 */
	List<Comment> findAllBylesson(LessonPlan lesson);			// listing list of comment based on lesson type
	
	/**
	 * Find list of comment made on Phet Resource
	 * @param phet phet Object
	 * @return List of Comment object
	 */
	List<Comment> findAllByphet(Phets phet);					// listing list of comment based on phet type
	
	/**
	 * Find list of comment made on Document Resource
	 * @param document Document Object
	 * @return List of Comment object
	 */
	List<Comment> findAllBydocument(DocumentExternal document);	// listing list of comment based on Document type
	
	/**
	 * Find list of comment made on ConceptMap Resource
	 * @param conceptMap Object
	 * @return List of Comment object
	 */
	List<Comment> findALlByconceptMap(ConceptMap conceptMap);	// listing list of comment based on Concept-map type

}

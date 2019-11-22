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

public interface CommentRepository extends CrudRepository<Comment, Integer>{
	
	List<Comment> findAllByvideo(VideoExternal video);			// listing list of comment based on Video type
	
	List<Comment> findAllByarticle(ArticleExternal article);	// listing list of comment based on article type
	
	List<Comment> findAllByquiz(QuizQuestion quiz);				// listing list of comment based on Quiz type
	
	List<Comment> findAllBylesson(LessonPlan lesson);			// listing list of comment based on lesson type
	
	List<Comment> findAllByphet(Phets phet);					// listing list of comment based on phet type
	
	List<Comment> findAllBydocument(DocumentExternal document);	// listing list of comment based on Document type
	
	List<Comment> findALlByconceptMap(ConceptMap conceptMap);	// listing list of comment based on Concept-map type

}

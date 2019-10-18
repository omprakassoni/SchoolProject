package com.adminportal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Comment;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.VideoExternal;

public interface CommentRepository extends CrudRepository<Comment, Integer>{
	
	List<Comment> findAllByvideo(VideoExternal video);
	
	List<Comment> findAllByarticle(ArticleExternal article);
	
	List<Comment> findAllByquiz(QuizQuestion quiz);
	
	List<Comment> findAllBylesson(LessonPlan lesson);
	
	List<Comment> findAllByphet(Phets phet);
	
	List<Comment> findAllBydocument(DocumentExternal document);

}

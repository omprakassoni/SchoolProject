/*  Company Name  : Spoken Tutorial IIT bombay
 * 	Author Name	  : Om Prakash
 * 	Version		  : 1.0
 * Description   : Implementation of Comment Service Interface to define method for database operation.
 */
package com.adminportal.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.adminportal.repository.CommentRepository;
import com.adminportal.service.CommentService;

/**
 * Default implementation of the {@link com.adminportal.service.CommentService} interface.  
 * @author om prakash
 *
 */
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository comRepo;
	
	/**
	 * @see com.adminportal.service.CommentService#countRow()
	 */
	@Override
	public int countRow() {
		
		return (int) comRepo.count();
	}

	/**
	 * @see com.adminportal.service.CommentService#getCommentByVideoId(VideoExternal video)
	 */
	@Override
	public List<Comment> getCommentByVideoId(VideoExternal video) {
		
		List<Comment> com=comRepo.findAllByvideo(video);
		
		return com;
	}

	/**
	 * @see com.adminportal.service.CommentService#addReplyComment(Comment comment, Set<CommentReply> commentReply)
	 */
	@Override
	public Comment addReplyComment(Comment comment, Set<CommentReply> commentReply) {
			
			comment.getCommentreply().addAll(commentReply);
			comRepo.save(comment);
			
		return null;
	}

	/**
	 * @see com.adminportal.service.CommentService#findById(int id)
	 */
	@Override
	public Comment findById(int id) {
		Optional<Comment> local=comRepo.findById(id);
		return local.get();
	}

	/**
	 * @see com.adminportal.service.CommentService#getCommentByArticleId(ArticleExternal article)
	 */
	@Override
	public List<Comment> getCommentByArticleId(ArticleExternal article) {
		
		List<Comment> com=comRepo.findAllByarticle(article);
		return com;
	}

	/**
	 * @see com.adminportal.service.CommentService#getCommentByQuizId(QuizQuestion quiz)
	 */
	@Override
	public List<Comment> getCommentByQuizId(QuizQuestion quiz) {
		
		List<Comment> com=comRepo.findAllByquiz(quiz);
		return com;
	}

	/**
	 * @see com.adminportal.service.CommentService#getCommentByDocumentId(DocumentExternal document)
	 */
	@Override
	public List<Comment> getCommentByDocumentId(DocumentExternal document) {
		
		List<Comment> com=comRepo.findAllBydocument(document);
		return com;
	}

	/**
	 * @see com.adminportal.service.CommentService#getCommentByPhetId(Phets phet)
	 */
	@Override
	public List<Comment> getCommentByPhetId(Phets phet) {
		
		List<Comment> com=comRepo.findAllByphet(phet);
		return com;
	}

	/**
	 * @see com.adminportal.service.CommentService#getCommentByLessonId(LessonPlan lesson)
	 */
	@Override
	public List<Comment> getCommentByLessonId(LessonPlan lesson) {
		
		List<Comment> com=comRepo.findAllBylesson(lesson);
		return com;
	}

	/**
	 * @see com.adminportal.service.CommentService#getCommentByConceptMap(ConceptMap conceptMap)
	 */
	@Override
	public List<Comment> getCommentByConceptMap(ConceptMap conceptMap) {
		
		List<Comment> com=comRepo.findALlByconceptMap(conceptMap);
		return com;
		
	}

	
}

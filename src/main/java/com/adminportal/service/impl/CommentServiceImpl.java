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

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository comRepo;
	
	@Override
	public int countRow() {
		
		return (int) comRepo.count();
	}

	@Override
	public List<Comment> getCommentByVideoId(VideoExternal video) {
		
		List<Comment> com=comRepo.findAllByvideo(video);
		
		return com;
	}

	@Override
	public Comment addReplyComment(Comment comment, Set<CommentReply> commentReply) {
			
			comment.getCommentreply().addAll(commentReply);
			comRepo.save(comment);
			
		return null;
	}

	@Override
	public Comment findById(int id) {
		Optional<Comment> local=comRepo.findById(id);
		return local.get();
	}

	@Override
	public List<Comment> getCommentByArticleId(ArticleExternal article) {
		
		List<Comment> com=comRepo.findAllByarticle(article);
		return com;
	}

	@Override
	public List<Comment> getCommentByQuizId(QuizQuestion quiz) {
		
		List<Comment> com=comRepo.findAllByquiz(quiz);
		return com;
	}

	@Override
	public List<Comment> getCommentByDocumentId(DocumentExternal document) {
		
		List<Comment> com=comRepo.findAllBydocument(document);
		return com;
	}

	@Override
	public List<Comment> getCommentByPhetId(Phets phet) {
		
		List<Comment> com=comRepo.findAllByphet(phet);
		return com;
	}

	@Override
	public List<Comment> getCommentByLessonId(LessonPlan lesson) {
		
		List<Comment> com=comRepo.findAllBylesson(lesson);
		return com;
	}

	@Override
	public List<Comment> getCommentByConceptMap(ConceptMap conceptMap) {
		
		List<Comment> com=comRepo.findALlByconceptMap(conceptMap);
		return com;
		
	}

	
}

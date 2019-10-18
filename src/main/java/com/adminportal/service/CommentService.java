package com.adminportal.service;

import java.util.List;
import java.util.Set;

import javax.swing.text.Document;

import com.adminportal.content.ArticleExternal;
import com.adminportal.content.Comment;
import com.adminportal.content.CommentReply;
import com.adminportal.content.DocumentExternal;
import com.adminportal.content.LessonPlan;
import com.adminportal.content.Phets;
import com.adminportal.content.QuizQuestion;
import com.adminportal.content.VideoExternal;
import com.adminportal.domain.User;

public interface CommentService {
	
	int  countRow();
	
	List<Comment> getCommentByVideoId(VideoExternal video);
	
	List<Comment> getCommentByArticleId(ArticleExternal article);
	
	List<Comment> getCommentByQuizId(QuizQuestion quiz);
	
	List<Comment> getCommentByDocumentId(DocumentExternal document);
	
	List<Comment> getCommentByPhetId(Phets phet);
	
	List<Comment> getCommentByLessonId(LessonPlan lesson);
	
	Comment addReplyComment(Comment comment,Set<CommentReply> commentReply);
	
	Comment findById(int id);

}

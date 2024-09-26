package com.marondal.marondalgram.comment.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.marondal.marondalgram.comment.domain.Comment;
import com.marondal.marondalgram.comment.dto.CommentView;
import com.marondal.marondalgram.comment.repository.CommentRepository;
import com.marondal.marondalgram.user.domain.User;
import com.marondal.marondalgram.user.service.UserService;

@Service
public class CommentService {
	
	private CommentRepository commentRepository;
	private UserService userService;
	
	public CommentService(
			CommentRepository commentRepository
			, UserService userService) {
		this.commentRepository = commentRepository;
		this.userService = userService;
	}
	
	public Comment addComment(int postId, int userId, String contents) {
		
		Comment comment = Comment.builder()
			.postId(postId)
			.userId(userId)
			.contents(contents)
			.build();
		
		return commentRepository.save(comment);
		
	}
	
	public List<CommentView> getCommentListByPostId(int postId) {
		
		List<Comment> commentList = commentRepository.findByPostId(postId);
		
		List<CommentView> commentViewList = new ArrayList<>();
		
		for(Comment comment:commentList) {
			
			int userId = comment.getUserId();
			User user = userService.getUserById(userId);
			
			CommentView commentView = CommentView.builder()
									.commentId(comment.getId())
									.userId(userId)
									.contents(comment.getContents())
									.loginId(user.getLoginId())
									.build();
			
			
			commentViewList.add(commentView);
		}
		
		return commentViewList;
	}
	
	public void deleteCommentByPostId(int postId) {
		commentRepository.deleteByPostId(postId);
	}

}

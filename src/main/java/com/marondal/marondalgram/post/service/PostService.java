package com.marondal.marondalgram.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.marondal.marondalgram.comment.dto.CommentView;
import com.marondal.marondalgram.comment.service.CommentService;
import com.marondal.marondalgram.common.FileManager;
import com.marondal.marondalgram.like.service.LikeService;
import com.marondal.marondalgram.post.domain.Post;
import com.marondal.marondalgram.post.dto.CardView;
import com.marondal.marondalgram.post.repository.PostRepository;
import com.marondal.marondalgram.user.domain.User;
import com.marondal.marondalgram.user.service.UserService;

@Service
public class PostService {
	
	private PostRepository postRepository;
	private UserService userService;
	private LikeService likeService;
	private CommentService commentService;
	
	public PostService(
			PostRepository postRepository
			, UserService userService
			, LikeService likeService
			, CommentService commentService) {
		this.postRepository = postRepository;
		this.userService = userService;
		this.likeService = likeService;
		this.commentService = commentService;
	}
	
	public Post addPost(int userId, String contents, MultipartFile file) {
		
		String urlPath = FileManager.saveFile(userId, file);
		
		Post post = Post.builder()
					.userId(userId)
					.contents(contents)
					.imagePath(urlPath)
					.build();
					
		
		Post result = postRepository.save(post);
		
		return result;
	}
	
	public List<CardView> getPostList(int loginUserId) {
		
		List<Post> postList = postRepository.findAllByOrderByIdDesc();
		
		List<CardView> cardViewList = new ArrayList<>();
		
		for(Post post:postList) {
			
			int userId = post.getUserId();
			User user = userService.getUserById(userId);
			
			int likeCount = likeService.getLikeCount(post.getId());
			boolean isLike = likeService.isLikeByUserIdAndPostId(loginUserId, post.getId());
			
			List<CommentView> commentList = commentService.getCommentListByPostId(post.getId());
			
			CardView cardView = CardView.builder()
								.postId(post.getId())
								.userId(userId)
								.contents(post.getContents())
								.imagePath(post.getImagePath())
								.loginId(user.getLoginId())
								.likeCount(likeCount)
								.isLike(isLike)
								.commentList(commentList)
								.build();
			
			cardViewList.add(cardView);
		}	
		
		return cardViewList; 
	}
	
	public boolean deletePost(int id, int userId) {
		
		Optional<Post> optionalPost = postRepository.findByIdAndUserId(id, userId);
		Post post = optionalPost.orElse(null);
		
		if(post != null) {
			// 좋아요
			likeService.deleteLikeByPostId(id);
			// 댓글
			commentService.deleteCommentByPostId(id);
						
			FileManager.removeFile(post.getImagePath());
			postRepository.delete(post);
			return true;
		} else {
			return false;
		}
		
	}

}

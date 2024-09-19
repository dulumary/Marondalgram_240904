package com.marondal.marondalgram.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.marondal.marondalgram.common.FileManager;
import com.marondal.marondalgram.post.domain.Post;
import com.marondal.marondalgram.post.dto.CardView;
import com.marondal.marondalgram.post.repository.PostRepository;
import com.marondal.marondalgram.user.domain.User;
import com.marondal.marondalgram.user.service.UserService;

@Service
public class PostService {
	
	private PostRepository postRepository;
	private UserService userService;
	
	public PostService(
			PostRepository postRepository
			, UserService userService) {
		this.postRepository = postRepository;
		this.userService = userService;
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
	
	public List<CardView> getPostList() {
		
		List<Post> postList = postRepository.findAllByOrderByIdDesc();
		
		List<CardView> cardViewList = new ArrayList<>();
		
		for(Post post:postList) {
			
			int userId = post.getUserId();
			User user = userService.getUserById(userId);
			
			CardView cardView = CardView.builder()
								.postId(post.getId())
								.userId(userId)
								.contents(post.getContents())
								.imagePath(post.getImagePath())
								.loginId(user.getLoginId())
								.build();
			
			cardViewList.add(cardView);
		}	
		
		return cardViewList; 
	}

}

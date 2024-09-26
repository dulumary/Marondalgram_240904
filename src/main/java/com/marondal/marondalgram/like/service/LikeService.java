package com.marondal.marondalgram.like.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.marondal.marondalgram.like.domain.Like;
import com.marondal.marondalgram.like.repository.LikeRepository;

import jakarta.transaction.Transactional;

@Service
public class LikeService {
	
	private LikeRepository likeRepository;
	
	public LikeService(LikeRepository likeRepository) {
		this.likeRepository = likeRepository;
	}
	
	public Like addLike(int postId, int userId) {
		
		Like like = Like.builder()
					.postId(postId)
					.userId(userId)
					.build();
		
		return likeRepository.save(like);
	}
	
	public boolean deleteLike(int postId, int userId) {
		
		Optional<Like> optionalLike = likeRepository.findByPostIdAndUserId(postId, userId);
		Like like = optionalLike.orElse(null);
		
		if(like != null) {
			likeRepository.delete(like);
			return true;
		} else {
			return false;
		}
	}
	
	// 게시글에 대응되는 좋아요 개수 조회 
	public int getLikeCount(int postId) {
		
		return likeRepository.countByPostId(postId);
		
	}
	
	// 특정사용자가 특정 게시글에 좋아요를 했는지 안했는지 
	public boolean isLikeByUserIdAndPostId(int userId, int postId) {
		// 특정 userId와 postId가 일치하는 행 조회
		int count = likeRepository.countByUserIdAndPostId(userId, postId);
		
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public void deleteLikeByPostId(int postId) {
		likeRepository.deleteByPostId(postId);
	}

}

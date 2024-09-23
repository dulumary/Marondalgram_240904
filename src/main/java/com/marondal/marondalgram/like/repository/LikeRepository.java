package com.marondal.marondalgram.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marondal.marondalgram.like.domain.Like;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	
	// SELECT count(*) FROM `like` WHERE `postId` = #{postId}
	public int countByPostId(int postId);
	
	// SELECT count(*) FROM `like` WHERE `userId` = #{userId} AND `postId` = #{postId}
	public int countByUserIdAndPostId(int userId, int postId);
	

}

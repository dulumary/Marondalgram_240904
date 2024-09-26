package com.marondal.marondalgram.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marondal.marondalgram.like.domain.Like;

import jakarta.transaction.Transactional;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	
	// SELECT count(*) FROM `like` WHERE `postId` = #{postId}
	public int countByPostId(int postId);
	
	// SELECT count(*) FROM `like` WHERE `userId` = #{userId} AND `postId` = #{postId}
	public int countByUserIdAndPostId(int userId, int postId);
	
	// SELECT * FROM `like` WHERE `postId` = #{postId} AND `userId` = #{userId}
	public Optional<Like> findByPostIdAndUserId(int postId, int userId);
	
	// transaction
	// SELECT * FROM `like` WHERE `postId` = #{postId}
	// DELETE FROM `like` WHERE `postId` = #{postId}
	@Transactional
	public void deleteByPostId(int postId);
}

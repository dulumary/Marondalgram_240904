package com.marondal.marondalgram.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marondal.marondalgram.comment.domain.Comment;

import jakarta.transaction.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	// WHERE `postId` = #{postId}
	public List<Comment> findByPostId(int postId);
	
	@Transactional
	public void deleteByPostId(int postId);
	

}

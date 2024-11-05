package com.marondal.marondalgram.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marondal.marondalgram.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

	// ORDER BY `id` DESC
	public List<Post> findTop3ByOrderByIdDesc();
	
	// WHERE `id` = #{id} AND `userId` = #{userId}
	public Optional<Post> findByIdAndUserId(int id, int userId);
	
	public List<Post> findTop3ByIdLessThanOrderByIdDesc(int id);
}

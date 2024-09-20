package com.marondal.marondalgram.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marondal.marondalgram.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}

package com.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.entity.Comment;
import com.blogapp.payloads.CommentDto;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

	
}

package com.blogapp.services;

import com.blogapp.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId);
	
	void deleteComment(Integer commentId);
}

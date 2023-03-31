package com.blogapp.services.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.entity.Comment;
import com.blogapp.entity.Post;
import com.blogapp.entity.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CommentDto;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.CommentRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.repositories.UserRepositories;
import com.blogapp.services.CommentService;

@Service
public class CommentSeriveImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private UserRepositories userRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper mapper;
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		// TODO Auto-generated method stub
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		
		//commentDto.setPostDto(this.mapper.map(post, PostDto.class));
		//commentDto.setUserDto(this.mapper.map(user, UserDto.class));
		
		Comment comment = this.mapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		comment.setUser(user);
		
		Comment updatedComment = this.commentRepo.save(comment);
		return this.mapper.map(updatedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentId", commentId));
		this.commentRepo.delete(comment);
	}

}

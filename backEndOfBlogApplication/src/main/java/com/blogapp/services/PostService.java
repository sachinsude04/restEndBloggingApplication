package com.blogapp.services;

import java.util.List;

import com.blogapp.entity.Post;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;

public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	
	void deletePost(Integer postId);
	
	//get all Posts
	PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//get single Post
	PostDto getPostById(Integer postId);

	List<PostDto> getPostByCategory(Integer categoryId);
	List<PostDto> getPostByUser(Integer userId);
	
	//search post 
	List<PostDto> searchPosts(String keyword);
}

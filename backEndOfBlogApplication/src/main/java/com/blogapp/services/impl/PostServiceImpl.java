package com.blogapp.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapp.entity.Category;
import com.blogapp.entity.Post;
import com.blogapp.entity.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.repositories.CategoryRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.repositories.UserRepositories;
import com.blogapp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepositories userRepositories;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		// TODO Auto-generated method stub
		
		User user = this.userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		Post post=this.mapper.map(postDto, Post.class);
		post.setImageName("default.jpg");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post updatedPost = this.postRepo.save(post);
		return this.mapper.map(updatedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
		post.setTitle(postDto.getTitle());
		post.setContent(post.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepo.save(post);
		
		return this.mapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		//Pageable p=PageRequest.of(pageNumber, pageSize);
		//normal find all method for all records but not usefull in project
		//List<Post> posts =this.postRepo.findAll();
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		//by using turnary oprator
		//Sort sort1=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p= PageRequest.of(pageNumber, pageSize, sort);
		 Page<Post> pagePosts = this.postRepo.findAll(p);
		 List<Post> posts = pagePosts.getContent();
		 List<PostDto> postDtos = posts.stream().map((post)->this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		System.out.println("Pagiable is working"+pageNumber+","+pageSize);
		
		PostResponse postResponse=new PostResponse();
		postResponse.setPostDtos(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElement(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		
		return this.mapper.map(post, PostDto.class);
	}
	//post by category
	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		
		List<PostDto> postDto = posts.stream().map((post)->this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

	//post by user
	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepositories.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDto = posts.stream().map((post)->this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}
	
	//search method
	@Override
	public List<PostDto> searchPosts(String keyword) {
		// TODO Auto-generated method stub
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map((post)->this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}
}

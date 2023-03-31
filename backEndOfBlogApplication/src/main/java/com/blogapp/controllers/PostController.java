package com.blogapp.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogapp.config.AppConstants;
import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.services.FileService;
import com.blogapp.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	//create 
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> creatPost(@RequestBody PostDto postDto,
			@PathVariable("userId") Integer userId,
			@PathVariable("categoryId") Integer categoryId
			)
	{
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
		
	}
	//get post by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Integer userId){
		
		List<PostDto> postByUser = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postByUser,HttpStatus.OK);
	}
	
	//get post by Category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId") Integer categoryId){
		
		List<PostDto> posts = this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//get post by postId
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId){
		
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	//get all post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value = "sortBy" , defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir" , defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
			)	{
		 PostResponse allPosts = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPosts,HttpStatus.OK);
	}
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable("postId") Integer postId){
		
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully",true),HttpStatus.OK);
	}
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> deletePostById(@RequestBody PostDto postDto, @PathVariable("postId") Integer postId){
		
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	//searching
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle( 
			@PathVariable("keywords") String keywords
			)
	{
		List<PostDto> result = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	// post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException
	{
		String fileName = this.fileService.uploadImage(path, image);
		PostDto postDto= this.postService.getPostById(postId);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
}

package com.blogapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogapp.entity.Category;
import com.blogapp.entity.Post;
import com.blogapp.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	//this is also working but i learn how to write query in jpa
	//List<Post> findByTitleContaining(String title);
	
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title); 
}

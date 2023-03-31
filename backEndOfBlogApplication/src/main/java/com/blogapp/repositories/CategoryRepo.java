package com.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

	
}

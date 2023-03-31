package com.blogapp.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.entity.Category;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.repositories.CategoryRepo;
import com.blogapp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.mapper.map(categoryDto, Category.class);
		System.out.println("in service"+category.getCategoryTitle());
		Category category2= categoryRepo.save(category);
		
		return this.mapper.map(category2, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "CategoryId", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = categoryRepo.save(category);
		return this.mapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		return this.mapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> categoriesDto = categories.stream().map((category)->this.mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return categoriesDto;
	}

}

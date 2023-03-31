package com.blogapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		System.out.println("Testing.................="+categoryDto.getCategoryTitle());
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("categoryId") Integer categoryId){
		CategoryDto createCategory = this.categoryService.updateCategory(categoryDto,categoryId);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.OK);
	}
	//delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId){
		
		this.categoryService.deleteCategory(categoryId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true) ,HttpStatus.OK);
	}
	//get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Integer categoryId){
		
		CategoryDto category = this.categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(category,HttpStatus.OK);
	}
	//getAll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories(){
		
		List<CategoryDto> categories = this.categoryService.getCategories();
		return new ResponseEntity<List<CategoryDto>>(categories,HttpStatus.OK);
	}
	
}

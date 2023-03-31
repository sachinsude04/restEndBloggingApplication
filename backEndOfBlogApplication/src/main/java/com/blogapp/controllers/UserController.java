package com.blogapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.UserDto;
import com.blogapp.services.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser()
	{
		List<UserDto> dtos=service.getAllUser();
		return new ResponseEntity<List<UserDto>>(dtos,HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@Valid @PathVariable("id") Integer UserId)
	{
		UserDto dtos=service.getUserById(UserId);
		return ResponseEntity.ok(dtos);
	}
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser( @RequestBody @Valid UserDto userDto,@PathVariable("id") Integer userId){
		UserDto updatedUser = this.service.updateUser(userDto, userId);
		return new ResponseEntity<>(updatedUser,HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUserById(@PathVariable("id") Integer userId) {
		
		service.deleteUser(userId);
		return new ResponseEntity<>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
	}
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser( @RequestBody @Valid UserDto userDto)
	{
		
		UserDto userDto1 = service.createUser(userDto);
		return new ResponseEntity<UserDto>(userDto1, HttpStatus.CREATED);
	}
	
	
}

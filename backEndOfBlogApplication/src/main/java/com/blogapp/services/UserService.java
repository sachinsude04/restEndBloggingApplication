package com.blogapp.services;

import java.util.List;

import com.blogapp.entity.User;
import com.blogapp.payloads.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto userDto);
 	UserDto createUser(UserDto user);
 	UserDto updateUser(UserDto userDto,Integer userId);
 	UserDto getUserById(Integer userId);
 	List<UserDto> getAllUser();
 	
	void deleteUser(Integer userId);
	
	
}

package com.blogapp.services.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapp.config.AppConstants;
import com.blogapp.entity.Role;
import com.blogapp.entity.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.RoleRepo;
import com.blogapp.repositories.UserRepositories;
import com.blogapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepositories repositories;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User savedUser=repositories.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		
		User user=this.repositories.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser=this.repositories.save(user);
		UserDto userDto2=this.userToDto(updatedUser);
		
		return userDto2;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.repositories.findById(userId)
		.orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		
		List<User> users=this.repositories.findAll();
		List<UserDto> userDto = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.repositories.findById(userId)
		.orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
		
		this.repositories.delete(user);
	}
	
	public User dtoToUser(UserDto dto)
	{
		User user=this.modelMapper.map(dto,User.class);
//		user.setId(dto.getId());
//		user.setName(dto.getName());
//		user.setEmail(dto.getEmail());
//		user.setPassword(dto.getPassword());
//		user.setAbout(dto.getAbout());
		return user;
	}
	public UserDto userToDto(User user) {
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		///write by me
		//user.setRoles(new HashSet<Role>(this.roleRepo.findAll()));
		
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User upadedUser = this.repositories.save(user);
		return this.modelMapper.map(upadedUser,UserDto.class);
	}
}


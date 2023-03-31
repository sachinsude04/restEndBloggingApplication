package com.blogapp.securityblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.blogapp.entity.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.repositories.UserRepositories;

//@Component
@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepositories userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loading user from database by userdata

		User user = this.userRepo.findByEmail(username)
				.orElseThrow(()->new ResourceNotFoundException("User","email : "+username,0));
		return user;
	}

}

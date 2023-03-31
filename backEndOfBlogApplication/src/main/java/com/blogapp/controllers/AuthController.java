package com.blogapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.exceptions.ApiException;
import com.blogapp.payloads.JwtAuthRequest;
import com.blogapp.payloads.JwtAuthResponse;
import com.blogapp.payloads.UserDto;
import com.blogapp.securityblog.JwtTokenHelper;
import com.blogapp.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService  userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest authRequest) throws Exception {

		System.out.println("UserName :" + authRequest.getUsername());
		this.authenticate(authRequest.getUsername(), authRequest.getPassword());

		UserDetails userDetail = this.userDetailsService.loadUserByUsername(authRequest.getUsername());

		String generateToken = this.jwtTokenHelper.generateToken(userDetail);

		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setToken(generateToken);

		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);

	}

	private void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);

		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		} catch (BadCredentialsException e) {
			// TODO: handle exception
			System.out.println("Invalid Details  !!!!");
			throw new ApiException("Invalid username or password");
		}
	}
	
	//register new user api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		
		System.out.println("Inside Register controller");
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}
}

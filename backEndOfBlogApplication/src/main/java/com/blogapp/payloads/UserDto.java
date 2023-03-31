package com.blogapp.payloads;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.blogapp.entity.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class UserDto implements Serializable{
	
	
	private int id;
	@NotEmpty(message = "Please enter name min 4 Charactor !!!!!!! ")
	@NotBlank
	@Size(min = 4,max = 50)
	private String name;
	@Email(message = "Email Address is not valid")
	@Size(min = 12)
	private String email;
	
	@NotEmpty
	@Size(min = 4, max = 10,message = "Password Must be min 4 chars and maximum of 10 chars !!!")
	private String password;
	
	@NotEmpty
	
	private String about;
	
	private Set<CommentDto> comments= new HashSet<>();
	
	
	private Set<RoleDto> roles=new HashSet<>();
	
}

package com.blogapp.payloads;

import com.blogapp.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CommentDto {

	private int id;
	private String content;
	
	//private UserDto userDto;
	
	//private PostDto postDtpo;
}

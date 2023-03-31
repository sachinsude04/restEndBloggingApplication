package com.blogapp.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

	String resourceNme;
	String fieldName;
	Long fieldValue;
	public ResourceNotFoundException(String resourceNme, String fieldName, long fieldValue) {
		super(String.format("%s not Found with %s :%s", resourceNme,fieldName,fieldValue));
		this.resourceNme = resourceNme;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
}

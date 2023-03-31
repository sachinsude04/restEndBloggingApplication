package com.blogapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blogapp.repositories.UserRepositories;

@SpringBootTest
class BackEndOfBlogApplicationTests {

	@Autowired
	private UserRepositories repositories;
	@Test
	void contextLoads() {
	}
	
	@Test
	public void repoTest()
	{
		String className=this.repositories.getClass().getName();
		String packageName=this.repositories.getClass().getPackageName();
		System.out.println("className"+className);
		System.out.println("PackageName"+packageName);
	}
}

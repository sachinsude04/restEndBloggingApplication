package com.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	
	
}

package com.test.poi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.poi.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	
}

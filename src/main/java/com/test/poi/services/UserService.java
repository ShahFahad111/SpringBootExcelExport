package com.test.poi.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.test.poi.model.User;
import com.test.poi.repository.UserRepo;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepo userRepo;
	
	public List<User> findAllUser(){
		return userRepo.findAll(Sort.by("email").ascending());
	}
	
	public boolean addU(User user) {
		User save = userRepo.save(user);
		System.out.println("Saved : " + save);
		if(user.equals(save))
			return true;
		else
			return false;
	}
}

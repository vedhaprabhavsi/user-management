package com.cognizant.usermanagement.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cognizant.usermanagement.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username){

		com.cognizant.usermanagement.model.User foundedUser = repo.findByUsername(username);
		
		if(foundedUser == null){
			return null;
		}
			
		String uname = foundedUser.getUsername();
		String pwd = foundedUser.getPassword();
		
		return new User(uname, pwd, new ArrayList<>());
	}
	
}

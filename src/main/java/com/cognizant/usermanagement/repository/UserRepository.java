package com.cognizant.usermanagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.usermanagement.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
	
	User findByUsername(String username);
}

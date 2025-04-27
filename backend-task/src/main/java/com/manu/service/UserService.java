package com.manu.service;

import java.util.Optional;

import com.manu.entity.User;

public interface UserService {
	
    User registerUser(String username, String password);
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findById(String id);
    
    boolean validateCredentials(String username, String password);
}

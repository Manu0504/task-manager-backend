package com.manu.service.Impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.manu.entity.User;
import com.manu.repository.UserRepository;
import com.manu.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User newUser = new User(username, password);
        return userRepository.save(newUser);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public boolean validateCredentials(String username, String password) {
        Optional<User> user = findByUsername(username);
        return user.isPresent() && user.get().getPassword().equals(password);
    }
}

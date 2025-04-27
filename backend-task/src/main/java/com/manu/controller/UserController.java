package com.manu.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manu.entity.User;
import com.manu.security.JwtTokenProvider;
import com.manu.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        
        if (username == null || password == null) {
            return new ResponseEntity<>("Username and password are required", HttpStatus.BAD_REQUEST);
        }
        
        try {
            User user = userService.registerUser(username, password);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("userId", user.getId());
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        
        if (username == null || password == null) {
            return new ResponseEntity<>("Username and password are required", HttpStatus.BAD_REQUEST);
        }
        
        if (userService.validateCredentials(username, password)) {
            Optional<User> user = userService.findByUsername(username);
            if (user.isPresent()) {
                String token = jwtTokenProvider.createToken(username, user.get().getId());
                
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("userId", user.get().getId());
                
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        
        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }
}
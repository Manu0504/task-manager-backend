package com.manu.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.manu.entity.User;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public User save(User user) {
        // Check if user already exists
        Optional<User> existingUser = findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            // Update existing user
            users.remove(existingUser.get());
        }
        users.add(user);
        return user;
    }

    public Optional<User> findById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public void deleteById(String id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}
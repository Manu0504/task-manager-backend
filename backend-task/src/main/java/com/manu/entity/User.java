package com.manu.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
	
    private String id;
    private String username;
    private String password;
    private List<Task> tasks;

    public User() {
        this.id = UUID.randomUUID().toString();
        this.tasks = new ArrayList<>();
    }

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

}
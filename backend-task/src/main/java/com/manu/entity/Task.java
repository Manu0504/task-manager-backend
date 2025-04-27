package com.manu.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Task {

	private String id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private String userId;
    
    public Task() {
        this.id = UUID.randomUUID().toString();
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    public Task(String title, String description, String userId) {
        this();
        this.title = title;
        this.description = description;
        this.userId = userId;
    }
   
}

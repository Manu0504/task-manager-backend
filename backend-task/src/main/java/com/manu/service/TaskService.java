package com.manu.service;

import java.util.List;
import java.util.Optional;

import com.manu.entity.Task;

public interface TaskService {
	
    Task createTask(String title, String description, String userId);
    
    List<Task> getUserTasks(String userId);
    
    Optional<Task> findById(String id);
    
    boolean deleteTask(String id, String userId);
    
}

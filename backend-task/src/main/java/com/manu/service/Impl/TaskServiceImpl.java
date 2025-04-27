package com.manu.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manu.entity.Task;
import com.manu.repository.TaskRepository;
import com.manu.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String title, String description, String userId) {
        Task task = new Task(title, description, userId);
        return taskRepository.save(task);
    }

    public List<Task> getUserTasks(String userId) {
        return taskRepository.findByUserId(userId);
    }

    public Optional<Task> findById(String id) {
        return taskRepository.findById(id);
    }

    public boolean deleteTask(String id, String userId) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent() && task.get().getUserId().equals(userId)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
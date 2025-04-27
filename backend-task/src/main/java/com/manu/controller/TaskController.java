package com.manu.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manu.entity.Task;
import com.manu.security.JwtTokenProvider;
import com.manu.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public TaskController(TaskService taskService, JwtTokenProvider jwtTokenProvider) {
        this.taskService = taskService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Map<String, String> request, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        
        String title = request.get("title");
        String description = request.get("description");
        
        if (title == null) {
            return new ResponseEntity<>("Title is required", HttpStatus.BAD_REQUEST);
        }
        
        Task task = taskService.createTask(title, description, userId);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getUserTasks(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        
        List<Task> tasks = taskService.getUserTasks(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable String taskId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        
        Optional<Task> task = taskService.findById(taskId);
        
        if (task.isPresent() && task.get().getUserId().equals(userId)) {
            return new ResponseEntity<>(task.get(), HttpStatus.OK);
        }
        
        return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable String taskId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        
        boolean deleted = taskService.deleteTask(taskId, userId);
        
        if (deleted) {
            return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
        }
        
        return new ResponseEntity<>("Task not found or unauthorized", HttpStatus.NOT_FOUND);
    }
}
package com.manu.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.manu.entity.Task;

@Repository
public class TaskRepository {
    private final List<Task> tasks = new ArrayList<>();

    public Task save(Task task) {
        // Check if task already exists
        Optional<Task> existingTask = findById(task.getId());
        if (existingTask.isPresent()) {
            // Update existing task
            tasks.remove(existingTask.get());
        }
        tasks.add(task);
        return task;
    }

    public Optional<Task> findById(String id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    public List<Task> findByUserId(String userId) {
        return tasks.stream()
                .filter(task -> task.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    public void deleteById(String id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }
}
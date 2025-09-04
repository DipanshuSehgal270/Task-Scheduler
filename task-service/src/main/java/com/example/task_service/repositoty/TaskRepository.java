package com.example.task_service;

import com.example.task_service.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface repository extends JpaRepository<Task,Long> {
    // A custom method to find all tasks for a specific user
    List<Task> findByUserId(Long userId);
}

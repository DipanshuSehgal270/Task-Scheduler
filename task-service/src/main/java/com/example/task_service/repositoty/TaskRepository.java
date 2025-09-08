package com.example.task_service.repositoty;

import com.example.task_service.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    // A custom method to find all tasks for a specific user
    List<Task> findByUserId(Long userId);
}

package com.example.task_service.service;

import com.example.task_service.dto.TaskRequest;
import com.example.task_service.entity.Task;
import com.example.task_service.repositoty.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(TaskRequest taskRequest, Long userId) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDueDateTime(taskRequest.getDueDateTime());
        task.setUserId(userId);
        task.setStatus("PENDING");
        return taskRepository.save(task);
    }

    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }
}

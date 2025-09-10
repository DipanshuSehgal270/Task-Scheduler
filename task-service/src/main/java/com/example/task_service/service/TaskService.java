package com.example.task_service.service;

import com.example.task_service.dto.TaskRequest;
import com.example.task_service.dto.TaskResponse;
import com.example.task_service.entity.Task;
import com.example.task_service.exception.TaskNotFoundException;
import com.example.task_service.repositoty.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public TaskService(TaskRepository taskRepository,ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    public TaskResponse createTask(TaskRequest taskRequest, Long userId) {

        //convert request to entity
        Task task = modelMapper.map(taskRequest, Task.class);
        task.setUserId(userId);
        task.setStatus("PENDING");

        //save the task in repository
        Task savedTask = taskRepository.save(task);

        //convert entity to dto
        TaskResponse response = modelMapper.map(savedTask , TaskResponse.class);
        return response;

    }

    public List<TaskResponse> getTasksByUserId(Long userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(task -> modelMapper.map(task, TaskResponse.class))
                .collect(Collectors.toList());
    }

    public TaskResponse getTaskById(Long taskId , Long userId)
    {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found..."));
        if (!task.getUserId().equals(userId)) {
            throw new TaskNotFoundException("Task not found...");
        }
        return modelMapper.map(task, TaskResponse.class);
    }

    public TaskResponse updateTask(Long userId , Long taskId , TaskRequest taskRequest)
    {
        Task fecthedTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found..."));
        if (!fecthedTask.getUserId().equals(userId)) {
            throw new TaskNotFoundException("Task not found...");
        }

        // 2. Use Method 2: Update the fetched task with data from the request
        modelMapper.map(taskRequest, fecthedTask);

        // 3. Save the updated task back to the database
        Task updatedTask = taskRepository.save(fecthedTask);

        // 4. Convert the final entity to a response DTO and return it
        return modelMapper.map(updatedTask, TaskResponse.class);
    }

    public void deleteTask(Long taskId, Long userId) {
        Task taskToDelete = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        if (!taskToDelete.getUserId().equals(userId)) {
            throw new TaskNotFoundException("Task not found with id: " + taskId);
        }
        taskRepository.delete(taskToDelete);
    }

}

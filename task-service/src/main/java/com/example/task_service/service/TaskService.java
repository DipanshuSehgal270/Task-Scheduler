package com.example.task_service.service;

import com.example.task_service.client.CollaborationClient;
import com.example.task_service.client.UserClient;
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
    private final CollaborationClient collaborationClient;
    private final UserClient userClient;

    public TaskService(TaskRepository taskRepository, ModelMapper modelMapper, CollaborationClient collaborationClient, UserClient userClient) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.collaborationClient = collaborationClient;
        this.userClient = userClient;
    }

    public void checkPermission(Long tasklistId){
        collaborationClient.checkMembership(tasklistId);
    }

    public TaskResponse createTask(TaskRequest taskRequest, Long tasklistId) {

        checkPermission(tasklistId);

        //convert request to entity
        Task task = modelMapper.map(taskRequest, Task.class);
        task.setTasklistId(tasklistId);
        task.setStatus("PENDING");

        //save the task in repository
        Task savedTask = taskRepository.save(task);

        //convert entity to dto
        return modelMapper.map(savedTask , TaskResponse.class);
    }

    public List<TaskResponse> getTasksByTaskListId(Long tasklistId) {

        checkPermission(tasklistId);

        return taskRepository.findByTasklistId(tasklistId).stream()
                .map(task -> modelMapper.map(task, TaskResponse.class))
                .collect(Collectors.toList());
    }

    public TaskResponse getTaskById(Long taskId)
    {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found..."));
        checkPermission(task.getTasklistId());
        return modelMapper.map(task, TaskResponse.class);
    }

    public TaskResponse updateTask(Long tasklistId , Long taskId , TaskRequest taskRequest)
    {
        Task fetchedTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found..."));

        checkPermission(fetchedTask.getTasklistId());

        // 2. Use Method 2: Update the fetched task with data from the request
        modelMapper.map(taskRequest, fetchedTask);

        // 3. Save the updated task back to the database
        Task updatedTask = taskRepository.save(fetchedTask);

        // 4. Convert the final entity to a response DTO and return it
        return modelMapper.map(updatedTask, TaskResponse.class);
    }

    public void deleteTask(Long taskId) {

        Task taskToDelete = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        checkPermission(taskToDelete.getTasklistId());

        taskRepository.delete(taskToDelete);
    }

}

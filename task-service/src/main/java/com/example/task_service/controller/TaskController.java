package com.example.task_service.controller;


import com.example.task_service.client.UserClient;
import com.example.task_service.dto.TaskRequest;
import com.example.task_service.dto.TaskResponse;
import com.example.task_service.dto.UserResponse;
import com.example.task_service.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserClient userClient;

    public TaskController(TaskService taskService, UserClient userClient) {
        this.taskService = taskService;
        this.userClient = userClient;
    }

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest, Principal principal) {
        String userEmail = principal.getName();

        // Use the Feign client to call the User Service and get user details
        UserResponse user = userClient.getUserByEmail(userEmail);

        // Now we have the userId!
        TaskResponse createdTask = taskService.createTask(taskRequest, user.getId());
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping("/get")
    public ResponseEntity<List<TaskResponse>> getUserTasks(Principal principal) {
        String userEmail = principal.getName();
        UserResponse user = userClient.getUserByEmail(userEmail);
        List<TaskResponse> tasks = taskService.getTasksByUserId(user.getId());
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(Principal principal , @PathVariable Long taskId , @RequestBody TaskRequest taskRequest)
    {
        TaskResponse updatedTask = taskService.updateTask(getUserId(principal),taskId,taskRequest);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId, Principal principal) {
        taskService.deleteTask(taskId, getUserId(principal));
        return ResponseEntity.noContent().build();
    }

    private Long getUserId(Principal principal) {
        String userEmail = principal.getName();
        UserResponse user = userClient.getUserByEmail(userEmail);
        return user.getId();
    }

}

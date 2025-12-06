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
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        // Now we have the userId!
        TaskResponse createdTask = taskService.createTask(taskRequest, taskRequest.getTaskListId());
        return ResponseEntity.ok(createdTask);
    }


    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasksByList(
            // We read the taskListId from a URL query parameter, e.g., /tasks?taskListId=101
            @RequestParam Long taskListId)
    {
        List<TaskResponse> tasks = taskService.getTasksByTaskListId(taskListId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{taskId}")
     public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long taskId) {
        System.out.println("--- DEVTOOLS TEST: Change detected! ---");
         TaskResponse task = taskService.getTaskById(taskId); // Service would handle auth
         return ResponseEntity.ok(task);
     }

     //TODO CHECK THIS
    @PutMapping("/update/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long tasklistid,
                                                   @PathVariable Long taskId,
                                                   @RequestBody TaskRequest taskRequest) {
        TaskResponse updatedTask = taskService.updateTask(tasklistid , taskId, taskRequest);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    private Long getUserId(Principal principal) {
        String userEmail = principal.getName();
        UserResponse user = userClient.getUserByEmail(userEmail);
        return user.getId();
    }

}

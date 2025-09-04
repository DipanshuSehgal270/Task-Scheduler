package com.example.task_service.controller;


import com.example.task_service.dto.TaskRequest;
import com.example.task_service.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // We'll pass the userId as a header for now.
    // In a real advanced setup, this would be extracted from the JWT.
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskRequest taskRequest, Principal principal) {
        // The 'principal.getName()' will give us the user's email from the JWT
        String userEmail = principal.getName();

        // **Problem to solve next**: We have the email, but our service needs the user ID.
        // For now, we'll just log it to prove security is working.
        System.out.println("Creating task for user: " + userEmail);

        // We will implement the logic to get userId from userEmail in the next step.
        // For now, we can't create the task.
        return ResponseEntity.ok("Security works! Task creation for " + userEmail + " needs userId lookup.");
    }

    @GetMapping
    public ResponseEntity<?> getUserTasks(Principal principal) {
        String userEmail = principal.getName();
        System.out.println("Fetching tasks for user: " + userEmail);
        return ResponseEntity.ok("Security works! Task fetching for " + userEmail + " needs userId lookup.");
    }

}

package com.example.collaboration_service.controller;

import com.example.collaboration_service.client.UserClient;
import com.example.collaboration_service.dto.TaskListRequest;
import com.example.collaboration_service.dto.TaskListResponse;
import com.example.collaboration_service.dto.UserResponse;
import com.example.collaboration_service.service.CollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/lists")
public class TaskListController {

    @Autowired
    private CollaborationService collaborationService;
    @Autowired
    private UserClient userClient;

    private Long getUserId(Principal principal) {
        String userEmail = principal.getName();
        UserResponse user = userClient.getUserByEmail(userEmail);
        return user.getId();
    }

    @PostMapping
    public ResponseEntity<TaskListResponse> createTaskList(@RequestBody TaskListRequest request, Principal principal) {
        Long ownerId = getUserId(principal);
        TaskListResponse response = collaborationService.createTaskList(request, ownerId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

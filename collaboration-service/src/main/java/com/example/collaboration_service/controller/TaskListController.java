package com.example.collaboration_service.controller;

import com.example.collaboration_service.client.UserClient;
import com.example.collaboration_service.dto.InvitationRequest;
import com.example.collaboration_service.dto.TaskListRequest;
import com.example.collaboration_service.dto.TaskListResponse;
import com.example.collaboration_service.dto.UserResponse;
import com.example.collaboration_service.service.CollaborationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/lists")
public class TaskListController {

    private final CollaborationService collaborationService;
    private final UserClient userClient;

    public TaskListController(CollaborationService collaborationService, UserClient userClient) {
        this.collaborationService = collaborationService;
        this.userClient = userClient;
    }

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

    //add data into to tasklist made in collab service
    //add or invite different users in the tasklist with role as member.  {create a qr or invite link}

    //create 3 get methods where first would be of getting all the lists the user is member of, second fetch
    // all the lists the user is leader of , 3rd fetch all the lists a user is either member or leader of .

    @GetMapping("/member")
    public ResponseEntity<List<TaskListResponse>> getMemberLists(Principal principal) {
        List<TaskListResponse> lists = collaborationService.getMemberLists(getUserId(principal));
        return ResponseEntity.ok(lists);
    }

    // --- NEW ENDPOINT 2: Get lists where user is LEADER ---
    @GetMapping("/leader")
    public ResponseEntity<List<TaskListResponse>> getLeaderLists(Principal principal) {
        List<TaskListResponse> lists = collaborationService.getLeaderLists(getUserId(principal));
        return ResponseEntity.ok(lists);
    }

    // --- NEW ENDPOINT 3: Get ALL lists user is part of ---
    // We map this to the base "/lists" path for simplicity
    @GetMapping
    public ResponseEntity<List<TaskListResponse>> getAllUserLists(Principal principal) {
        List<TaskListResponse> lists = collaborationService.getAllUserLists(getUserId(principal));
        return ResponseEntity.ok(lists);
    }

    @GetMapping("/{taskListId}/check-membership")
    public ResponseEntity<Void> checkMembership(
            @PathVariable Long taskListId,
            Principal principal)
    {
        Long userId = getUserId(principal);
        boolean isMember = collaborationService.isUserMemberOfList(taskListId, userId);

        if (isMember) {
            // If the user is a member, return 200 OK.
            return ResponseEntity.ok().build();
        } else {
            // If not, return 404 Not Found.
            // The TaskService's Feign client will see this and throw an exception.
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{listId}/members")
    public ResponseEntity<Void> addMember(
            @PathVariable Long listId,
            @RequestBody InvitationRequest request,
            Principal principal)
    {
        Long requesterId = getUserId(principal);
        collaborationService.addMember(listId, request, requesterId);
        return ResponseEntity.ok().build();
    }

}

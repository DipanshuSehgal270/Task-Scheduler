package com.example.collaboration_service.service;

import com.example.collaboration_service.client.UserClient;
import com.example.collaboration_service.dto.*;
import com.example.collaboration_service.entity.Membership;
import com.example.collaboration_service.entity.TaskList;
import com.example.collaboration_service.entity.TaskListRole;
import com.example.collaboration_service.repository.MembershipRepository;
import com.example.collaboration_service.repository.TaskListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CollaborationService {

    private final TaskListRepository taskListRepository;
    private final MembershipRepository membershipRepository;
    private final ModelMapper modelMapper;
    private final UserClient userClient;
    private final RabbitTemplate rabbitTemplate;

    public CollaborationService(TaskListRepository taskListRepository, MembershipRepository membershipRepository, ModelMapper modelMapper, UserClient userClient, RabbitTemplate rabbitTemplate) {
        this.taskListRepository = taskListRepository;
        this.membershipRepository = membershipRepository;
        this.modelMapper = modelMapper;
        this.userClient = userClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public TaskListResponse createTaskList(TaskListRequest request, Long ownerId) {
        TaskList taskList = new TaskList();
        taskList.setListName(request.getName());
        taskList.setOwnerId(ownerId);
        TaskList savedTaskList = taskListRepository.save(taskList);

        Membership membership = new Membership();
        membership.setTaskListId(savedTaskList.getId());
        membership.setUserId(ownerId);
        membership.setRole(TaskListRole.LEADER);
        membershipRepository.save(membership);

        return modelMapper.map(savedTaskList, TaskListResponse.class);
    }

    @Transactional
    public void addMember(Long taskListId, InvitationRequest request, Long requesterId) {
        // 1. Validation Logic (Existing)
        Membership requesterMembership = membershipRepository.findByTaskListIdAndUserId(taskListId, requesterId)
                .orElseThrow(() -> new RuntimeException("Access Denied"));

        if (requesterMembership.getRole() != TaskListRole.LEADER) {
            throw new RuntimeException("Only leaders can invite members");
        }

        UserResponse invitee = userClient.getUserByEmail(request.getEmail());
        if (invitee == null) throw new RuntimeException("User not found");

        if (membershipRepository.findByTaskListIdAndUserId(taskListId, invitee.getId()).isPresent()) {
            throw new RuntimeException("User is already a member");
        }

        // 2. Save Membership (Existing)
        Membership newMember = new Membership();
        newMember.setTaskListId(taskListId);
        newMember.setUserId(invitee.getId());
        newMember.setRole(TaskListRole.MEMBER);
        membershipRepository.save(newMember);

        // 3. Publish Event (NEW)
        InvitationEvent event = new InvitationEvent(request.getEmail(), taskListId);
        rabbitTemplate.convertAndSend("invitationQueue", event);
        System.out.println("--- Event Published to RabbitMQ: " + event + " ---");
    }

    private List<TaskListResponse> getTaskListDetails(List<Membership> membershipList)
    {
        if(membershipList.isEmpty())
        {
            return Collections.emptyList();
        }

        Set<Long> taskListIds = membershipList.stream()
                .map(Membership::getTaskListId)
                .collect(Collectors.toSet());

        List<TaskList> taskLists = taskListRepository.findAllById(taskListIds);

        return taskLists.stream()
                .map(taskList -> modelMapper.map(taskList,TaskListResponse.class))
                .collect(Collectors.toList());
    }

    public List<TaskListResponse> getMemberLists(Long userId)
    {
        List<Membership> membershipList = membershipRepository.findByUserIdAndRole(userId , TaskListRole.MEMBER);
        return getTaskListDetails(membershipList);
    }

    public List<TaskListResponse> getLeaderLists(Long userId) {
        List<Membership> leaderMemberships = membershipRepository.findByUserIdAndRole(userId, TaskListRole.LEADER);
        return getTaskListDetails(leaderMemberships);
    }

    public List<TaskListResponse> getAllUserLists(Long userId) {
        List<Membership> allMemberships = membershipRepository.findByUserId(userId);
        return getTaskListDetails(allMemberships);
    }


    public boolean isUserMemberOfList(Long taskListId, Long userId) {
        // We use the repository method we created earlier.
        return membershipRepository.findByTaskListIdAndUserId(taskListId, userId).isPresent();
    }
}
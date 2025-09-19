package com.example.collaboration_service.service;

import com.example.collaboration_service.dto.TaskListRequest;
import com.example.collaboration_service.dto.TaskListResponse;
import com.example.collaboration_service.entity.Membership;
import com.example.collaboration_service.entity.TaskList;
import com.example.collaboration_service.entity.TaskListRole;
import com.example.collaboration_service.repository.MembershipRepository;
import com.example.collaboration_service.repository.TaskListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollaborationService {

    @Autowired
    private TaskListRepository taskListRepository;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private ModelMapper modelMapper;

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
}
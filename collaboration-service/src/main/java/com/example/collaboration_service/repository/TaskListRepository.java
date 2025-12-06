package com.example.collaboration_service.repository;

import com.example.collaboration_service.entity.Membership;
import com.example.collaboration_service.entity.TaskList;
import com.example.collaboration_service.entity.TaskListRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {

    List<TaskList> findByOwnerId(Long ownerId);

}

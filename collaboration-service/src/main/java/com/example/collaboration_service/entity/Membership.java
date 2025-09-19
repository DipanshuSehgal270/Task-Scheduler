package com.example.collaboration_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "memberships")
@Data
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long taskListId;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private TaskListRole role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Long taskListId) {
        this.taskListId = taskListId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TaskListRole getRole() {
        return role;
    }

    public void setRole(TaskListRole role) {
        this.role = role;
    }
}

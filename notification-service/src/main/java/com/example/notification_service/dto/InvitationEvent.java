package com.example.notification_service.dto;

import java.io.Serializable;

public class InvitationEvent implements Serializable {

    private String userEmail;
    private Long taskListId;

    public InvitationEvent() {
    }

    public InvitationEvent(String userEmail, Long taskListId) {
        this.userEmail = userEmail;
        this.taskListId = taskListId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Long taskListId) {
        this.taskListId = taskListId;
    }

    @Override
    public String toString() {
        return "InvitationEvent{" +
                "userEmail='" + userEmail + '\'' +
                ", taskListId=" + taskListId +
                '}';
    }
}

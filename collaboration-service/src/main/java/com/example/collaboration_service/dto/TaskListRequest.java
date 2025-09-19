package com.example.collaboration_service.dto;

import lombok.Data;

@Data
public class TaskListRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

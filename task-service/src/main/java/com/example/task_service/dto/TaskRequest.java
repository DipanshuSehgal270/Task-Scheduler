package com.example.task_service.dto;

import com.example.task_service.entity.Priority;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskRequest {

    private String title;
    private String description;
    private Priority priority;
    private LocalDateTime dueDateTime;

    private Long taskListId;

    public Long getTaskListId() {
        return taskListId;
    }
    public void setTaskListId(Long taskListId) {
        this.taskListId = taskListId;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }
}

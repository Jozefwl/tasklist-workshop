package com.waldhauser.tasklist.rest.model.task;

import java.util.UUID;

public class TaskResponse {
    private UUID id;
    private UUID ownerId;
    private String title;
    private String description;
    private UUID tasklistId;

    public TaskResponse(UUID id, UUID ownerId, String title, String description, UUID tasklistId) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.tasklistId = tasklistId;
    }

    // getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getOwnerId() { return ownerId; }
    public void setOwnerId(UUID id) { this.ownerId = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public UUID getTasklistId() { return tasklistId; }
    public void setTasklistId(UUID tasklistId) { this.tasklistId = tasklistId; }
}

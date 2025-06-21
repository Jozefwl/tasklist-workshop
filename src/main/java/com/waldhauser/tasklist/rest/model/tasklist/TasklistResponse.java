package com.waldhauser.tasklist.rest.model.tasklist;

import com.waldhauser.tasklist.rest.model.task.TaskResponse;

import java.util.List;
import java.util.UUID;

public class TasklistResponse {

    private UUID id;
    private UUID ownerId;
    private String name;
    private String description;
    private List<TaskResponse> tasks;

    public TasklistResponse(UUID id, UUID ownerId, String name, String description, List<TaskResponse> tasks) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    // getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getOwnerId() { return ownerId; }
    public void setOwnerId(UUID id) { this.ownerId = ownerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<TaskResponse> getTasks() { return tasks; }
    public void setTasks(List<TaskResponse> tasks) { this.tasks = tasks; }
}

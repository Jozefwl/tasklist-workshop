package com.waldhauser.tasklist.rest.model.task;


import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public class TaskCreateRequest {

    @NotNull(message = "Tasklist ID cannot be null")
    private UUID tasklistId;
    @NotNull(message = "Owner ID cannot be null")
    private UUID ownerId;
    @NotNull(message = "Name cannot be null")
    @Length(min = 5, max = 255, message = "Name must be between 5 and 255 characters")
    private String name;
    private String description;

    // getters and setters

    public UUID getTasklistId() {
        return tasklistId;
    }

    public void setTasklistId(UUID tasklistId) {
        this.tasklistId = tasklistId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
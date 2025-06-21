package com.waldhauser.tasklist.rest.model.task;


import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

/**
 * Represents a request to create a new task within a specific task list.
 * It contains necessary details such as task name, description, owner ID,
 * and the associated task list ID.
 *
 * This class is used to transfer data from the client to the server during
 * task creation operations. Proper validation constraints are defined for its fields
 * to ensure data consistency and correctness.
 */
public class TaskCreateRequest {

    @NotNull(message = "Tasklist ID cannot be null")
    private UUID tasklistId;
    @NotNull(message = "Name cannot be null")
    @Length(min = 5, max = 255, message = "Name must be between 5 and 255 characters")
    private String name;
    private String description;

    // getters and setters

    /**
     * Retrieves the unique identifier of the task list associated with the task creation request.
     *
     * @return the UUID representing the task list ID
     */
    public UUID getTasklistId() {
        return tasklistId;
    }

    /**
     * Sets the unique identifier of the task list associated with the task creation request.
     *
     * @param tasklistId the UUID representing the task list to which the task belongs
     */
    public void setTasklistId(UUID tasklistId) {
        this.tasklistId = tasklistId;
    }

    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task associated with the task creation request.
     *
     * @param name the name of the task as a string
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the description of the task associated with the request.
     *
     * @return the task description as a string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task associated with the request.
     *
     * @param description the task description as a string
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
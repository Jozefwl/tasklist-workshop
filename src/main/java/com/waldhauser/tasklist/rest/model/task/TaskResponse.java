package com.waldhauser.tasklist.rest.model.task;

import java.util.UUID;

/**
 * Represents the response model for a task entity.
 * This class contains essential information about a task, including
 * its unique identifier, owner, title, description, and association
 * with a specific task list.
 * <p>
 * Typically used to transfer task data from the server to the client.
 */
public class TaskResponse {
    private UUID id;
    private UUID ownerId;
    private String title;
    private String description;
    private UUID tasklistId;

    /**
     * Constructs a new TaskResponse object that encapsulates information
     * about a task including its unique identifier, owner, title, description,
     * and the task list it belongs to.
     *
     * @param id the unique identifier of the task
     * @param ownerId the unique identifier of the owner of the task
     * @param title the title of the task
     * @param description the description of the task
     * @param tasklistId the unique identifier of the task list to which the task belongs
     */
    public TaskResponse(UUID id, UUID ownerId, String title, String description, UUID tasklistId) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.tasklistId = tasklistId;
    }

    /**
     * Retrieves the unique identifier of the task.
     *
     * @return the UUID representing the task ID
     */
    public UUID getId() { return id; }

    /**
     * Sets the unique identifier of the task.
     *
     * @param id the UUID representing the task ID
     */
    public void setId(UUID id) { this.id = id; }

    /**
     * Retrieves the unique identifier of the owner of the task.
     *
     * @return the UUID representing the owner ID
     */
    public UUID getOwnerId() { return ownerId; }

    /**
     * Sets the unique identifier of the owner of the task.
     *
     * @param id the UUID representing the owner ID
     */
    public void setOwnerId(UUID id) { this.ownerId = id; }

    /**
     * Retrieves the title of the task.
     *
     * @return the title of the task as a string
     */
    public String getTitle() { return title; }

    /**
     * Sets the title of the task.
     *
     * @param title the title of the task as a string
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Retrieves the description of the task.
     *
     * @return the task description as a string
     */
    public String getDescription() { return description; }

    /**
     * Sets the description of the task.
     *
     * @param description the task description as a string
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Retrieves the unique identifier of the task list associated with the task.
     *
     * @return the UUID representing the task list ID
     */
    public UUID getTasklistId() { return tasklistId; }

    /**
     * Sets the unique identifier of the task list associated with the task.
     *
     * @param tasklistId the UUID representing the task list to which the task belongs
     */
    public void setTasklistId(UUID tasklistId) { this.tasklistId = tasklistId; }
}

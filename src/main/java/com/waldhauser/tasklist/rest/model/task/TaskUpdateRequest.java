package com.waldhauser.tasklist.rest.model.task;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

/**
 * Represents a request to update the details of an existing task.
 * This class is typically used to transfer the updated information of a task
 * from the client to the server during task update operations.
 * <p>
 * The fields within this class are validated to ensure data consistency
 * and correctness. The ID and name fields are mandatory with specified constraints.
 */
public class TaskUpdateRequest {

    @NotNull(message = "ID cannot be null")
    private UUID id;
    @NotNull(message = "Name cannot be null")
    @Length(min = 5, max = 255, message = "Name must be between 5 and 255 characters")
    private String name;
    private String description;

    // getters and setters

    /**
     * Retrieves the unique identifier of the task associated with this request.
     *
     * @return the UUID representing the task ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the task associated with this request.
     *
     * @param id the UUID representing the task ID
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Retrieves the name associated with the task update request.
     *
     * @return the name of the task as a string
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for the task update request.
     *
     * @param name the name of the task as a string
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the description associated with the task update request.
     *
     * @return the task description as a string
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task associated with the update request.
     *
     * @param description the task description as a string
     */
    public void setDescription(String description) {
        this.description = description;
    }

}

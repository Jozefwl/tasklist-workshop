package com.waldhauser.tasklist.rest.model.tasklist;

import com.waldhauser.tasklist.rest.model.task.TaskResponse;

import java.util.List;
import java.util.UUID;

/**
 * Represents the response model for a tasklist entity. This class encapsulates
 * essential information about a tasklist, including its unique identifier, owner,
 * name, description, and the list of tasks associated with it.
 * <p>
 * Typically used to transfer tasklist data from the server to the client as part of
 * the API response for tasklist-related operations.
 */
public class TasklistResponse {

    private UUID id;
    private UUID ownerId;
    private String name;
    private String description;
    private List<TaskResponse> tasks;

    /**
     * Constructs a new TasklistResponse object that encapsulates essential information
     * about a tasklist, including its unique identifier, owner, name, description, and
     * the list of tasks associated with it.
     *
     * @param id the unique identifier of the tasklist
     * @param ownerId the unique identifier of the owner of the tasklist
     * @param name the name of the tasklist
     * @param description the description of the tasklist
     * @param tasks the list of tasks associated with the tasklist
     */
    public TasklistResponse(UUID id, UUID ownerId, String name, String description, List<TaskResponse> tasks) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    /**
     * Retrieves the unique identifier of the tasklist.
     *
     * @return the UUID representing the unique identifier of the tasklist
     */
    public UUID getId() { return id; }

    /**
     * Sets the unique identifier for the tasklist.
     *
     * @param id the UUID representing the unique identifier of the tasklist
     */
    public void setId(UUID id) { this.id = id; }

    /**
     * Retrieves the unique identifier of the owner associated with the tasklist.
     *
     * @return the UUID representing the unique identifier of the tasklist owner
     */
    public UUID getOwnerId() { return ownerId; }

    /**
     * Sets the unique identifier of the owner associated with the tasklist.
     *
     * @param id the UUID representing the unique identifier of the tasklist owner
     */
    public void setOwnerId(UUID id) { this.ownerId = ownerId; }

    /**
     * Retrieves the name of the tasklist.
     *
     * @return the name of the tasklist
     */
    public String getName() { return name; }

    /**
     * Sets the name of the tasklist.
     *
     * @param name the name of the tasklist
     */
    public void setName(String name) { this.name = name; }

    /**
     * Retrieves the description of the tasklist.
     *
     * @return the description of the tasklist, or null if no description has been set
     */
    public String getDescription() { return description; }

    /**
     * Sets the description of the tasklist.
     *
     * @param description the description of the tasklist, which can provide additional context or details;
     *                    may be null if no description is needed
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Retrieves the list of tasks associated with the tasklist.
     *
     * @return a list of {@code TaskResponse} objects representing the tasks in the tasklist
     */
    public List<TaskResponse> getTasks() { return tasks; }

    /**
     * Sets the list of tasks associated with the tasklist.
     *
     * @param tasks a list of {@code TaskResponse} objects representing the tasks to be associated with the tasklist
     */
    public void setTasks(List<TaskResponse> tasks) { this.tasks = tasks; }
}

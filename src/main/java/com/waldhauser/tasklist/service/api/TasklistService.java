package com.waldhauser.tasklist.service.api;

import com.waldhauser.tasklist.domain.model.Tasklist;
import com.waldhauser.tasklist.rest.model.tasklist.TasklistResponse;
import com.waldhauser.tasklist.rest.model.tasklist.TasklistUpdateRequest;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing tasklists. This interface defines methods for
 * performing CRUD operations on tasklists, including retrieving, creating, updating,
 * and deleting tasklists within the system.
 */
public interface TasklistService {

    /**
     * Retrieves all tasklists associated with a specific owner based on their unique identifier.
     *
     * @param ownerId the unique identifier of the owner whose tasklists are to be retrieved
     * @return a list of {@code TasklistResponse} objects representing the tasklists associated with the given owner
     */
    List<TasklistResponse> getAllTasklistsByOwnerId(UUID ownerId);

    /**
     * Retrieves a tasklist by its unique identifier.
     *
     * @param tasklistId the unique identifier of*/
    TasklistResponse getTasklistById(UUID tasklistId);

    /**
     * Creates and saves a new task list in the system.
     *
     * @param tasklist the {@code Tasklist*/
    Tasklist create(Tasklist tasklist);

    /**
     * Updates an existing task list with new information provided in the update request.
     *
     * @param updatedTasklist the {@code TasklistUpdateRequest} object containing the updated details for the task list
     * @return*/
    Tasklist update(TasklistUpdateRequest updatedTasklist);

    /**
     * Deletes a tasklist identified by its unique identifier.
     *
     * @param tasklistId the unique identifier of the tasklist to be deleted
     * @throws IllegalAccessException if the operation is not permitted for the current user
     */
    void delete(UUID tasklistId) throws IllegalAccessException;

}

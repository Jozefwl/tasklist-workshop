package com.waldhauser.tasklist.service.api;

import com.waldhauser.tasklist.domain.model.Task;
import com.waldhauser.tasklist.rest.model.task.TaskCreateRequest;
import com.waldhauser.tasklist.rest.model.task.TaskResponse;
import com.waldhauser.tasklist.rest.model.task.TaskUpdateRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * TaskService provides an interface for managing task-related operations.
 * It includes functionalities for creating, retrieving, updating, and deleting tasks.
 */
public interface TaskService {

    /**
     * Retrieves all tasks associated with a specific owner based on their unique identifier.
     *
     * @param ownerId the unique identifier of the owner whose tasks are to be retrieved
     * @return a list of {@code TaskResponse} objects representing the tasks associated with the given owner
     */
    List<TaskResponse> getAllTasksByOwnerId(UUID ownerId);

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param taskId the unique identifier of the task to be retrieved
     * @return an {@code Optional<TaskResponse>} containing the task information if found, or an empty {@code Optional} if the task does not*/
    Optional<TaskResponse> getTaskById(UUID taskId);

    /**
     * Creates a new task within a specified task list based on the provided {@code TaskCreateRequest}.
     *
     * @param request an instance of {@code TaskCreateRequest} containing the details of the task to be created,
     *                such as the name, description, owner ID, and associated task list ID
     * @param tasklistId the unique identifier of the task list within which the task will be created
     * @return a {@code TaskResponse} object containing the details of the newly created task
     */
    TaskResponse create(TaskCreateRequest request, UUID tasklistId);

    /**
     * Updates the details of an existing task based on the provided {@code TaskUpdateRequest}.
     * The input request contains the necessary information to locate and modify the task.
     *
     * @param updatedTask an instance of {@code TaskUpdateRequest} containing the updated task details,
     *                    including its unique identifier, new name, and optional description
     * @return the updated {@code Task} entity reflecting the changes applied
     */
    Task update(TaskUpdateRequest updatedTask);

    void delete(UUID taskId) throws IllegalAccessException;

}

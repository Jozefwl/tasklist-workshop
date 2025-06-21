package com.waldhauser.tasklist.rest.controller;

import com.waldhauser.tasklist.rest.model.task.TaskCreateRequest;
import com.waldhauser.tasklist.rest.model.task.TaskResponse;
import com.waldhauser.tasklist.rest.model.task.TaskUpdateRequest;
import com.waldhauser.tasklist.service.api.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

/**
 * Rest controller that manages task-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting tasks.
 * The class is secured by Spring Security to ensure user-specific task management.
 */
@RestController
@RequestMapping(path = "task")
@Tag(name = "Task Management", description = "Endpoints for managing tasks.")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Retrieves all tasks associated with the authenticated user.
     * The method extracts the authenticated user's ID from the Spring Security context
     * and fetches the tasks owned by that user.
     *
     * @return a list of {@code TaskResponse} objects representing the tasks owned by the authenticated user
     */
    @Operation(summary = "Get all tasks associated with the authenticated user.")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @GetMapping("/getAll")
    public List<TaskResponse> getAllTasksByOwnerId() {
        // Get the authenticated user ID from Spring Security context
        String userIdString = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userId = UUID.fromString(userIdString);

        return taskService.getAllTasksByOwnerId(userId);
    }

    /**
     * Retrieves a single task by its ID if the authenticated user is the owner.
     * The method checks the owner's ID of the task against the authenticated user's ID.
     * If the task does not exist or the user is not the owner, a {@code ResponseStatusException} is thrown.
     *
     * @param id the unique identifier of the task to be retrieved
     * @return a {@code TaskResponse} object representing the details of the retrieved task
     * @throws ResponseStatusException if the task is not found or the user is not authorized
     */
    @Operation(summary = "Get a single task by its ID if the authenticated user is the owner.")
    @ApiResponse(responseCode = "200", description = "Task retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @ApiResponse(responseCode = "401", description = "User is not authorized to access the task")
    @GetMapping("/get/{id}")
    public TaskResponse getTaskById(@Parameter(description = "ID of the task to be taken.") @PathVariable UUID id) {
        String userIdString = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userId = UUID.fromString(userIdString);

        try {
            TaskResponse taskResponse = taskService.getTaskById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            if (!taskResponse.getOwnerId().equals(userId)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You aren't the owner of task");
            }
            return taskResponse;

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Tasklist owner doesn't match"
            );
        }

    }

    /**
     * Creates a new task for the authenticated user within the specified tasklist.
     * The method retrieves the authenticated user's ID from the Spring Security context,
     * associates the task with the proper tasklist, and delegates the creation process to the task service.
     * If the specified tasklist is not found, an {@code EntityNotFoundException} is thrown.
     *
     * @param request the {@code TaskCreateRequest} object containing details of the task to be created
     *                such as name, description, due date, and the ID of the tasklist to associate with
     * @return the created {@code Task} object after successful persistence
     * @throws EntityNotFoundException if the specified tasklist does not exist
     */
    @Operation(summary = "Create a new task for the authenticated user within the specified tasklist.")
    @ApiResponse(responseCode = "200", description = "Task created successfully")
    @PostMapping("/create")
    public TaskResponse createTask(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Task request for creation", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskCreateRequest.class))) @RequestBody TaskCreateRequest request) {

        // service will handle tasklist validation, so pass the tasklistId
        return taskService.create(request, request.getTasklistId());
    }

    /**
     * Updates an existing task with the provided details.
     * Delegates the update operation to the task service.
     *
     * @param task the {@code TaskUpdateRequest} object containing updated details for the task
     *             such as name, description, due date, and other attributes to be modified
     * @return the updated {@code TaskResponse} object containing the details of the task after the update
     * @throws IllegalAccessException if the user is not authorized to update the task
     */
    @Operation(summary = "Update an existing task based on the details provided in the request.")
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @PostMapping("/update")
    public TaskResponse updateTask(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Task request to be upodated", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskCreateRequest.class))) @RequestBody TaskUpdateRequest task) throws IllegalAccessException {
        return taskService.update(task);
    }

    /**
     * Deletes a task based on the provided unique identifier.
     * Delegates the deletion operation to the task service.
     *
     * @param id the unique identifier of the task to be deleted
     * @throws IllegalAccessException if the user is not authorized to delete the task
     */
    @Operation(summary = "Delete a task based on the provided unique identifier.")
    @ApiResponse(responseCode = "200", description = "Task deleted successfully")
    @DeleteMapping("/delete/{id}")
    public void deleteTask(@Parameter(description = "ID of the task to be deleted") @PathVariable("id") UUID id) throws IllegalAccessException {
        taskService.delete(id);
    }

}

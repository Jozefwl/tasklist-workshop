package com.waldhauser.tasklist.rest.controller;

import com.waldhauser.tasklist.domain.model.Tasklist;
import com.waldhauser.tasklist.rest.model.tasklist.TasklistCreateRequest;
import com.waldhauser.tasklist.rest.model.tasklist.TasklistResponse;
import com.waldhauser.tasklist.rest.model.tasklist.TasklistUpdateRequest;
import com.waldhauser.tasklist.service.api.TasklistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Controller for managing tasklists. This class provides endpoints for creating, retrieving,
 * updating, and deleting tasklists. It uses the {@code TasklistService} to perform business logic
 * and interact with the application's data layer.
 */
@RestController
@RequestMapping(path = "tasklist")
@Tag(name = "Tasklist Management", description = "Endpoints for managing tasklists.")
public class TasklistController {

    private final TasklistService tasklistService;

    /**
     * Constructs a new TasklistController instance.
     *
     * @param tasklistService the service used to handle tasklist-related business logic and data operations
     */
    public TasklistController(TasklistService tasklistService) {
        this.tasklistService = tasklistService;
    }

    /**
     * Retrieves all tasklists associated with the currently authenticated user.
     *
     * @return a list of {@code TasklistResponse} objects representing the tasklists owned
     *         by the currently authenticated user.
     */
    @Operation(summary = "Get all tasklists associated with the currently authenticated user.")
    @ApiResponse(responseCode = "200", description = "Tasklists retrieved successfully")
    @GetMapping("/getAll")
    public List<TasklistResponse> getAll() {
        // Get the authenticated user ID from Spring Security context
        String userIdString = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userId = UUID.fromString(userIdString);

        return tasklistService.getAllTasklistsByOwnerId(userId);
    }

    /**
     * Retrieves a specific tasklist by its unique identifier. Ensures that the authenticated user
     * is the owner of the requested tasklist.
     *
     * @param id the unique identifier of the tasklist to be retrieved
     * @return the {@code TasklistResponse} object representing the details of the tasklist
     * @throws ResponseStatusException if the authenticated user does not own the tasklist or if any errors occur during retrieval
     */
    @Operation(summary = "Get a specific tasklist by its unique identifier.")
    @ApiResponse(responseCode = "200", description = "Tasklist retrieved successfully")
    @GetMapping("/get/{id}")
    public TasklistResponse get(@Parameter(description = "ID of the Task list to be returned.") @PathVariable("id") UUID id) {
        // JWT validation is handled by the security filter
        String userIdString = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userId = UUID.fromString(userIdString);

        try {
            TasklistResponse tasklistResponse = tasklistService.getTasklistById(id);

            if(!tasklistResponse.getOwnerId().equals(userId)){
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "You aren't the owner of this tasklist");
            }
            return tasklistResponse;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Creates a new tasklist for the currently authenticated user.
     *
     * @param tasklist the {@code TasklistCreateRequest} object containing the details of the tasklist to be created
     * @return the newly created {@code Tasklist} object
     */
    @PostMapping("/create")
    @Operation(summary = "Create a new task list for the currently authenticated user.")
    @ApiResponse(responseCode = "200", description = "Task list created successfully")
    public Tasklist create(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Task list request for creation", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TasklistCreateRequest.class))) @RequestBody TasklistCreateRequest tasklist) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return tasklistService.create(new Tasklist(
                UUID.fromString(userId),
                tasklist.getName(),
                tasklist.getDescription(),
                new ArrayList<>()
        ));
    }

    /**
     * Updates an existing tasklist with the provided details.
     *
     * @param tasklist the {@code TasklistUpdateRequest} object containing the updated information for the tasklist
     * @return the updated {@code Tasklist} object
     */
    @Operation(summary = "Update an existing tasklist with the provided details.")
    @ApiResponse(responseCode = "200", description = "Tasklist updated successfully")
    @PostMapping("/update")
    public Tasklist update(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Task list request for update", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TasklistCreateRequest.class))) @RequestBody TasklistUpdateRequest tasklist) {
        return tasklistService.update(tasklist);
    }

    /**
     * Deletes a tasklist identified by its unique identifier.
     *
     * @param id the unique identifier of the tasklist to be deleted
     * @throws IllegalAccessException if the operation is not permitted for the current user
     */
    @Operation(summary = "Delete a tasklist identified by its unique identifier.")
    @ApiResponse(responseCode = "200", description = "Tasklist deleted successfully")
    @DeleteMapping("/delete/{id}")
    public void delete(@Parameter(description = "ID of the task list to be deleted") @PathVariable("id") UUID id) throws IllegalAccessException {
        tasklistService.delete(id);
    }
}
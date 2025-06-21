package com.waldhauser.tasklist.rest.controller;

import com.waldhauser.tasklist.domain.model.Task;
import com.waldhauser.tasklist.domain.repository.TasklistRepository;
import com.waldhauser.tasklist.rest.model.task.TaskCreateRequest;
import com.waldhauser.tasklist.rest.model.task.TaskResponse;
import com.waldhauser.tasklist.rest.model.task.TaskUpdateRequest;
import com.waldhauser.tasklist.service.api.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "task")
public class TaskController {

    private final TaskService taskService;
    private final TasklistRepository tasklistRepository;

    public TaskController(TaskService taskService, TasklistRepository tasklistRepository) {
        this.taskService = taskService;
        this.tasklistRepository = tasklistRepository;
    }


    @GetMapping("/getAll")
    public List<TaskResponse> getAllTasksByOwnerId() {
        // Get the authenticated user ID from Spring Security context
        String userIdString = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userId = UUID.fromString(userIdString);

        return taskService.getAllTasksByOwnerId(userId);
    }

    @GetMapping("/get/{id}")
    public TaskResponse getTaskById(@PathVariable UUID id) {
        String userIdString = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userId = UUID.fromString(userIdString);

        try{
            TaskResponse taskResponse = taskService.getTaskById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            if(!taskResponse.getOwnerId().equals(userId)){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You aren't the owner of task");
            }
            return taskResponse;

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Tasklist owner doesn't match"
            );
        }

    }

    @PostMapping("/create")
    public TaskResponse createTask(@Valid @RequestBody TaskCreateRequest request) {
        String userIdString = SecurityContextHolder.getContext().getAuthentication().getName();

        // service will handle tasklist validation, so pass the tasklistId
        return taskService.create(request, request.getTasklistId());
    }

    @PostMapping("/update")
    public Task updateTask(@Valid @RequestBody TaskUpdateRequest task) {
        return taskService.update(task);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable("id") UUID id) throws IllegalAccessException {
        taskService.delete(id);
    }

}

package com.waldhauser.tasklist.rest.controller;

import com.waldhauser.tasklist.domain.model.Tasklist;
import com.waldhauser.tasklist.rest.model.tasklist.TasklistCreateRequest;
import com.waldhauser.tasklist.rest.model.tasklist.TasklistResponse;
import com.waldhauser.tasklist.rest.model.tasklist.TasklistUpdateRequest;
import com.waldhauser.tasklist.service.api.TasklistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "tasklist")
public class TasklistController {

    private final TasklistService tasklistService;

    public TasklistController(TasklistService tasklistService) {
        this.tasklistService = tasklistService;
    }

    @GetMapping("/getAll")
    public List<TasklistResponse> getAll() {
        // Get the authenticated user ID from Spring Security context
        String userIdString = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userId = UUID.fromString(userIdString);

        return tasklistService.getAllTasklistsByOwnerId(userId);
    }

    @GetMapping("/get/{id}")
    public TasklistResponse get(@PathVariable("id") UUID id) {
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

    @PostMapping("/create")
    public Tasklist create(@Valid @RequestBody TasklistCreateRequest tasklist) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        return tasklistService.create(new Tasklist(
                UUID.fromString(userId),
                tasklist.getName(),
                tasklist.getDescription(),
                new ArrayList<>()
        ));
    }

    @PostMapping("/update")
    public Tasklist update(@Valid @RequestBody TasklistUpdateRequest tasklist) {
        return tasklistService.update(tasklist);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") UUID id) throws IllegalAccessException {
        tasklistService.delete(id);
    }
}
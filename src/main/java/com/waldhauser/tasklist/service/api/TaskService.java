package com.waldhauser.tasklist.service.api;

import com.waldhauser.tasklist.domain.model.Task;
import com.waldhauser.tasklist.rest.model.task.TaskCreateRequest;
import com.waldhauser.tasklist.rest.model.task.TaskResponse;
import com.waldhauser.tasklist.rest.model.task.TaskUpdateRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    List<TaskResponse> getAllTasksByOwnerId(UUID ownerId);

    Optional<TaskResponse> getTaskById(UUID taskId);

    TaskResponse create(TaskCreateRequest request, UUID tasklistId);

    Task update(TaskUpdateRequest updatedTask);

    void delete(UUID taskId) throws IllegalAccessException;

}

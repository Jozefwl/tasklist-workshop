package com.waldhauser.tasklist.service.api;

import com.waldhauser.tasklist.domain.model.Tasklist;
import com.waldhauser.tasklist.rest.model.tasklist.TasklistResponse;
import com.waldhauser.tasklist.rest.model.tasklist.TasklistUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface TasklistService {

    List<TasklistResponse> getAllTasklistsByOwnerId(UUID ownerId);

    TasklistResponse getTasklistById(UUID tasklistId);

    Tasklist create(Tasklist tasklist);

    Tasklist update(TasklistUpdateRequest updatedTasklist);

    void delete(UUID tasklistId) throws IllegalAccessException;

}

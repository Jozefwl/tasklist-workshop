package com.waldhauser.tasklist.service.impl;

import com.waldhauser.tasklist.domain.model.Task;
import com.waldhauser.tasklist.domain.model.Tasklist;
import com.waldhauser.tasklist.domain.repository.TaskRepository;
import com.waldhauser.tasklist.domain.repository.TasklistRepository;
import com.waldhauser.tasklist.rest.model.task.TaskResponse;
import com.waldhauser.tasklist.rest.model.tasklist.TasklistResponse;
import com.waldhauser.tasklist.rest.model.tasklist.TasklistUpdateRequest;
import com.waldhauser.tasklist.service.api.TasklistService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TasklistServiceImpl implements TasklistService {

    private final TasklistRepository tasklistRepository;
    private final TaskRepository taskRepository;

    public TasklistServiceImpl(TasklistRepository tasklistRepository, TaskRepository taskRepository) {
        this.tasklistRepository = tasklistRepository;
        this.taskRepository = taskRepository;
    }

    // ---------- Get all tasklists by ownerId ----------
    public List<TasklistResponse> getAllTasklistsByOwnerId(UUID ownerId) {
        return tasklistRepository.findByOwnerId(ownerId).stream()
                .map(tasklist -> {
                    List<TaskResponse> taskResponses = tasklist.getTasks().stream()
                            .map(task -> new TaskResponse(
                                    task.getId(),
                                    task.getOwnerId(),
                                    task.getName(),
                                    task.getDescription(),
                                    task.getTasklist().getId()
                            ))
                            .collect(Collectors.toList());

                    return new TasklistResponse(
                            tasklist.getId(),
                            tasklist.getOwnerId(),
                            tasklist.getName(),
                            tasklist.getDescription(),
                            taskResponses
                    );
                })
                .collect(Collectors.toList());
    }

    // ---------- Get single tasklist by tasklistId ----------
    public TasklistResponse getTasklistById(UUID tasklistId) {
        return tasklistRepository.findById(tasklistId)
                .map(tasklist -> {
                    List<TaskResponse> taskResponses = tasklist.getTasks().stream()
                            .map(task -> new TaskResponse(
                                    task.getId(),
                                    task.getOwnerId(),
                                    task.getName(),
                                    task.getDescription(),
                                    task.getTasklist().getId()
                            ))
                            .collect(Collectors.toList());

                    return new TasklistResponse(
                            tasklist.getId(),
                            tasklist.getOwnerId(),
                            tasklist.getName(),
                            tasklist.getDescription(),
                            taskResponses
                    );
                })
                .orElseThrow(() -> new EntityNotFoundException("Tasklist not found with id: " + tasklistId));
    }

    // -------------------- CREATE -------------------------\
    public Tasklist create(Tasklist tasklist) {
            tasklist.setName(tasklist.getName());
            tasklist.setDescription(tasklist.getDescription());
            tasklist.setOwnerId(tasklist.getOwnerId());
            return tasklistRepository.save(tasklist);
    }

    // ---------------------- UPDATE ------------------------
    public Tasklist update(TasklistUpdateRequest updatedTasklist) {
            Tasklist tasklist = tasklistRepository.findById(updatedTasklist.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Tasklist not found"));
            if(updatedTasklist.getName() != null){tasklist.setName(updatedTasklist.getName());}
            if(updatedTasklist.getDescription() != null){tasklist.setDescription(updatedTasklist.getDescription());}
            return tasklistRepository.save(tasklist);
    }

    // ---------- DELETE ----------
    public void delete(UUID tasklistId) throws IllegalAccessException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Tasklist tasklist = tasklistRepository.findById(tasklistId)
                .orElseThrow(() -> new EntityNotFoundException("Tasklist not found"));

        if(tasklist.getOwnerId().equals(UUID.fromString(userId))) {
            // delete all tasks associated with this tasklist
            List<Task> associatedTasks = taskRepository.findByTasklistId(tasklistId);
            if (!associatedTasks.isEmpty()) {
                taskRepository.deleteAll(associatedTasks);
            }

            // delete the tasklist itself
            tasklistRepository.delete(tasklist);
        } else {
            throw new IllegalAccessException("You aren't allowed to delete this tasklist");
        }
    }


}

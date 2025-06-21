package com.waldhauser.tasklist.task;

import com.waldhauser.tasklist.domain.model.Task;
import com.waldhauser.tasklist.domain.repository.TaskRepository;
import com.waldhauser.tasklist.rest.model.task.TaskCreateRequest;
import com.waldhauser.tasklist.rest.model.task.TaskResponse;
import com.waldhauser.tasklist.rest.model.task.TaskUpdateRequest;
import com.waldhauser.tasklist.domain.model.Tasklist;
import com.waldhauser.tasklist.domain.repository.TasklistRepository;
import com.waldhauser.tasklist.service.api.TaskService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TasklistRepository tasklistRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TasklistRepository tasklistRepository) {
        this.taskRepository = taskRepository;
        this.tasklistRepository = tasklistRepository;
    }

    // ---------- Get all tasks by ownerId ----------
    public List<TaskResponse> getAllTasksByOwnerId(UUID ownerId) {
        return taskRepository.findByOwnerId(ownerId).stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getOwnerId(),
                        task.getName(),
                        task.getDescription(),
                        task.getTasklist().getId()
                ))
                .collect(Collectors.toList());
    }

    // ---------- Get single task by its id ----------
    public Optional<TaskResponse> getTaskById(UUID taskId) {
        return taskRepository.findById(taskId)
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getOwnerId(),
                        task.getName(),
                        task.getDescription(),
                        task.getTasklist().getId()
                ));
    }

    // ---------- CREATE ----------
    public TaskResponse create(TaskCreateRequest request, UUID tasklistId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        // validate tasklist exists and get it
        Tasklist tasklist = tasklistRepository.findById(tasklistId)
                .orElseThrow(() -> new EntityNotFoundException("Tasklist not found"));

        // create and populate task
        Task task = new Task();
        task.setOwnerId(UUID.fromString(userId));
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setTasklist(tasklist);

        // save task
        Task savedTask = taskRepository.save(task);

        // return TaskResponse to avoid circular reference
        return new TaskResponse(
                savedTask.getId(),
                savedTask.getOwnerId(),
                savedTask.getName(), // This maps to 'title' in TaskResponse
                savedTask.getDescription(),
                savedTask.getTasklist().getId()
        );
    }

    // ---------- UPDATE ----------
    public Task update(TaskUpdateRequest updatedTask) {
        Task task = taskRepository.findById(updatedTask.getId())
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        if(updatedTask.getName() != null) {task.setName(updatedTask.getName());}
        if(updatedTask.getDescription() != null) {task.setDescription(updatedTask.getDescription());}
        return taskRepository.save(task);
    }

    // ---------- DELETE ----------
    public void delete(UUID taskId) throws IllegalAccessException {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if(task.getOwnerId().equals(UUID.fromString(userId))) {
            taskRepository.delete(task);
        } else {
            throw new IllegalAccessException("You aren't allowed to delete this task");
        }

    }

}

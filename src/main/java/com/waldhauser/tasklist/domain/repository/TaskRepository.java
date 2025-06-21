package com.waldhauser.tasklist.domain.repository;

import com.waldhauser.tasklist.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    // Find task by its ID
    Optional<Task> findById(UUID id);

    // Find tasks by ownerId
    List<Task> findByOwnerId(UUID ownerId);

    // Find tasks by tasklistId
    List<Task> findByTasklistId(UUID tasklistId);
}

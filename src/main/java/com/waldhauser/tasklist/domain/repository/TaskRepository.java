package com.waldhauser.tasklist.domain.repository;

import com.waldhauser.tasklist.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

/**
 * TaskRepository provides data access and query methods for Task entities in the database.
 * This repository extends JpaRepository, allowing CRUD operations and custom query methods
 * for the Task entity.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    /**
     * Retrieves a Task entity by its unique identifier (UUID).
     *
     * @param id the unique identifier of the task to be retrieved
     * @return an Optional containing the Task if found, or an empty Optional if no task exists
     */
    Optional<Task> findById(UUID id);

    /**
     * Retrieves a list of tasks associated with the specified owner.
     *
     * @param ownerId the unique identifier of the task owner
     * @return a list of tasks owned by the specified owner
     */
    List<Task> findByOwnerId(UUID ownerId);

    /**
     * Retrieves a list of tasks that are associated with the specified task list.
     *
     * @param tasklistId the unique identifier of the task list whose tasks are to be retrieved
     * @return a list of tasks belonging to the specified task list
     */
    List<Task> findByTasklistId(UUID tasklistId);
}

package com.waldhauser.tasklist.domain.repository;

import com.waldhauser.tasklist.domain.model.Tasklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing Tasklist entities in the persistence layer.
 * It provides methods for performing CRUD operations and custom queries
 * related to Tasklist instances.
 * <p>
 * This interface extends JpaRepository to leverage Spring Data JPA's
 * repository functionalities such as pagination, sorting, and advanced query capabilities.
 * <p>
 * Key Methods:
 * - {@link #findById(UUID)}: Retrieves a Tasklist by its unique identifier.
 * - {@link #findByOwnerId(UUID)}: Retrieves a list of Tasklists associated with a specific owner.
 * <p>
 * Responsibilities:
 * - Abstracts the interaction with the database for Tasklist entities.
 * - Supports defining additional custom query methods specific to Tasklist.
 * <p>
 * Usage Considerations:
 * - Ensure that Tasklist entities are correctly mapped in the persistence layer.
 * - The methods provided by this interface are designed to operate on UUID-based identifiers
 *   for both Tasklists and their owners.
 */
@Repository
public interface TasklistRepository extends JpaRepository<Tasklist, UUID> {

    /**
     * Retrieves a Tasklist entity by its unique identifier.
     *
     * @param id the unique identifier (UUID) of the Tasklist to retrieve
     * @return an Optional containing the Tasklist if found, or an empty Optional if no Tasklist exists with the given ID
     */
    Optional<Tasklist> findById(UUID id);

    /**
     * Retrieves a list of Tasklist entities that are associated with the specified owner.
     *
     * @param ownerId the unique identifier (UUID) of the owner whose Tasklists are to be retrieved
     * @return a list of Tasklist entities associated with the specified owner, or an empty list if no Tasklists exist for the owner
     */
    List<Tasklist> findByOwnerId(UUID ownerId);
}
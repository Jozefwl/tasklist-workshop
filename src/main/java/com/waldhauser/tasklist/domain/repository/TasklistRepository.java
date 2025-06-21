package com.waldhauser.tasklist.domain.repository;

import com.waldhauser.tasklist.domain.model.Tasklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface TasklistRepository extends JpaRepository<Tasklist, UUID> {
    // Find tasklist by its ID
    Optional<Tasklist> findById(UUID id);

    // Find tasklists by ownerId
    List<Tasklist> findByOwnerId(UUID ownerId);
}
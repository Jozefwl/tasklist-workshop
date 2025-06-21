package com.waldhauser.tasklist.domain.repository;

import com.waldhauser.tasklist.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository provides data access and query methods for User entities in the database.
 * This repository extends JpaRepository, allowing CRUD operations and custom query methods
 * for the User entity.
 * <p>
 * Responsibilities:
 * - Abstracts database interactions for User entities.
 * - Ensures efficient execution of standard database operations (create, read, update, delete).
 * - Supports defining custom query methods specific to User.
 * <p>
 * Key Methods:
 * - {@link #findByName(String)}: Retrieves a User by their unique name.
 * <p>
 * Usage Considerations:
 * - The `findByName` method enables retrieval of a User based on their unique name.
 *   This assumes that the `name` field in the User entity is unique.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a User entity by their unique name.
     *
     * @param name the unique name of the user to be retrieved
     * @return an Optional containing the User if found, or an empty Optional if no user exists
     */
    Optional<User> findByName(String name);
}

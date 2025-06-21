package com.waldhauser.tasklist.rest.model.tasklist;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

/**
 * Represents a request to update an existing tasklist. This class encapsulates
 * the necessary information required to update a tasklist, including its unique
 * identifier, name, and an optional description.
 * <p>
 * The {@code id} and {@code name} fields are mandatory and must meet specific
 * validation constraints. The {@code description} field is optional and allows
 * for providing additional details about the tasklist.
 */
public class TasklistUpdateRequest {

    @NotNull(message = "ID cannot be null")
    private UUID id;
    @NotNull(message = "Name cannot be null")
    @Length(min = 5, max = 255, message = "Name must be between 5 and 255 characters")
    private String name;
    private String description;

    /**
     * Constructs an empty {@code TasklistUpdateRequest} object.
     * This no-argument constructor is primarily used for deserialization or
     * for scenarios where the fields of the request will be set programmatically
     * before processing.
     */
    public TasklistUpdateRequest() {
    }

    /**
     * Constructs a new {@code TasklistUpdateRequest} object with the specified ID, name, and description.
     * This constructor initializes the tasklist update request with a unique identifier, a mandatory name,
     * and an optional description. These values are used to update an existing tasklist.
     *
     * @param id the unique identifier of the tasklist to be updated, must not be null
     * @param name the new name of the tasklist, must not be null and must have a length between 5 and 255 characters
     * @param description an optional new description of the tasklist, which can provide additional context or details;
     *                    may be null if no description is needed
     */
    public TasklistUpdateRequest(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Retrieves the unique identifier of the tasklist or tasklist update request.
     *
     * @return the UUID representing the unique identifier
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the tasklist update request.
     *
     * @param id the UUID representing the unique identifier of the tasklist; must not be null
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the tasklist or tasklist update request.
     *
     * @return the name of the tasklist or tasklist update request, as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for the tasklist update request.
     *
     * @param name the new name of the tasklist; must not be null and must have a length between 5 and 255 characters
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the description of the tasklist update request.
     *
     * @return the description of the tasklist update request, or null if no description is set
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description for the tasklist update request.
     *
     * @param description the description of the tasklist, which can provide additional
     *                    context or details; may be null if no description is needed
     */
    public void setDescription(String description) {
        this.description = description;
    }

}

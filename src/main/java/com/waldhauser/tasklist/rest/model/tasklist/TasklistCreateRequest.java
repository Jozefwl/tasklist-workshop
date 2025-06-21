package com.waldhauser.tasklist.rest.model.tasklist;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;


/**
 * Represents a request to create a new tasklist. This class encapsulates the necessary information
 * needed to create a tasklist, including its name and an optional description.
 * <p>
 * The {@code name} field is mandatory and must meet specific validation constraints. The
 * {@code description} field is optional and allows additional details about the tasklist.
 */
public class TasklistCreateRequest {

    @NotNull(message = "Name cannot be null")
    @Length(min = 5, max = 255, message = "Name must be between 5 and 255 characters")
    private String name;
    private String description;

    /**
     * Constructs an empty {@code TasklistCreateRequest} object.
     * This no-argument constructor is typically used for deserialization purposes
     * or when creating a new instance to programmatically set properties later.
     */
    public TasklistCreateRequest() {
    }

    /**
     * Constructs a new {@code TasklistCreateRequest} object with the specified name and description.
     * This constructor initializes the tasklist creation request with a mandatory name and an optional
     * description that provides additional details about the tasklist.
     *
     * @param name the name of the tasklist, which must not be null and must have a length between 5 and 255 characters
     * @param description an optional description of the tasklist, which can provide additional context or*/
    public TasklistCreateRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Retrieves the name of the tasklist.
     *
     * @return the name of the tasklist, which is a non-null string with a length between 5 and 255 characters
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the tasklist.
     *
     * @param name the name of the tasklist, which must not be null and must have a length between 5 and 255 characters
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the description of the tasklist.
     *
     * @return the description of the tasklist, or null if no description has been provided
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the tasklist.
     *
     * @param description the description of the tasklist, which can provide additional context or details;
     *                    may be null if no description is needed
     */
    public void setDescription(String description) {
        this.description = description;
    }

}

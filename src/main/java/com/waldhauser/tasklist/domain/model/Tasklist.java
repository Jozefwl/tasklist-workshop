package com.waldhauser.tasklist.domain.model;

import jakarta.persistence.*;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a task list entity in the system. A Tasklist acts as a container
 * for multiple tasks associated with a specific owner. Each task list has
 * attributes such as a unique identifier, name, description, and a list of
 * tasks it contains.
 * <p>
 * The Tasklist entity is mapped to the "tasklists" table in the database,
 * and its primary key is generated as a UUID. The relationship between
 * Tasklist and Task is defined as a one-to-many relationship, ensuring that
 * each task is associated with a specific task list.
 * <p>
 * Key attributes:
 * - id: A unique identifier for the task list, generated automatically by the database.
 * - ownerId: The unique identifier of the user who owns the task list.
 * - name: The name or title of the task list.
 * - description: A detailed description providing additional information about the task list.
 * - tasks: A list of tasks that belong to this task list.
 * <p>
 * Relationships:
 * - The Tasklist entity has a one-to-many relationship with the Task entity,
 *   allowing multiple tasks to belong to a single task list.
 * <p>
 * Usage considerations:
 * - It is important to assign a valid ownerId to associate the task list with a specific user.
 * - All tasks within the task list are managed through the cascade and orphan removal settings,
 *   ensuring that task operations are propagated appropriately when modifying the task list.
 */
@Entity
@Table(name = "tasklists")
public class Tasklist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = false, nullable = false)
    private UUID ownerId;

    @Column(unique = false, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private String description;

    @OneToMany(mappedBy = "tasklist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    /**
     * Default constructor for the Tasklist class.
     *
     * Creates a new instance of Tasklist with default values. This constructor
     * is primarily used by frameworks or libraries that require a no-argument
     * constructor, such as JPA (Java Persistence API).
     *
     * Note: When using this constructor, all attributes should be manually
     * initialized or set through setters before persisting or manipulating
     * the Tasklist entity.
     */
    public Tasklist() {
    }

    /**
     * Constructs a new Tasklist instance with the specified attributes.
     *
     * @param id          The unique identifier of the task list. This is typically
     *                    generated by the database.
     * @param ownerId     The unique identifier of the user who owns this task list.
     * @param name        The name or title of the task list.
     * @param description A brief description providing additional details about
     *                    the task list.
     * @param tasks       The list of tasks associated with this task list. This
     *                    can include any number of tasks or be empty initially.
     */
    public Tasklist(UUID id, UUID ownerId, String name, String description, List<Task> tasks) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    /**
     * Constructs a new Tasklist instance with the specified attributes,
     * excluding the unique identifier (id).
     *
     * @param ownerId     The unique identifier of the user who owns this task list.
     * @param name        The name or title of the task list.
     * @param description A brief description providing additional details about the task list.
     * @param tasks       The list of tasks associated with this task list. This
     *                    can include any number of tasks or be empty initially.
     */
    public Tasklist(UUID ownerId, String name, String description, List<Task> tasks) {
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    /**
     * Retrieves the unique identifier of the task list.
     *
     * @return the UUID representing the unique identifier of the task list.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the task list.
     *
     * @param id the UUID representing the unique identifier of the task list
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Retrieves the unique identifier of the owner associated with this task list.
     *
     * @return the UUID representing the unique identifier of the task list's owner.
     */
    public UUID getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the unique identifier of the owner associated with this task list.
     *
     * @param ownerId the UUID representing the unique identifier of the task list's owner
     */
    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Retrieves the name or title of the task list.
     *
     * @return the name representing the title of the task list.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name or title of the task list.
     *
     * @param name the name or title to be assigned to the task list
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the description of the task list.
     *
     * @return the description providing additional details about the task list.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task list.
     *
     * @param description the description to be assigned to the task list
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the list of tasks associated with this task list.
     *
     * @return a list of tasks belonging to this task list.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Sets the list of tasks associated with this task list.
     *
     * @param tasks the list of tasks to be assigned to the task list
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

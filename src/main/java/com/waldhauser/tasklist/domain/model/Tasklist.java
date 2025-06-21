package com.waldhauser.tasklist.domain.model;

import jakarta.persistence.*;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;


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

    public Tasklist() {
    }

    public Tasklist(UUID id, UUID ownerId, String name, String description, List<Task> tasks) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    public Tasklist(UUID ownerId, String name, String description, List<Task> tasks) {
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

package com.waldhauser.tasklist.domain.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // this is jakarta import and generated value UUID bc database sets it
    private UUID id;

    @Column(unique = false, nullable = false)
    private UUID ownerId;

    // this sucks, but has to be this way to be tied to the tasklist entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tasklist_id", nullable = false)
    private Tasklist tasklist;

    @Column(unique = false, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private String description;

    public Task() {
    }

    public Task(UUID id, UUID ownerId, Tasklist tasklist, String name, String description) {
        this.id = id;
        this.ownerId = ownerId;
        this.tasklist = tasklist;
        this.name = name;
        this.description = description;
    }

    public Task(Tasklist tasklist, UUID ownerId, String name, String description) {
        this.tasklist = tasklist;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
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

    public Tasklist getTasklist() {
        return tasklist;
    }
    public void setTasklist(Tasklist tasklist) {
        this.tasklist = tasklist;
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
}

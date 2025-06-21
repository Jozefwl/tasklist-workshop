package com.waldhauser.tasklist.domain.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users") // set which table to use
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // this is jakarta import and generated value UUID bc database sets it
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    // Generate -> Constructors
    public User() {
    }

    public User(UUID id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    // One constructor without id bc database sets id
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Generate -> Getter and Setter
    // Getters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Generate -> toString (all)

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

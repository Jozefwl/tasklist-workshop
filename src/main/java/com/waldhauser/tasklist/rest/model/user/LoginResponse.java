package com.waldhauser.tasklist.rest.model.user;

import java.util.UUID;

public class LoginResponse {
    private UUID id;
    private String name;
    private String token;

    public LoginResponse(UUID id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}

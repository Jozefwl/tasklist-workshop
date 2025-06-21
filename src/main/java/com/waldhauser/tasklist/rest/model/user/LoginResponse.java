package com.waldhauser.tasklist.rest.model.user;

import java.util.UUID;

/**
 * Represents the response returned from the login process.
 * <p>
 * This class encapsulates user-specific data needed after a successful
 * authentication, including a unique user ID, the user's name, and an
 * authentication token for further interactions with the system.
 * <p>
 * Use this class to transmit authentication information securely
 * between the system and its clients.
 */
public class LoginResponse {
    private UUID id;
    private String name;
    private String token;

    /**
     * Constructs a new {@code LoginResponse} instance with the provided parameters.
     *
     * @param id    the unique identifier of the user
     * @param name  the name of the user
     * @param token the authentication token assigned to the user after login
     */
    public LoginResponse(UUID id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

    /**
     * Retrieves the unique identifier associated with*/
    public UUID getId() { return id; }
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the user.
     *
     * @return the name of the user as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name of the user associated with this response.
     *
     * @param name the new name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the authentication token assigned to the user after login.
     *
     * @return the authentication token as a String
     */
    public String getToken() {
        return token;
    }

    /**
     * Updates the authentication token for the user associated with this response.
     *
     * @param token the new authentication token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
}

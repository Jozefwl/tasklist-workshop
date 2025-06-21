package com.waldhauser.tasklist.rest.model.user;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UserRequest {

    @NotNull(message = "Name cannot be null")
    @Length(min = 5, max = 255, message = "Name must be between 5 and 255 characters")
    private String name;
    @NotNull(message = "Password cannot be null")
    @Length(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;

    public UserRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

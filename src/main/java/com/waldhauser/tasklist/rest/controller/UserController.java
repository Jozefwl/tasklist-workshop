package com.waldhauser.tasklist.rest.controller;

import com.waldhauser.tasklist.domain.model.User;
import com.waldhauser.tasklist.rest.model.user.LoginResponse;
import com.waldhauser.tasklist.rest.model.user.UserRequest;
import com.waldhauser.tasklist.service.api.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The UserController class handles user-related operations including
 * registration and login for the authentication process.
 *
 * This controller exposes RESTful endpoints for user management,
 * specifically allowing the creation of new users and authenticating
 * existing users.
 *
 * It relies on the UserService for business logic related to user
 * registration and login functionality.
 *
 * Endpoints:
 * - POST /auth/register: Registers a new user with a unique name
 *   and password and returns the created User object.
 * - POST /auth/login: Authenticates an existing user by validating
 *   their credentials and returns a LoginResponse including a token
 *   for further authentication.
 */
@RestController
@RequestMapping(path = "auth")
@Tag(name = "User Management", description = "Endpoints for user registration and authentication.")
public class UserController {

    private final UserService userService;

    /**
     * Constructs a UserController instance with the provided UserService.
     *
     * @param userService the service layer that handles business logic for
     *                    user-related operations, including registration
     *                    and login.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user by processing the provided user data.
     * The method delegates the registration logic, which includes
     * user validation and persistence, to the UserService.
     *
     * @param user the user object containing the name and password
     *             of the user to be registered
     * @return the registered User object with updated information
     */
    @Operation(summary = "Registers a new user by processing the provided user data.")
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    @PostMapping("/register")
    public User register(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "The user to be registered.", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserRequest.class))) @RequestBody UserRequest user) {
        return userService.register(user.getName(), user.getPassword());
    }

    /**
     * Authenticates a user by validating their credentials.
     * This method delegates to the UserService to verify the provided
     * user information and returns a LoginResponse containing details
     * about the authenticated user, including a token for further authentication.
     *
     * @param user the user object containing the name and password to be authenticated
     * @return a LoginResponse object containing the user's ID, name, and authentication token
     */
    @Operation(summary = "Authenticates a user by validating their credentials.")
    @ApiResponse(responseCode = "200", description = "User authenticated successfully")
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody UserRequest user) {
        return userService.login(user.getName(), user.getPassword());
    }


}
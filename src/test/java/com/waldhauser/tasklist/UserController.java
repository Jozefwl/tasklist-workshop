package com.waldhauser.tasklist;

import com.waldhauser.tasklist.domain.model.User;
import com.waldhauser.tasklist.rest.controller.UserController;
import com.waldhauser.tasklist.rest.model.user.LoginResponse;
import com.waldhauser.tasklist.service.api.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void registerReturnsUserWhenValidInput() throws Exception {
        User user = new User();
        user.setName("testingUser");
        user.setPassword("password");
        when(userService.register("testingUser", "password")).thenReturn(user);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"testingUser\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testingUser"));
    }

    @Test
    void registerReturnsErrorWhenMissingName() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"password\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerReturnsBadRequestWhenUserIsNull() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerReturnsInternalServerErrorWhenServiceThrowsException() throws Exception {
        when(userService.register("errorUser", "password")).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"errorUser\",\"password\":\"password\"}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void loginReturnsLoginResponseWhenValidCredentials() throws Exception {
        User user = new User();
        user.setName("testingUser");
        user.setPassword("testPasswd");
        LoginResponse response = new LoginResponse(UUID.fromString("debeffb6-fb3c-4129-9ed9-61234b950ce7"), user.getName(), "testtoken");
        when(userService.login(user.getName(), user.getPassword())).thenReturn(response);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"testingUser\",\"password\":\"testPasswd\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("testtoken"));
    }

    @Test
    void loginReturnsErrorWhenInvalidCredentials() throws Exception {
        when(userService.login("testingUser", "wrongPasswd")).thenThrow(new RuntimeException("Invalid credentials"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"testingUser\",\"password\":\"wrongPasswd\"}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void loginReturnsErrorWhenMissingPassword() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"testingUser\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginReturnsBadRequestWhenUserIsNull() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginReturnsInternalServerErrorWhenServiceThrowsException() throws Exception {
        when(userService.login("errorUser", "password")).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"errorUser\",\"password\":\"password\"}"))
                .andExpect(status().isInternalServerError());
    }
}
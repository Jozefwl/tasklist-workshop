package com.waldhauser.tasklist;

import com.waldhauser.tasklist.domain.model.Tasklist;
import com.waldhauser.tasklist.rest.controller.TasklistController;
import com.waldhauser.tasklist.rest.model.tasklist.TasklistResponse;
import com.waldhauser.tasklist.service.api.TasklistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TaskListControllerTests {

    @Mock
    private TasklistService tasklistService;

    @InjectMocks
    private TasklistController tasklistController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(tasklistController).build();
    }

    @Test
    void getAllReturnsTasklistsForAuthenticatedUser() throws Exception {
        UUID userId = UUID.randomUUID();
        List<TasklistResponse> tasklists = List.of(new TasklistResponse(UUID.randomUUID(), userId, "ListName", null, null));
        org.springframework.security.core.Authentication authentication = org.mockito.Mockito.mock(org.springframework.security.core.Authentication.class);
        when(authentication.getName()).thenReturn(userId.toString());
        org.springframework.security.core.context.SecurityContext securityContext = org.mockito.Mockito.mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        org.springframework.security.core.context.SecurityContextHolder.setContext(securityContext);
        when(tasklistService.getAllTasklistsByOwnerId(userId)).thenReturn(tasklists);

        mockMvc.perform(get("/tasklist/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ownerId").value(userId.toString()));
    }

    @Test
    void getReturnsTasklistWhenUserIsOwner() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID tasklistId = UUID.randomUUID();
        TasklistResponse response = new TasklistResponse(tasklistId, userId, "ListName", null, null);

        org.springframework.security.core.Authentication authentication = org.mockito.Mockito.mock(org.springframework.security.core.Authentication.class);
        when(authentication.getName()).thenReturn(userId.toString());

        org.springframework.security.core.context.SecurityContext securityContext = org.mockito.Mockito.mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        org.springframework.security.core.context.SecurityContextHolder.setContext(securityContext);
        when(tasklistService.getTasklistById(tasklistId)).thenReturn(response);

        mockMvc.perform(get("/tasklist/get/" + tasklistId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tasklistId.toString()));
    }

    @Test
    void getReturnsUnauthorizedWhenUserIsNotOwner() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();
        UUID tasklistId = UUID.randomUUID();
        TasklistResponse response = new TasklistResponse(tasklistId, userId, "ListName", null, null);

        org.springframework.security.core.Authentication authentication = org.mockito.Mockito.mock(org.springframework.security.core.Authentication.class);
        when(authentication.getName()).thenReturn(otherUserId.toString());

        org.springframework.security.core.context.SecurityContext securityContext = org.mockito.Mockito.mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        org.springframework.security.core.context.SecurityContextHolder.setContext(securityContext);
        when(tasklistService.getTasklistById(tasklistId)).thenReturn(response);

        mockMvc.perform(get("/tasklist/get/" + tasklistId))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getThrowsInternalServerErrorWhenServiceThrowsException() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID tasklistId = UUID.randomUUID();
        org.springframework.security.core.Authentication authentication = org.mockito.Mockito.mock(org.springframework.security.core.Authentication.class);
        when(authentication.getName()).thenReturn(userId.toString());
        org.springframework.security.core.context.SecurityContext securityContext = org.mockito.Mockito.mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        org.springframework.security.core.context.SecurityContextHolder.setContext(securityContext);
        when(tasklistService.getTasklistById(tasklistId)).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(get("/tasklist/get/" + tasklistId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void createAssignsOwnerIdFromAuthenticatedUser() throws Exception {
        UUID userId = UUID.randomUUID();
        Tasklist tasklist = new Tasklist();
        tasklist.setName("New List");
        Tasklist created = new Tasklist();
        created.setId(UUID.randomUUID());
        created.setName("New List");
        created.setOwnerId(userId);
        org.springframework.security.core.Authentication authentication = org.mockito.Mockito.mock(org.springframework.security.core.Authentication.class);
        when(authentication.getName()).thenReturn(userId.toString());
        org.springframework.security.core.context.SecurityContext securityContext = org.mockito.Mockito.mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        org.springframework.security.core.context.SecurityContextHolder.setContext(securityContext);
        when(tasklistService.create(any(Tasklist.class))).thenReturn(created);

        mockMvc.perform(post("/tasklist/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New List\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ownerId").value(userId.toString()));
    }
}
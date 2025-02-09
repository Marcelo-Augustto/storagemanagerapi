package com.example.storagemanagerapi.controller;

import com.example.storagemanagerapi.model.User;
import com.example.storagemanagerapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User(1L, "john_doe", "password123", "john.doe@example.com", "USER");
    }

    @Test
    @Order(1)
    public void testRegisterUser() throws Exception {
        when(userService.registerUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john_doe\",\"password\":\"password123\",\"email\":\"john.doe@example.com\",\"role\":\"USER\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @Order(2)
    public void testLoginUser() throws Exception {
        when(userService.loginUser("john_doe", "password123")).thenReturn(java.util.Optional.of(user));

        mockMvc.perform(post("/api/users/login")
                        .param("username", "john_doe")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));
    }

    @Test
    @Order(3)
    public void testGetUserDetails() throws Exception {
        when(userService.getUserById(1L)).thenReturn(java.util.Optional.of(user));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @Order(4)
    public void testUpdateUser() throws Exception {
        User updatedUser = new User(1L, "john_doe_updated", "newpassword123", "john.doe_updated@example.com", "ADMIN");

        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"john_doe_updated\",\"password\":\"newpassword123\",\"email\":\"john.doe_updated@example.com\",\"role\":\"ADMIN\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe_updated"))
                .andExpect(jsonPath("$.email").value("john.doe_updated@example.com"));
    }

    @Test
    @Order(5)
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }
}

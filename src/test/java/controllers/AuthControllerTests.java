package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controllers.AuthController;
import org.example.model.User;
import org.example.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTests {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    List<User> mockUser = Collections.singletonList(new User(100, "user", "password", "user@example.com"));
    @Test
    public void testGetAllUsers() throws Exception {
        when(authService.getAllUsers()).thenReturn(mockUser);
        mockMvc.perform(get("/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].userName").value("user"));
        verify(authService, times(1)).getAllUsers();
    }

    @Test
    public void testAddUser() throws Exception {
        User user = new User();
        user.setUserName("user");

        mockMvc.perform(MockMvcRequestBuilders.post("/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetUserByName() throws Exception {
        String userName = "user";
        User user = new User();
        user.setUserName(userName);
        when(authService.getUserByName(userName)).thenReturn(user);

       mockMvc.perform(MockMvcRequestBuilders.get("/getUserByName")
                        .param("userName", userName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteUserByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteUserByName")
                        .param("userName", "user"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteAllUsers"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}

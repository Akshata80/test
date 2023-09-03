package services;

import org.example.model.User;
import org.example.repository.AuthRepository;
import org.example.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class AuthServiceTests {
    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private AuthService authService;

    User mockUser = new User(100, "user", "password", "user@example.com");

    @BeforeEach
    void setUp() {
        authRepository = Mockito.mock(AuthRepository.class);
        authService = new AuthService();
        authService.authrepository = authRepository;

        List<User> testData = new ArrayList<>();
        testData.add(new User(100, "user", "password", "user@example.com"));
        authService.dataStore = testData;
    }

    @Test
    void LoadUserByUsername() {
        when(authRepository.findByUserName("user")).thenReturn(mockUser);
        UserDetails userDetails = authService.loadUserByUsername("user");
        assertNotNull(userDetails);
        assertEquals("user", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }

    @Test
    void LoadUserByUsernameNotFound() {
        when(authRepository.findByUserName("apollo")).thenReturn(null);
        assertThrows(NullPointerException.class, () -> {
            authService.loadUserByUsername("apollo");
        });
    }

    @Test
    void GetAllUsers() {
        when(authRepository.findAll()).thenReturn(authService.dataStore);
        List<User> users = authService.getAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    void AddUserValid() {
        when(authRepository.save(mockUser)).thenReturn(mockUser);
        String result = authService.addUser(mockUser);
        assertEquals(mockUser.toString(), result);
    }

    @Test
    void AddUserInvalid() {
        User invalidUser = new User(100, "user", "password", "");
        String result = authService.addUser(invalidUser);
        assertEquals("inValid User Details", result);
    }

    @Test
    void GetUserById() {
        when(authRepository.findByUserName("user")).thenReturn(mockUser);
        User user = authService.getUserByName("user");
        assertEquals(mockUser.getUserName(), user.getUserName());
    }

    @Test
    void DeleteUserById() {
        when(authRepository.deleteByUserName("user")).thenReturn("User is Deleted Successfully");
        authService.deleteUserByName("user");
    }

    @Test
    void testDeleteUsers() {
        authService.deleteUsers();
    }

}

package com.deloitte.flight.service;

import com.deloitte.flight.dao.UserDao;
import com.deloitte.flight.dto.SignupDto;
import com.deloitte.flight.model.User;
import com.deloitte.flight.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User(1L, "user@deloitte.com", "password123", "USER");
    }

    @Test
    @DisplayName("registerUser should successfully save user when email is unique")
    void testRegisterUser_Success() {
        SignupDto signupDto = new SignupDto("test@deloitte.com", "password123", "USER");

        when(userDao.existsByEmail("test@deloitte.com")).thenReturn(false);
        when(userDao.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setUserId(10L);
            return u;
        });

        User registered = userService.registerUser(signupDto);

        assertNotNull(registered);
        assertEquals("test@deloitte.com", registered.getEmail());
        assertEquals("USER", registered.getRole());
        verify(userDao, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("registerUser should throw exception when email already exists")
    void testRegisterUser_EmailAlreadyExists() {
        SignupDto signupDto = new SignupDto("user@deloitte.com", "password123", "USER");
        when(userDao.existsByEmail("user@deloitte.com")).thenReturn(true);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(signupDto);
        });

        assertTrue(thrown.getMessage().contains("already exists"));
        verify(userDao, never()).save(any(User.class));
    }

    @Test
    @DisplayName("authenticate should return User when credentials match")
    void testAuthenticate_Success() {
        when(userDao.findByEmail("user@deloitte.com")).thenReturn(Optional.of(sampleUser));

        User authenticated = userService.authenticate("user@deloitte.com", "password123");

        assertNotNull(authenticated);
        assertEquals(1L, authenticated.getUserId());
        assertEquals("user@deloitte.com", authenticated.getEmail());
    }

    @Test
    @DisplayName("authenticate should throw exception when email not found")
    void testAuthenticate_UserNotFound() {
        when(userDao.findByEmail("unknown@deloitte.com")).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.authenticate("unknown@deloitte.com", "password123");
        });

        assertEquals("Invalid email or password", thrown.getMessage());
    }

    @Test
    @DisplayName("authenticate should throw exception when password does not match")
    void testAuthenticate_WrongPassword() {
        when(userDao.findByEmail("user@deloitte.com")).thenReturn(Optional.of(sampleUser));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.authenticate("user@deloitte.com", "wrongPassword");
        });

        assertEquals("Invalid email or password", thrown.getMessage());
    }
}

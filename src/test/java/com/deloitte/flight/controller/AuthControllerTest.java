package com.deloitte.flight.controller;

import com.deloitte.flight.dto.LoginDto;
import com.deloitte.flight.dto.SignupDto;
import com.deloitte.flight.model.User;
import com.deloitte.flight.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    @DisplayName("GET /login should display login page with empty loginDto")
    void testShowLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginDto"));
    }

    @Test
    @DisplayName("POST /login with valid user credentials should redirect to user dashboard")
    void testProcessLogin_UserSuccess() throws Exception {
        User user = new User(1L, "user@deloitte.com", "user123", "USER");
        when(userService.authenticate("user@deloitte.com", "user123")).thenReturn(user);

        mockMvc.perform(post("/login")
                        .param("email", "user@deloitte.com")
                        .param("password", "user123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/dashboard"));

        verify(userService, times(1)).authenticate("user@deloitte.com", "user123");
    }

    @Test
    @DisplayName("POST /login with valid admin credentials should redirect to admin dashboard")
    void testProcessLogin_AdminSuccess() throws Exception {
        User admin = new User(2L, "admin@deloitte.com", "admin123", "ADMIN");
        when(userService.authenticate("admin@deloitte.com", "admin123")).thenReturn(admin);

        mockMvc.perform(post("/login")
                        .param("email", "admin@deloitte.com")
                        .param("password", "admin123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/dashboard"));
    }

    @Test
    @DisplayName("POST /login with invalid credentials should return to login page with errorMessage")
    void testProcessLogin_InvalidCredentials() throws Exception {
        when(userService.authenticate("invalid@deloitte.com", "wrongpass"))
                .thenThrow(new IllegalArgumentException("Invalid email or password"));

        mockMvc.perform(post("/login")
                        .param("email", "invalid@deloitte.com")
                        .param("password", "wrongpass"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("errorMessage", "Invalid email or password"));
    }

    @Test
    @DisplayName("POST /login with missing fields should fail validation and return login view")
    void testProcessLogin_ValidationFailure() throws Exception {
        mockMvc.perform(post("/login")
                        .param("email", "")
                        .param("password", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().hasErrors());
    }

    @Test
    @DisplayName("GET /signup should display signup page with empty signupDto")
    void testShowSignupPage() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("signupDto"));
    }

    @Test
    @DisplayName("POST /signup with valid details should register user and redirect to login")
    void testProcessSignup_Success() throws Exception {
        User user = new User(1L, "newuser@deloitte.com", "password123", "USER");
        when(userService.registerUser(any(SignupDto.class))).thenReturn(user);

        mockMvc.perform(post("/signup")
                        .param("email", "newuser@deloitte.com")
                        .param("password", "password123")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(userService, times(1)).registerUser(any(SignupDto.class));
    }

    @Test
    @DisplayName("POST /signup with duplicate email should show field error on signup view")
    void testProcessSignup_DuplicateEmail() throws Exception {
        when(userService.registerUser(any(SignupDto.class)))
                .thenThrow(new IllegalArgumentException("An account with this email already exists"));

        mockMvc.perform(post("/signup")
                        .param("email", "existing@deloitte.com")
                        .param("password", "password123")
                        .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().hasErrors());
    }

    @Test
    @DisplayName("POST /signup with invalid inputs should fail validation")
    void testProcessSignup_ValidationFailure() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("email", "not-an-email")
                        .param("password", "123") // too short
                        .param("role", "INVALID_ROLE"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().hasErrors());
    }

    @Test
    @DisplayName("GET /logout should invalidate session and redirect to login with logout param")
    void testLogout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout=true"));
    }
}

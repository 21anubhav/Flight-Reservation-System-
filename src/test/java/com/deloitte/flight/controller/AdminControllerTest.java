package com.deloitte.flight.controller;

import com.deloitte.flight.dto.AddFlightDto;
import com.deloitte.flight.model.User;
import com.deloitte.flight.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FlightService flightService;

    @InjectMocks
    private AdminController adminController;

    private User adminUser;
    private MockHttpSession adminSession;

    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .setViewResolvers(viewResolver)
                .build();

        adminUser = new User(1L, "admin@deloitte.com", "admin123", "ADMIN");
        adminSession = new MockHttpSession();
        adminSession.setAttribute("currentUser", adminUser);
    }

    @Test
    @DisplayName("GET /admin/dashboard should display admin dashboard for admin")
    void testShowAdminDashboard() throws Exception {
        mockMvc.perform(get("/admin/dashboard").session(adminSession))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-dashboard"))
                .andExpect(model().attributeExists("adminUser"));
    }

    @Test
    @DisplayName("POST /admin/add-flight with valid details should add flight and redirect")
    void testProcessAddFlight_Success() throws Exception {
        when(flightService.existsByFlightNumber("AI999")).thenReturn(false);

        mockMvc.perform(post("/admin/add-flight")
                        .session(adminSession)
                        .param("flightNumber", "AI999")
                        .param("capacity", "20")
                        .param("source", "Bangalore")
                        .param("destination", "Kolkata")
                        .param("departureTime", "09:00")
                        .param("arrivalTime", "11:30")
                        .param("pricePerSeat", "5500.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/dashboard"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(flightService, times(1)).addFlight(any(AddFlightDto.class));
    }

    @Test
    @DisplayName("POST /admin/add-flight with duplicate flight number should return error")
    void testProcessAddFlight_DuplicateFlightNumber() throws Exception {
        when(flightService.existsByFlightNumber("AI101")).thenReturn(true);

        mockMvc.perform(post("/admin/add-flight")
                        .session(adminSession)
                        .param("flightNumber", "AI101")
                        .param("capacity", "20")
                        .param("source", "Bangalore")
                        .param("destination", "Hyderabad")
                        .param("departureTime", "10:45")
                        .param("arrivalTime", "20:40")
                        .param("pricePerSeat", "5000.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-flight"))
                .andExpect(model().hasErrors());

        verify(flightService, never()).addFlight(any(AddFlightDto.class));
    }
}

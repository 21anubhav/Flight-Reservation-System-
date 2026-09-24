package com.deloitte.flight.controller;

import com.deloitte.flight.model.Flight;
import com.deloitte.flight.service.FlightService;
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

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FlightService flightService;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(homeController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    @DisplayName("GET / should display home page with empty search form")
    void testHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("searchFlightDto"));
    }

    @Test
    @DisplayName("POST /search with valid distinct cities should return matching flights")
    void testSearchFlights_Success() throws Exception {
        Flight flight = new Flight(1L, "AI101", "Bangalore", "Hyderabad", "10:45", "20:40", 20, 5000.0);
        when(flightService.searchFlights("Bangalore", "Hyderabad")).thenReturn(Collections.singletonList(flight));

        mockMvc.perform(post("/search")
                        .param("source", "Bangalore")
                        .param("destination", "Hyderabad"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("availableFlights"))
                .andExpect(model().attribute("searchPerformed", true));

        verify(flightService, times(1)).searchFlights("Bangalore", "Hyderabad");
    }

    @Test
    @DisplayName("POST /search with same source and destination should reject with error")
    void testSearchFlights_SameCityError() throws Exception {
        mockMvc.perform(post("/search")
                        .param("source", "Bangalore")
                        .param("destination", "Bangalore"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().hasErrors());

        verify(flightService, never()).searchFlights(anyString(), anyString());
    }
}

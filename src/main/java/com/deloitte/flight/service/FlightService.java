package com.deloitte.flight.service;

import com.deloitte.flight.dto.AddFlightDto;
import com.deloitte.flight.dto.FlightJourneySummaryDto;
import com.deloitte.flight.model.Flight;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    Flight addFlight(AddFlightDto addFlightDto);
    Flight getFlightById(Long flightId);
    Flight getFlightByNumber(String flightNumber);
    List<Flight> getAllFlights();
    List<Flight> searchFlights(String source, String destination);
    boolean existsByFlightNumber(String flightNumber);
    List<FlightJourneySummaryDto> getBookedJourneysSummary(boolean upcoming, LocalDate currentDate);
    void updateFlightOperationalStatus(Long flightId, String status, String gate, String terminal);
}

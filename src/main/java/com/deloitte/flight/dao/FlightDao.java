package com.deloitte.flight.dao;

import com.deloitte.flight.dto.FlightJourneySummaryDto;
import com.deloitte.flight.model.Flight;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FlightDao {
    Flight save(Flight flight);
    Optional<Flight> findById(Long flightId);
    Optional<Flight> findByFlightNumber(String flightNumber);
    List<Flight> findAll();
    List<Flight> findBySourceAndDestination(String source, String destination);
    boolean existsByFlightNumber(String flightNumber);
    List<FlightJourneySummaryDto> findBookedJourneysSummary(boolean upcoming, LocalDate currentDate);
}

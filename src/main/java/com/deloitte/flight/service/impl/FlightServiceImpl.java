package com.deloitte.flight.service.impl;

import com.deloitte.flight.dao.FlightDao;
import com.deloitte.flight.dto.AddFlightDto;
import com.deloitte.flight.dto.FlightJourneySummaryDto;
import com.deloitte.flight.exception.ResourceNotFoundException;
import com.deloitte.flight.model.Flight;
import com.deloitte.flight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightDao flightDao;

    @Autowired
    public FlightServiceImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    @Transactional
    public Flight addFlight(AddFlightDto addFlightDto) {
        if (addFlightDto == null || addFlightDto.getFlightNumber() == null) {
            throw new IllegalArgumentException("Flight details are required");
        }
        String cleanFlightNumber = addFlightDto.getFlightNumber().trim().toUpperCase();
        if (flightDao.existsByFlightNumber(cleanFlightNumber)) {
            throw new IllegalArgumentException("Flight number '" + cleanFlightNumber + "' already exists");
        }

        Flight flight = new Flight();
        flight.setFlightNumber(cleanFlightNumber);
        flight.setSource(addFlightDto.getSource().trim());
        flight.setDestination(addFlightDto.getDestination().trim());
        flight.setDepartureTime(addFlightDto.getDepartureTime().trim());
        flight.setArrivalTime(addFlightDto.getArrivalTime().trim());
        flight.setCapacity(addFlightDto.getCapacity() != null ? addFlightDto.getCapacity() : 20);
        flight.setPricePerSeat(addFlightDto.getPricePerSeat());

        return flightDao.save(flight);
    }

    @Override
    public Flight getFlightById(Long flightId) {
        if (flightId == null) {
            throw new ResourceNotFoundException("Flight ID cannot be null");
        }
        return flightDao.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with ID: " + flightId));
    }

    @Override
    public Flight getFlightByNumber(String flightNumber) {
        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            throw new ResourceNotFoundException("Flight number cannot be empty");
        }
        return flightDao.findByFlightNumber(flightNumber.trim())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with number: " + flightNumber));
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightDao.findAll();
    }

    @Override
    public List<Flight> searchFlights(String source, String destination) {
        if (source == null || destination == null) {
            return flightDao.findAll();
        }
        return flightDao.findBySourceAndDestination(source.trim(), destination.trim());
    }

    @Override
    public boolean existsByFlightNumber(String flightNumber) {
        if (flightNumber == null) return false;
        return flightDao.existsByFlightNumber(flightNumber.trim());
    }

    @Override
    public List<FlightJourneySummaryDto> getBookedJourneysSummary(boolean upcoming, LocalDate currentDate) {
        if (currentDate == null) {
            currentDate = LocalDate.now();
        }
        return flightDao.findBookedJourneysSummary(upcoming, currentDate);
    }
}

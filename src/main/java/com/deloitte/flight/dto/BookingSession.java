package com.deloitte.flight.dto;

import com.deloitte.flight.model.Flight;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingSession implements Serializable {
    private static final long serialVersionUID = 1L;

    private Flight flight;
    private LocalDate journeyDate;
    private int totalPassengers;
    private int currentPassengerIndex = 1; // 1-based for UI (e.g. Passenger 1 of 2)
    private List<PassengerDto> passengers = new ArrayList<>();

    public BookingSession() {
    }

    public BookingSession(Flight flight, LocalDate journeyDate, int totalPassengers) {
        this.flight = flight;
        this.journeyDate = journeyDate;
        this.totalPassengers = totalPassengers;
        this.currentPassengerIndex = 1;
        this.passengers = new ArrayList<>();
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public int getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(int totalPassengers) {
        this.totalPassengers = totalPassengers;
    }

    public int getCurrentPassengerIndex() {
        return currentPassengerIndex;
    }

    public void setCurrentPassengerIndex(int currentPassengerIndex) {
        this.currentPassengerIndex = currentPassengerIndex;
    }

    public List<PassengerDto> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerDto> passengers) {
        this.passengers = passengers;
    }

    public int getRemainingPassengers() {
        return totalPassengers - currentPassengerIndex;
    }

    public boolean isLastPassenger() {
        return currentPassengerIndex >= totalPassengers;
    }

    public void addOrUpdatePassenger(int index1Based, PassengerDto passenger) {
        while (passengers.size() < index1Based) {
            passengers.add(null);
        }
        passengers.set(index1Based - 1, passenger);
    }

    public PassengerDto getPassenger(int index1Based) {
        if (index1Based <= passengers.size()) {
            return passengers.get(index1Based - 1);
        }
        return new PassengerDto();
    }

    public double calculateTotalPrice() {
        if (flight != null && flight.getPricePerSeat() != null) {
            return flight.getPricePerSeat() * totalPassengers;
        }
        return 0.0;
    }
}

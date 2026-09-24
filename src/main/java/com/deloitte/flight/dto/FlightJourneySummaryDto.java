package com.deloitte.flight.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class FlightJourneySummaryDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long flightId;
    private String flightNumber;
    private LocalDate journeyDate;
    private Integer totalPassengers;
    private Integer totalBookings;
    private Double totalRevenue;
    private String daysUntilJourney;
    private boolean completed;

    public FlightJourneySummaryDto() {
    }

    public FlightJourneySummaryDto(Long flightId, String flightNumber, LocalDate journeyDate,
                                  Integer totalPassengers, Integer totalBookings, Double totalRevenue,
                                  String daysUntilJourney, boolean completed) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.journeyDate = journeyDate;
        this.totalPassengers = totalPassengers;
        this.totalBookings = totalBookings;
        this.totalRevenue = totalRevenue;
        this.daysUntilJourney = daysUntilJourney;
        this.completed = completed;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public Integer getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(Integer totalPassengers) {
        this.totalPassengers = totalPassengers;
    }

    public Integer getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(Integer totalBookings) {
        this.totalBookings = totalBookings;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public String getDaysUntilJourney() {
        return daysUntilJourney;
    }

    public void setDaysUntilJourney(String daysUntilJourney) {
        this.daysUntilJourney = daysUntilJourney;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

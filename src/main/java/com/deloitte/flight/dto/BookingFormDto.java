package com.deloitte.flight.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class BookingFormDto {

    @NotNull(message = "Flight is required")
    private Long flightId;

    @NotBlank(message = "Journey Date is required")
    private String journeyDate; // formatted as YYYY-MM-DD or MM/DD/YYYY

    @NotNull(message = "Number of passengers is required")
    @Min(value = 1, message = "At least 1 passenger is required")
    @Max(value = 10, message = "Maximum 10 passengers allowed")
    private Integer seats = 1;

    public BookingFormDto() {
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate != null ? journeyDate.trim() : null;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public LocalDate getParsedJourneyDate() {
        if (journeyDate == null || journeyDate.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(journeyDate.trim());
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}

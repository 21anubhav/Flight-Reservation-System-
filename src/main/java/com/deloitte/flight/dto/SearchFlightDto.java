package com.deloitte.flight.dto;

import javax.validation.constraints.NotBlank;

public class SearchFlightDto {

    @NotBlank(message = "Departure city is required")
    private String source;

    @NotBlank(message = "Destination city is required")
    private String destination;

    public SearchFlightDto() {
    }

    public SearchFlightDto(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source != null ? source.trim() : null;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination != null ? destination.trim() : null;
    }
}

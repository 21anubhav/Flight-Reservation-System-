package com.deloitte.flight.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long pnrNo;
    private String passengerName;
    private String gender;
    private Integer age;
    private LocalDate journeyDate;
    private String seatNo;
    private Long bookingId;
    private String status; // "CONFIRMED", "CANCELLED"

    // Helper display fields
    private String flightNumber;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;

    public Ticket() {
    }

    public Ticket(Long pnrNo, String passengerName, String gender, Integer age,
                  LocalDate journeyDate, String seatNo, Long bookingId, String status) {
        this.pnrNo = pnrNo;
        this.passengerName = passengerName;
        this.gender = gender;
        this.age = age;
        this.journeyDate = journeyDate;
        this.seatNo = seatNo;
        this.bookingId = bookingId;
        this.status = status;
    }

    public Long getPnrNo() {
        return pnrNo;
    }

    public void setPnrNo(Long pnrNo) {
        this.pnrNo = pnrNo;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isCancelled() {
        return "CANCELLED".equalsIgnoreCase(this.status);
    }
}

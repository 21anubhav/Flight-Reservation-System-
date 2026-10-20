package com.deloitte.flight.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long bookingId;
    private LocalDate bookingDate;
    private LocalDate journeyDate;
    private Integer seats;
    private Double totalPrice;
    private String status; // "CONFIRMED", "CANCELLED"
    private Long flightId;
    private Long userId;

    // Enterprise coupon & discount fields
    private String couponCode;
    private Double discountAmount = 0.0;

    // Additional display fields joined from Flight / User
    private String flightNumber;
    private String airline = "Air India";
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private String userEmail;
    private String terminal = "T2";
    private String gate = "G14";

    public Booking() {
    }

    public Booking(Long bookingId, LocalDate bookingDate, LocalDate journeyDate, Integer seats,
                   Double totalPrice, String status, Long flightId, Long userId) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.journeyDate = journeyDate;
        this.seats = seats;
        this.totalPrice = totalPrice;
        this.status = status;
        this.flightId = flightId;
        this.userId = userId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Double getDiscountAmount() {
        return discountAmount != null ? discountAmount : 0.0;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirline() {
        return airline != null ? airline : "Air India";
    }

    public void setAirline(String airline) {
        this.airline = airline;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTerminal() {
        return terminal != null ? terminal : "T2";
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getGate() {
        return gate != null ? gate : "G14";
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public boolean isCancelled() {
        return "CANCELLED".equalsIgnoreCase(this.status);
    }
}

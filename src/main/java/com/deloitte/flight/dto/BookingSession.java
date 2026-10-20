package com.deloitte.flight.dto;

import com.deloitte.flight.model.Flight;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookingSession implements Serializable {
    private static final long serialVersionUID = 1L;

    private Flight flight;
    private LocalDate journeyDate;
    private int totalPassengers;
    private int currentPassengerIndex = 1; // 1-based for UI (e.g. Passenger 1 of 2)
    private List<PassengerDto> passengers = new ArrayList<>();

    // Enterprise seat map & coupon features
    private Set<String> occupiedSeats = new HashSet<>();
    private String couponCode;
    private double discountAmount = 0.0;
    private String couponMessage;

    public BookingSession() {
    }

    public BookingSession(Flight flight, LocalDate journeyDate, int totalPassengers) {
        this.flight = flight;
        this.journeyDate = journeyDate;
        this.totalPassengers = totalPassengers;
        this.currentPassengerIndex = 1;
        this.passengers = new ArrayList<>();
        this.occupiedSeats = new HashSet<>();
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

    public Set<String> getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(Set<String> occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getCouponMessage() {
        return couponMessage;
    }

    public void setCouponMessage(String couponMessage) {
        this.couponMessage = couponMessage;
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

    public double calculateBaseFare() {
        if (flight != null && flight.getPricePerSeat() != null) {
            return flight.getPricePerSeat() * totalPassengers;
        }
        return 0.0;
    }

    public double calculateTaxes() {
        return Math.round(calculateBaseFare() * 0.05); // 5% GST
    }

    public double calculateNetPrice() {
        double subtotal = calculateBaseFare() + calculateTaxes();
        return Math.max(0.0, subtotal - discountAmount);
    }

    public boolean applyCoupon(String code) {
        if (code == null || code.trim().isEmpty()) {
            return false;
        }
        String clean = code.trim().toUpperCase();
        double base = calculateBaseFare();

        if ("DELOITTE20".equals(clean)) {
            this.couponCode = clean;
            this.discountAmount = Math.round(base * 0.20);
            this.couponMessage = "Coupon 'DELOITTE20' applied! 20% discount.";
            return true;
        } else if ("DELOITTE15".equals(clean)) {
            this.couponCode = clean;
            this.discountAmount = Math.round(base * 0.15);
            this.couponMessage = "Coupon 'DELOITTE15' applied! 15% discount.";
            return true;
        } else if ("FLY500".equals(clean)) {
            this.couponCode = clean;
            this.discountAmount = Math.min(base, 500.0);
            this.couponMessage = "Coupon 'FLY500' applied! Flat ₹500 discount.";
            return true;
        } else if ("FIRSTFLY".equals(clean)) {
            this.couponCode = clean;
            this.discountAmount = Math.round(base * 0.10);
            this.couponMessage = "Coupon 'FIRSTFLY' applied! 10% discount.";
            return true;
        } else {
            this.couponMessage = "Invalid coupon code.";
            return false;
        }
    }
}

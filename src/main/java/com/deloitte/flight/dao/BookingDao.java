package com.deloitte.flight.dao;

import com.deloitte.flight.model.Booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingDao {
    Booking save(Booking booking);
    Optional<Booking> findById(Long bookingId);
    List<Booking> findByUserId(Long userId);
    int countBookedSeats(Long flightId, LocalDate journeyDate);
    void updateStatus(Long bookingId, String status);
    List<Booking> findAll();
}

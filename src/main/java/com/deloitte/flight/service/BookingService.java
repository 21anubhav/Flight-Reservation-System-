package com.deloitte.flight.service;

import com.deloitte.flight.dto.BookingSession;
import com.deloitte.flight.model.Booking;
import com.deloitte.flight.model.Ticket;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    int getAvailableSeats(Long flightId, LocalDate journeyDate);
    Booking createBooking(BookingSession session, Long userId);
    List<Booking> getBookingsByUserId(Long userId);
    Booking getBookingById(Long bookingId);
    void cancelBooking(Long bookingId, Long userId, boolean isAdmin);
    List<Ticket> getTicketsByBookingId(Long bookingId);
}

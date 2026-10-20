package com.deloitte.flight.dao;

import com.deloitte.flight.model.Ticket;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TicketDao {
    Ticket save(Ticket ticket);
    void saveAll(List<Ticket> tickets);
    List<Ticket> findByBookingId(Long bookingId);
    Optional<Ticket> findByPnrNo(Long pnrNo);
    void updateStatusByBookingId(Long bookingId, String status);
    List<String> findBookedSeatNumbers(Long flightId, LocalDate journeyDate);
}

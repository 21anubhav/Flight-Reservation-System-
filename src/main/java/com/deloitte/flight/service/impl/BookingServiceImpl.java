package com.deloitte.flight.service.impl;

import com.deloitte.flight.dao.BookingDao;
import com.deloitte.flight.dao.FlightDao;
import com.deloitte.flight.dao.TicketDao;
import com.deloitte.flight.dto.BookingSession;
import com.deloitte.flight.dto.PassengerDto;
import com.deloitte.flight.exception.ResourceNotFoundException;
import com.deloitte.flight.exception.SeatUnavailableException;
import com.deloitte.flight.exception.UnauthorizedException;
import com.deloitte.flight.model.Booking;
import com.deloitte.flight.model.Flight;
import com.deloitte.flight.model.Ticket;
import com.deloitte.flight.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;
    private final FlightDao flightDao;
    private final TicketDao ticketDao;

    @Autowired
    public BookingServiceImpl(BookingDao bookingDao, FlightDao flightDao, TicketDao ticketDao) {
        this.bookingDao = bookingDao;
        this.flightDao = flightDao;
        this.ticketDao = ticketDao;
    }

    @Override
    public int getAvailableSeats(Long flightId, LocalDate journeyDate) {
        Flight flight = flightDao.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with ID: " + flightId));
        int bookedSeats = bookingDao.countBookedSeats(flightId, journeyDate);
        int capacity = flight.getCapacity() != null ? flight.getCapacity() : 20;
        return Math.max(0, capacity - bookedSeats);
    }

    @Override
    @Transactional
    public Booking createBooking(BookingSession session, Long userId) {
        if (session == null || session.getFlight() == null) {
            throw new IllegalArgumentException("Booking session data is required");
        }

        Long flightId = session.getFlight().getFlightId();
        LocalDate journeyDate = session.getJourneyDate();
        int requestedSeats = session.getTotalPassengers();

        int availableSeats = getAvailableSeats(flightId, journeyDate);
        if (availableSeats < requestedSeats) {
            throw new SeatUnavailableException("Not enough seats available! Only " + availableSeats +
                    " seat(s) remaining for flight " + session.getFlight().getFlightNumber() + " on " + journeyDate);
        }

        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());
        booking.setJourneyDate(journeyDate);
        booking.setSeats(requestedSeats);
        booking.setTotalPrice(session.calculateTotalPrice());
        booking.setStatus("CONFIRMED");
        booking.setFlightId(flightId);
        booking.setUserId(userId);

        Booking savedBooking = bookingDao.save(booking);

        // Allocate seat numbers
        List<String> bookedSeats = ticketDao.findBookedSeatNumbers(flightId, journeyDate);
        Set<String> occupiedSet = new HashSet<>(bookedSeats);

        List<Ticket> tickets = new ArrayList<>();
        int seatCounter = 1;
        int maxCapacity = session.getFlight().getCapacity() != null ? session.getFlight().getCapacity() : 20;

        for (PassengerDto passengerDto : session.getPassengers()) {
            if (passengerDto == null) continue;

            // Find next unassigned seat number like A1, A2, etc.
            String assignedSeat = null;
            while (seatCounter <= maxCapacity) {
                String candidate = "A" + seatCounter;
                seatCounter++;
                if (!occupiedSet.contains(candidate)) {
                    assignedSeat = candidate;
                    occupiedSet.add(candidate);
                    break;
                }
            }
            if (assignedSeat == null) {
                assignedSeat = "A" + (occupiedSet.size() + 1);
            }

            Ticket ticket = new Ticket();
            ticket.setPassengerName(passengerDto.getPassengerName());
            ticket.setGender(passengerDto.getGender());
            ticket.setAge(passengerDto.getAge());
            ticket.setJourneyDate(journeyDate);
            ticket.setSeatNo(assignedSeat);
            ticket.setBookingId(savedBooking.getBookingId());
            ticket.setStatus("CONFIRMED");

            tickets.add(ticket);
        }

        ticketDao.saveAll(tickets);

        return savedBooking;
    }

    @Override
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingDao.findByUserId(userId);
    }

    @Override
    public Booking getBookingById(Long bookingId) {
        return bookingDao.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
    }

    @Override
    @Transactional
    public void cancelBooking(Long bookingId, Long userId, boolean isAdmin) {
        Booking booking = getBookingById(bookingId);
        if (!isAdmin && !booking.getUserId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to cancel this booking.");
        }
        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            return; // Already cancelled
        }

        // Release seats by marking booking and tickets as CANCELLED
        bookingDao.updateStatus(bookingId, "CANCELLED");
        ticketDao.updateStatusByBookingId(bookingId, "CANCELLED");
    }

    @Override
    public List<Ticket> getTicketsByBookingId(Long bookingId) {
        return ticketDao.findByBookingId(bookingId);
    }
}

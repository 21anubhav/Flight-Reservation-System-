package com.deloitte.flight.service;

import com.deloitte.flight.dao.BookingDao;
import com.deloitte.flight.dao.FlightDao;
import com.deloitte.flight.dao.TicketDao;
import com.deloitte.flight.dto.BookingSession;
import com.deloitte.flight.dto.PassengerDto;
import com.deloitte.flight.exception.SeatUnavailableException;
import com.deloitte.flight.model.Booking;
import com.deloitte.flight.model.Flight;
import com.deloitte.flight.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingDao bookingDao;

    @Mock
    private FlightDao flightDao;

    @Mock
    private TicketDao ticketDao;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Flight sampleFlight;

    @BeforeEach
    void setUp() {
        sampleFlight = new Flight(1L, "AI304", "Bangalore", "Hyderabad", "17:25", "17:27", 20, 5600.0);
    }

    @Test
    @DisplayName("getAvailableSeats should return capacity minus booked seats")
    void testGetAvailableSeats() {
        LocalDate date = LocalDate.of(2026, 10, 15);
        when(flightDao.findById(1L)).thenReturn(Optional.of(sampleFlight));
        when(bookingDao.countBookedSeats(1L, date)).thenReturn(5);

        int available = bookingService.getAvailableSeats(1L, date);

        assertEquals(15, available);
    }

    @Test
    @DisplayName("createBooking should succeed and assign seats when capacity is sufficient")
    void testCreateBooking_Success() {
        LocalDate date = LocalDate.of(2026, 10, 15);
        when(flightDao.findById(1L)).thenReturn(Optional.of(sampleFlight));
        when(bookingDao.countBookedSeats(1L, date)).thenReturn(2);
        when(ticketDao.findBookedSeatNumbers(1L, date)).thenReturn(Collections.singletonList("A1"));

        Booking savedBooking = new Booking(5001L, LocalDate.now(), date, 2, 11200.0, "CONFIRMED", 1L, 2L);
        when(bookingDao.save(any(Booking.class))).thenReturn(savedBooking);

        BookingSession session = new BookingSession(sampleFlight, date, 2);
        session.addOrUpdatePassenger(1, new PassengerDto("Alice", 28, "Female"));
        session.addOrUpdatePassenger(2, new PassengerDto("Bob", 30, "Male"));

        Booking result = bookingService.createBooking(session, 2L);

        assertNotNull(result);
        assertEquals(5001L, result.getBookingId());
        verify(ticketDao, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("createBooking should throw SeatUnavailableException when remaining seats are insufficient")
    void testCreateBooking_SeatUnavailable() {
        LocalDate date = LocalDate.of(2026, 10, 15);
        when(flightDao.findById(1L)).thenReturn(Optional.of(sampleFlight));
        when(bookingDao.countBookedSeats(1L, date)).thenReturn(19); // only 1 seat left

        BookingSession session = new BookingSession(sampleFlight, date, 2); // requesting 2 seats

        assertThrows(SeatUnavailableException.class, () -> {
            bookingService.createBooking(session, 2L);
        });

        verify(bookingDao, never()).save(any(Booking.class));
    }

    @Test
    @DisplayName("cancelBooking should update status to CANCELLED for booking and tickets")
    void testCancelBooking() {
        Booking booking = new Booking(5001L, LocalDate.now(), LocalDate.of(2026, 10, 15), 1, 5600.0, "CONFIRMED", 1L, 2L);
        when(bookingDao.findById(5001L)).thenReturn(Optional.of(booking));

        bookingService.cancelBooking(5001L, 2L, false);

        verify(bookingDao, times(1)).updateStatus(5001L, "CANCELLED");
        verify(ticketDao, times(1)).updateStatusByBookingId(5001L, "CANCELLED");
    }
}

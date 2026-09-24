package com.deloitte.flight.controller;

import com.deloitte.flight.dto.BookingFormDto;
import com.deloitte.flight.dto.BookingSession;
import com.deloitte.flight.dto.PassengerDto;
import com.deloitte.flight.exception.ResourceNotFoundException;
import com.deloitte.flight.model.Booking;
import com.deloitte.flight.model.Flight;
import com.deloitte.flight.model.Ticket;
import com.deloitte.flight.model.User;
import com.deloitte.flight.service.BookingService;
import com.deloitte.flight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class BookingController {

    private final FlightService flightService;
    private final BookingService bookingService;

    @Autowired
    public BookingController(FlightService flightService, BookingService bookingService) {
        this.flightService = flightService;
        this.bookingService = bookingService;
    }

    // Step 1: Initial Booking Form (Page 6)
    @GetMapping("/book-flight")
    public String showBookingForm(@RequestParam("flightId") Long flightId,
                                  Model model,
                                  HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login?redirect=/book-flight?flightId=" + flightId;
        }

        Flight flight = flightService.getFlightById(flightId);
        BookingFormDto bookingFormDto = new BookingFormDto();
        bookingFormDto.setFlightId(flightId);
        bookingFormDto.setSeats(1);

        model.addAttribute("flight", flight);
        model.addAttribute("bookingFormDto", bookingFormDto);
        return "book-flight";
    }

    @PostMapping("/book-flight")
    public String processBookingForm(@Valid @ModelAttribute("bookingFormDto") BookingFormDto formDto,
                                     BindingResult bindingResult,
                                     Model model,
                                     HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login?redirect=/book-flight?flightId=" + formDto.getFlightId();
        }

        Flight flight = flightService.getFlightById(formDto.getFlightId());
        model.addAttribute("flight", flight);

        if (bindingResult.hasErrors()) {
            return "book-flight";
        }

        LocalDate journeyDate = formDto.getParsedJourneyDate();
        if (journeyDate == null) {
            bindingResult.rejectValue("journeyDate", "error.journeyDate", "Please provide a valid date (YYYY-MM-DD)");
            return "book-flight";
        }

        if (journeyDate.isBefore(LocalDate.now())) {
            bindingResult.rejectValue("journeyDate", "error.journeyDate", "Journey date cannot be in the past");
            return "book-flight";
        }

        // Check seat availability
        int availableSeats = bookingService.getAvailableSeats(flight.getFlightId(), journeyDate);
        if (availableSeats < formDto.getSeats()) {
            model.addAttribute("seatError", "Insufficient seats! Only " + availableSeats +
                    " seat(s) available on " + journeyDate + " for this flight.");
            return "book-flight";
        }

        // Initialize booking session for multi-passenger flow
        BookingSession bookingSession = new BookingSession(flight, journeyDate, formDto.getSeats());
        session.setAttribute("bookingSession", bookingSession);

        return "redirect:/book-flight/passenger?step=1";
    }

    // Step 2: Add Passenger Screens (Page 7)
    @GetMapping("/book-flight/passenger")
    public String showAddPassengerPage(@RequestParam(value = "step", defaultValue = "1") int step,
                                       Model model,
                                       HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        BookingSession bookingSession = (BookingSession) session.getAttribute("bookingSession");
        if (bookingSession == null) {
            return "redirect:/";
        }

        if (step < 1 || step > bookingSession.getTotalPassengers()) {
            step = 1;
        }

        bookingSession.setCurrentPassengerIndex(step);

        PassengerDto passengerDto = bookingSession.getPassenger(step);
        model.addAttribute("passengerDto", passengerDto);
        model.addAttribute("bookingSession", bookingSession);
        model.addAttribute("currentStep", step);
        model.addAttribute("totalSteps", bookingSession.getTotalPassengers());
        model.addAttribute("remainingPassengers", bookingSession.getTotalPassengers() - step);
        model.addAttribute("isLastPassenger", step == bookingSession.getTotalPassengers());

        return "add-passenger";
    }

    @PostMapping("/book-flight/passenger")
    public String processAddPassenger(@RequestParam("step") int step,
                                      @Valid @ModelAttribute("passengerDto") PassengerDto passengerDto,
                                      BindingResult bindingResult,
                                      Model model,
                                      HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        BookingSession bookingSession = (BookingSession) session.getAttribute("bookingSession");
        if (bookingSession == null) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("bookingSession", bookingSession);
            model.addAttribute("currentStep", step);
            model.addAttribute("totalSteps", bookingSession.getTotalPassengers());
            model.addAttribute("remainingPassengers", bookingSession.getTotalPassengers() - step);
            model.addAttribute("isLastPassenger", step == bookingSession.getTotalPassengers());
            return "add-passenger";
        }

        bookingSession.addOrUpdatePassenger(step, passengerDto);

        if (step < bookingSession.getTotalPassengers()) {
            return "redirect:/book-flight/passenger?step=" + (step + 1);
        } else {
            // Final step: Complete Booking
            Booking completedBooking = bookingService.createBooking(bookingSession, currentUser.getUserId());
            session.removeAttribute("bookingSession");
            return "redirect:/booking-confirmation?bookingId=" + completedBooking.getBookingId();
        }
    }

    // Step 3: Booking Confirmation (Page 7)
    @GetMapping("/booking-confirmation")
    public String showBookingConfirmation(@RequestParam("bookingId") Long bookingId,
                                          Model model,
                                          HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        Booking booking = bookingService.getBookingById(bookingId);
        List<Ticket> tickets = bookingService.getTicketsByBookingId(bookingId);

        model.addAttribute("booking", booking);
        model.addAttribute("tickets", tickets);
        return "booking-confirmation";
    }
}

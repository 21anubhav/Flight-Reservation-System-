package com.deloitte.flight.controller;

import com.deloitte.flight.exception.UnauthorizedException;
import com.deloitte.flight.model.Booking;
import com.deloitte.flight.model.Ticket;
import com.deloitte.flight.model.User;
import com.deloitte.flight.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final BookingService bookingService;

    @Autowired
    public UserController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    private User getAuthenticatedUser(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            throw new UnauthorizedException("Please log in to continue.");
        }
        return user;
    }

    @GetMapping("/dashboard")
    public String showUserDashboard(Model model, HttpSession session) {
        User user = getAuthenticatedUser(session);
        model.addAttribute("user", user);
        return "user-dashboard";
    }

    @GetMapping("/my-bookings")
    public String showMyBookings(Model model, HttpSession session) {
        User user = getAuthenticatedUser(session);
        List<Booking> bookings = bookingService.getBookingsByUserId(user.getUserId());
        model.addAttribute("bookings", bookings);
        model.addAttribute("user", user);
        return "my-bookings";
    }

    @PostMapping("/cancel-booking/{bookingId}")
    public String cancelBooking(@PathVariable("bookingId") Long bookingId,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        User user = getAuthenticatedUser(session);
        bookingService.cancelBooking(bookingId, user.getUserId(), user.isAdmin());
        redirectAttributes.addFlashAttribute("successMessage", "Booking #" + bookingId + " has been cancelled. Seats have been released.");
        return "redirect:/user/my-bookings";
    }

    @GetMapping("/view-tickets/{bookingId}")
    public String viewTickets(@PathVariable("bookingId") Long bookingId,
                              Model model,
                              HttpSession session) {
        User user = getAuthenticatedUser(session);
        Booking booking = bookingService.getBookingById(bookingId);

        // Security check: ensure booking belongs to user or user is admin
        if (!user.isAdmin() && !booking.getUserId().equals(user.getUserId())) {
            throw new UnauthorizedException("You are not authorized to view these tickets.");
        }

        List<Ticket> tickets = bookingService.getTicketsByBookingId(bookingId);
        model.addAttribute("booking", booking);
        model.addAttribute("tickets", tickets);
        model.addAttribute("user", user);

        return "view-tickets";
    }
}

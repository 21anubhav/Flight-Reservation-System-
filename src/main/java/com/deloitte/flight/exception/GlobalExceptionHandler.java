package com.deloitte.flight.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request, Model model) {
        logger.error("Resource not found at {}: {}", request.getRequestURI(), ex.getMessage());
        model.addAttribute("errorTitle", "Not Found");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("status", 404);
        return "error";
    }

    @ExceptionHandler(SeatUnavailableException.class)
    public String handleSeatUnavailable(SeatUnavailableException ex, HttpServletRequest request, Model model) {
        logger.warn("Seat unavailable exception at {}: {}", request.getRequestURI(), ex.getMessage());
        model.addAttribute("errorTitle", "Booking Error - Seat Unavailable");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("status", 400);
        return "error";
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorized(UnauthorizedException ex, HttpServletRequest request, Model model) {
        logger.warn("Unauthorized access attempt to {}: {}", request.getRequestURI(), ex.getMessage());
        model.addAttribute("errorTitle", "Unauthorized Access");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("status", 403);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, HttpServletRequest request, Model model) {
        logger.error("Unhandled exception processing request {}: ", request.getRequestURI(), ex);
        model.addAttribute("errorTitle", "Unexpected Server Error");
        model.addAttribute("errorMessage", "An unexpected error occurred while processing your request. Please try again later.");
        model.addAttribute("status", 500);
        return "error";
    }
}

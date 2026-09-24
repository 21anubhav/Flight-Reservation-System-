package com.deloitte.flight.controller;

import com.deloitte.flight.dto.AddFlightDto;
import com.deloitte.flight.dto.FlightJourneySummaryDto;
import com.deloitte.flight.exception.UnauthorizedException;
import com.deloitte.flight.model.User;
import com.deloitte.flight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final FlightService flightService;

    @Autowired
    public AdminController(FlightService flightService) {
        this.flightService = flightService;
    }

    private void checkAdminAccess(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.isAdmin()) {
            throw new UnauthorizedException("You must be an administrator to access this page.");
        }
    }

    @GetMapping("/dashboard")
    public String showAdminDashboard(Model model, HttpSession session) {
        checkAdminAccess(session);
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("adminUser", currentUser);
        return "admin-dashboard";
    }

    @GetMapping("/add-flight")
    public String showAddFlightPage(Model model, HttpSession session) {
        checkAdminAccess(session);
        if (!model.containsAttribute("addFlightDto")) {
            AddFlightDto dto = new AddFlightDto();
            dto.setCapacity(20); // Default 20
            model.addAttribute("addFlightDto", dto);
        }
        return "add-flight";
    }

    @PostMapping("/add-flight")
    public String processAddFlight(@Valid @ModelAttribute("addFlightDto") AddFlightDto addFlightDto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  Model model,
                                  HttpSession session) {
        checkAdminAccess(session);

        if (bindingResult.hasErrors()) {
            return "add-flight";
        }

        if (flightService.existsByFlightNumber(addFlightDto.getFlightNumber())) {
            bindingResult.rejectValue("flightNumber", "error.flightNumber",
                    "Flight number '" + addFlightDto.getFlightNumber() + "' already exists");
            return "add-flight";
        }

        try {
            flightService.addFlight(addFlightDto);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Flight " + addFlightDto.getFlightNumber() + " added successfully!");
            return "redirect:/admin/dashboard";
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("flightNumber", "error.flightNumber", ex.getMessage());
            return "add-flight";
        }
    }

    @GetMapping("/view-flights")
    public String showViewFlightsPage(@RequestParam(value = "tab", defaultValue = "upcoming") String tab,
                                      Model model,
                                      HttpSession session) {
        checkAdminAccess(session);

        LocalDate currentDate = LocalDate.now();
        model.addAttribute("currentDate", currentDate);

        boolean isUpcoming = !"completed".equalsIgnoreCase(tab);
        model.addAttribute("activeTab", isUpcoming ? "upcoming" : "completed");

        List<FlightJourneySummaryDto> upcomingJourneys = flightService.getBookedJourneysSummary(true, currentDate);
        List<FlightJourneySummaryDto> completedJourneys = flightService.getBookedJourneysSummary(false, currentDate);

        model.addAttribute("upcomingJourneys", upcomingJourneys);
        model.addAttribute("completedJourneys", completedJourneys);
        model.addAttribute("journeys", isUpcoming ? upcomingJourneys : completedJourneys);

        return "view-flights";
    }
}

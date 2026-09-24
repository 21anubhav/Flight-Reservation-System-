package com.deloitte.flight.controller;

import com.deloitte.flight.dto.SearchFlightDto;
import com.deloitte.flight.model.Flight;
import com.deloitte.flight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {

    private final FlightService flightService;

    @Autowired
    public HomeController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        if (!model.containsAttribute("searchFlightDto")) {
            model.addAttribute("searchFlightDto", new SearchFlightDto());
        }
        return "index";
    }

    @PostMapping("/search")
    public String searchFlights(@Valid @ModelAttribute("searchFlightDto") SearchFlightDto searchDto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        if (searchDto.getSource().equalsIgnoreCase(searchDto.getDestination())) {
            bindingResult.rejectValue("destination", "error.destination", "Source and Destination cannot be the same city");
            return "index";
        }

        List<Flight> flights = flightService.searchFlights(searchDto.getSource(), searchDto.getDestination());
        model.addAttribute("availableFlights", flights);
        model.addAttribute("searchPerformed", true);
        model.addAttribute("searchedSource", searchDto.getSource());
        model.addAttribute("searchedDestination", searchDto.getDestination());

        return "index";
    }

    @GetMapping("/search")
    public String searchGet(Model model) {
        return "redirect:/";
    }
}

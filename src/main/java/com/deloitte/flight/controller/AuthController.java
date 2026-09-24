package com.deloitte.flight.controller;

import com.deloitte.flight.dto.LoginDto;
import com.deloitte.flight.dto.SignupDto;
import com.deloitte.flight.model.User;
import com.deloitte.flight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSignupPage(Model model, HttpSession session) {
        if (session.getAttribute("currentUser") != null) {
            User user = (User) session.getAttribute("currentUser");
            return user.isAdmin() ? "redirect:/admin/dashboard" : "redirect:/user/dashboard";
        }
        if (!model.containsAttribute("signupDto")) {
            model.addAttribute("signupDto", new SignupDto());
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@Valid @ModelAttribute("signupDto") SignupDto signupDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        try {
            userService.registerUser(signupDto);
            redirectAttributes.addFlashAttribute("successMessage", "Account created successfully! Please log in.");
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("email", "error.email", ex.getMessage());
            return "signup";
        }
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "redirect", required = false) String redirect,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model,
                                HttpSession session) {
        if (session.getAttribute("currentUser") != null) {
            User user = (User) session.getAttribute("currentUser");
            if (redirect != null && !redirect.trim().isEmpty() && !redirect.contains("login")) {
                return "redirect:" + redirect;
            }
            return user.isAdmin() ? "redirect:/admin/dashboard" : "redirect:/user/dashboard";
        }

        LoginDto loginDto = new LoginDto();
        if (redirect != null && !redirect.trim().isEmpty()) {
            loginDto.setRedirect(redirect);
        }
        if (!model.containsAttribute("loginDto")) {
            model.addAttribute("loginDto", loginDto);
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
                               BindingResult bindingResult,
                               HttpSession session,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        try {
            User user = userService.authenticate(loginDto.getEmail(), loginDto.getPassword());
            session.setAttribute("currentUser", user);

            String redirect = loginDto.getRedirect();
            if (redirect != null && !redirect.trim().isEmpty() && !redirect.contains("login")) {
                return "redirect:" + redirect;
            }

            if (user.isAdmin()) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/user/dashboard";
            }
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
}

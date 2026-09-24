package com.deloitte.flight.service;

import com.deloitte.flight.dto.SignupDto;
import com.deloitte.flight.model.User;

import java.util.Optional;

public interface UserService {
    User registerUser(SignupDto signupDto);
    User authenticate(String email, String password);
    boolean existsByEmail(String email);
    Optional<User> findById(Long userId);
}

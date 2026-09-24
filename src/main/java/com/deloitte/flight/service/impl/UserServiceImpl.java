package com.deloitte.flight.service.impl;

import com.deloitte.flight.dao.UserDao;
import com.deloitte.flight.dto.SignupDto;
import com.deloitte.flight.model.User;
import com.deloitte.flight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public User registerUser(SignupDto signupDto) {
        if (signupDto == null || signupDto.getEmail() == null) {
            throw new IllegalArgumentException("User registration details must be provided");
        }
        String cleanEmail = signupDto.getEmail().trim().toLowerCase();
        if (userDao.existsByEmail(cleanEmail)) {
            throw new IllegalArgumentException("An account with this email already exists");
        }

        User user = new User();
        user.setEmail(cleanEmail);
        user.setPassword(signupDto.getPassword()); // Can be enhanced with password encoder
        user.setRole(signupDto.getRole().toUpperCase());

        return userDao.save(user);
    }

    @Override
    public User authenticate(String email, String password) {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Email and password are required");
        }
        Optional<User> userOpt = userDao.findByEmail(email.trim().toLowerCase());
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        User user = userOpt.get();
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return user;
    }

    @Override
    public boolean existsByEmail(String email) {
        if (email == null) return false;
        return userDao.existsByEmail(email.trim().toLowerCase());
    }

    @Override
    public Optional<User> findById(Long userId) {
        if (userId == null) return Optional.empty();
        return userDao.findById(userId);
    }
}

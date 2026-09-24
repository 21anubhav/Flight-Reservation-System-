package com.deloitte.flight.dao;

import com.deloitte.flight.model.User;

import java.util.Optional;

public interface UserDao {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long userId);
    boolean existsByEmail(String email);
}

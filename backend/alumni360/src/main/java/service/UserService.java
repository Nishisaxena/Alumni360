package com.alumni360.service;

import com.alumni360.model.User;
import com.alumni360.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User register(User user) {
        // check existing
        Optional<User> existing = repo.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        // hash password
        user.setPasswordHash( encoder.encode(user.getPasswordHash()) );
        return repo.save(user);
    }
}


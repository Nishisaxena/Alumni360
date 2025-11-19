package com.alumni360.controller;

import com.alumni360.model.User;
import com.alumni360.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // allow React dev server
@Validated
public class RegistrationController {

    private final UserService userService;
    public RegistrationController(UserService userService) {
        this.UserService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        try {
            User saved = userService.register(user);
            // do not return password hash
            saved.setPasswordHash(null);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server error");
        }
    }
}


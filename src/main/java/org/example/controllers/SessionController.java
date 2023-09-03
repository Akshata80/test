package org.example.controllers;

import org.example.model.User;
import org.example.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SessionController {
    @Autowired
    private AuthService authService;

    @GetMapping("/user-data")
    public ResponseEntity<Object> getUserData() {
        Object userData = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> cachedData = authService.getAllData();
        return ResponseEntity.ok(userData);
    }
}


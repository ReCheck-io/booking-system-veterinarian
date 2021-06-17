package io.recheck.jobsapp.bookingvet.backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public Authentication login(Authentication authentication) {
        return authentication;
    }

}
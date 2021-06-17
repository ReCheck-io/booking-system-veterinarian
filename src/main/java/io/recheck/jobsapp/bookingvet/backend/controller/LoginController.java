package io.recheck.jobsapp.bookingvet.backend.controller;

import io.recheck.jobsapp.bookingvet.backend.dto.UsernamePasswordAuthenticationDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public UsernamePasswordAuthenticationDTO login(Authentication authentication) {
        return new UsernamePasswordAuthenticationDTO(authentication);
    }

}
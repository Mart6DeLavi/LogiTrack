package com.dataverse.authservice.controllers;

import com.dataverse.authservice.dtos.JwtRequestDto;
import com.dataverse.authservice.dtos.UserRegistrationDto;
import com.dataverse.authservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        return authService.createUser(userRegistrationDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody JwtRequestDto jwtRequestDto) {
        return authService.createAuthToken(jwtRequestDto);
    }

    @GetMapping("/info")
    public String info(Principal principal) {
        return principal.getName();
    }

}

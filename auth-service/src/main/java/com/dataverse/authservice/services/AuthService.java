package com.dataverse.authservice.services;

import com.dataverse.authservice.dtos.JwtRequestDto;
import com.dataverse.authservice.dtos.JwtResponseDto;
import com.dataverse.authservice.dtos.UserDto;
import com.dataverse.authservice.dtos.UserRegistrationDto;
import com.dataverse.authservice.entities.Role;
import com.dataverse.authservice.entities.User;
import com.dataverse.authservice.exceptions.AppError;
import com.dataverse.authservice.repositories.RoleRepository;
import com.dataverse.authservice.repositories.UserRepository;
import com.dataverse.authservice.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;


    private static final Logger logger = LoggerFactory.getLogger(PerformanceMonitorInterceptor.class);

    public ResponseEntity<?> createAuthToken(JwtRequestDto authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Optional<User> userOptional = userRepository.findUserByUsername(authRequest.getUsername());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String token = jwtTokenUtils.generationUserToken(userDetails, user.getRoles());
                logger.info("Token: {} for username: {} was generated", token, userDetails.getUsername());
                kafkaTemplate.send("auth-service", authRequest.getUsername(), token);
                logger.info("Message sent to kafka: {} with username: {}", token, authRequest.getUsername());
                return ResponseEntity.ok(new JwtResponseDto(token));
            } else {
                return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Invalid user type"), HttpStatus.BAD_REQUEST);
            }
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Uncorrected login or password"), HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> createUser(UserRegistrationDto userRegistrationDto) {
        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Passwords do not match"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findUserByUsername(userRegistrationDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "A User with the username " + userRegistrationDto.getUsername() + " already exists"), HttpStatus.BAD_REQUEST);
        }

        User user = userService.createNewUser(userRegistrationDto);
        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            throw new IllegalStateException("Role USER not found in the database");
        }
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);

        logger.info("Created user with: \nusername: {} \nemail: {}", user.getUsername(), user.getEmail());
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail(), "USER"));
    }
}

package com.joach27.urltoshort.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.joach27.urltoshort.dto.AuthRequest;
import com.joach27.urltoshort.dto.AuthResponse;
import com.joach27.urltoshort.repository.UserRepository;
import com.joach27.urltoshort.security.service.JwtService;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse authenticate(AuthRequest request) {
        // 1. Spring Security checks whether the password matches the hash in the database
        // If it doesn't match, a BadCredentialsException will be thrown automatically
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        // 2. If we reach this point, the user is legitimate. Fetch them.
        var user = userRepository.findByUsername(request.username())
                .orElseThrow();

        // 3. Generate the token and return it
        var token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
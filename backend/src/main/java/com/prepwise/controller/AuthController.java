package com.prepwise.controller;

import com.prepwise.dto.AuthRequestDTO;
import com.prepwise.dto.AuthResponseDTO;
import com.prepwise.dto.RegisterRequestDTO;
import com.prepwise.entity.User;
import com.prepwise.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for authentication endpoints
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    
    private final AuthService authService;
    
    /**
     * Register new user
     * @param request registration request
     * @return authentication response with token
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        log.info("Register request received for email: {}", request.getEmail());
        AuthResponseDTO response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Login user
     * @param request login request
     * @return authentication response with token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
        log.info("Login request received for email: {}", request.getEmail());
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Refresh JWT token
     * @param authentication authenticated user
     * @return new token
     */
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(Authentication authentication) {
        log.info("Token refresh request from user: {}", authentication.getName());
        String newToken = authService.refreshToken(authentication.getName());
        return ResponseEntity.ok(newToken);
    }
}

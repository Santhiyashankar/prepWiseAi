package com.prepwise.service;

import com.prepwise.dto.AuthRequestDTO;
import com.prepwise.dto.AuthResponseDTO;
import com.prepwise.dto.RegisterRequestDTO;
import com.prepwise.entity.User;
import com.prepwise.repository.UserRepository;
import com.prepwise.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for authentication operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    
    /**
     * Register new user
     * @param request registration request
     * @return authentication response with token
     */
    public AuthResponseDTO register(RegisterRequestDTO request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Registration attempt with existing email: {}", request.getEmail());
            throw new RuntimeException("Email already registered");
        }
        
        // Create new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setIsActive(true);
        
        User savedUser = userRepository.save(user);
        log.info("New user registered with email: {}", request.getEmail());
        
        // Generate token
        String token = jwtTokenProvider.generateToken(savedUser.getEmail());
        
        return AuthResponseDTO.builder()
                .token(token)
                .user(userService.convertToDTO(savedUser))
                .message("User registered successfully")
                .build();
    }
    
    /**
     * Login user
     * @param request login request
     * @return authentication response with token
     */
    public AuthResponseDTO login(AuthRequestDTO request) {
        try {
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            
            // Get user
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Generate token
            String token = jwtTokenProvider.generateToken(user.getEmail());
            
            log.info("User logged in successfully: {}", request.getEmail());
            
            return AuthResponseDTO.builder()
                    .token(token)
                    .user(userService.convertToDTO(user))
                    .message("Login successful")
                    .build();
                    
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", request.getEmail());
            throw new RuntimeException("Invalid email or password");
        }
    }
    
    /**
     * Refresh JWT token
     * @param email user email
     * @return new token
     */
    public String refreshToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        log.info("Token refreshed for user: {}", email);
        return jwtTokenProvider.generateToken(email);
    }
}

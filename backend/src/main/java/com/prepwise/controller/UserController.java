package com.prepwise.controller;

import com.prepwise.dto.UserDTO;
import com.prepwise.entity.User;
import com.prepwise.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for user endpoints
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    
    private final UserService userService;
    
    /**
     * Get user profile
     * @param userId user id
     * @return user details
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable Long userId) {
        log.info("Get user profile request for user id: {}", userId);
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(userService.convertToDTO(user));
    }
    
    /**
     * Update user profile
     * @param userId user id
     * @param userDTO updated user data
     * @return updated user details
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UserDTO userDTO) {
        log.info("Update user profile request for user id: {}", userId);
        
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setBio(userDTO.getBio());
        user.setPhone(userDTO.getPhone());
        user.setProfilePhoto(userDTO.getProfilePhoto());
        
        User updatedUser = userService.updateUserProfile(userId, user);
        return ResponseEntity.ok(userService.convertToDTO(updatedUser));
    }
    
    /**
     * Get user dashboard statistics
     * @param userId user id
     * @return dashboard stats
     */
    @GetMapping("/{userId}/dashboard")
    public ResponseEntity<?> getDashboardStats(@PathVariable Long userId) {
        log.info("Get dashboard stats for user id: {}", userId);
        // TODO: Implement dashboard stats logic
        return ResponseEntity.ok(new Object());
    }
}

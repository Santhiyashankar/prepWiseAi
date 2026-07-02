package com.prepwise.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating interview requests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewRequestDTO {
    
    @NotNull(message = "Interview type is required")
    private String type; // HR, TECHNICAL, CODING
    
    @NotNull(message = "Difficulty is required")
    private String difficulty; // EASY, INTERMEDIATE, HARD
    
    private Integer duration;
}

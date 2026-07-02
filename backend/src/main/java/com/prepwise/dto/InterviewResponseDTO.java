package com.prepwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for submitting interview responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResponseDTO {
    
    @NotNull(message = "Question ID is required")
    private Long questionId;
    
    @NotBlank(message = "Response is required")
    private String response;
    
    private Integer responseTimeSeconds;
}

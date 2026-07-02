package com.prepwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for requesting AI feedback
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequestDTO {
    
    @NotNull(message = "Interview ID is required")
    private Long interviewId;
    
    @NotNull(message = "Question ID is required")
    private Long questionId;
    
    @NotBlank(message = "User response is required")
    private String userResponse;
    
    @NotBlank(message = "Question text is required")
    private String question;
}

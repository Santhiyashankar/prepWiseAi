package com.prepwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for API error responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiErrorDTO {
    
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private String path;
}

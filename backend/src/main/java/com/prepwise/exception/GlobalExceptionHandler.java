package com.prepwise.exception;

import com.prepwise.dto.ApiErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    /**
     * Handle validation exceptions
     * @param ex MethodArgumentNotValidException
     * @param request WebRequest
     * @return validation error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        log.warn("Validation error: {}", errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Handle resource not found exceptions
     * @param ex RuntimeException
     * @param request WebRequest
     * @return error response
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorDTO> handleRuntimeException(
            RuntimeException ex,
            WebRequest request) {
        
        log.error("Runtime exception: {}", ex.getMessage());
        
        ApiErrorDTO errorDTO = ApiErrorDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Handle all other exceptions
     * @param ex Exception
     * @param request WebRequest
     * @return error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleGlobalException(
            Exception ex,
            WebRequest request) {
        
        log.error("Global exception: {}", ex.getMessage(), ex);
        
        ApiErrorDTO errorDTO = ApiErrorDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("An unexpected error occurred")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

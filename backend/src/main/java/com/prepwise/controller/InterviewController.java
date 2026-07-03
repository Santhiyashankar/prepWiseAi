package com.prepwise.controller;

import com.prepwise.dto.InterviewRequestDTO;
import com.prepwise.entity.Interview;
import com.prepwise.service.InterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for interview endpoints
 */
@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
@Slf4j
public class InterviewController {
    
    private final InterviewService interviewService;
    
    /**
     * Create new interview
     * @param userId user id
     * @param request interview request
     * @return created interview
     */
    @PostMapping
    public ResponseEntity<Interview> createInterview(
            @RequestParam Long userId,
            @Valid @RequestBody InterviewRequestDTO request) {
        log.info("Create interview request for user id: {}", userId);
        Interview interview = interviewService.createInterview(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(interview);
    }
    
    /**
     * Get interview by id
     * @param id interview id
     * @return interview details
     */
    @GetMapping("/{id}")
    public ResponseEntity<Interview> getInterview(@PathVariable Long id) {
        log.info("Get interview request for interview id: {}", id);
        Interview interview = interviewService.getInterviewById(id);
        return ResponseEntity.ok(interview);
    }
    
    /**
     * Get all interviews for a user
     * @param userId user id
     * @param page page number
     * @param size page size
     * @return page of interviews
     */
    @GetMapping
    public ResponseEntity<Page<Interview>> getUserInterviews(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Get user interviews for user id: {}", userId);
        Pageable pageable = PageRequest.of(page, size);
        Page<Interview> interviews = interviewService.getUserInterviews(userId, pageable);
        return ResponseEntity.ok(interviews);
    }
    
    /**
     * End interview
     * @param id interview id
     * @return updated interview
     */
    @PutMapping("/{id}/end")
    public ResponseEntity<Interview> endInterview(@PathVariable Long id) {
        log.info("End interview request for interview id: {}", id);
        Interview interview = interviewService.endInterview(id);
        return ResponseEntity.ok(interview);
    }
}

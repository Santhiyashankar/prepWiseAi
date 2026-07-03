package com.prepwise.controller;

import com.prepwise.dto.FeedbackRequestDTO;
import com.prepwise.entity.Feedback;
import com.prepwise.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for feedback endpoints
 */
@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {
    
    private final FeedbackService feedbackService;
    
    /**
     * Generate AI feedback for a response
     * @param request feedback request
     * @return generated feedback
     */
    @PostMapping("/generate")
    public ResponseEntity<Feedback> generateFeedback(@Valid @RequestBody FeedbackRequestDTO request) {
        log.info("Generate feedback request for interview id: {} question id: {}", 
                request.getInterviewId(), request.getQuestionId());
        Feedback feedback = feedbackService.generateAIFeedback(
                request.getInterviewId(),
                request.getQuestionId(),
                request.getUserResponse(),
                request.getQuestion()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(feedback);
    }
    
    /**
     * Get all feedback for an interview
     * @param interviewId interview id
     * @return list of feedback
     */
    @GetMapping("/{interviewId}")
    public ResponseEntity<List<Feedback>> getInterviewFeedback(@PathVariable Long interviewId) {
        log.info("Get feedback request for interview id: {}", interviewId);
        List<Feedback> feedbackList = feedbackService.getInterviewFeedback(interviewId);
        return ResponseEntity.ok(feedbackList);
    }
}

package com.prepwise.controller;

import com.prepwise.entity.InterviewQuestion;
import com.prepwise.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for interview questions endpoints
 */
@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {
    
    private final QuestionService questionService;
    
    /**
     * Get questions by type
     * @param type question type (HR, TECHNICAL, CODING)
     * @param difficulty difficulty level (optional)
     * @param limit number of questions (default 5)
     * @return list of questions
     */
    @GetMapping
    public ResponseEntity<List<InterviewQuestion>> getQuestions(
            @RequestParam String type,
            @RequestParam(required = false) String difficulty,
            @RequestParam(defaultValue = "5") int limit) {
        log.info("Get questions request for type: {} difficulty: {}", type, difficulty);
        
        List<InterviewQuestion> questions;
        if (difficulty != null) {
            questions = questionService.getRandomQuestionsByDifficulty(type, difficulty, limit);
        } else {
            questions = questionService.getRandomQuestions(type, limit);
        }
        return ResponseEntity.ok(questions);
    }
    
    /**
     * Get question by id
     * @param id question id
     * @return question details
     */
    @GetMapping("/{id}")
    public ResponseEntity<InterviewQuestion> getQuestion(@PathVariable Long id) {
        log.info("Get question request for question id: {}", id);
        InterviewQuestion question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }
}

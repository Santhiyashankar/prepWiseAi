package com.prepwise.service;

import com.prepwise.entity.Feedback;
import com.prepwise.entity.Interview;
import com.prepwise.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service class for feedback operations (AI-generated feedback)
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FeedbackService {
    
    private final FeedbackRepository feedbackRepository;
    private final InterviewService interviewService;
    private final OpenAIService openAIService;
    
    /**
     * Generate AI feedback for a response
     * @param interviewId interview id
     * @param questionId question id
     * @param userResponse user's response
     * @param question question text
     * @return generated feedback
     */
    public Feedback generateAIFeedback(Long interviewId, Long questionId, String userResponse, String question) {
        Interview interview = interviewService.getInterviewById(interviewId);
        
        // Call OpenAI API to generate feedback
        String aiFeedback = openAIService.generateFeedback(question, userResponse);
        String[] suggestions = openAIService.generateSuggestions(question, userResponse);
        BigDecimal score = openAIService.generateScore(question, userResponse);
        
        Feedback feedback = new Feedback();
        feedback.setInterview(interview);
        feedback.setQuestion(new com.prepwise.entity.InterviewQuestion());
        feedback.getQuestion().setId(questionId);
        feedback.setAiFeedback(aiFeedback);
        feedback.setScore(score);
        feedback.setSuggestions(String.join(",", suggestions));
        feedback.setFeedbackSource(Feedback.FeedbackSource.AI);
        
        Feedback savedFeedback = feedbackRepository.save(feedback);
        log.info("AI feedback generated for interview id: {} and question id: {}", interviewId, questionId);
        
        return savedFeedback;
    }
    
    /**
     * Get all feedback for an interview
     * @param interviewId interview id
     * @return list of feedback
     */
    public List<Feedback> getInterviewFeedback(Long interviewId) {
        return feedbackRepository.findByInterviewId(interviewId);
    }
    
    /**
     * Get feedback by interview and question
     * @param interviewId interview id
     * @param questionId question id
     * @return list of feedback
     */
    public List<Feedback> getFeedbackByQuestion(Long interviewId, Long questionId) {
        return feedbackRepository.findByInterviewIdAndQuestionId(interviewId, questionId);
    }
}

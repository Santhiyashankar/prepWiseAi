package com.prepwise.service;

import com.prepwise.entity.PerformanceAnalytics;
import com.prepwise.entity.User;
import com.prepwise.repository.PerformanceAnalyticsRepository;
import com.prepwise.repository.UserRepository;
import com.prepwise.repository.UserScoreRepository;
import com.prepwise.repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Service class for performance analytics operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PerformanceAnalyticsService {
    
    private final PerformanceAnalyticsRepository analyticsRepository;
    private final UserRepository userRepository;
    private final UserScoreRepository scoreRepository;
    private final InterviewRepository interviewRepository;
    
    /**
     * Get or create performance analytics for a user
     * @param userId user id
     * @return performance analytics
     */
    public PerformanceAnalytics getUserAnalytics(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        Optional<PerformanceAnalytics> existing = analyticsRepository.findByUserId(userId);
        if (existing.isPresent()) {
            return existing.get();
        }
        
        // Create new analytics
        PerformanceAnalytics analytics = new PerformanceAnalytics();
        analytics.setUser(user);
        analytics.setTotalInterviews(0);
        analytics.setTotalQuestionsAnswered(0);
        analytics.setAverageScore(BigDecimal.ZERO);
        
        return analyticsRepository.save(analytics);
    }
    
    /**
     * Update analytics after interview completion
     * @param userId user id
     */
    public void updateAnalyticsAfterInterview(Long userId) {
        PerformanceAnalytics analytics = getUserAnalytics(userId);
        
        // Update total interviews
        long completedInterviews = interviewRepository.countCompletedInterviews(userId);
        analytics.setTotalInterviews((int) completedInterviews);
        
        // Update average score
        Double avgScore = scoreRepository.getAverageScore(userId);
        if (avgScore != null) {
            analytics.setAverageScore(new BigDecimal(String.valueOf(avgScore)));
        }
        
        analyticsRepository.save(analytics);
        log.info("Analytics updated for user id: {}", userId);
    }
}

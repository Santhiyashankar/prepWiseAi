package com.prepwise.service;

import com.prepwise.dto.InterviewRequestDTO;
import com.prepwise.entity.Interview;
import com.prepwise.entity.InterviewQuestion;
import com.prepwise.entity.User;
import com.prepwise.repository.InterviewQuestionRepository;
import com.prepwise.repository.InterviewRepository;
import com.prepwise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for interview operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InterviewService {
    
    private final InterviewRepository interviewRepository;
    private final InterviewQuestionRepository questionRepository;
    private final UserRepository userRepository;
    
    /**
     * Create new interview
     * @param userId user id
     * @param request interview request
     * @return created interview
     */
    public Interview createInterview(Long userId, InterviewRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        Interview interview = new Interview();
        interview.setUser(user);
        interview.setInterviewType(Interview.InterviewType.valueOf(request.getType()));
        interview.setDifficulty(InterviewQuestion.Difficulty.valueOf(request.getDifficulty()));
        interview.setDurationMinutes(request.getDuration() != null ? request.getDuration() : 30);
        interview.setStatus(Interview.InterviewStatus.IN_PROGRESS);
        interview.setStartTime(LocalDateTime.now());
        
        Interview savedInterview = interviewRepository.save(interview);
        log.info("Interview created with id: {} for user id: {}", savedInterview.getId(), userId);
        
        return savedInterview;
    }
    
    /**
     * Get interview by id
     * @param id interview id
     * @return interview
     */
    public Interview getInterviewById(Long id) {
        return interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + id));
    }
    
    /**
     * Get all interviews for user
     * @param userId user id
     * @param pageable pagination info
     * @return page of interviews
     */
    public Page<Interview> getUserInterviews(Long userId, Pageable pageable) {
        return interviewRepository.findByUserId(userId, pageable);
    }
    
    /**
     * Get interviews by type
     * @param userId user id
     * @param type interview type
     * @param pageable pagination info
     * @return page of interviews
     */
    public Page<Interview> getInterviewsByType(Long userId, String type, Pageable pageable) {
        return interviewRepository.findByUserIdAndInterviewType(
                userId,
                Interview.InterviewType.valueOf(type),
                pageable
        );
    }
    
    /**
     * End interview
     * @param id interview id
     * @return updated interview
     */
    public Interview endInterview(Long id) {
        Interview interview = getInterviewById(id);
        interview.setEndTime(LocalDateTime.now());
        interview.setStatus(Interview.InterviewStatus.COMPLETED);
        
        log.info("Interview ended with id: {}", id);
        return interviewRepository.save(interview);
    }
    
    /**
     * Get random questions for interview
     * @param type interview type
     * @param difficulty difficulty level
     * @param limit number of questions
     * @return list of questions
     */
    public List<InterviewQuestion> getQuestionsForInterview(String type, String difficulty, int limit) {
        return questionRepository.findRandomByTypeAndDifficulty(type, difficulty, limit);
    }
}

package com.prepwise.repository;

import com.prepwise.entity.InterviewResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for InterviewResponse entity
 */
@Repository
public interface InterviewResponseRepository extends JpaRepository<InterviewResponse, Long> {
    
    /**
     * Find all responses for an interview
     * @param interviewId interview id
     * @return list of responses
     */
    List<InterviewResponse> findByInterviewId(Long interviewId);
    
    /**
     * Find response by interview and question
     * @param interviewId interview id
     * @param questionId question id
     * @return Optional containing response if found
     */
    Optional<InterviewResponse> findByInterviewIdAndQuestionId(Long interviewId, Long questionId);
}

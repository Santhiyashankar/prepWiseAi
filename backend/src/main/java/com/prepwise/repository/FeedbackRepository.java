package com.prepwise.repository;

import com.prepwise.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Feedback entity
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    /**
     * Find all feedback for an interview
     * @param interviewId interview id
     * @return list of feedback
     */
    List<Feedback> findByInterviewId(Long interviewId);
    
    /**
     * Find feedback by interview and question
     * @param interviewId interview id
     * @param questionId question id
     * @return list of feedback
     */
    List<Feedback> findByInterviewIdAndQuestionId(Long interviewId, Long questionId);
}

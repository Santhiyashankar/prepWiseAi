package com.prepwise.repository;

import com.prepwise.entity.Interview;
import com.prepwise.entity.Interview.InterviewStatus;
import com.prepwise.entity.Interview.InterviewType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Interview entity
 */
@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    
    /**
     * Find all interviews for a user
     * @param userId user id
     * @param pageable pagination info
     * @return Page of interviews
     */
    Page<Interview> findByUserId(Long userId, Pageable pageable);
    
    /**
     * Find interviews by user and type
     * @param userId user id
     * @param type interview type
     * @param pageable pagination info
     * @return Page of interviews
     */
    Page<Interview> findByUserIdAndInterviewType(Long userId, InterviewType type, Pageable pageable);
    
    /**
     * Find interviews by user and status
     * @param userId user id
     * @param status interview status
     * @param pageable pagination info
     * @return Page of interviews
     */
    Page<Interview> findByUserIdAndStatus(Long userId, InterviewStatus status, Pageable pageable);
    
    /**
     * Count completed interviews for a user
     * @param userId user id
     * @return count of completed interviews
     */
    @Query("SELECT COUNT(i) FROM Interview i WHERE i.user.id = :userId AND i.status = 'COMPLETED'")
    long countCompletedInterviews(@Param("userId") Long userId);
}

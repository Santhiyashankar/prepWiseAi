package com.prepwise.repository;

import com.prepwise.entity.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for UserScore entity
 */
@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
    
    /**
     * Find all scores for a user
     * @param userId user id
     * @return list of scores
     */
    List<UserScore> findByUserId(Long userId);
    
    /**
     * Find all scores for an interview
     * @param interviewId interview id
     * @return list of scores
     */
    List<UserScore> findByInterviewId(Long interviewId);
    
    /**
     * Calculate average score for a user
     * @param userId user id
     * @return average score
     */
    @Query("SELECT AVG(s.score) FROM UserScore s WHERE s.user.id = :userId")
    Double getAverageScore(@Param("userId") Long userId);
}

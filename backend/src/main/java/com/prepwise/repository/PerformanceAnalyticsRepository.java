package com.prepwise.repository;

import com.prepwise.entity.PerformanceAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for PerformanceAnalytics entity
 */
@Repository
public interface PerformanceAnalyticsRepository extends JpaRepository<PerformanceAnalytics, Long> {
    
    /**
     * Find performance analytics by user id
     * @param userId user id
     * @return Optional containing analytics if found
     */
    Optional<PerformanceAnalytics> findByUserId(Long userId);
}

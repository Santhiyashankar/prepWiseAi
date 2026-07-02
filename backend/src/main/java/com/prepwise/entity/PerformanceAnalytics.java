package com.prepwise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PerformanceAnalytics entity for user performance tracking
 */
@Entity
@Table(name = "performance_analytics", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceAnalytics {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @Column(name = "total_interviews")
    private Integer totalInterviews = 0;
    
    @Column(name = "total_questions_answered")
    private Integer totalQuestionsAnswered = 0;
    
    @Column(name = "average_score", precision = 5, scale = 2)
    private BigDecimal averageScore = BigDecimal.ZERO;
    
    @Column(name = "best_score", precision = 5, scale = 2)
    private BigDecimal bestScore;
    
    @Column(name = "worst_score", precision = 5, scale = 2)
    private BigDecimal worstScore;
    
    @Column(name = "improvement_trend", length = 50)
    private String improvementTrend;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated = LocalDateTime.now();
}

package com.prepwise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Feedback entity representing AI-generated feedback for interview responses
 */
@Entity
@Table(name = "feedback", indexes = {
    @Index(name = "idx_interview_id", columnList = "interview_id"),
    @Index(name = "idx_question_id", columnList = "question_id"),
    @Index(name = "idx_feedback_interview", columnList = "interview_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private InterviewQuestion question;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id")
    private InterviewResponse response;
    
    @Column(name = "ai_feedback", nullable = false, columnDefinition = "LONGTEXT")
    private String aiFeedback;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal score;
    
    @Column(columnDefinition = "JSON")
    private String strengths;
    
    @Column(columnDefinition = "JSON")
    private String improvements;
    
    @Column(columnDefinition = "JSON")
    private String suggestions;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "feedback_source")
    private FeedbackSource feedbackSource = FeedbackSource.AI;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum FeedbackSource {
        AI, MANUAL
    }
}

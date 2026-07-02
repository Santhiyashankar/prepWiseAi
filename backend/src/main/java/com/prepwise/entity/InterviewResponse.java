package com.prepwise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * InterviewResponse entity representing user responses to interview questions
 */
@Entity
@Table(name = "interview_responses", indexes = {
    @Index(name = "idx_interview_id", columnList = "interview_id"),
    @Index(name = "idx_question_id", columnList = "question_id"),
    @Index(name = "idx_responses_interview", columnList = "interview_id")
},
uniqueConstraints = {
    @UniqueConstraint(name = "unique_interview_question", columnNames = {"interview_id", "question_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResponse {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private InterviewQuestion question;
    
    @Column(name = "user_response", nullable = false, columnDefinition = "LONGTEXT")
    private String userResponse;
    
    @Column(name = "response_time_seconds")
    private Integer responseTimeSeconds;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

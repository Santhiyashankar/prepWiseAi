package com.prepwise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * InterviewQuestion entity representing interview questions bank
 */
@Entity
@Table(name = "interview_questions", indexes = {
    @Index(name = "idx_type", columnList = "question_type"),
    @Index(name = "idx_difficulty", columnList = "difficulty"),
    @Index(name = "idx_category", columnList = "category")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewQuestion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private QuestionType questionType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;
    
    @Column(length = 100)
    private String category;
    
    @Column(columnDefinition = "JSON")
    private String hints;
    
    @Column(name = "expected_keywords", columnDefinition = "JSON")
    private String expectedKeywords;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum QuestionType {
        HR, TECHNICAL, CODING
    }
    
    public enum Difficulty {
        EASY, INTERMEDIATE, HARD
    }
}

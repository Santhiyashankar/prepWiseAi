-- PrepWise AI Database Schema
-- MySQL 8.0+

CREATE DATABASE IF NOT EXISTS prepwise_ai CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE prepwise_ai;

-- Users Table
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    profile_photo LONGTEXT,
    bio TEXT,
    phone VARCHAR(20),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Interview Questions Bank
CREATE TABLE interview_questions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    question_type ENUM('HR', 'TECHNICAL', 'CODING') NOT NULL,
    difficulty ENUM('EASY', 'INTERMEDIATE', 'HARD') NOT NULL,
    question TEXT NOT NULL,
    category VARCHAR(100),
    hints JSON,
    expected_keywords JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_type (question_type),
    INDEX idx_difficulty (difficulty),
    INDEX idx_category (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Interviews Table
CREATE TABLE interviews (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    interview_type ENUM('HR', 'TECHNICAL', 'CODING') NOT NULL,
    difficulty ENUM('EASY', 'INTERMEDIATE', 'HARD') NOT NULL,
    duration_minutes INT,
    status ENUM('SCHEDULED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') DEFAULT 'SCHEDULED',
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    total_score DECIMAL(5, 2),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Interview Responses Table
CREATE TABLE interview_responses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    interview_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    user_response LONGTEXT NOT NULL,
    response_time_seconds INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (interview_id) REFERENCES interviews(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES interview_questions(id) ON DELETE RESTRICT,
    INDEX idx_interview_id (interview_id),
    INDEX idx_question_id (question_id),
    UNIQUE KEY unique_interview_question (interview_id, question_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Feedback Table
CREATE TABLE feedback (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    interview_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    response_id BIGINT,
    ai_feedback LONGTEXT NOT NULL,
    score DECIMAL(5, 2),
    strengths JSON,
    improvements JSON,
    suggestions JSON,
    feedback_source ENUM('AI', 'MANUAL') DEFAULT 'AI',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (interview_id) REFERENCES interviews(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES interview_questions(id) ON DELETE RESTRICT,
    FOREIGN KEY (response_id) REFERENCES interview_responses(id) ON DELETE SET NULL,
    INDEX idx_interview_id (interview_id),
    INDEX idx_question_id (question_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- User Scores Table
CREATE TABLE user_scores (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    interview_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    score DECIMAL(5, 2) NOT NULL,
    max_score DECIMAL(5, 2) DEFAULT 100,
    category VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (interview_id) REFERENCES interviews(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES interview_questions(id) ON DELETE RESTRICT,
    INDEX idx_user_id (user_id),
    INDEX idx_interview_id (interview_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Performance Analytics Table
CREATE TABLE performance_analytics (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE,
    total_interviews INT DEFAULT 0,
    total_questions_answered INT DEFAULT 0,
    average_score DECIMAL(5, 2) DEFAULT 0,
    best_score DECIMAL(5, 2),
    worst_score DECIMAL(5, 2),
    improvement_trend VARCHAR(50),
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- JWT Tokens Table (for token blacklisting if needed)
CREATE TABLE jwt_tokens (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    token VARCHAR(500) NOT NULL,
    is_revoked BOOLEAN DEFAULT FALSE,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_expires_at (expires_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create indexes for better query performance
CREATE INDEX idx_interviews_user_status ON interviews(user_id, status);
CREATE INDEX idx_interviews_user_created ON interviews(user_id, created_at);
CREATE INDEX idx_responses_interview ON interview_responses(interview_id);
CREATE INDEX idx_feedback_interview ON feedback(interview_id);
CREATE INDEX idx_scores_user_interview ON user_scores(user_id, interview_id);

-- Triggers for updating timestamps
DELIMITER //

CREATE TRIGGER users_update_timestamp
BEFORE UPDATE ON users
FOR EACH ROW
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;
END//

CREATE TRIGGER interviews_update_timestamp
BEFORE UPDATE ON interviews
FOR EACH ROW
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;
END//

CREATE TRIGGER questions_update_timestamp
BEFORE UPDATE ON interview_questions
FOR EACH ROW
BEGIN
    SET NEW.updated_at = CURRENT_TIMESTAMP;
END//

DELIMITER ;
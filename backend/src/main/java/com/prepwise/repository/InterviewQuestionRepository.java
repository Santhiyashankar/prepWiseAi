package com.prepwise.repository;

import com.prepwise.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for InterviewQuestion entity
 */
@Repository
public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {
    
    /**
     * Find questions by type
     * @param type question type
     * @return list of questions
     */
    List<InterviewQuestion> findByQuestionType(InterviewQuestion.QuestionType type);
    
    /**
     * Find questions by type and difficulty
     * @param type question type
     * @param difficulty difficulty level
     * @return list of questions
     */
    List<InterviewQuestion> findByQuestionTypeAndDifficulty(
        InterviewQuestion.QuestionType type,
        InterviewQuestion.Difficulty difficulty
    );
    
    /**
     * Find random questions by type with limit
     * @param type question type
     * @param limit number of questions
     * @return list of random questions
     */
    @Query(value = "SELECT * FROM interview_questions WHERE question_type = :type ORDER BY RAND() LIMIT :limit",
           nativeQuery = true)
    List<InterviewQuestion> findRandomByType(
        @Param("type") String type,
        @Param("limit") int limit
    );
    
    /**
     * Find random questions by type and difficulty
     * @param type question type
     * @param difficulty difficulty level
     * @param limit number of questions
     * @return list of random questions
     */
    @Query(value = "SELECT * FROM interview_questions WHERE question_type = :type AND difficulty = :difficulty ORDER BY RAND() LIMIT :limit",
           nativeQuery = true)
    List<InterviewQuestion> findRandomByTypeAndDifficulty(
        @Param("type") String type,
        @Param("difficulty") String difficulty,
        @Param("limit") int limit
    );
}

package com.prepwise.service;

import com.prepwise.entity.InterviewQuestion;
import com.prepwise.repository.InterviewQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for interview questions operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class QuestionService {
    
    private final InterviewQuestionRepository questionRepository;
    
    /**
     * Get question by id
     * @param id question id
     * @return question
     */
    public InterviewQuestion getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }
    
    /**
     * Get questions by type
     * @param type question type
     * @return list of questions
     */
    public List<InterviewQuestion> getQuestionsByType(String type) {
        return questionRepository.findByQuestionType(
                InterviewQuestion.QuestionType.valueOf(type)
        );
    }
    
    /**
     * Get questions by type and difficulty
     * @param type question type
     * @param difficulty difficulty level
     * @return list of questions
     */
    public List<InterviewQuestion> getQuestionsByTypeAndDifficulty(String type, String difficulty) {
        return questionRepository.findByQuestionTypeAndDifficulty(
                InterviewQuestion.QuestionType.valueOf(type),
                InterviewQuestion.Difficulty.valueOf(difficulty)
        );
    }
    
    /**
     * Get random questions
     * @param type question type
     * @param limit number of questions
     * @return list of random questions
     */
    public List<InterviewQuestion> getRandomQuestions(String type, int limit) {
        return questionRepository.findRandomByType(type, limit);
    }
    
    /**
     * Get random questions by difficulty
     * @param type question type
     * @param difficulty difficulty level
     * @param limit number of questions
     * @return list of random questions
     */
    public List<InterviewQuestion> getRandomQuestionsByDifficulty(String type, String difficulty, int limit) {
        return questionRepository.findRandomByTypeAndDifficulty(type, difficulty, limit);
    }
}

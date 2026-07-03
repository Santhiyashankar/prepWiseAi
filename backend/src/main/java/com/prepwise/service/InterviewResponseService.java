package com.prepwise.service;

import com.prepwise.entity.Interview;
import com.prepwise.entity.InterviewResponse;
import com.prepwise.repository.InterviewResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for interview responses operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InterviewResponseService {
    
    private final InterviewResponseRepository responseRepository;
    private final InterviewService interviewService;
    
    /**
     * Submit interview response
     * @param interviewId interview id
     * @param questionId question id
     * @param response response text
     * @param responseTime response time in seconds
     * @return created response
     */
    public InterviewResponse submitResponse(Long interviewId, Long questionId, String response, Integer responseTime) {
        Interview interview = interviewService.getInterviewById(interviewId);
        
        InterviewResponse interviewResponse = new InterviewResponse();
        interviewResponse.setInterview(interview);
        interviewResponse.setQuestion(new com.prepwise.entity.InterviewQuestion());
        interviewResponse.getQuestion().setId(questionId);
        interviewResponse.setUserResponse(response);
        interviewResponse.setResponseTimeSeconds(responseTime);
        
        InterviewResponse savedResponse = responseRepository.save(interviewResponse);
        log.info("Response submitted for interview id: {} and question id: {}", interviewId, questionId);
        
        return savedResponse;
    }
    
    /**
     * Get all responses for an interview
     * @param interviewId interview id
     * @return list of responses
     */
    public List<InterviewResponse> getInterviewResponses(Long interviewId) {
        return responseRepository.findByInterviewId(interviewId);
    }
    
    /**
     * Get response by interview and question
     * @param interviewId interview id
     * @param questionId question id
     * @return response if found
     */
    public InterviewResponse getResponse(Long interviewId, Long questionId) {
        return responseRepository.findByInterviewIdAndQuestionId(interviewId, questionId)
                .orElse(null);
    }
}

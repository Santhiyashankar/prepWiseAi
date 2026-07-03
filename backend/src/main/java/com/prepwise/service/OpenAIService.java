package com.prepwise.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service class for OpenAI API operations
 */
@Service
@Slf4j
public class OpenAIService {
    
    @Value("${openai.api.key}")
    private String apiKey;
    
    @Value("${openai.model:gpt-3.5-turbo}")
    private String model;
    
    /**
     * Generate feedback using OpenAI API
     * @param question interview question
     * @param userResponse user's response
     * @return AI-generated feedback
     */
    public String generateFeedback(String question, String userResponse) {
        try {
            OpenAiService service = new OpenAiService(apiKey);
            
            List<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), 
                    "You are an expert interview coach. Provide constructive and encouraging feedback on interview answers."));
            messages.add(new ChatMessage(ChatMessageRole.USER.value(),
                    String.format("Question: %s\n\nAnswer: %s\n\nProvide detailed feedback on this answer.", question, userResponse)));
            
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(model)
                    .messages(messages)
                    .maxTokens(500)
                    .build();
            
            String response = service.createChatCompletion(request).getChoices().get(0).getMessage().getContent();
            log.info("AI feedback generated successfully");
            return response;
            
        } catch (Exception e) {
            log.error("Error generating AI feedback: {}", e.getMessage());
            return "Unable to generate feedback at the moment. Please try again later.";
        }
    }
    
    /**
     * Generate suggestions using OpenAI API
     * @param question interview question
     * @param userResponse user's response
     * @return array of suggestions
     */
    public String[] generateSuggestions(String question, String userResponse) {
        try {
            OpenAiService service = new OpenAiService(apiKey);
            
            List<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(),
                    "You are an expert interview coach. Provide 3-5 specific suggestions to improve the answer."));
            messages.add(new ChatMessage(ChatMessageRole.USER.value(),
                    String.format("Question: %s\n\nAnswer: %s\n\nProvide specific suggestions as a comma-separated list.", question, userResponse)));
            
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(model)
                    .messages(messages)
                    .maxTokens(300)
                    .build();
            
            String response = service.createChatCompletion(request).getChoices().get(0).getMessage().getContent();
            return response.split(",");
            
        } catch (Exception e) {
            log.error("Error generating suggestions: {}", e.getMessage());
            return new String[]{"Try to provide more specific examples", "Add more technical details"};
        }
    }
    
    /**
     * Generate score using OpenAI API
     * @param question interview question
     * @param userResponse user's response
     * @return score as BigDecimal
     */
    public BigDecimal generateScore(String question, String userResponse) {
        try {
            OpenAiService service = new OpenAiService(apiKey);
            
            List<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(),
                    "You are an expert interviewer. Rate the answer on a scale of 0-100. Return only the numerical score."));
            messages.add(new ChatMessage(ChatMessageRole.USER.value(),
                    String.format("Question: %s\n\nAnswer: %s\n\nProvide only a numerical score between 0 and 100.", question, userResponse)));
            
            ChatCompletionRequest request = ChatCompletionRequest.builder()
                    .model(model)
                    .messages(messages)
                    .maxTokens(10)
                    .build();
            
            String response = service.createChatCompletion(request).getChoices().get(0).getMessage().getContent();
            double score = Double.parseDouble(response.trim());
            return new BigDecimal(String.valueOf(score / 100)); // Convert to 0-1 scale
            
        } catch (Exception e) {
            log.error("Error generating score: {}", e.getMessage());
            return new BigDecimal("0.75"); // Default score
        }
    }
}

package com.prepwise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Main Spring Boot Application class for PrepWise AI
 * Full-Stack AI Interview Preparation Platform
 */
@SpringBootApplication
public class PrepWiseAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrepWiseAiApplication.class, args);
    }

    /**
     * Bean for password encoding using BCrypt
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

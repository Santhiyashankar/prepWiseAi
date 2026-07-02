-- Sample Data for PrepWise AI

USE prepwise_ai;

-- Sample Users
INSERT INTO users (email, password, first_name, last_name, bio, phone, is_active) VALUES
('john.doe@example.com', '$2a$10$slYQmyNdGzin7olVN3p5Be9DjH.P5ZbIvQaDQbqbS/G.bcHxFmuzW', 'John', 'Doe', 'Software Engineer', '+1-555-0001', TRUE),
('jane.smith@example.com', '$2a$10$slYQmyNdGzin7olVN3p5Be9DjH.P5ZbIvQaDQbqbS/G.bcHxFmuzW', 'Jane', 'Smith', 'Full Stack Developer', '+1-555-0002', TRUE),
('alex.johnson@example.com', '$2a$10$slYQmyNdGzin7olVN3p5Be9DjH.P5ZbIvQaDQbqbS/G.bcHxFmuzW', 'Alex', 'Johnson', 'Data Scientist', '+1-555-0003', TRUE);

-- Sample HR Questions
INSERT INTO interview_questions (question_type, difficulty, question, category, hints, expected_keywords) VALUES
('HR', 'EASY', 'Tell us about yourself and your professional background.', 'Introduction', 
 '["Start with your name and current role", "Mention your key achievements", "Connect to the job position"]',
 '["Experience", "Skills", "Achievements", "Relevant"]'),

('HR', 'EASY', 'What are your greatest strengths?', 'Self Assessment',
 '["Think of 2-3 key strengths", "Provide examples", "Link to job requirements"]',
 '["Technical skills", "Leadership", "Communication", "Problem-solving"]'),

('HR', 'INTERMEDIATE', 'Tell us about a time when you faced a challenge at work. How did you overcome it?', 'Problem Solving',
 '["Use STAR method (Situation, Task, Action, Result)", "Be specific with details", "Show learning outcome"]',
 '["Challenge", "Overcome", "Solution", "Result", "Learning"]'),

('HR', 'INTERMEDIATE', 'Why do you want to work for our company?', 'Company Interest',
 '["Research the company first", "Mention specific projects or values", "Connect your skills to their needs"]',
 '["Company values", "Projects", "Culture", "Growth"]'),

('HR', 'HARD', 'Describe a situation where you had to work with a difficult team member or manager.', 'Teamwork',
 '["Be diplomatic and professional", "Show empathy", "Highlight resolution"]',
 '["Communication", "Conflict resolution", "Respect", "Professionalism"]');

-- Sample Technical Questions
INSERT INTO interview_questions (question_type, difficulty, question, category, hints, expected_keywords) VALUES
('TECHNICAL', 'EASY', 'What is REST API and what are its key principles?', 'Web Services',
 '["Explain architectural style", "Mention HTTP methods", "Talk about resources"]',
 '["REST", "HTTP", "Stateless", "Resources", "Methods"]'),

('TECHNICAL', 'EASY', 'Explain the difference between SQL and NoSQL databases.', 'Databases',
 '["Structure differences", "Use cases", "Scalability", "Performance"]',
 '["Structured", "Flexible", "ACID", "BASE", "Scalability"]'),

('TECHNICAL', 'INTERMEDIATE', 'What is the MVC architecture pattern?', 'Architecture',
 '["Explain Model, View, Controller", "Their responsibilities", "Communication flow"]',
 '["Model", "View", "Controller", "Separation", "Concerns"]'),

('TECHNICAL', 'INTERMEDIATE', 'How does JWT (JSON Web Token) authentication work?', 'Security',
 '["Explain token structure", "Header, Payload, Signature", "Token lifecycle"]',
 '["Token", "Signature", "Stateless", "Header", "Payload"]'),

('TECHNICAL', 'HARD', 'Explain the concept of microservices and its advantages/disadvantages.', 'Architecture',
 '["Define microservices", "Benefits and drawbacks", "Communication patterns"]',
 '["Scalability", "Independent", "Distributed", "Complexity", "Deployment"]');

-- Sample Coding Questions
INSERT INTO interview_questions (question_type, difficulty, question, category, hints, expected_keywords) VALUES
('CODING', 'EASY', 'Write a function to find the maximum number in an array.', 'Arrays',
 '["Iterate through array", "Compare elements", "Return maximum"]',
 '["Loop", "Comparison", "Array", "Return"]'),

('CODING', 'EASY', 'Reverse a string without using built-in functions.', 'Strings',
 '["Use character array", "Two pointer approach", "Swap elements"]',
 '["Iteration", "Swap", "Characters", "Loop"]'),

('CODING', 'INTERMEDIATE', 'Implement a function to find if a number is a palindrome.', 'Numbers',
 '["Compare original with reversed", "Use modulo operator", "Compare digits"]',
 '["Palindrome", "Digits", "Comparison", "Logic"]'),

('CODING', 'INTERMEDIATE', 'Write a function to merge two sorted arrays into one sorted array.', 'Arrays',
 '["Use two pointers", "Compare elements", "Place smaller element first"]',
 '["Merge", "Sorted", "Pointers", "Comparison"]'),

('CODING', 'HARD', 'Implement a binary search tree and write functions to insert and search.', 'Data Structures',
 '["Create node structure", "Implement insertion logic", "Implement search logic", "Handle recursion"]',
 '["Tree", "Binary", "Node", "Recursion", "Insert", "Search"]');

-- Sample Interviews
INSERT INTO interviews (user_id, interview_type, difficulty, duration_minutes, status, start_time, end_time, total_score) VALUES
(1, 'TECHNICAL', 'INTERMEDIATE', 30, 'COMPLETED', '2024-01-10 10:00:00', '2024-01-10 10:30:00', 82.5),
(1, 'HR', 'EASY', 20, 'COMPLETED', '2024-01-12 14:00:00', '2024-01-12 14:20:00', 88.0),
(2, 'CODING', 'HARD', 45, 'COMPLETED', '2024-01-11 09:00:00', '2024-01-11 09:45:00', 75.5),
(2, 'TECHNICAL', 'INTERMEDIATE', 30, 'IN_PROGRESS', '2024-01-15 10:00:00', NULL, NULL),
(3, 'HR', 'INTERMEDIATE', 30, 'COMPLETED', '2024-01-13 11:00:00', '2024-01-13 11:30:00', 90.0);

-- Sample Interview Responses
INSERT INTO interview_responses (interview_id, question_id, user_response, response_time_seconds) VALUES
(1, 11, 'REST API is an architectural style for designing networked applications using HTTP methods (GET, POST, PUT, DELETE) on resources identified by URIs. Key principles include statelessness, client-server separation, and resource-based URLs.', 120),
(1, 12, 'SQL databases are structured with predefined schemas and use ACID properties. NoSQL databases are flexible with dynamic schemas and use BASE properties. SQL is better for complex queries while NoSQL scales horizontally.', 150),
(2, 6, 'I handle stress by prioritizing tasks, breaking them into manageable parts, and communicating with my team about deadlines. I recently managed multiple projects by setting clear priorities.', 180),
(3, 18, 'function findMaxInArray(arr) { let max = arr[0]; for(let i = 1; i < arr.length; i++) { if(arr[i] > max) max = arr[i]; } return max; }', 300);

-- Sample Feedback
INSERT INTO feedback (interview_id, question_id, response_id, ai_feedback, score, strengths, improvements, suggestions) VALUES
(1, 11, 1, 'Excellent explanation! You covered the fundamental principles of REST API comprehensively. Your mention of HTTP methods and statelessness shows strong understanding.', 85.0,
 '["Clear explanation", "Covered key concepts", "Good examples"]',
 '["Could mention caching", "REST constraints could be deeper"]',
 '["Research REST maturity model", "Study real-world API design"]'),

(1, 12, 2, 'Very good response! You clearly understood the differences between SQL and NoSQL. Your explanation of ACID vs BASE properties is accurate and well-articulated.', 80.0,
 '["Accurate comparison", "Covered important aspects"]',
 '["Could mention specific examples", "Scalability discussion could be deeper"]',
 '["Learn about distributed databases", "Study CAP theorem"]');

-- Sample User Scores
INSERT INTO user_scores (user_id, interview_id, question_id, score, max_score, category) VALUES
(1, 1, 11, 85.0, 100, 'Accuracy'),
(1, 1, 12, 80.0, 100, 'Clarity'),
(1, 2, 6, 88.0, 100, 'Communication'),
(2, 3, 18, 75.5, 100, 'Problem Solving'),
(3, 5, 6, 90.0, 100, 'Communication');

-- Sample Performance Analytics
INSERT INTO performance_analytics (user_id, total_interviews, total_questions_answered, average_score, best_score, worst_score, improvement_trend) VALUES
(1, 2, 4, 85.0, 88.0, 80.0, 'STABLE'),
(2, 2, 5, 82.75, 90.0, 75.5, 'IMPROVING'),
(3, 1, 1, 90.0, 90.0, 90.0, 'STABLE');
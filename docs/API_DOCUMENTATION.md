# PrepWise AI - API Documentation

## Base URL
```
http://localhost:8080/api
```

## Authentication

All protected endpoints require JWT token in Authorization header:
```
Authorization: Bearer <jwt_token>
```

---

## 1. Authentication Endpoints

### 1.1 Register User

**Endpoint**: `POST /auth/register`

**Request Body**:
```json
{
  "email": "user@example.com",
  "password": "securePassword123",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Response** (200 OK):
```json
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "createdAt": "2024-01-15T10:30:00Z"
}
```

**Error Responses**:
- `400 Bad Request` - Invalid input
- `409 Conflict` - Email already exists

---

### 1.2 Login User

**Endpoint**: `POST /auth/login`

**Request Body**:
```json
{
  "email": "user@example.com",
  "password": "securePassword123"
}
```

**Response** (200 OK):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe"
  }
}
```

**Error Responses**:
- `401 Unauthorized` - Invalid credentials
- `404 Not Found` - User not found

---

### 1.3 Refresh Token

**Endpoint**: `POST /auth/refresh`

**Headers**:
```
Authorization: Bearer <old_jwt_token>
```

**Response** (200 OK):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

## 2. User Endpoints

### 2.1 Get User Profile

**Endpoint**: `GET /users/{userId}`

**Headers**:
```
Authorization: Bearer <jwt_token>
```

**Response** (200 OK):
```json
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "createdAt": "2024-01-15T10:30:00Z",
  "profilePhoto": "https://...",
  "bio": "Software Engineer"
}
```

---

### 2.2 Update User Profile

**Endpoint**: `PUT /users/{userId}`

**Request Body**:
```json
{
  "firstName": "Jane",
  "lastName": "Smith",
  "bio": "Full Stack Developer",
  "phone": "+1234567890"
}
```

**Response** (200 OK):
```json
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "Jane",
  "lastName": "Smith",
  "bio": "Full Stack Developer",
  "phone": "+1234567890"
}
```

---

### 2.3 Get User Dashboard Stats

**Endpoint**: `GET /users/{userId}/dashboard`

**Response** (200 OK):
```json
{
  "totalInterviews": 15,
  "averageScore": 78.5,
  "totalQuestions": 45,
  "recentInterviews": [
    {
      "id": 1,
      "type": "TECHNICAL",
      "score": 85,
      "date": "2024-01-15T10:30:00Z"
    }
  ]
}
```

---

## 3. Interview Endpoints

### 3.1 Create Interview

**Endpoint**: `POST /interviews`

**Request Body**:
```json
{
  "type": "TECHNICAL",
  "duration": 30,
  "difficulty": "INTERMEDIATE"
}
```

**Valid Types**: `HR`, `TECHNICAL`, `CODING`
**Valid Difficulties**: `EASY`, `INTERMEDIATE`, `HARD`

**Response** (201 Created):
```json
{
  "id": 1,
  "userId": 1,
  "type": "TECHNICAL",
  "duration": 30,
  "difficulty": "INTERMEDIATE",
  "startTime": "2024-01-15T10:30:00Z",
  "status": "IN_PROGRESS",
  "questions": [
    {
      "id": 1,
      "question": "What is REST API?",
      "order": 1
    }
  ]
}
```

---

### 3.2 Get Interview

**Endpoint**: `GET /interviews/{interviewId}`

**Response** (200 OK):
```json
{
  "id": 1,
  "userId": 1,
  "type": "TECHNICAL",
  "duration": 30,
  "difficulty": "INTERMEDIATE",
  "startTime": "2024-01-15T10:30:00Z",
  "endTime": null,
  "status": "IN_PROGRESS",
  "score": null,
  "questions": [
    {
      "id": 1,
      "question": "What is REST API?",
      "order": 1
    }
  ]
}
```

---

### 3.3 Get All Interviews for User

**Endpoint**: `GET /interviews`

**Query Parameters**:
- `userId` (required)
- `page` (optional, default: 0)
- `size` (optional, default: 10)
- `type` (optional, filter by type)
- `status` (optional, filter by status)

**Response** (200 OK):
```json
{
  "content": [
    {
      "id": 1,
      "type": "TECHNICAL",
      "score": 85,
      "startTime": "2024-01-15T10:30:00Z",
      "status": "COMPLETED"
    }
  ],
  "totalElements": 15,
  "totalPages": 2,
  "currentPage": 0
}
```

---

### 3.4 Submit Interview Response

**Endpoint**: `POST /interviews/{interviewId}/response`

**Request Body**:
```json
{
  "questionId": 1,
  "response": "User's answer to the question"
}
```

**Response** (201 Created):
```json
{
  "id": 1,
  "interviewId": 1,
  "questionId": 1,
  "response": "User's answer to the question",
  "createdAt": "2024-01-15T10:35:00Z"
}
```

---

### 3.5 End Interview

**Endpoint**: `PUT /interviews/{interviewId}/end`

**Request Body**:
```json
{
  "endTime": "2024-01-15T11:00:00Z"
}
```

**Response** (200 OK):
```json
{
  "id": 1,
  "status": "COMPLETED",
  "endTime": "2024-01-15T11:00:00Z",
  "score": 82
}
```

---

## 4. Questions Endpoints

### 4.1 Get Questions by Type

**Endpoint**: `GET /questions`

**Query Parameters**:
- `type` (required) - `HR`, `TECHNICAL`, or `CODING`
- `difficulty` (optional) - `EASY`, `INTERMEDIATE`, `HARD`
- `limit` (optional, default: 5)

**Response** (200 OK):
```json
{
  "questions": [
    {
      "id": 1,
      "type": "TECHNICAL",
      "difficulty": "INTERMEDIATE",
      "question": "What is REST API?",
      "category": "Web Services"
    }
  ]
}
```

---

### 4.2 Get Single Question

**Endpoint**: `GET /questions/{questionId}`

**Response** (200 OK):
```json
{
  "id": 1,
  "type": "TECHNICAL",
  "difficulty": "INTERMEDIATE",
  "question": "What is REST API?",
  "category": "Web Services",
  "hints": [
    "Think about HTTP methods",
    "Consider stateless communication"
  ]
}
```

---

## 5. Feedback Endpoints

### 5.1 Generate AI Feedback

**Endpoint**: `POST /feedback/generate`

**Request Body**:
```json
{
  "interviewId": 1,
  "questionId": 1,
  "userResponse": "User's answer to the question",
  "question": "What is REST API?"
}
```

**Response** (201 Created):
```json
{
  "id": 1,
  "interviewId": 1,
  "questionId": 1,
  "feedback": "Great answer! You covered the key points...",
  "score": 85,
  "suggestions": [
    "Try to provide more examples",
    "Include security considerations"
  ],
  "generatedAt": "2024-01-15T10:40:00Z"
}
```

---

### 5.2 Get Interview Feedback

**Endpoint**: `GET /feedback/{interviewId}`

**Response** (200 OK):
```json
{
  "interviewId": 1,
  "feedbacks": [
    {
      "id": 1,
      "questionId": 1,
      "feedback": "Great answer!...",
      "score": 85,
      "suggestions": []
    }
  ],
  "overallScore": 82,
  "generatedAt": "2024-01-15T10:45:00Z"
}
```

---

## 6. Performance/Score Endpoints

### 6.1 Get Interview Scores

**Endpoint**: `GET /scores/{userId}`

**Query Parameters**:
- `type` (optional) - filter by interview type
- `period` (optional) - `WEEK`, `MONTH`, `ALL`

**Response** (200 OK):
```json
{
  "userId": 1,
  "averageScore": 78.5,
  "totalInterviews": 15,
  "scores": [
    {
      "interviewId": 1,
      "type": "TECHNICAL",
      "score": 85,
      "date": "2024-01-15T10:30:00Z"
    }
  ]
}
```

---

### 6.2 Get Performance Analytics

**Endpoint**: `GET /analytics/{userId}/performance`

**Response** (200 OK):
```json
{
  "userId": 1,
  "totalInterviews": 15,
  "totalQuestions": 45,
  "averageScore": 78.5,
  "scoreByType": {
    "TECHNICAL": 82,
    "HR": 75,
    "CODING": 76
  },
  "scoreByDifficulty": {
    "EASY": 90,
    "INTERMEDIATE": 80,
    "HARD": 65
  },
  "improvementTrend": "IMPROVING"
}
```

---

## 7. Error Handling

All error responses follow this format:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Detailed error message",
  "timestamp": "2024-01-15T10:30:00Z",
  "path": "/api/auth/login"
}
```

### Common Error Codes

| Code | Error | Description |
|------|-------|-------------|
| 400 | Bad Request | Invalid input or validation error |
| 401 | Unauthorized | Missing or invalid JWT token |
| 403 | Forbidden | User not authorized for this resource |
| 404 | Not Found | Resource not found |
| 409 | Conflict | Resource already exists |
| 500 | Internal Server Error | Server error |

---

## 8. Pagination

Endpoints that return lists support pagination:

```json
{
  "content": [...],
  "totalElements": 100,
  "totalPages": 10,
  "currentPage": 0,
  "pageSize": 10,
  "hasNext": true,
  "hasPrevious": false
}
```

---

## 9. Rate Limiting

API implements rate limiting:
- **Limit**: 100 requests per minute per user
- **Header**: `X-RateLimit-Remaining`

---

## 10. Testing with cURL

### Register
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123",
    "firstName": "Test",
    "lastName": "User"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

### Get Profile (with token)
```bash
curl -X GET http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer <your_token_here>"
```

---

## 11. Postman Collection

Import the provided Postman collection:
`postman/PrepWiseAI-API.postman_collection.json`

Steps:
1. Open Postman
2. Click "Import"
3. Select the collection file
4. Set base URL: `http://localhost:8080/api`
5. Start testing!

---

**For more details, refer to the Setup Guide or contact support.**
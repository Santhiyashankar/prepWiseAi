# PrepWise AI - Full-Stack Interview Preparation Platform

A comprehensive AI-powered interview preparation platform built with React.js, Spring Boot, MySQL, and OpenAI API integration.

## 🎯 Features

- **User Authentication**: Secure registration and login with JWT tokens
- **Dashboard**: Personalized user dashboard with interview history
- **Mock Interviews**: Multiple interview types (HR, Technical, Coding)
- **AI-Powered Feedback**: Real-time feedback using OpenAI API
- **Performance Analysis**: Score tracking and performance metrics
- **User Profile**: Profile management and settings
- **Responsive UI**: Modern and clean user interface
- **Error Handling**: Comprehensive error handling and validation

## 🛠️ Tech Stack

### Backend
- **Framework**: Java Spring Boot 3.x
- **ORM**: Spring Data JPA
- **Database**: MySQL
- **Authentication**: Spring Security + JWT
- **Build Tool**: Maven
- **API Documentation**: Springdoc OpenAPI

### Frontend
- **Framework**: React.js 18.x with Vite
- **Routing**: React Router v6
- **HTTP Client**: Axios
- **Styling**: CSS3
- **State Management**: React Hooks

### External Services
- **AI Integration**: OpenAI API

## 📁 Project Structure

```
prepWiseAi/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/prepwise/
│   │   │   │   ├── config/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── entity/
│   │   │   │   ├── exception/
│   │   │   │   ├── repository/
│   │   │   │   ├── security/
│   │   │   │   ├── service/
│   │   │   │   └── PrepWiseAiApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   ├── pom.xml
│   └── .gitignore
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── styles/
│   │   ├── App.jsx
│   │   ├── main.jsx
│   │   └── index.css
│   ├── public/
│   ├── package.json
│   ├── vite.config.js
│   └── .gitignore
├── database/
│   ├── schema.sql
│   └── sample_data.sql
├── postman/
│   └── PrepWiseAI-API.postman_collection.json
├── docs/
│   ├── API_DOCUMENTATION.md
│   └── SETUP_GUIDE.md
└── README.md
```

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- MySQL 8.0+
- Maven 3.6+

### Backend Setup

1. **Navigate to backend directory**
   ```bash
   cd backend
   ```

2. **Configure database**
   - Update `src/main/resources/application.properties` with your MySQL credentials
   - Execute SQL scripts from `database/` folder

3. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

Backend will be available at `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure environment**
   - Create `.env` file with:
   ```
   VITE_API_URL=http://localhost:8080/api
   ```

4. **Run development server**
   ```bash
   npm run dev
   ```

Frontend will be available at `http://localhost:5173`

## 🔐 Environment Variables

### Backend (.env or application.properties)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/prepwise_ai
spring.datasource.username=root
spring.datasource.password=your_password
jwt.secret=your_jwt_secret_key
jwt.expiration=86400000
openai.api.key=your_openai_api_key
```

### Frontend (.env)
```
VITE_API_URL=http://localhost:8080/api
VITE_OPENAI_KEY=your_openai_api_key
```

## 📚 API Documentation

See [API_DOCUMENTATION.md](docs/API_DOCUMENTATION.md) for detailed endpoint documentation.

### Sample Endpoints

- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `GET /api/users/{userId}` - Get user profile
- `POST /api/interviews` - Create new interview
- `GET /api/interviews/{interviewId}` - Get interview details
- `POST /api/feedback` - Generate AI feedback

## 🧪 Testing

Import the Postman collection from `postman/PrepWiseAI-API.postman_collection.json` to test all API endpoints.

## 🗂️ Database Schema

The database includes the following main tables:
- `users` - User account information
- `interviews` - Interview records
- `interview_questions` - Question bank
- `interview_responses` - User responses
- `feedback` - AI-generated feedback
- `user_scores` - Performance scores

## 🔒 Security Features

- JWT-based authentication
- Password encryption using BCrypt
- CORS configuration
- Input validation and sanitization
- SQL injection prevention via JPA parameterized queries
- Secure API endpoints with role-based access control

## 📖 Setup Guide

See [SETUP_GUIDE.md](docs/SETUP_GUIDE.md) for detailed setup instructions.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📧 Support

For support, email support@prepwise.ai or open an issue in the repository.

## 🎓 Learning Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev)
- [OpenAI API Documentation](https://platform.openai.com/docs)
- [JWT Documentation](https://jwt.io)

---

**Happy Preparing! 🚀**
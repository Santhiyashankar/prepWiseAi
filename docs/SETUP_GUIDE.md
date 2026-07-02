# PrepWise AI - Complete Setup Guide

## Prerequisites

Before you begin, ensure you have the following installed:

### System Requirements
- **Java Development Kit (JDK)**: Version 17 or higher
- **Node.js**: Version 18 or higher (includes npm)
- **MySQL Server**: Version 8.0 or higher
- **Maven**: Version 3.6 or higher
- **Git**: For version control

### Verification
```bash
java -version      # Should show version 17+
node -v           # Should show version 18+
npm -v            # Should show version 8+
mysql --version   # Should show version 8.0+
mvn -v            # Should show version 3.6+
```

## Step 1: Database Setup

### 1.1 Create Database

```bash
mysql -u root -p
```

```sql
CREATE DATABASE prepwise_ai CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE prepwise_ai;
```

### 1.2 Execute Schema

```bash
mysql -u root -p prepwise_ai < database/schema.sql
mysql -u root -p prepwise_ai < database/sample_data.sql
```

### 1.3 Verify Tables

```sql
SHOW TABLES;
DESC users;
```

## Step 2: Backend Setup (Java Spring Boot)

### 2.1 Navigate to Backend

```bash
cd backend
```

### 2.2 Configure Application Properties

Edit `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/prepwise_ai?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_mysql_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# JWT Configuration
jwt.secret=your_super_secret_jwt_key_change_this_in_production_with_at_least_256_bits
jwt.expiration=86400000

# OpenAI Configuration
openai.api.key=your_openai_api_key_here
openai.model=gpt-3.5-turbo
openai.max-tokens=500

# CORS Configuration
cors.allowed-origins=http://localhost:5173,http://localhost:3000
cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
cors.allowed-headers=*
cors.allow-credentials=true

# Logging
logging.level.root=INFO
logging.level.com.prepwise=DEBUG
```

### 2.3 Build Backend

```bash
mvn clean install
```

### 2.4 Run Backend

```bash
mvn spring-boot:run
```

Or using Java directly:

```bash
java -jar target/prepwise-ai-1.0.0.jar
```

### 2.5 Verify Backend

Access API documentation at: `http://localhost:8080/api/swagger-ui.html`

Test health endpoint:
```bash
curl http://localhost:8080/api/health
```

## Step 3: Frontend Setup (React + Vite)

### 3.1 Navigate to Frontend

```bash
cd ../frontend
```

### 3.2 Install Dependencies

```bash
npm install
```

### 3.3 Configure Environment Variables

Create `.env` file in frontend directory:

```
VITE_API_URL=http://localhost:8080/api
VITE_APP_NAME=PrepWise AI
VITE_APP_VERSION=1.0.0
```

### 3.4 Run Development Server

```bash
npm run dev
```

### 3.5 Access Application

Open browser and navigate to: `http://localhost:5173`

## Step 4: OpenAI API Integration

### 4.1 Get API Key

1. Visit [OpenAI Platform](https://platform.openai.com/api-keys)
2. Create new API key
3. Copy the key

### 4.2 Configure in Backend

Update `application.properties`:

```properties
openai.api.key=sk-xxxxxxxxxxxxx
```

### 4.3 Add to Frontend (if needed)

Update `.env`:

```
VITE_OPENAI_KEY=sk-xxxxxxxxxxxxx
```

## Step 5: Testing APIs

### 5.1 Using Postman

1. Open Postman
2. Import collection: `postman/PrepWiseAI-API.postman_collection.json`
3. Set variables:
   - `base_url`: http://localhost:8080/api
   - `token`: (obtain from login endpoint)

### 5.2 Test Registration

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

### 5.3 Test Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

## Troubleshooting

### MySQL Connection Issues

**Problem**: `Connection refused`

**Solution**:
```bash
# Check MySQL status
mysql -u root -p -e "SELECT 1"

# If MySQL is not running, start it
# On Mac:
brew services start mysql

# On Linux:
sudo service mysql start

# On Windows:
net start MySQL80
```

### Port Already in Use

**Problem**: `Address already in use`

**Solution**:
```bash
# Find process using port
lsof -i :8080  # Backend
lsof -i :5173  # Frontend

# Kill process
kill -9 <PID>

# Or use different port in application.properties
server.port=8081
```

### Maven Build Failures

**Solution**:
```bash
# Clear Maven cache
rm -rf ~/.m2/repository

# Rebuild
mvn clean install -U
```

### Node Dependencies Issues

**Solution**:
```bash
# Clear npm cache
npm cache clean --force

# Reinstall
rm -rf node_modules package-lock.json
npm install
```

### JWT Token Issues

**Problem**: `Unauthorized 401`

**Solution**:
1. Ensure token is included in Authorization header
2. Verify token is not expired
3. Check JWT secret in application.properties

### CORS Issues

**Problem**: `Access to XMLHttpRequest blocked by CORS`

**Solution**:
1. Verify CORS configuration in backend
2. Ensure frontend URL is in allowed origins
3. Check browser console for detailed error

## Development Workflow

### 1. Start All Services

**Terminal 1 - Backend**
```bash
cd backend
mvn spring-boot:run
```

**Terminal 2 - Frontend**
```bash
cd frontend
npm run dev
```

**Terminal 3 - MySQL (if needed)**
```bash
mysql -u root -p
```

### 2. Code Changes

- Backend changes: Auto-reloaded with DevTools
- Frontend changes: Auto-reloaded with Vite HMR

### 3. Database Changes

- Modify entity classes
- Hibernate will auto-migrate with `ddl-auto=update`
- For schema changes, update schema.sql

## Production Deployment

### Backend

```bash
# Build production JAR
mvn clean package -DskipTests

# Run JAR
java -jar target/prepwise-ai-1.0.0.jar
```

### Frontend

```bash
# Build production bundle
npm run build

# Output: dist/ folder ready for deployment
```

## Next Steps

1. Review API documentation: [API_DOCUMENTATION.md](API_DOCUMENTATION.md)
2. Check database schema: `database/schema.sql`
3. Explore code structure: Follow the project structure above
4. Customize branding and features as needed
5. Add more interview questions to database
6. Implement additional AI features

## Additional Resources

- [Spring Boot Guide](https://spring.io/guides/gs/spring-boot/)
- [React Tutorial](https://react.dev/learn)
- [OpenAI API](https://platform.openai.com/docs/api-reference)
- [JWT Authentication](https://jwt.io/introduction)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---

**You're all set! Happy coding! 🚀**
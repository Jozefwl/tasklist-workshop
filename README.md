# Tasklist Spring Boot Application

**Description:**  
A Spring Boot-based REST API for managing tasks and tasklists with secure authentication. This project demonstrates the implementation of a modern web application using Spring Boot, Spring Security, and JWT authentication.

![Java Version](https://img.shields.io/badge/Java-24-blue?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-darkgreen?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-blue?style=for-the-badge&logo=postgresql)

## Features

- User Authentication with JWT
- Complete Task Management (CRUD operations)
- Tasklist Management (CRUD operations)
- RESTful API endpoints
- PostgreSQL database integration
- Spring Security implementation
- User-based authorization (users can only access their own tasks/tasklists)
- Comprehensive error handling

## Tech Stack

- Java 24
- Spring Boot 3.4.5
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (JSON Web Tokens)
- Maven

## API Endpoints

API description is available here: http://localhost:8080/swagger-ui/index.html#/

### Authentication

#### `POST /auth/register`
Registers a new user.

- **Request Body:**
```json
{
  "username": "newUser",
  "password": "userPassword"
}
```

- **Response:**
```json
{
  "id": "sad6845fdg65-654fds-89fxgh46-...",
  "name": "newUser",
  "password": "userPassword"
}
```

#### `POST /auth/login`
Authenticate user and receive JWT token.

- **Request Body:**
```json
{
  "username": "newUser",
  "password": "userPassword"
}
```

- **Response:**
```json
{
  "id": "sad6845fdg65-654fds-89fxgh46-...",
  "name": "newUser",
  "token": "eyJhbGciOiJIUzI1NiIs..."
}
```

### Tasklist Management

#### `GET /tasklist/getAll`
Get all tasklists for the authenticated user.

- **Headers:** `Authorization: Bearer <JWT_TOKEN>`
- **Response:**
```json
[
  {
    "id": "uuid",
    "ownerId": "user-uuid",
    "name": "My Tasklist",
    "description": "Description of the tasklist",
    "tasks": [
      {
        "id": "task-uuid",
        "ownerId": "user-uuid",
        "name": "Task name",
        "description": "Task Description",
        "tasklistId": "tasklist-uuid"
      }
    ]
  }
]
```

#### `GET /tasklist/get/{id}`
Get a specific tasklist by ID (only if user is the owner).

- **Headers:** `Authorization: Bearer <JWT_TOKEN>`
- **Path Parameters:** `id` (UUID) - Tasklist ID
- **Response:**
```json
{
  "id": "uuid",
  "ownerId": "user-uuid",
  "name": "My Tasklist",
  "description": "Description of the tasklist",
  "tasks": [...]
}
```

#### `POST /tasklist/create`
Create a new tasklist for the authenticated user.

- **Headers:** `Authorization: Bearer <JWT_TOKEN>`
- **Request Body:**
```json
{
  "name": "New Tasklist",
  "description": "Description of the new tasklist"
}
```

- **Response:**
```json
{
  "id": "uuid",
  "ownerId": "user-uuid",
  "name": "New Tasklist",
  "description": "Description of the new tasklist",
  "tasks": []
}
```

#### `POST /tasklist/update`
Update an existing tasklist.

- **Headers:** `Authorization: Bearer <JWT_TOKEN>`
- **Request Body:**
```json
{
  "id": "tasklist-uuid",
  "name": "Updated Tasklist Name",
  "description": "Updated description"
}
```

#### `DELETE /tasklist/delete/{id}`
Delete a tasklist by ID.

- **Headers:** `Authorization: Bearer <JWT_TOKEN>`
- **Path Parameters:** `id` (UUID) - Tasklist ID

### Task Management

#### `GET /task/getAll`
Get all tasks for the authenticated user.

- **Headers:** `Authorization: Bearer <JWT_TOKEN>`
- **Response:**
```json
[
  {
    "id": "task-uuid",
    "ownerId": "user-uuid",
    "name": "Task name",
    "description": "Task Description",
    "tasklistId": "tasklist-uuid"
  }
]
```

#### `GET /task/get/{id}`
Get a specific task by ID (only if user is the owner).

- **Headers:** `Authorization: Bearer <JWT_TOKEN>`
- **Path Parameters:** `id` (UUID) - Task ID
- **Response:**
```json
{
  "id": "task-uuid",
  "ownerId": "user-uuid",
  "name": "Task name",
  "description": "Task Description",
  "tasklistId": "tasklist-uuid"
}
```

#### `POST /task/create`
Create a new task in a specific tasklist.

- **Headers:** `Authorization: Bearer <JWT_TOKEN>`
- **Request Body:**
```json
{
  "name": "New Task",
  "description": "Task description",
  "tasklistId": "tasklist-uuid"
}
```

- **Response:**
```json
{
  "id": "task-uuid",
  "ownerId": "user-uuid",
  "name": "New Task",
  "description": "Task description",
  "tasklistId": "tasklist-uuid"
}
```

#### `POST /task/update`
Update an existing task.

- **Headers:** `Authorization: Bearer <JWT_TOKEN>`
- **Request Body:**
```json
{
  "id": "task-uuid",
  "name": "Updated Task name",
  "description": "Updated task description"
}
```

#### `DELETE /task/delete/{id}`
Delete a task by ID.

- **Headers:** `Authorization: Bearer <JWT_TOKEN>`
- **Path Parameters:** `id` (UUID) - Task ID

## Authorization & Security

- All endpoints except `/auth/register`, `/auth/login` require JWT authentication
- Users can only access their own tasks and tasklists
- The application validates ownership for all CRUD operations
- JWT tokens are extracted from the `Authorization: Bearer <token>` header

## Error Responses

The API returns appropriate HTTP status codes:
- `200 OK` - Successful operation
- `401 Unauthorized` - Invalid JWT token or user not authorized to access resource
- `404 Not Found` - Resource not found
- `400 Bad Request` - Invalid request data

## How to Run

1. **Prerequisites:**
    - Java 24
    - Maven
    - PostgreSQL

2. **Configuration:**
    - Update `application.properties` with your PostgreSQL connection details
    - Configure JWT secret key in `application.properties`

3. **Build and Run:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the API:**
    - Base URL: `http://localhost:8080`
    - Use Postman or similar tools to test the endpoints

## Security Notes

- The current configuration includes debug logging for development
- JWT secret and database credentials should be moved to environment variables in production
- Default token expiration is set to 1 hour (3600000 ms)
- All task and tasklist operations are protected by user ownership validation

## Future Enhancements

- User roles and permissions
- Task completion status and due dates
- Task priorities and categories
- Rate limiting
- API documentation with Swagger/OpenAPI
- Enhanced error handling and validation
- Task sharing between users
- Email notifications for task reminders

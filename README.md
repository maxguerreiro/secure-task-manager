---

# 🧩 Task Manager API

A simple and secure REST API for managing users and tasks, built with Spring Boot, MongoDB, and JWT authentication.

This project demonstrates a clean architecture approach with authentication, authorization, and proper separation of concerns.

---

## Features
- 🔐 JWT Authentication
- 👥 User management with roles (ADMIN / USER)
- ✅ Task management per user
- 🛡️ Role-based access control
- ⚙️ Global exception handling
- 📦 MongoDB integration

---

## Tech Stack
- Java 17+
- Spring Boot
- Spring Security
- MongoDB
- JWT (JSON Web Token)

## Authentication Flow
- Create a user
- Login with credentials
- Receive a JWT token
- Use the token in protected requests
- Authorization: Bearer YOUR_TOKEN

---
# API Endpoints

## 🔐 Auth
- Login
- POST /auth/login

### Request
    {
      "email": "user@email.com", 
      "password": "123456"
    }

### Response

JWT TOKEN (string)

---

## 👤 Users
- Create User
- POST /users

### Request

    {
      "userName": "User", 
      "email": "user@email.com", 
      "password": "123456", 
      "roles": "USER"
    }

roles can be: USER or ADMIN (optional, defaults to USER)

---

## Get All Users (ADMIN only)
#GET /users

---

## Get User by ID
### GET /users/{id}
- ADMIN → can access any user
- USER → can only access their own data

---

## Update User
PUT /users/{id}

### Request

    {
      "userName": "New Name", 
      "email": "new@email.com", 
      "password": "newpassword" 
    }

---

## Delete User
### DELETE /users/{id}
ADMIN → can delete any user
USER → can delete only their own account

# 📋 Tasks

## Create Task
## POST /tasks

### Request

    {
      "title": "Study Spring Boot",
      "description": "Finish security module"
    }

---

## Get My Tasks
### GET /tasks/mytasks

Returns only tasks from the authenticated user.

---

## Get My Task by ID
###GET /tasks/mytasks/{id}

---

## Get All Tasks (ADMIN only)
### GET /tasks

---

## Get Task by ID (ADMIN only)
### GET /tasks/{id}

---

## Update Task
### PUT /tasks/{id}

### Request

    {
      "title": "Updated title",
      "description": "Updated description"
    }

## Complete Task (Toggle)
### PATCH /tasks/{id}/complete
Marks as completed/uncompleted

---

# ⚠️ Error Handling

## All errors follow this structure:

    {
      "timestamp": "2026-01-01T12:00:00Z",
      "status": 404,
      "error": "Resource not found",
      "message": "Resource not found. Id 123",
      "path": "/tasks/123"
    }

## Common Errors
- 404 Not Found
- 403 Forbidden
- 401 Unauthorized

---

# 🔒 Security Rules
## Endpoint	Access
- /auth/**	Public
- POST /users	Public
- GET /users	ADMIN only
- DELETE /users	Authenticated (with rules)
- /tasks/mytasks	Authenticated
- /tasks	ADMIN only

---


# ▶️ Running the Project
- Clone the repository
- Configure MongoDB connection
- Run the application:
- ./mvnw spring-boot:run

---
# Testing Tips
- Use Postman or Insomnia
- Always login first to get a token
### Add token to headers:
- Authorization: Bearer YOUR_TOKEN

---

#💡 Future Improvements
- Refresh tokens
- Pagination
- Task categories
- Docker support

---
# 📌 Author

## Maxwell Guerreiro dos santos

This project was built as a learning and portfolio piece, focusing on clean code, security, and real-world backend practices.

# KBase Document Management System

## Overview

KBase is a Knowledge Base Document Management System developed to manage knowledge articles, projects, and documents.

The system provides authentication, role-based authorization, CRUD operations, and document management features.

---

## Technologies

### Backend
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL

### Frontend
- ReactJS
- Vite
- JavaScript
- CSS

### Testing Tools
- Postman
- Swagger UI

---

## Main Features

### User Authentication
- User login
- Role-based access control

### Knowledge Article Management

CRUD functions:

- Create articles
- View articles
- Update articles
- Delete articles

### Project Management

- Create projects
- Manage project information
- Organize documents by project

### Document Management

- Store documents
- Manage uploaded files
- Organize files according to projects

---

## Database Design

Database:
MySQL


Main entities:

- User
- Role
- KnowledgeArticle
- Project
- Document

---

## API Endpoints

### Authentication


POST /auth/login


### Knowledge Article


GET /articles

POST /articles

PUT /articles/{id}

DELETE /articles/{id}


---

## Project Structure


KBase-Document-Management

├── demo
│ └── Spring Boot Backend
│
├── kbase-frontend
│ └── React Frontend
│
└── README.md


---

## How to Run

### Backend

Open backend folder:


cd demo


Run Spring Boot:


mvn spring-boot:run


Backend runs at:


http://localhost:8080


---

### Frontend

Open frontend folder:


cd kbase-frontend


Install dependencies:


npm install


Run:


npm run dev


Frontend runs at:


http://localhost:5173


---

## API Testing

API was tested using:

- Swagger UI
- Postman

Testing includes:

- Authentication
- CRUD Knowledge Article
- Permission checking

---

## Author

Student: Huynh Ngoc Truong

---

## GitHub Repository

https://github.com/BigbeeGDK24/KBase-Document-Management

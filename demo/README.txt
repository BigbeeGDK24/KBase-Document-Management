# KBase - Knowledge Base Management System


## Giới thiệu

KBase (Knowledge Base Management System) là hệ thống quản lý
kiến thức cho phép người dùng tạo, cập nhật, tìm kiếm và quản lý
các bài viết kiến thức.

Backend được xây dựng bằng Spring Boot với JWT Authentication
và phân quyền USER/ADMIN.


---

# Công nghệ sử dụng


## Backend

- Java 21
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate


## Database

- PostgreSQL


## API Documentation

- Swagger OpenAPI 3



---

# Chức năng chính


## Authentication

- Register tài khoản
- Login
- JWT Token Authentication
- Phân quyền USER và ADMIN



## User Management


### USER

- Xem thông tin cá nhân đang đăng nhập


### ADMIN

- Xem danh sách tất cả user
- Xóa user



## Knowledge Article Management


Các chức năng bài viết:

- Tạo bài viết
- Xem danh sách bài viết
- Xem chi tiết bài viết
- Cập nhật bài viết
- Xóa bài viết


## Permission


### USER

- Tạo bài viết
- Cập nhật bài viết của chính mình
- Xóa bài viết của chính mình


### ADMIN

- Cập nhật mọi bài viết
- Xóa mọi bài viết



## Search và Filter

- Tìm kiếm bài viết theo title
- Tìm kiếm theo content
- Lọc bài viết theo category


## Pagination

- Phân trang danh sách bài viết
- Sắp xếp bài viết theo thời gian tạo



---

# Cấu trúc Project


```
src/main/java/com/kbase/demo

├── controller
│
├── service
│
├── repository
│
├── entity
│
├── dto
│
├── security
│
├── config
│
└── exception
```



---

# Database


Database sử dụng:

```
PostgreSQL
```


Tên database:

```
kbase_db
```



---

# Cách chạy Project


## 1. Clone project


```bash
git clone <repository-url>
```



## 2. Cấu hình Database


Mở file:

```
application.properties
```


Cấu hình:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/kbase_db

spring.datasource.username=postgres

spring.datasource.password=your_password
```



## 3. Build project


```bash
mvn clean package
```



## 4. Run project


```bash
mvn spring-boot:run
```



Server chạy tại:


```
http://localhost:8080
```



---

# Swagger API Documentation


Truy cập:


```
http://localhost:8080/swagger-ui/index.html
```



Cách sử dụng:


1. Login lấy JWT Token


2. Nhấn nút:

```
Authorize
```


3. Nhập:


```
Bearer <your_token>
```



---

# API Endpoints



## Authentication


### Register

```
POST /auth/register
```


### Login

```
POST /auth/login
```



---

## User API


### Lấy profile user hiện tại

```
GET /users/me
```


### Lấy danh sách user

```
GET /users
```


(Chỉ ADMIN)


### Xóa user

```
DELETE /users/{id}
```


(Chỉ ADMIN)



---

## Article API


### Lấy tất cả bài viết

```
GET /articles
```


### Lấy bài viết theo ID

```
GET /articles/{id}
```


### Tạo bài viết

```
POST /articles
```


### Cập nhật bài viết

```
PUT /articles/{id}
```


### Xóa bài viết

```
DELETE /articles/{id}
```


### Tìm kiếm bài viết


```
GET /articles/search?keyword=value
```


### Lọc theo category


```
GET /articles/category?name=value
```


### Pagination


```
GET /articles/page?page=0&size=5
```



---

# Security


Hệ thống sử dụng:

- JWT Authentication
- Spring Security Filter
- Role Based Authorization


Role:


```
USER
```


và


```
ADMIN
```



---

# Exception Handling


Backend có Global Exception Handler:


Xử lý:

- 400 Bad Request
- 403 Forbidden
- Validation Error
- Resource Not Found



---

# Author


KBase Backend Project

Built with Spring Boot
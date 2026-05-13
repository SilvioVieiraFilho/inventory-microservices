# 🚀 Inventory Microservices System

<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&color=0:0d6efd,100:6610f2&height=240&section=header&text=Inventory%20Microservices%20System&fontSize=40&fontColor=ffffff&animation=fadeIn&fontAlignY=38" />
</p>

<p align="center">

<img src="https://img.shields.io/badge/Java-21-red?style=for-the-badge&logo=openjdk" />
<img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot" />
<img src="https://img.shields.io/badge/Microservices-Architecture-blueviolet?style=for-the-badge" />
<img src="https://img.shields.io/badge/Spring_Security-JWT-success?style=for-the-badge" />
<img src="https://img.shields.io/badge/OpenFeign-REST_Communication-blue?style=for-the-badge" />
<img src="https://img.shields.io/badge/JUnit5-Tests-orange?style=for-the-badge" />

</p>

---

# 📌 About The Project

Enterprise backend application based on **Microservices Architecture** using:

- Java 21
- Spring Boot 3
- Spring Security + JWT
- OpenFeign
- Spring Data JPA
- H2 Database
- Automated Testing

The project simulates a real-world backend ecosystem focused on:

✔ Scalable architecture  
✔ Service isolation  
✔ REST communication  
✔ Authentication & Authorization  
✔ Business rules validation  
✔ Event tracking  
✔ Clean Code  
✔ SOLID principles  
✔ Enterprise backend patterns

---

# 🏗️ Architecture Overview

```txt
                 ┌────────────────────┐
                 │       Client       │
                 └─────────┬──────────┘
                           │
                           ▼
              ┌────────────────────────┐
              │    produto-service     │
              │  Inventory Management  │
              └─────────┬──────────────┘
                        │ REST/OpenFeign
                        ▼
              ┌────────────────────────┐
              │   historico-service    │
              │    Audit & Tracking    │
              └────────────────────────┘
```

---

# 🧩 Microservices

# 📦 produto-service

Responsible for inventory management and business rules.

## Features

- Product CRUD
- Inventory control
- Product merge logic
- Dynamic filters
- JWT Authentication
- Role-based Authorization
- Event publishing
- REST API
- OpenFeign integration

---

# 🕘 historico-service

Responsible for audit logs and event persistence.

## Features

- Product event history
- Audit logging
- Inventory tracking
- Event persistence
- JWT protected routes
- REST event consumption

---

# 🔗 Communication Between Services

Microservices communicate using:

- REST APIs
- OpenFeign Client

## Flow

```txt
produto-service
        │
        ├── send inventory events
        ▼
historico-service
```

---

# 🔐 Security Layer

Security implementation includes:

- Spring Security
- JWT Authentication
- Stateless Authentication
- Authorization Filters
- Protected Routes
- Access Control by Role
- Authentication Middleware

---

# 🧠 Business Rules

# 📦 Product Rules

- Duplicate products are merged automatically
- SOLD_OUT products cannot receive stock
- Product updates generate history events
- Inventory operations are audited
- Invalid inventory operations are blocked

---

# 👤 User Rules

- BLOCKED users cannot authenticate
- DISABLED users cannot access protected routes
- Authentication requires valid JWT token
- Invalid credentials return authentication errors

---

# 🛠️ Technologies

| Technology      | Purpose                        |
| --------------- | ------------------------------ |
| Java 21         | Main language                  |
| Spring Boot 3   | Backend framework              |
| Spring Security | Authentication & Authorization |
| JWT             | Stateless authentication       |
| OpenFeign       | Service communication          |
| Spring Data JPA | Persistence                    |
| Hibernate       | ORM                            |
| H2 Database     | In-memory database             |
| MapStruct       | DTO Mapping                    |
| JUnit 5         | Unit testing                   |
| Mockito         | Mocking                        |
| Maven           | Dependency management          |

---

# 📂 Project Structure

```txt
inventory-microservices/
│
├── produto-service/
│
├── historico-service/
│
├── docs/
│   ├── Arquitetura/
│   ├── Postman/
│   └── imagem/
│
└── README.md
```

---

# 🧪 Automated Tests

The project contains:

✔ Unit Tests  
✔ Service Layer Tests  
✔ Repository Tests  
✔ JWT Security Tests  
✔ Mockito-based mocks  
✔ Business Rules Validation Tests

---

# 📘 API Documentation

After running the services:

# produto-service

```txt
http://localhost:8080/swagger-ui.html
```

# historico-service

```txt
http://localhost:8081/swagger-ui.html
```

---

# ▶️ Running The Project

# Clone repository

```bash
git clone https://github.com/YOUR_USERNAME/inventory-microservices.git
```

---

# Run produto-service

```bash
cd produto-service
./mvnw spring-boot:run
```

---

# Run historico-service

```bash
cd historico-service
./mvnw spring-boot:run
```

---

# 🔑 Authentication

## Login endpoint

```http
POST /auth/login
```

## Example response

```json
{
  "message": "Login realizado com sucesso",
  "data": {
    "token": "JWT_TOKEN",
    "email": "user@email.com",
    "role": "USER"
  }
}
```

---

# 📬 Postman Collection

The repository contains a complete Postman collection with:

- Authentication requests
- Product CRUD
- Inventory operations
- JWT protected routes
- Integration tests

Location:

```txt
docs/Postman/
```

---

# 📈 Technical Highlights

✔ Microservices Architecture  
✔ JWT Authentication  
✔ Spring Security  
✔ OpenFeign Communication  
✔ REST APIs  
✔ Domain Isolation  
✔ Clean Architecture  
✔ SOLID Principles  
✔ Automated Tests  
✔ DTO Pattern  
✔ Enterprise Backend Design  
✔ Audit System  
✔ Real Business Rules

---

# 👨‍💻 Author

## Silvio Rodrigues Vieira Filho

Backend Developer focused on:

- Java
- Spring Boot
- Microservices
- REST APIs
- Security
- Backend Architecture

This project was created for professional portfolio and backend architecture studies.

---

# ⭐ Future Improvements

- Docker support
- API Gateway
- Service Discovery
- PostgreSQL integration
- Kafka event streaming
- CI/CD pipeline
- Kubernetes deployment
- Observability & Monitoring

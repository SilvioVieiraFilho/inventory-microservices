# 🚀 Inventory Microservices System

<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&color=0:0d6efd,100:6610f2&height=220&section=header&text=Inventory%20Microservices%20System&fontSize=36&fontColor=ffffff&animation=fadeIn" />
</p>

<p align="center">

<img src="https://img.shields.io/badge/Java-21-red?style=for-the-badge&logo=openjdk" />
<img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot" />
<img src="https://img.shields.io/badge/Microservices-Architecture-blueviolet?style=for-the-badge" />
<img src="https://img.shields.io/badge/Spring_Security-JWT-success?style=for-the-badge" />
<img src="https://img.shields.io/badge/OpenFeign-REST_Communication-blue?style=for-the-badge" />

</p>

---

# 📌 About the Project

Backend system based on **Microservices Architecture** using **Java + Spring Boot**.

This project was designed to simulate a real-world backend environment, applying modern software engineering practices focused on:

- Scalable Architecture
- Service Communication
- JWT Authentication
- Domain Isolation
- Clean Code
- SOLID Principles
- Automated Testing
- Real Business Rules

---

# 🏗️ System Architecture

```txt
                 ┌────────────────────┐
                 │       Client       │
                 └─────────┬──────────┘
                           │
                           ▼
              ┌────────────────────────┐
              │    produto-service     │
              └─────────┬──────────────┘
                        │ REST/OpenFeign
                        ▼
              ┌────────────────────────┐
              │   historico-service    │
              └────────────────────────┘
```

---

# 🧩 Microservices

## 📦 produto-service

Responsible for product management and business rules.

### Features

- Product registration
- Product update
- Dynamic filtering
- Product merge logic
- Inventory control
- JWT Security
- Integration with historico-service

---

## 🕘 historico-service

Responsible for audit logs and history tracking.

### Features

- Product event history
- Change tracking
- Audit logging
- Event persistence
- REST event consumption

---

# 🔗 Service Communication

Microservices communicate through:

- REST API
- OpenFeign Client

Flow:

```txt
produto-service
        │
        ├── sends events
        ▼
historico-service
```

---

# 🔐 Security

Implemented using:

- Spring Security
- JWT Authentication
- Stateless Authentication
- Authorization Filters
- Protected Routes
- Role-based Access Control

---

# 🧠 Business Rules

### 📦 Product Rules

- Duplicate products are merged automatically
- SOLD_OUT products cannot receive stock
- Product updates generate history events
- Inventory operations are audited

---

### 👤 User Rules

- BLOCKED users cannot authenticate
- DISABLED users cannot access the system
- Login requires valid credentials

---

# 🛠️ Technologies

| Technology      | Purpose                        |
| --------------- | ------------------------------ |
| Java 21         | Main language                  |
| Spring Boot     | Backend framework              |
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
│   ├── architecture.png
│   ├── uml.png
│   └── der.png
│
└── README.md
```

---

# 🧪 Testing

The project includes:

- Unit Tests
- Domain Tests
- Service Layer Tests
- Security Tests
- Mockito-based mocks
- JaCoCo coverage

---

# 📘 API Documentation

After starting the services:

## produto-service

```txt
http://localhost:8080/swagger-ui.html
```

## historico-service

```txt
http://localhost:8081/swagger-ui.html
```

---

# ▶️ Running the Project

## Clone repository

```bash
git clone YOUR_REPOSITORY
```

---

## Run produto-service

```bash
cd produto-service
./mvnw spring-boot:run
```

---

## Run historico-service

```bash
cd historico-service
./mvnw spring-boot:run
```

---

# 📈 Technical Highlights

✔️ Microservices Architecture  
✔️ REST Communication  
✔️ OpenFeign Integration  
✔️ JWT Authentication  
✔️ Spring Security  
✔️ Domain-Driven Structure  
✔️ Clean Code  
✔️ SOLID Principles  
✔️ Automated Tests  
✔️ Scalable Backend Design  
✔️ Real Business Rules

---

# 👨‍💻 Author

**Silvio Rodrigues Vieira Filho**

Backend Developer focused on Java & Spring Boot ecosystem.

Project created for backend architecture studies and professional portfolio.

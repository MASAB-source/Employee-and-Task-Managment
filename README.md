# Employee-and-Task-Managment

# Employee & Task Management System

A robust RESTful API backend built with **Spring Boot 3**, **Spring Data JPA**, and **PostgreSQL**. The application manages employees and their assigned tasks with role-based validation, custom DTO mapping, and interactive Swagger UI documentation.

---

## Prerequisites

Before setting up and running the project, ensure you have the following installed on your system:

* **Java Development Kit (JDK)**: Java 17 or Java 21
* **PostgreSQL Database**: Version 14 or higher (running locally on port `5432`)
* **Build Tool**: Apache Maven (or use the included `./mvnw` / `.\mvnw` wrapper)
* **API Testing Tool**: Browser (for Swagger UI) or Postman / cURL


---

## Project Setup & Installation

### 1. Clone the Repository
```bash
git clone <repository-url>
cd employee

---
## 🛠️ Tech Stack

* **Framework:** Spring Boot 3
* **Language:** Java 17+
* **Database:** PostgreSQL
* **ORM:** Spring Data JPA / Hibernate
* **API Documentation:** Springdoc OpenAPI (Swagger UI)
* **Build Tool:** Maven

---

## 🚀 Features

* **Employee Management:** Full CRUD operations for managing system users with assigned roles (`ROLE_ADMIN`, `ROLE_MANAGER`, `ROLE_EMPLOYEE`).
* **Task Management:** Create, retrieve, update, and delete tasks mapped to specific employees.
* **DTO Pattern & Clean Architecture:** Request/Response DTO separation using modern Java Records.
* **Interactive API Testing:** Embedded Swagger UI interface for testing endpoints directly in the browser.

---

## 📁 Project Structure

```text
src/main/java/employee/task/employee/
├── domain/                  # JPA Entities & Enums (Task, Employee, Role, TaskStatus)
├── dtos/                    # Request/Response Java Records
├── repository/              # Spring Data JPA Repositories
├── service/                 # Business Logic Interfaces & Implementations
├── controller/              # REST API Controllers (EmployeeController, TaskController)
└── EmployeeApplication.java # Application Main Entry Point

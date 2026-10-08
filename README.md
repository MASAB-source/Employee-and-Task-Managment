# Employee-and-Task-Managment

# Employee & Task Management System

A robust RESTful API backend built with **Spring Boot 3**, **Spring Data JPA**, and **PostgreSQL**. The application manages employees and their assigned tasks with role-based validation, custom DTO mapping, and interactive Swagger UI documentation.

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

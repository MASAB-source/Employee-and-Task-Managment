# 🏢 Employee & Task Management System
A enterprise-grade backend RESTful API designed to manage workforce data, task allocations, security roles, and operational workflows.

# 📌 1. PROJECT OVERVIEW & SYSTEM ARCHITECTURE
High-Level Summary
The Employee & Task Management System serves as the central engine for an organization's internal workflows. It securely manages employee profiles, delegates tasks, monitors completion statuses, and enforces organization-wide access policies.

Built with enterprise standards, the application guarantees data integrity, secure session management using JSON Web Tokens (JWT), input validation, dynamic pagination/filtering for large datasets, and error reporting.

Non-Technical Layer Breakdown
Think of the application as an automated digital office:

Controller Layer (The Reception Desk): Receives incoming requests from users (via web apps or Swagger UI), checks if the request format is valid, and directs it to the right department.

Service Layer (The Processing Department): Enforces all business logic, rules, and calculations (e.g., verifying if an employee exists before assigning them a task).

Repository Layer (The Filing Clerks): Communicates directly with the database to store, retrieve, update, or erase records.

PostgreSQL Database (The Secure Vault): The physical disk storage where all company records reside permanently and safely.

[ Client Request ] (Swagger UI / Postman / Frontend App)
                               │
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                      CONTROLLER LAYER                       │
│  - Receives HTTP Requests (GET, POST, PUT, DELETE)           │
│  - Validates input formats (@Valid)                         │
└──────────────────────────────┬──────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                        SERVICE LAYER                        │
│  - Executes business rules and calculations                 │
│  - Manages Security, JWT authentication & Role Authorization │
└──────────────────────────────┬──────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                      REPOSITORY LAYER                       │
│  - Translates Java requests into database queries (SQL)     │
│  - Spring Data JPA Object-Relational Mapping (ORM)          │
└──────────────────────────────┬──────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                     POSTGRESQL DATABASE                     │
│  - Persistent SQL data storage (employee_db)                │
└─────────────────────────────────────────────────────────────┘


Key Capabilities
Security & Authentication: Password encryption using BCrypt and stateless session control using JWT.

Role-Based Access Control (RBAC): Tiered permissions across 3 roles (ROLE_ADMIN, ROLE_MANAGER, ROLE_EMPLOYEE).

Employee Management: Full CRUD (Create, Read, Update, Delete) capability for staff records.

Task Management: Real-time task creation, assignment, status tracking, and priority management.

Smart Search & Pagination: Efficient record filtering by name, role, status, or date with page-by-page result navigation.

Data Validation: Strict input checking to prevent broken or malformed records.

<img width="1129" height="816" alt="image" src="https://github.com/user-attachments/assets/abe19394-9d26-4c0f-896e-2a80471fef30" />

🛠️ 3. PREREQUISITES & ONE-TIME ENVIRONMENT SETUP
Follow these installation instructions to prepare your environment.

Step 1: Install Java Development Kit (JDK 17 or JDK 21)
Download JDK 17 or 21 from Eclipse Adoptium (Temurin) or Oracle JDK.

Run the installer and complete the setup wizard.

Verification: Open your terminal (PowerShell or Terminal) and run:

<img width="761" height="111" alt="image" src="https://github.com/user-attachments/assets/f5aa4181-977e-48ec-9e21-206d1cd038c2" />

Expected Output: openjdk version "17.0.x" or "21.0.x"

 Step 2: Install PostgreSQL & pgAdmin
Download the PostgreSQL Installer from PostgreSQL Official Site.

Run the installer. During installation:

Keep the default port set to 5432.

Choose a password for the primary postgres administrative account (e.g., postgres or admin123) and save it securely.

Ensure pgAdmin 4 is checked in the component list.

Verification: Open terminal and run:

<img width="764" height="111" alt="image" src="https://github.com/user-attachments/assets/768238f7-2d76-417e-9474-df9973a021e4" />

Expected Output: psql (PostgreSQL) 14.x (or newer)

 Step 3: Install Git
Download Git from git-scm.com.

Complete the installation steps.

Verification: Open terminal and run:

<img width="762" height="113" alt="image" src="https://github.com/user-attachments/assets/6df2ddd0-581b-4a64-bcfb-304f7bdd602d" />

Expected Output: git version 2.x.x

🗄️ 4. DATABASE SETUP & CONFIGURATION (POSTGRESQL)Option A: Setup using pgAdmin GUI (Easiest)Launch pgAdmin from your applications menu.Enter your master password when prompted.In the left panel, expand Servers $\rightarrow$ Right-click PostgreSQL $\rightarrow$ Select Connect Server.Right-click Databases $\rightarrow$ Create $\rightarrow$ Database...Set Database name to: employee_dbClick Save.Option B: Setup using psql Command LineOpen your terminal and start the PostgreSQL prompt:

<img width="756" height="113" alt="image" src="https://github.com/user-attachments/assets/7cc1db4f-9591-446b-980c-a059cf946193" />

Enter your PostgreSQL administrative password.

Execute the database creation command:

<img width="752" height="106" alt="image" src="https://github.com/user-attachments/assets/b396413f-f88c-4424-8989-7e36b3ba8d46" />

# Step 2: Configure Project application.properties
Navigate to the project directory and open src/main/resources/application.properties in a text editor (VS Code, Notepad, or IntelliJ). Update the configuration to match your local database settings:

 System Port Configuration
server.port=8080

 PostgreSQL Database Connection Parameters
spring.datasource.url=jdbc:postgresql://localhost:5432/employee_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_POSTGRES_PASSWORD_HERE
spring.datasource.driver-class-name=org.postgresql.Driver

 JPA / Hibernate Auto Schema Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

 JWT Security Configuration
application.security.jwt.secret-key=xxxx
application.security.jwt.expiration=xxxx

Note: Replace YOUR_POSTGRES_PASSWORD_HERE with the password you created during PostgreSQL installation.

# 🚀 5. HOW TO RUN THE APPLICATION
Option 1: Command Line (Using Maven Wrapper)
Open your terminal and navigate into the root project directory:

<img width="670" height="102" alt="image" src="https://github.com/user-attachments/assets/38a61c32-3dc7-458b-bbbe-705bf01fc02d" />

Execute the run command according to your Operating System:

Windows (PowerShell / CMD):

<img width="637" height="96" alt="image" src="https://github.com/user-attachments/assets/4186c4a0-7ba9-4b0c-a03b-7def344e815b" />

Linux / macOS (Terminal):

<img width="635" height="90" alt="image" src="https://github.com/user-attachments/assets/2fa43552-280e-4f8d-bbfb-f1b70bc1aa78" />

Option 2: Using Visual Studio Code or IntelliJ IDEA
Open the project folder in your preferred IDE.

Wait for the editor to automatically import Maven dependencies.

Navigate to: src/main/java/employee/task/employee/EmployeeApplication.java

Right-click inside EmployeeApplication.java and select Run 'EmployeeApplication' (or click the green Play button above the main method).

How to Confirm the Server Has Started
Watch the terminal window output. When boot-up completes successfully, you will see output ending with these log lines:

2026-10-09T06:57:10.280-07:00  INFO 13540 --- HikariPool-1 - Start completed.
2026-10-09T06:57:15.199-07:00  INFO 13540 --- Tomcat started on port 8080 (http) with context path '/'
2026-10-09T06:57:15.212-07:00  INFO 13540 --- Started EmployeeApplication in 9.403 seconds

Verification: Open your web browser and go to http://localhost:8080/swagger-ui/index.html. If the interactive API page loads, the system is fully active and ready for use.

# 🧪 6. RUNNING AUTOMATED UNIT & INTEGRATION TESTS
The system includes 15+ automated tests verifying controller endpoints, security guards, service workflows, and database repository integrity.

Execute Tests
Run the testing suite from the project root:

Windows (PowerShell):

<img width="673" height="92" alt="image" src="https://github.com/user-attachments/assets/52004ba8-ca0e-498f-b00a-33ef5f0fcd76" />

Linux / macOS:

<img width="669" height="102" alt="image" src="https://github.com/user-attachments/assets/ca1e1b45-7120-464e-812b-bce18755c76b" />

Expected Test Output
Maven will compile test classes, launch an embedded test context, run each scenario, and output the results:

<img width="704" height="411" alt="image" src="https://github.com/user-attachments/assets/9f71ae8d-a545-4648-85e6-de2d266742e5" />

# 📖 7. INTERACTIVE API DOCUMENTATION & TESTING (SWAGGER UI)
The project includes an interactive Swagger interface allowing direct API interaction from any web browser.

Step 1: Access Swagger UI
Open your browser and navigate to:

<img width="714" height="101" alt="image" src="https://github.com/user-attachments/assets/8e71f80e-5f40-40d1-b970-909b086d6e14" />

Step 2: Register & Authenticate a User
Expand the POST /api/auth/register endpoint.

Click Try it out.

Replace the request body with valid user JSON data:

<img width="683" height="206" alt="image" src="https://github.com/user-attachments/assets/3f030e18-84e6-44b7-87e1-69de0ceef534" />

Click Execute. Verify a 201 Created or 200 OK HTTP status response is returned.

Step 3: Login to Extract JWT Token
Expand POST /api/auth/login.

Click Try it out and enter credentials:

<img width="674" height="179" alt="image" src="https://github.com/user-attachments/assets/60dcf64e-6ff6-4087-b30a-92cb52aa74e3" />

Click Execute.

Locate the token string returned in the JSON response body:

<img width="359" height="152" alt="image" src="https://github.com/user-attachments/assets/8309aaa5-513a-4294-bff8-df1a6fbaea54" />

Copy the entire token string (excluding quotes).

Step 4: Authorize Swagger Session
Scroll to the top right of the Swagger UI page and click the green Authorize lock button.

In the Value input field, paste the copied JWT token.

Click Authorize, then click Close.

<img width="622" height="213" alt="image" src="https://github.com/user-attachments/assets/74c945ec-23fa-4077-a437-2cf0f8b37f75" />

You are now authorized. All subsequent requests submitted through Swagger will automatically attach your credentials, unlocking protected endpoints based on your user role.

# 🚨 8. GLOBAL EXCEPTION HANDLING & ERROR CODES
The application enforces consistent structured error messaging across all endpoints using a Global Exception Handler (@ControllerAdvice).

Standard HTTP Status Codes
200 OK: Request processed successfully.

201 Created: Resource successfully created.

400 Bad Request: Validation failure or invalid request body format.

401 Unauthorized: Authentication missing or expired JWT token.

403 Forbidden: User logged in but lacks necessary permissions for the resource.

404 Not Found: Target record ID or endpoint does not exist.

500 Internal Server Error: Unexpected system error.

Example Error Response Formats
1. Validation Error (400 Bad Request)
Returned when mandatory fields are omitted or improperly formatted:

<img width="703" height="296" alt="image" src="https://github.com/user-attachments/assets/a659ec82-1e6f-4c55-9baf-e9ee5b119fe9" />

Resource Not Found Error (404 Not Found)
Returned when querying an invalid record ID:

<img width="718" height="215" alt="image" src="https://github.com/user-attachments/assets/6ae7ccaf-15b5-4825-b8c3-40675cb43700" />

Unauthorized Access Error (403 Forbidden)
Returned when an employee attempts an administrative action:

<img width="705" height="230" alt="image" src="https://github.com/user-attachments/assets/50afba23-2652-41fa-acac-b9378937fa0f" />

# 9. PROJECT FOLDER STRUCTURE
Below is the directory map detailing project architecture organization:

<img width="455" height="668" alt="image" src="https://github.com/user-attachments/assets/1b0a690e-c617-44be-9c56-db370432f816" />


# TROUBLESHOOTING & COMMON SOLUTIONS
Error: Port 8080 is already in use

Fix (Windows PowerShell):

Stop-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess -Force

Error: Connection to localhost:5432 refused

Fix: Open Windows Services (services.msc), locate postgresql-x64-xx, right-click, and select Start.

Error: 401 Unauthorized on API Request

Fix: Verify you logged in via /api/auth/login, copied the full token without extra spaces or quotes, and authorized it in Swagger.
















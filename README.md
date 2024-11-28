
# User Management System
- # Spring Boot Application with PostgreSQL, Hibernate, JPA, and JWT


---

## Team Members

- [Danushka](https://github.com/Nandalochana)
- [Naga](https://github.com/nagadheerajb)
- [Shubhangi](https://github.com/shubhanginaik)


# Teamwork

- Designing REST API endpoints
- Database schema
- Workable backend server with Spring Boot & Hibernate


# Project Description

This application is a Spring Boot-based backend service that provides:
- Secure JWT-based authentication.
- Data persistence using PostgreSQL.
- Entity management using Hibernate and JPA.


# Features

- **User Management**
    - User registration and login functionality
    - User authentication using email/password
    - User CRUD Operations: Create, read, update, and delete users
    - Custom roles and permissions (e.g., Admin, Dev, PM)
    - User profile management
    - Customizable DTOs: Easily extendable User DTOs for different context
    - Scalable Architecture: Designed with scalability and maintainability in mind
    - API Documentation: Swagger-enabled API documentation for easy integration and testing
  - **Projects and Workspaces**
  - Ability to create and manage multiple projects/workspaces
  - Project details: name, description, start/end dates, status
  - **Tasks and Issues**
  - Task/issue creation with title, description, priority, and deadline
  - Task/issue tracking: status updates (e.g., To-Do, In Progress, Done)
  - Activity tracking: status updates (e.g., To-Do, In Progress, Done)

# Recommendations for Timeline-Based Projects

This project was successfully completed in 3 weeks, with clear milestones and deliverables for each phase:

- Planning and Setup
-- Defined project scope, requirements, and deliverables.
-- Established the project structure and database schema.
-- Configured Spring Boot with security, JPA, and Swagger integrations.

- Development
* Implemented core features:
-- Entity creation and validation.
-- CRUD operations with role-based security.
-- database schema creation.
-- User registration and authentication.
-- JWT token generation and validation.

- Testing and Deployment
- Integrated Postman and Swagger for API testing.
- Performed code reviews to maintain quality and consistency
- Documented the project (API docs, README, etc.)
- Deployed a stable version for testing and feedback

# Prerequisites

-List the software and tools required to run the application.

- Java 17 or later
- Spring Boot
- PostgreSQL
- Maven
- Postman
- IDE (IntelliJ IDEA, Eclipse, etc.)
- Git
- JWT (JSON Web Token)
- Hibernate
- JPA (Java Persistence API)
- Lombok
- Swagger
  

# Getting Started

## Cloning the Repository
    
- Clone the repository using the following command:
    ```bash
    git clone https://github.com/nagadheerajb/fs19-java-backend.git
    ```
  
- Change the directory to the project root:
    ```bash
    cd fs19-java-backend
    ```

# Configuration

- Database Setup:

1. Configure the database in src/main/resources/application.properties:
- Initialize the database:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=create
    ```
  - Run the application
  - The application will automatically create the necessary tables in the database.
  - Once the database schema is created, stop the application and change the property to update:
    ```properties
    spring.jpa.hibernate.ddl-auto=update
    ```
    - Run the application again to apply the changes.
    
    
- The application will start on port 8080 by default.
- Open a web browser and navigate to `http://localhost:8080` to access the application.
- Use Postman or Swagger to test the API endpoints.


## Testing the application

-- put the property create in the application.properties file
- Run the application
- The application will automatically create the necessary tables in the database.
- run the test cases


# Code Structure

- The project follows a modular structure with separate packages for each layer:
- Controller Layer: Handles HTTP requests and responses.
- Service Layer: Contains business logic.
- Application Layer: Contains DTOs, mappers, and other application-specific classes.
- Repository Layer: Interacts with the database using JPA.
- DTOs: Data transfer objects for request and response payloads.
- config: Contains configuration classes for Swagger, JWT, and other configurations.


# API Endpoints

Please find the API documentation in the `api-docs` folder.


## Team Collaboration Tools

- Trello:
-- Managed the project board with task assignments and status updates.
-- Used columns like To Do, In Progress, Review, and Done to track progress.
* Code Reviews:
-- Regular peer reviews to ensure clean, maintainable code.
-- Integrated code review checklists for consistency.
* Communication:
-- Daily stand-ups to synchronize progress.
-- Slack/Email for discussions and quick resolutions.


## Menu

- [Vision](#vision)
- [Business Requirements](#business-requirements)
- [Requirements](#requirements)
- [Mandatory Features](#mandatory-features)
- [Optional Features](#optional-features)

---

## Vision

You are required to build a fullstack project management system similar to Trello, Jira, or Monday.

The project can be single- or multi-tenant.

The main requirements are as follows:

- **User Management**
- **Projects and Workspaces**
- **Tasks and Issues**

To take the project to the next level, consider these additional requirements:

- **Collaboration**
- **Real-time Collaboration**
- **Integration with Other Platforms**
- **Reporting and Analytics**

### Business Requirements

- Brainstorm the backend design in terms of entity structure and how they will interact. 
- Discuss the implementation of architecture: CLEAN, DDD, TDD, and any possible pattern your team want to apply (repository pattern, CQRS, etc.).
- **Deadline Management**: Use any project management tool of your choice to ensure timely delivery.

---

## Requirements

_For team assignment, only 1 member should fork the repo, then the admin can invite other members to contribute in the same repo. The rest of the team members, must fork from the common repo(admin repo), making PRs against the common repo when changes are needed. Remember to have a develop branch before merging to main. Each feature/schema/bug/issue should have its own branch, and only one member should work on one branch/file at a time. Before making any new branch, make sure to sync the fork and run `git pull` to avoid conflicts with the common team repo._

1. **Create ERD Diagram** with entities, attributes, and relationships. Include the ERD as an image in the project.

2. **Design the API Endpoints** to follow REST API architecture. Document the endpoints in `.md` files, providing detailed explanations of queries, parameters, request bodies, authentication requirements (if applicable), sample responses, and status codes.

3. **Basic Entities** (Additional entities may be included as needed):
	Possible entities:
   - Tenant
   - User
   - Project
   - Workspace
   - Task
   - Role
   - Permission
   - Comment (optional)
   - Notification (optional)

4. **Develop Backend Server** using CLEAN Architecture:

   - Use exception handler to provide meaningful responses to users.
   - Unit testing is required primarily for the Domain and Service layers. It would be ideal to test also the controllers and data access.
   - The README file should clearly describe the project with sufficient detail and a readable structure.

---

## Mandatory Features

- **User Management**
   - User registration and login functionality
   - User authentication using email/password or other methods (e.g., Google, GitHub)
   - Custom roles and permissions (e.g., HR, Dev, PM, Guest)

- **Projects and Workspaces**
   - Ability to create and manage multiple projects/workspaces
   - Project details: name, description, start/end dates, status

- **Tasks and Issues**
   - Task/issue creation with title, description, priority, and deadline
   - Task/issue tracking: status updates (e.g., To-Do, In Progress, Done)
   - Assign tasks/issues to team members or specific users

- **Boards and Kanban (UI-related)**
   - Customizable boards for different projects/workspaces
   - Card-based representation of tasks/issues on the board
   - Drag-and-drop reordering of cards
   - Board filters and custom views (e.g., due dates, priority)

---

## Optional Features

- **Collaboration and Communication**
   - Notification system: email/text updates on task/issue changes
   - Tagging team members in comments
   - File attachments and commenting on tasks/issues

- **Real-Time Collaboration**
   - Real-time commenting with instant updates for team members
   - Auto-updates for task statuses

- **Integrations and APIs**
   - Integration with Google Drive, Trello, Slack, GitHub issues, calendar, and email clients

- **Gantt Charts and Timelines**
   - Gantt chart visualization for project timelines

- **Reporting and Analytics**
   - Customizable dashboards for project leaders and stakeholders
   - Task/issue analytics: time spent, effort required, conversion rates, etc.
 
- **Team by Danushka,Naga & Shubhangi**

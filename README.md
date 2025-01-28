# Project Management Application Backend

## Overview

This project is a Spring Boot-based backend service designed for project management. It provides features such as secure JWT-based authentication, user and workspace management, task tracking, and real-time notifications. The application leverages PostgreSQL, Hibernate, JPA, and other modern tools to deliver a scalable and maintainable architecture.

## Features

- **Authentication & Authorization**: Secure JWT-based authentication with role-based access control to manage user permissions effectively.
- **User Management**: Includes user registration, login, CRUD operations for user profiles, and password management.
- **Workspace Management**: Enables managing multiple workspaces with options to assign and organize users.
- **Task Management**: Features CRUD operations for tasks with prioritization, deadlines, and status tracking.
- **Real-Time Notifications**: Leverages WebSocket technology to deliver instant notifications on workspace updates and task changes.
- **API Documentation**: Interactive API documentation through Swagger for seamless integration and testing.

## Project Structure

The project follows a modular structure for better scalability and maintenance, adhering to **Domain-Driven Design (DDD)** principles while incorporating elements of **Clean Architecture**. Below is a tree-view representation of the directory layout:

```
src/
├── main/
│   ├── java/
│   │   └── fs19/java/backend/
│   │       ├── application/         # Core application logic
│   │       │   ├── dto/            # Data Transfer Objects
│   │       │   ├── events/         # Domain events
│   │       │   ├── listeners/      # Event listeners
│   │       │   ├── mapper/         # Data mappers
│   │       │   └── service/        # Business logic services
│   │       ├── config/             # Configuration classes (e.g., JWT, RabbitMQ, Swagger)
│   │       ├── domain/             # Core domain entities and repositories
│   │       ├── infrastructure/     # Infrastructure and database-specific logic
│   │       ├── presentation/       # Controllers for handling HTTP requests
│   │       └── BackendApplication.java  # Main entry point
│   └── resources/
│       ├── application.properties  # Configuration file
│       ├── diagrams/               # ER diagram and other visual resources
│           └── ER Diagram.png      # Entity Relationship diagram
│       └── docs/                   # Static resources (if any)
├── test/                           # Unit and integration tests
```

## Resources

- **ER Diagram**: ![ER Diagram](src/main/resources/diagrams/ER%20Diagram.png)
- **API Documentation**: Accessible at `http://localhost:8080/swagger-ui.html` after starting the application.

## Prerequisites

- Java 17 or higher
- Maven
- [PostgreSQL](https://www.postgresql.org/)
- [RabbitMQ](https://www.rabbitmq.com/docs/download)

## Setup Instructions

### Step 1: Clone the Repository

```bash
git clone https://github.com/nagadheerajb/Project_Management_Backend.git
```

### Step 2: Install PostgreSQL and RabbitMQ

- **PostgreSQL**:
  - Install PostgreSQL. Refer to the [PostgreSQL installation guide](https://www.postgresql.org/).
  - Create a database named `javabackenddb`.
  - Set up a user with appropriate privileges.

- **RabbitMQ**:
  - Install RabbitMQ. Refer to the [RabbitMQ installation guide](https://www.rabbitmq.com/docs/download).
  - Ensure it is running.

### Step 3: Configure the Application

Update the configuration file `src/main/resources/application.properties` with the following example:

```properties
spring.application.name=<your application name>
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=<create/none>
spring.jpa.show-sql=<true/false>
spring.jpa.properties.hibernate.format_sql=<true/false>
spring.datasource.url=jdbc:postgresql://localhost:5432/javabackenddb
spring.datasource.username=<your_username>
spring.datasource.password=<your_password>
spring.rabbitmq.host=<localhost>
spring.rabbitmq.port=<5672>
spring.rabbitmq.username=<guest>
spring.rabbitmq.password=<guest>
spring.rabbitmq.virtual-host=/
server.port=8080
jwt.secret=your_jwt_secret_key
springdoc.swagger-ui.doc-expansion=none
springdoc.swagger-ui.default-model-expand-depth=-1
springdoc.swagger-ui.default-models-expand-depth=-1
springdoc.swagger-ui.filter=true
springdoc.swagger-ui.deep-linking=true
springdoc.swagger-ui.show-extensions=true
```

### Step 4: Build the Project

Navigate to the project directory and build the project:

```bash
mvn clean install
```

### Step 5: Run the Application

Start the application:

```bash
mvn spring-boot:run
```

### Step 6: Access the Application

- **API Documentation**: `http://localhost:8080/swagger-ui/index.html`
- **Default Port**: `8080`

## Testing

### Running Unit Tests

Run unit tests to validate the functionality of individual components:

```bash
mvn test
```

## Tools and Technologies

| Tool/Technology | Purpose                     |
|-----------------|-----------------------------|
| Spring Boot     | Backend framework           |
| PostgreSQL      | Relational database         |
| Hibernate & JPA | ORM for database interaction|
| JWT             | Secure authentication       |
| RabbitMQ        | Asynchronous messaging      |
| Swagger         | API documentation           |
| Lombok          | Simplifies boilerplate code |
| JUnit           | Unit testing framework      |

## Contribution Guidelines

1. Fork the repository.
2. Create a feature branch using the Git Flow model:
   - **Feature branches**: Used for new features. Naming convention: `feature/<name>`
   - **Hotfix branches**: Used for urgent fixes. Naming convention: `hotfix/<name>`
   - **Develop branch**: Integration branch for all features. 
   - **Main branch**: Stable production-ready code.
3. Commit your changes:

```bash
git commit -m 'Add your message'
```

4. Push to the branch:

```bash
git push origin feature/your-feature
```

5. Open a pull request.

## Contact

For any queries or support, reach out:

- Email: [nagadheerajb@gmail.com](mailto:nagadheerajb@gmail.com?subject=Support%20Request&body=Please%20describe%20your%20issue%20here.)
- GitHub Issues: [Open an Issue](https://github.com/nagadheerajb/Project_Management_Backend/issues)

---

### Team Members

- [Naga](https://github.com/nagadheerajb)
- [Danushka](https://github.com/Nandalochana)
- [Shubhangi](https://github.com/shubhanginaik)


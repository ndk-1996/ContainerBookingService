# Container Booking Service
This project is a Container Booking Service built using Java and Spring Boot with WebFlux for reactive programming. It utilizes MongoDB as the database to store booking information.
It exposes two POST for checking booking availability and creating bookings.

## Tech Stack

Install and setup the following components excluding the swagger/openapi to run service on local machine:

| Component             | Version       | Description            |
|-----------------------|---------------|------------------------|
| **Java**              | 21            | Language version       |
| **Spring Boot**       | 3.x (WebFlux) | Reactive web framework |
| **Maven**             | 3.9.x         | Build automation tool  |
| **MongoDB**           | 8.2.1         | NoSQL database         |
| **mongosh**           | 2.5.9         | MongoDB Shell          |
| **Swagger / OpenAPI** | 3.x           | API documentation UI   |

## Project Setup
Clone the current repo and open it using your favorite IDE (Eclipse, IntelliJ, VS Code, etc.)
Preferably, use IntelliJ IDEA for better Maven and Spring Boot support.

## Database
Start and run the mongodb server on your local machine. You can leave it to the default configuration.

```bash
  mongod --dbpath <your_db_path>
```

```bash
  mongosh
```

## Building and Running the Application

```bash
  mvn clean install
```

```bash
  mvn spring-boot:run
```
Alternatively you can the build and run the application using intellij IDE as well.

Once started, the server will be available at:

🌐 http://localhost:8080

## Swagger UI
Once the application is running, you can access the Swagger UI for API documentation and testing at:

👉 http://localhost:8080/swagger-ui.html

You can explore and test all API endpoints interactively.

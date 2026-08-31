# Student Application System — Backend

Spring Boot + MySQL REST API for the Student Application System.

## Requirements

- Java 17+
- Maven 3.9+
- MySQL 8+

## 1. Create the database

Run:

    mysql -u root -p < database/schema.sql

## 2. Configure MySQL

Edit:

`src/main/resources/application.properties`

Change:

`spring.datasource.password=CHANGE_ME`

to your MySQL password.

## 3. Start the API

    mvn spring-boot:run

API runs at:

`http://localhost:8080`

## Endpoints

### Submit application

`POST /api/applications`

Example JSON:

    {
      "firstName":"Joshua",
      "lastName":"Samuel",
      "dateOfBirth":"2004-01-10",
      "gender":"Male",
      "email":"joshua@example.com",
      "phone":"0700000000",
      "address":"Kampala",
      "previousSchool":"ABC Secondary School",
      "yearCompleted":2023,
      "program":"Computer Science",
      "intake":"August",
      "additionalInfo":"First application"
    }

### Check by application number

`GET /api/applications/APP-2026-0001`

### Check by email

`GET /api/applications/search?email=joshua@example.com`

### Admin/list applications (development endpoint)

`GET /api/applications`

### Change status

`PATCH /api/applications/APP-2026-0001/status?value=Accepted`

Valid statuses: Pending, Under Review, Accepted, Rejected.

## Important security note

This is a development backend. The application endpoints are currently open to make frontend integration easy. Before using it for real student records, add JWT/session authentication, role-based admin authorization, secure CORS, HTTPS, rate limiting, file-upload validation, audit logs, and secrets through environment variables.

## GitHub

From the project directory:

    git init
    git add .
    git commit -m "Add Spring Boot backend"
    git branch -M main
    git remote add origin YOUR_REPOSITORY_URL
    git push -u origin main

# Student Application System v2

Full-stack-ready backend with:
- Student registration/login using HTTP Basic authentication
- Admin authentication and role protection
- Student application records
- Document upload (PDF/JPG/PNG, max 10MB)
- Secure document ownership checks
- Admin dashboard statistics
- Admin application/status management
- MySQL + Spring Data JPA

## Demo admin

Email: `admin@school.com`
Password: `Admin@123`

Change this before production.

## Run

Requirements: Java 17+, Maven 3.9+, MySQL 8+.

Create database:

    mysql -u root -p < database/schema.sql

Edit `src/main/resources/application.properties` and set the MySQL password.

Start:

    mvn spring-boot:run

API: http://localhost:8080

## Authentication

Students register:

    POST /api/auth/register

Then use HTTP Basic authentication for protected endpoints with the registered email/password.

Admin is seeded automatically on startup.

## Important

This is a strong development foundation, not a production admissions deployment. Before production, add HTTPS, JWT or secure session authentication, CSRF protection appropriate to the chosen auth architecture, strict CORS, rate limiting, account lockout, password reset/email verification, antivirus scanning for uploads, secure object storage, audit logging, and environment/secret management.

The uploaded `uploads/` directory is intentionally gitignored so student documents are not committed to GitHub.

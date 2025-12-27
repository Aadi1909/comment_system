# Spring Comments System API

JWT-secured Spring Boot 3.2 API for user registration/login and threaded comments. Uses Spring Security with stateless JWT auth, MySQL for persistence, and Redis cache support. Includes role-based access and admin-only endpoint examples. The project is in active development; delete workflows are planned and not fully implemented yet.

## Tech Stack
- Java 17, Spring Boot 3.2 (Web, Security, Data JPA)
- JWT (jjwt 0.11.5) for stateless authentication
- MySQL (default `comments_db`) with Hibernate/JPA
- Redis cache (optional; configured host/port)
- Gradle build with Lombok
- Tests: JUnit 5, spring-security-test

## Key Features
- Register/login flows returning JWT access tokens.
- Role-based authorization with `@PreAuthorize` examples.
- Public and secured endpoints, including admin-only delete sample (full delete lifecycle coming soon).
- Comment CRUD: list, create, reply, update; pagination for listings.
- H2 in-memory profile commented in `application.properties` for easy local trials.

## Upcoming Work
- Finalize comment deletion (soft delete and admin purge flows).
- Rate limiting and basic abuse protection.
- Refresh tokens and token revocation list backed by Redis.
- Email/password reset flow and optional 2FA.
- Audit logging for security-relevant actions.

## Prerequisites
- Java 17+
- MySQL 8+ running with a database named `comments_db` (user `appuser`, password `apppass` by default)
- Redis running on `localhost:6379` (optional; disable if not needed)
- Gradle wrapper is included; no global Gradle install required.

## Configuration
Default config is in `src/main/resources/application.properties`:
- `spring.datasource.url=jdbc:mysql://localhost:3306/comments_db`
- `spring.datasource.username=appuser`
- `spring.datasource.password=apppass`
- `spring.jpa.hibernate.ddl-auto=update`
- Redis: `spring.data.redis.host=localhost`, `spring.data.redis.port=6379`

For a quick in-memory setup, uncomment the H2 section in the same file and comment out the MySQL lines.

## Running Locally
1) Start MySQL and Redis (or enable H2 as noted above).
2) Build & run:
```
./gradlew bootRun
```
Windows: `gradlew.bat bootRun`
3) The app starts on `http://localhost:8080`.

## API Overview
- Public:
  - `POST /auth/register` — register a user.
  - `POST /auth/login` — authenticate and obtain JWT.
  - `GET /free` — health-style free endpoint.
  - `GET /comment` — list root comments with pagination.
- Authenticated (Bearer token):
  - `GET /secured` — sample secured endpoint.
  - `GET /comment/me` — list comments by the logged-in user.
  - `POST /comment` — create root comment.
  - `POST /comment/reply?parentId={id}` — reply to a comment.
  - `POST /comment/update?updateId={id}` — update own comment.
- Admin only:
  - `DELETE /delete` — sample admin-protected endpoint.

## Auth Flow
1) Register:
```
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password","name":"User"}'
```
2) Login (receive `token`):
```
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password"}'
```
3) Call secured endpoint:
```
curl http://localhost:8080/comment/me \
  -H "Authorization: Bearer <token>"
```

## Testing
```
./gradlew test
```

## Docker & Compose
- `Dockerfile` and `docker-compose.yml` are provided. Update environment variables as needed, then run `docker compose up` to start app plus dependencies.

## Project Structure
- `src/main/java/com/example/springsecurity` — application code.
- `config` — security and infrastructure configs.
- `controller` — REST controllers (auth, comments, samples).
- `dto`, `entity`, `repository`, `service` — domain layers for comments/users.

## Notes
- Passwords are encoded with BCrypt.
- Security filter chain is stateless; all non-public endpoints require `Authorization: Bearer <token>`.
- `spring.jpa.hibernate.ddl-auto=update` is convenient for dev; adjust for production.

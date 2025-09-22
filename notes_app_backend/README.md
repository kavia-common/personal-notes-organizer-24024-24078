# Notes App Backend (Ocean Professional)

Modern Spring Boot REST API with JWT authentication and CRUD operations for personal notes.

- Primary: #2563EB
- Secondary/Success: #F59E0B
- Error: #EF4444
- Minimal, clean API design with rounded-corner philosophy reflected in simplicity and clarity.

## Run
./gradlew bootRun

Swagger UI: /swagger-ui.html  
API Docs: /api-docs

## Auth
- POST /api/auth/register
  - body: { "username": "user", "password": "pass" }
  - returns: { "token": "..." }
- POST /api/auth/login
  - body: { "username": "user", "password": "pass" }
  - returns: { "token": "..." }

Use token as: Authorization: Bearer <token>

## Notes
- GET /api/notes?page=0&size=10
- POST /api/notes
  - { "title": "My note", "content": "..." }
- GET /api/notes/{id}
- PUT /api/notes/{id}
  - { "title": "Updated", "content": "..." }
- DELETE /api/notes/{id}

## Environment
Set via .env (or environment):
- APP_JWT_SECRET=your-256-bit-secret
- APP_JWT_TTL_SECONDS=3600

H2 in-memory DB is used by default for quick start.

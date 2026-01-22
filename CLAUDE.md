# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Fullstack portfolio application with Angular 20.3 frontend and Spring Boot 3.5.9 backend using JWT authentication.

## Build & Development Commands

### Frontend (from `frontend/` directory)
```bash
npm start          # Start dev server on localhost:4200
npm run build      # Production build
npm test           # Run Jasmine/Karma tests
ng generate component component-name   # Scaffold new component
```

### Backend (from `backend/` directory)
```bash
./mvnw spring-boot:run   # Start Spring Boot (requires env vars)
mvn clean install        # Build
mvn test                 # Run tests
```

### Environment Variables Required
- `MYSQL_URL`, `MYSQL_USERNAME`, `MYSQL_PASSWORD` - MySQL connection
- `MONGODB_URI` - MongoDB connection
- `JWT_SECRET` - JWT signing key
- `ADMIN_PASS` - Initial admin password

## Architecture

### Backend Structure
- **API Base Path**: All endpoints prefixed with `/api` (configured in application.properties)
- **Authentication**: JWT tokens in HTTP-only cookies, 30-minute expiration
- **Security Filters**: JwtAuthFilter → LoginRateLimitFilter → Spring Security
- **Databases**: MySQL (JPA for User entities), MongoDB (Application entities)

**Key Packages**:
- `controller/` - REST endpoints (AuthController, AdminController, ApplicationController)
- `service/` - Business logic (JwtService, UserService, ApplicationService)
- `security/` - JWT filter, rate limiting with Bucket4j
- `config/` - SecurityConfig (CSRF, CSP headers, CORS)

**Public Endpoints**: `/auth/login`, `/csrf`, `/csp-report`
**Protected Endpoints**: `/admin/**` requires ROLE_ADMIN

### Frontend Structure
Uses standalone components (no NgModules), signals for state, and OnPush change detection.

See `frontend/.claude/CLAUDE.md` for Angular-specific conventions:
- Use `input()`/`output()` functions instead of decorators
- Use native control flow (`@if`, `@for`) instead of `*ngIf`, `*ngFor`
- Use `inject()` function instead of constructor injection
- Use `class` bindings instead of `ngClass`
- Use `NgOptimizedImage` for static images

## Security Implementation

- **CSRF**: Cookie-based tokens via `CookieCsrfTokenRepository`
- **Rate Limiting**: Bucket4j on login endpoint
- **CSP**: Configured in SecurityConfig, reports to `/api/csp-report`
- **Passwords**: BCrypt encoding

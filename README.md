# 🔐 Auth Backend - Spring Boot

Sistema de autenticación y autorización completo desarrollado con **Spring Boot**, incluyendo login tradicional, autenticación con proveedores externos (Google y GitHub), manejo de JWT, refresh tokens y control de roles.

---

## 🚀 Características

- Registro de usuarios (email + contraseña)
- Login con JWT (stateless authentication)
- Login social con:
  - Google
  - GitHub
- Refresh tokens
- Logout
- Control de roles (USER / ADMIN)
- Protección de endpoints con Spring Security
- Integración con PostgreSQL

---

## 🧱 Arquitectura

El proyecto sigue una arquitectura en capas:
controller → service → repository → database

---

## 🛠️ Tecnologías

- Java 17
- Spring Boot 4
- Spring Security
- Spring Data JPA
- OAuth2 Client
- JWT
- PostgreSQL
- Docker

---

## ⚙️ Configuración

Variables de entorno:

```
GOOGLE_CLIENT_ID
GOOGLE_CLIENT_SECRET
GITHUB_CLIENT_ID
GITHUB_CLIENT_SECRET
```

Construir y ejecutar:

```
docker compose up --build
```

```
./mvnw spring-boot:run
```

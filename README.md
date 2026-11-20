# 🌴 Microservicio 3: Agencias y Catálogo (`ms-agencias-catalogo`)

**Proyecto Formativo SENA:** Huila Travel Expedition (HTE)  
**Responsable:** Juan Diego Oteca Pedreros (`com.oteca.agenciascatalogo`)  
**Puerto Local:** `8083`  
**Base de Datos MySQL:** `db_hte_agencias_catalogo`  
**Swagger UI:** `http://localhost:8083/swagger-ui.html`

---

## 📌 Descripción y Alcance
Este microservicio gestiona de manera autónoma el registro, validación de RNT y actualización de contacto de las **Agencias de Viajes del Huila** (RF1, RF3), así como el **Catálogo de Categorías y Planes Turísticos** con filtros de búsqueda en tiempo real (RF4, RF6, RF7, RF17).

## 🛠️ Tecnologías Implementadas
- **Java 21 & Spring Boot 3.4.3**
- **Spring Data JPA + Hibernate (`ddl-auto=validate`)**
- **Flyway (`V1__init_agencias_catalogo.sql`)** para control de versiones del esquema SQL
- **Jakarta Bean Validation (`@Valid`, `@NotBlank`, `@Email`, `@Positive`, `@Min`)**
- **Estándar Envolvente Universal (`ApiResponse<T>`)** y manejo centralizado de errores con **`@RestControllerAdvice` (`GlobalExceptionHandler`)**
- **Swagger / Springdoc OpenAPI** para documentación interactiva

# Ecom — E-Commerce REST API (Spring Boot)

A REST API for an e-commerce platform, built with Spring Boot. This project is a work in progress — the **Category** module is fully functional, and additional modules and features are actively being developed.

## 🚀 Tech Stack

- **Java**
- **Spring Framework**
- **Spring Boot**
- **Spring Web** — for building REST APIs
- **Spring Data JPA (Hibernate)** — for ORM and database interaction
- **H2 Database** — in-memory database for development/testing
- **Lombok** — to reduce boilerplate code (`@Data`, constructors, etc.)
- **Spring Validation** (`jakarta.validation`) — for request payload validation
- **Spring Exception Handling** (`@RestControllerAdvice`) — for centralized, clean error responses
- **Postman** — for API testing

## 📁 Project Structure

```
com.ecommerce.ecom
│
├── model
│   └── Category.java                  # JPA entity with validation constraints
│
├── repositories
│   └── CategoryRepository.java        # Spring Data JPA repository
│
├── service
│   ├── CategoryService.java           # Service interface
│   └── CategoryServiceImpl.java       # Service implementation (business logic)
│
├── controller
│   └── CategoryController.java        # REST endpoints
│
├── exceptions
│   ├── ApiException.java              # Custom exception (e.g. duplicate category)
│   ├── ResourceNotFoundException.java # Custom exception (e.g. category not found)
│   └── GlobalExceptionHandler.java    # @RestControllerAdvice — maps exceptions to HTTP responses
│
└── EcomApplication.java               # Main Spring Boot application class
```

## ✅ Features Implemented

### Category Module
Full CRUD functionality for managing product categories, with endpoints split into `public` and `admin` routes:

| Method | Endpoint                            | Description                          | Success Response                      |
|--------|---------------------------------------|----------------------------------------|------------------------------------------|
| GET    | `/api/public/categories`              | Get all categories                     | `200 OK` — list of categories            |
| POST   | `/api/admin/categories`                | Create a new category                  | `201 CREATED` — confirmation message     |
| PUT    | `/api/admin/categories/{categoryId}`   | Update an existing category            | `200 OK` — confirmation message          |
| DELETE | `/api/admin/categories/{categoryId}`   | Delete a category                       | `200 OK` — confirmation message          |

**Category entity validation (`Category.java`):**
- `categoryName` must not be null or empty
- `categoryName` must be at least 2 characters long
- `categoryName` is unique at the database level (`@Column(unique = true)`)

**Other features included:**
- Layered architecture (Controller → Service → Repository)
- Request validation using `@Valid` and Bean Validation annotations (`@NotNull`, `@NotEmpty`, `@Size`)
- Centralized exception handling via `GlobalExceptionHandler` (`@RestControllerAdvice`), covering:
  - `MethodArgumentNotValidException` → `400 BAD REQUEST` with a field → error-message map
  - `DataIntegrityViolationException` → `409 CONFLICT` for duplicate `categoryName`, `400 BAD REQUEST` otherwise
  - `ResourceNotFoundException` → `404 NOT FOUND` (e.g. category ID doesn't exist)
  - `ApiException` → `400 BAD REQUEST` (e.g. category name already exists, no categories found)
- H2 in-memory database for quick local testing

### Error Handling Examples

**Validation error** (`POST /api/admin/categories` with empty `categoryName`):
```json
{
  "categoryName": "Category name cannot be empty"
}
```

**Duplicate category** (`POST /api/admin/categories` with an existing name):
```
"Category with the name: Electronics already exists"
```

**Category not found** (`DELETE /api/admin/categories/99`):
```
"Category not found with categoryId: 99"
```

## 🛠️ Getting Started

### Prerequisites
- Java 17+ (or your configured JDK version)
- Maven
- Postman (or any API testing tool)

### Running the Application

```bash
# Clone the repository
git clone <your-repo-url>
cd <project-folder>

# Run the application
mvn spring-boot:run
```

The application will start on:
```
http://localhost:8080
```

### Accessing the H2 Database Console

```
http://localhost:8080/h2-console
```

Use the JDBC URL, username, and password configured in `application.properties` / `application.yml`.

## 📬 API Testing

All endpoints can be tested using **Postman**.


## 🗺️ Roadmap / Planned Enhancements

This project is under active development. Planned next steps include:

- [ ] **Pagination & Sorting** — for list endpoints (e.g., categories, products)
- [ ] **Product Module** — full CRUD with category relationships
- [ ] **Spring Security** — authentication & authorization (JWT-based)
- [ ] **Migrate from H2 to a production-grade SQL database** (e.g., MySQL/PostgreSQL)
- [ ] **DTO layer refinement** — separating internal entities from API request/response models
- [ ] **Unit & Integration Testing** — using JUnit and Mockito
- [ ] **Global response wrapper** — standardized API response format
- [ ] **API Documentation** — Swagger/OpenAPI integration
- [ ] **Order, Cart, and User modules**

## 🤝 Contributing

This is currently a personal/learning project, but suggestions and feedback are welcome via issues or pull requests.

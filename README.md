# Storage Manager System - API

## 📖 Overview
This is a **Spring Boot** application for managing inventory, orders, and user authentication in a storage management system. It includes **JWT-based authentication** and API documentation with **Swagger**.

## 🚀 Technologies Used 
- **Spring Boot** (REST API)
- **Spring Security** (JWT Authentication)
- **Spring Data JPA** (Database ORM)
- **MySQL** (Database)
- **Docker** (Containerization)
- **Swagger** (API Documentation)
- **OpenJDK 21** (Java Development Kit)

## ✍️ Prerequisites 
- Install **OpenJDK 21**
- Install **Docker & Docker Compose** (for running MySQL)
- Install **Maven** (for dependency management)

## 📜 Setup Instructions

### 1. Clone the Repository
```sh
git clone https://github.com/Marcelo-Augustto/storagemanagerapi
cd storagemanagerapi
```

### 2. Configure the Database
You will find all the database configuration in `src/main/resources/application.properties`. The first time you run the database, make shure that `spring.jpa.hibernate.ddl-auto` is set to `create`. After that, change to `update`, if you don't each time you run `docker compose up` it will drop all tables and create new ones.


### 3. Run MySQL using Docker
```sh
docker compose up -d
```

### 4. Build and Run the Application
```sh
mvnw clean install
mvnw spring-boot:run
```

## 🌐 API Endpoints 

### User Authentication
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/api/users/register` | Register a new user |
| POST | `/api/users/login` | Login and receive JWT token |
| GET | `/api/users/{id}` | Get user details |
| PUT | `/api/users/{id}` | Update user info |
| DELETE | `/api/users/{id}` | Delete a user |

### Product Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID |
| POST | `/api/products` | Create a new product |
| PUT | `/api/products/{id}` | Update product details |
| DELETE | `/api/products/{id}` | Delete a product |

### Order Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/api/orders/{userId}` | Create a new order for a user |
| GET | `/api/orders/{userId}` | Get orders for a user |
| PUT | `/api/orders/{orderId}/status` | Update order status |
| GET | `/api/orders/details/{orderId}` | Get order details |
| DELETE | `/api/orders/{orderId}` | Cancel an order |

### Stock Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| PUT | `/api/stocks/update/{productId}` | Update stock quantity |
| GET | `/api/stocks/low-stock/{productId}` | Check if stock is low |

### Reports
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/reports` | Get all reports |
| POST | `/api/reports/stock-movement` | Generate stock movement report |
| POST | `/api/reports/best-selling` | Generate best-selling products report |
| POST | `/api/reports/revenue` | Generate revenue report |

## 🔐 Security & Authentication 
This API uses **JWT (JSON Web Token)** for authentication. After login, users receive a token that must be included in the `Authorization` header for all secured endpoints:
```sh
Authorization: Bearer your-jwt-token
```


## 📄 Swagger Documentation 
Once the application is running, visit:
```
http://localhost:8080/swagger-ui/index.html
```
to explore and test the API endpoints.



## 🧪 Running Tests 
The project includes unit tests and integration tests using JUnit and Mockito. To run all tests:
```sh
mvnw test
```


## 📁 Project Structure 
```sh
.
├── compose.yaml
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── storagemanagerapi
│   │   │               ├── auth
│   │   │               │   ├── JwtFilter.java
│   │   │               │   └── JwtUtil.java
│   │   │               ├── config
│   │   │               │   ├── SecurityConfig.java
│   │   │               │   └── SwaggerConfig.java
│   │   │               ├── controller
│   │   │               │   ├── IndexController.java
│   │   │               │   ├── OrderController.java
│   │   │               │   ├── ProductController.java
│   │   │               │   ├── ReportController.java
│   │   │               │   ├── StockController.java
│   │   │               │   └── UserController.java
│   │   │               ├── enums
│   │   │               │   └── OrderStatus.java
│   │   │               ├── exception
│   │   │               │   ├── GlobalExceptionHandler.java
│   │   │               │   ├── order
│   │   │               │   │   └── OrderNotFoundException.java
│   │   │               │   ├── product
│   │   │               │   │   ├── ProductAlreadyExistsException.java
│   │   │               │   │   └── ProductNotFoundException.java
│   │   │               │   ├── report
│   │   │               │   │   ├── DataUnavailableException.java
│   │   │               │   │   └── ReportNotFoundException.java
│   │   │               │   ├── stock
│   │   │               │   │   ├── InsufficientStockException.java
│   │   │               │   │   └── StockNotFoundException.java
│   │   │               │   └── user
│   │   │               │       ├── InvalidCredentialsException.java
│   │   │               │       ├── UserAlreadyExistsException.java
│   │   │               │       └── UserNotFoundException.java
│   │   │               ├── model
│   │   │               │   ├── OrderItem.java
│   │   │               │   ├── Order.java
│   │   │               │   ├── Product.java
│   │   │               │   ├── Report.java
│   │   │               │   ├── Stock.java
│   │   │               │   └── User.java
│   │   │               ├── repository
│   │   │               │   ├── OrderRepository.java
│   │   │               │   ├── ProductRepository.java
│   │   │               │   ├── ReportRepository.java
│   │   │               │   ├── StockRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               ├── service
│   │   │               │   ├── OrderService.java
│   │   │               │   ├── ProductService.java
│   │   │               │   ├── ReportService.java
│   │   │               │   ├── StockService.java
│   │   │               │   └── UserService.java
│   │   │               └── StorageManagerApiApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── storagemanagerapi
│                       ├── controller
│                       │   ├── OrderControllerTest.java
│                       │   ├── ProductControllerTest.java
│                       │   └── UserControllerTest.java
│                       ├── StorageManagerApiApplicationTests.java
│                       ├── TestcontainersConfiguration.java
│                       └── TestStorageManagerApiApplication.java
```

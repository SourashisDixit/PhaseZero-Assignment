# phasezero-catalog-service

A Spring Boot backend service for managing a product catalogue using REST APIs. It supports adding products, listing, searching, filtering, sorting, and calculating total inventory value, as per the PhaseZero Java backend assignment. 

---

## 1. Tech Stack

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* H2 in-memory database

---

## 2. How to Build and Run

### Prerequisites

* Java 17 installed
* Maven installed (or use `mvnw`/`mvnw.cmd` included in the project)

### Run with Maven (command line)

1. Go to the project root (where `pom.xml` is located):

   ```bash
   mvn clean package
   mvn spring-boot:run
   ```

2. The application runs on:

   ```text
   http://localhost:8085
   ```

### Run in Eclipse

1. Import the project as **Existing Maven Project**.
2. Wait for Maven dependencies to download.
3. Open `Application.java` in `com.phasezero.catalog`.
4. Run as **Java Application**.
5. The app will start on port `8085`.

### H2 Console (optional)

* URL: `http://localhost:8085/h2-console`
* JDBC URL: `jdbc:h2:mem:productdb`
* Username / Password: as configured in `application.properties`.

---

## 3. Data Model

### Product Entity

Fields:

* `id` (Long) – auto-generated primary key
* `partNumber` (String) – unique business key
* `partName` (String) – product name (stored in lowercase)
* `category` (String) – product category
* `brand` (String) – brand name
* `price` (double) – unit price (non-negative)
* `stock` (int) – current inventory count (non-negative)
* `createdAt` (timestamp) – product creation time (set automatically)

Additional fields explanation:

* **id**: Added as a primary key to support JPA and database identity.
* **brand**: Added to capture brand information, used in validation.
* **createdAt**: Added to track when a product was created.

### Business Rules

* `partName` is stored in lowercase (normalized before saving).
* `partNumber` must be unique.
* `price` cannot be negative.
* `stock` cannot be negative.
* Required fields (e.g., partNumber, partName, category, brand, price, stock) must be provided.

---

## 4. Storage Design

The application uses **H2 in-memory database**.

* **Why H2**:

  * Lightweight and easy to configure.
  * Suitable for local development and testing without external DB setup.

* **Schema Management**:

  * Managed via JPA annotations on the `Product` entity.
  * Hibernate creates and updates the schema automatically using `spring.jpa.hibernate.ddl-auto=update`.

Note: Because it is an in-memory database, data is reset when the application restarts (for the current configuration).

---

## 5. Application Structure

* **Controller Layer**

  * Package: `com.phasezero.catalog.controller`
  * Class: `ProductController`
  * Handles HTTP requests under `/products`.
  * Delegates business logic to `ProductService` and uses `ValidationService` for input checks.

* **Service Layer**

  * Package: `com.phasezero.catalog.service` and `serviceImpl`
  * `ProductService` / `ProductServiceImpl`

    * Implements business logic: add product, list, search, filter, sort, inventory value.
    * Interacts with the repository.
  * `ValidationService` / `ValidationServiceImpl`

    * Validates incoming `ProductDto` payloads.
    * Enforces business rules (required fields, brand, non-negative values, uniqueness checks, partName normalization).

* **Repository Layer**

  * Package: `com.phasezero.catalog.repository`
  * Interface: `ProductRepository` (JPA repository)
  * Handles database operations (save, find, search, etc.) using H2.

* **DTO Layer**

  * Package: `com.phasezero.catalog.dto`
  * `ProductDto`: used as request payload for product creation.
  * `Response`: wrapper used to send response code, message, and data in a consistent format.

* **Entity Layer**

  * Package: `com.phasezero.catalog.entity`
  * `Product`: JPA entity mapped to `products` table in H2.

---

## 6. REST API Endpoints

Base URL:

```text
http://localhost:8085/products
```

### 1) Add New Product

* **Method**: `POST`
* **Path**: `/products/save`
* **Description**: Creates a new product after applying all validations and business rules.

**Request (JSON):**

```json
{
  "partNumber": "P1001",
  "partName": "Hydraulic Filter",
  "category": "filter",
  "brand": "Bosch",
  "price": 1200,
  "stock": 10
}
```

**Successful Response (example):**

```json
{
  "responseCode": 200,
  "message": "product saved successfully",
  "data": {
    "id": 1,
    "partNumber": "P1001",
    "partName": "hydraulic filter",
    "category": "filter",
    "brand": "Bosch",
    "price": 1200,
    "stock": 10,
    "createdAt": "2025-12-10T15:50:00"
  }
}
```

On validation error (example):

```json
{
  "responseCode": 400,
  "message": "Please provide the brand."
}
```

On duplicate `partNumber` (example):

```json
{
  "responseCode": 409,
  "message": "Product with this partNumber already exists."
}
```

---

### 2) List All Products

* **Method**: `GET`
* **Path**: `/products/get/all`
* **Query Param** (optional): `category`
* **Description**:

  * Without `category`: returns all products.
  * With `category`: returns products filtered by category.

**Example:**

```text
GET http://localhost:8085/products/get/all
GET http://localhost:8085/products/get/all?category=filter
```

**Sample Response:**

```json
{
  "responseCode": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "partNumber": "P1001",
      "partName": "hydraulic filter",
      "category": "filter",
      "brand": "Bosch",
      "price": 1200,
      "stock": 10,
      "createdAt": "2025-12-10T15:50:00"
    }
  ]
}
```

---

### 3) Search Products by Name

* **Method**: `GET`
* **Path**: `/products/search`
* **Query Param**: `name`
* **Description**: Returns products whose `partName` contains the given text, case-insensitive.

**Example:**

```text
GET http://localhost:8085/products/search?name=filter
```

---

### 4) Sort Products by Price (Ascending)

* **Method**: `GET`
* **Path**: `/products/sorted/price`
* **Description**: Returns all products sorted by price in ascending order.

**Example:**

```text
GET http://localhost:8085/products/sorted/price
```

---

### 5) Total Inventory Value

* **Method**: `GET`
* **Path**: `/products/inventory/value`
* **Description**: Returns the total inventory value:
  `sum(price * stock)` for all products.

**Example:**

```text
GET http://localhost:8085/products/inventory/value
```

**Sample Response:**

```json
{
  "responseCode": 200,
  "message": "success",
  "data": 25000.0
}
```

---

## 7. Validation Rules Implemented

For product creation:

* `partNumber` is required and must be unique.
* `partName` is required and normalized to lowercase before saving.
* `category` is required.
* `brand` is required.
* `price` is required and must be ≥ 0.
* `stock` is required and must be ≥ 0.
* On validation failure, a consistent JSON response is returned with `responseCode` and `message`.

---

## 8. Assumptions and Limitations

* Uses H2 in-memory database, so data is lost when the application restarts (with current configuration).
* No authentication or authorization is implemented.
* No update or delete endpoints are implemented; only create and read operations are provided.
* Error responses are wrapped in a custom `Response` object instead of a global exception handler with HTTP error bodies.
* Pagination and advanced filtering are not implemented.

---



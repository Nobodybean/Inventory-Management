# Spring Boot Inventory Management System

This project is an Inventory Management System built with Spring Boot, Gradle, and Java 17. The system manages products, categories, and inventory stock levels. It exposes RESTful APIs for these operations, integrates with a MySQL database, and includes features like JWT authentication, role-based access control, and email notifications for low-stock products.

## Features

### Core Functionality
1. **Category Management**
   - CRUD operations for categories
   - One-to-Many relationship with products

2. **Product Management**
   - CRUD operations for products
   - Filter products by category
   - Search products by name (with pagination)

3. **Inventory Management**
   - Update stock levels (add or remove stock)
   - Validate stock deduction for insufficient quantities
   - Generate a report of low-stock products (stock < 10)

### Bonus Features
- **JWT Authentication**
  - Secure endpoints with role-based access control (ADMIN and USER roles)
- **Email Notifications**
  - Notify administrators when products are low on stock
- **Swagger Documentation**
  - Auto-generated API documentation via Swagger UI

### Future Enhancements
- Deployment on Heroku and AWS
- CI/CD pipeline integration
- Monitoring and logging configuration

---

## Project Setup in IntelliJ IDEA

1. Open IntelliJ and create a new Spring Boot project.
2. Use the following settings:
   - **Name:** `inventory-management`
   - **Language:** Java
   - **Build System:** Gradle
   - **JDK:** Java 17

3. Select the following dependencies:
   - Spring Web
   - Spring Data JPA
   - MySQL Driver
   - Spring Security
   - Validation
   - Java Mail Sender
   - Spring Boot DevTools
   - Lombok

4. After project creation, update `build.gradle` with additional dependencies:

   ```gradle
   dependencies {
       implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
       implementation 'org.springframework.boot:spring-boot-starter-security'
       implementation 'org.springframework.boot:spring-boot-starter-validation'
       implementation 'org.springframework.boot:spring-boot-starter-web'
       implementation 'org.springframework.boot:spring-boot-starter-mail'
       
       // Swagger
       implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0'
       
       // JWT
       implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
       runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.5'
       runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.5'
       
       compileOnly 'org.projectlombok:lombok'
       runtimeOnly 'com.mysql:mysql-connector-j'
       developmentOnly 'org.springframework.boot:spring-boot-devtools'
       annotationProcessor 'org.projectlombok:lombok'
       
       testImplementation 'org.springframework.boot:spring-boot-starter-test'
       testImplementation 'org.springframework.security:spring-security-test'
   }
   ```

5. Enable annotation processing:
   - Go to **File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors**
   - Check "Enable annotation processing"

---

## Running the Application

1. **Configure MySQL Database**
   - Update `application.properties` with your database credentials:

     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     spring.jpa.hibernate.ddl-auto=update
     ```

2. **Build and Run**
   - Use Gradle to build and run the application:

     ```bash
     ./gradlew bootRun
     ```

3. **Access the Application**
   - Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Heroku Deployment

### Preparing for Deployment

1. Create the following files in the project root:

   - **`system.properties`**
     ```
     java.runtime.version=17
     ```

   - **`Procfile`**
     ```
     web: java -Dserver.port=$PORT -jar build/libs/*.jar
     ```

2. Update `application.properties` for Heroku:

   ```properties
   # PostgreSQL Configuration
   spring.datasource.url=${DATABASE_URL}
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   spring.jpa.hibernate.ddl-auto=update
   
   # JWT Configuration
   app.jwtSecret=${JWT_SECRET}
   app.jwtExpirationInMs=86400000

   # Email Configuration
   spring.mail.host=${MAIL_HOST}
   spring.mail.port=${MAIL_PORT}
   spring.mail.username=${MAIL_USERNAME}
   spring.mail.password=${MAIL_PASSWORD}
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true

   # Server Configuration
   server.port=${PORT:8080}
   ```

### Deploying

1. Install Heroku CLI:
   ```bash
   curl https://cli-assets.heroku.com/install.sh | sh
   ```

2. Create and configure the Heroku app:
   ```bash
   heroku login
   heroku create inventory-management-app
   heroku addons:create heroku-postgresql:hobby-dev
   ```

3. Set environment variables:
   ```bash
   heroku config:set JWT_SECRET=your-secret-key
   heroku config:set MAIL_HOST=smtp.gmail.com
   heroku config:set MAIL_PORT=587
   heroku config:set MAIL_USERNAME=your-email@gmail.com
   heroku config:set MAIL_PASSWORD=your-app-password
   ```

4. Deploy the application:
   ```bash
   git init
   git add .
   git commit -m "Initial commit"
   heroku git:remote -a inventory-management-app
   ./gradlew build
   heroku deploy:jar build/libs/inventory-management-0.0.1-SNAPSHOT.jar
   ```

5. Open the application:
   ```bash
   heroku open
   ```

---

## Key Endpoints

### Authentication
- POST `/api/auth/register`
- POST `/api/auth/login`

### Category Management
- GET `/api/categories`
- POST `/api/categories`
- PUT `/api/categories/{id}`
- DELETE `/api/categories/{id}`

### Product Management
- GET `/api/products`
- POST `/api/products`
- PUT `/api/products/{id}`
- DELETE `/api/products/{id}`
- GET `/api/products/search`

### Inventory Management
- PUT `/api/inventory/add-stock`
- PUT `/api/inventory/deduct-stock`
- GET `/api/inventory/low-stock`

---

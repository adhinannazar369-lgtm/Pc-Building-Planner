# AI-PC Build Planner — Java + MySQL

This is a Java web application based on the supplied AI-PC Build Planner HTML prototype.

## Technology
- Java 17
- Spring Boot 3
- Thymeleaf templates + HTML/CSS/JavaScript
- Spring Data JPA / Hibernate
- MySQL
- BCrypt password hashing

## Features included
- Register and log in with phone number and password
- Passwords are hashed before being stored
- Session-based login and logout
- PC purpose and budget selection
- Sample component recommendations and estimated totals
- Compatibility-review section (informational, not a live hardware validator)
- Save builds to MySQL and list saved builds for the logged-in user

## Important prototype limitations
The original HTML used example component pricing and retailer comparisons. This Java version labels its component prices as estimates; it does **not** fetch live Amazon/Flipkart/Newegg prices, perform AI inference, or guarantee hardware compatibility. Those require trusted product data and/or external API integrations.

## Requirements
1. Install JDK 17 or later.
2. Install MySQL Server and Maven (or open the project in IntelliJ IDEA).
3. Create the database (or let the configured URL create it):
   ```sql
   CREATE DATABASE aipc_build_planner;
   ```
4. Open `src/main/resources/application.properties` and replace `CHANGE_THIS_TO_YOUR_MYSQL_PASSWORD` with your MySQL password.
5. Run the application from the project folder:
   ```bash
   mvn spring-boot:run
   ```
   Or in IntelliJ, import as a Maven project and run `AiPcBuildPlannerApplication.java`.
6. Open http://localhost:8080 in your browser.

## Database tables
Hibernate creates/updates `app_users` and `saved_builds` automatically because `spring.jpa.hibernate.ddl-auto=update` is configured. For production, use migrations and set schema management deliberately.

## Project structure
```
src/main/java/com/aipc/planner/
  AiPcBuildPlannerApplication.java
  config/SecurityConfig.java
  controller/AuthController.java
  controller/PlannerController.java
  model/AppUser.java
  model/SavedBuild.java
  repository/UserRepository.java
  repository/SavedBuildRepository.java
src/main/resources/
  application.properties
  templates/auth.html
  templates/dashboard.html
  static/css/app.css
```

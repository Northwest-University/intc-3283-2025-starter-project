# Sample Project (Spring Boot + MySQL)

Welcome! This repository is a student-friendly starter showcasing a simple Spring Boot web application with Thymeleaf views, Spring Security, Flyway database migrations, and Docker Compose/Testcontainers for MySQL.

Use this README as your guide to understand the pieces of the project, how to run it locally, how the database is provisioned and migrated, and what each Maven dependency does.


## Table of Contents
- What’s in this project
- Components overview
- How the database works (Compose + Flyway)
- Running the app
- Resetting/refreshing the database
- Pulling images with Docker Compose
- Tests with Testcontainers
- Maven dependencies explained (from pom.xml)
- Useful URLs
- Troubleshooting


## What’s in this project
Key files and directories you’ll use:
- `src/main/java/.../SampleProjectApplication.java` — Spring Boot entry point
- `src/main/java/.../config/SecurityConfig.java` — Security setup
- `src/main/java/.../controller/IndexController.java` — Example controller
- `src/main/resources/templates` — Thymeleaf HTML templates and fragments
- `src/main/resources/application.yaml` — Application configuration
- `compose.yaml` — Docker Compose definition for MySQL
- `pom.xml` — Maven build and dependencies
- `src/test/java/...` — Tests and Testcontainers configuration


## Components overview
- Spring Boot Web (MVC): Serves pages and endpoints via controllers.
- Thymeleaf: Server-side templating for HTML views.
- Spring Security: Adds basic security scaffolding and Thymeleaf security extras.
- Flyway: Runs database migrations automatically at startup (see “Database migrations” below).
- MySQL: The relational database used by the app (via Docker Compose in dev).
- Spring Boot Actuator: Operational endpoints for health/info.
- DevTools: Optional hot reload during development.
- WebJars (Bootstrap, Font Awesome) and Thymeleaf Layout Dialect: Front-end helpers.
- Testcontainers: Spin up throwaway MySQL for tests.


## How the database works (Compose + Flyway)
- Docker Compose brings up a MySQL 8.4.6 container defined in `compose.yaml`.
  - Database: mydatabase
  - Username: intc3283
  - Password: password
  - Root password: password
  - Port: 3306 (published by Docker; Spring Boot’s Docker Compose integration will wire things automatically when running the app from your IDE or mvn spring-boot:run if Docker is available).
- Flyway is included (flyway-core and flyway-mysql) and will apply migration scripts on startup if they are present.
  - Default migration directory: `src/main/resources/db/migration`
  - Naming convention: `V1__description.sql`, `V2__another_change.sql`, etc.
  - Note: This starter currently doesn’t include example migration files. Create them in db/migration as needed.


## Running the app
Prerequisites:
- Java 24 (as configured in pom.xml > java.version)
- Maven 3.9+
- Docker Desktop or a compatible Docker Engine (for database via Compose and Testcontainers)

Steps:
1) Start the app (Spring Boot will also start Docker Compose-managed services if available):
```bash
mvn spring-boot:run
# or
./mvnw spring-boot:run
```

2) Alternatively, build a jar and run it:
```bash
mvn clean package
java -jar target/sample-project-0.0.1-SNAPSHOT.jar
```

The application starts on:
- App port: 8080
- Actuator port: 8081 (management server)


## Database migrations
- Create SQL migration files under: `src/main/resources/db/migration`
- Example file names:
  - `src/main/resources/db/migration/V1__init.sql`
  - `src/main/resources/db/migration/V2__add_users_table.sql`
- On startup, Flyway will detect and apply pending migrations to the MySQL database.


## Connecting to the database
When running with Docker Compose, typical JDBC URL (Spring will auto-configure when using Docker Compose integration):
```
jdbc:mysql://localhost:3306/mydatabase
Username: intc3283
Password: password
```

If you need to set it manually in application.yaml, you can add (example):

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mydatabase
    username: intc3283
    password: password
  flyway:
    enabled: true
```

Note: This project currently leverages Spring Boot’s Docker Compose integration (spring-boot-docker-compose dependency and spring.docker.compose.enabled=true) to auto-wire the datasource when the mysql service is up.


## Resetting/refreshing the database
To completely remove the database container and its volumes (start fresh):
```bash
docker compose down -v
docker compose up -d
```

If you prefer to run foreground to watch logs, omit -d:
```bash
docker compose up
```


## Pulling images with Docker Compose
Before starting services, you can ensure you have the latest images:
```bash
docker compose pull
```

You can also pull just the MySQL image:
```bash
docker compose pull mysql
```


## Tests with Testcontainers
- Unit/integration tests use Testcontainers to run MySQL in a disposable container during tests.
- See: `src/test/java/edu/northwestu/sampleproject/TestcontainersConfiguration.java`
  - It configures a MySQL 8.4.6 container and marks it with `@ServiceConnection` so Spring Boot can auto-configure the datasource for tests.
- Run tests with:
```bash
mvn test
```


## Maven dependencies explained (from pom.xml)
Core Spring Boot:
- spring-boot-starter-web: Spring MVC + embedded server for building web apps and REST endpoints. See: https://spring.io/projects/spring-boot#learn and Web MVC reference https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#web
- spring-boot-starter-thymeleaf: Server-side template engine integration. See: https://spring.io/guides/gs/handling-form-submission/ and Thymeleaf ref https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#web.servlet.spring-mvc.template-engines
- spring-boot-starter-security: Basic security and authentication/authorization support. See: https://spring.io/projects/spring-security#learn
- spring-boot-starter-actuator: Operational endpoints (health, metrics, info, etc.). See: https://spring.io/projects/spring-boot#learn and https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html

Database & migrations:
- flyway-core: Core Flyway migration engine. See: https://spring.io/guides/gs/managing-transactions/ and Boot How-to Flyway https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto.data-initialization.migration-tool.flyway
- flyway-mysql: Flyway support for MySQL. See: https://flywaydb.org/documentation/database/mysql
- mysql-connector-j (runtime): MySQL JDBC driver used by the app at runtime. See: https://dev.mysql.com/doc/connector-j/8.0/en/

Developer experience:
- spring-boot-devtools (runtime, optional): Auto-restart and live reload during development. See: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using.devtools
- spring-boot-docker-compose (runtime, optional): Spring Boot feature that manages docker compose services for local dev (e.g., auto-start mysql from compose.yaml). See: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.dev-services.docker-compose

View layer & assets:
- thymeleaf-extras-springsecurity6: Thymeleaf dialect to access Spring Security data in templates. See: https://github.com/thymeleaf/thymeleaf-extras-springsecurity
- webjars-locator-lite: Resolves WebJar asset paths automatically. See: https://www.webjars.org/
- webjars:bootstrap: Bootstrap CSS/JS packaged as a WebJar (version 5.3.8). See: https://www.webjars.org/ and https://getbootstrap.com/
- webjars:font-awesome: Font Awesome icons as a WebJar (version 7.0.0). See: https://www.webjars.org/ and https://fontawesome.com/
- thymeleaf-layout-dialect: Adds layout/decorator features to Thymeleaf templates (version 3.4.0). See: https://ultraq.github.io/thymeleaf-layout-dialect/

Testing:
- spring-boot-starter-test (test): JUnit, AssertJ, Spring Test. See: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#testing
- spring-boot-testcontainers (test): Integration between Spring Boot and Testcontainers. See: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.dev-services.testcontainers
- org.testcontainers:junit-jupiter (test): JUnit 5 integration for Testcontainers. See: https://java.testcontainers.org/
- org.testcontainers:mysql (test): MySQL container support for tests. See: https://java.testcontainers.org/modules/databases/mysql/
- spring-restdocs-mockmvc (test): Generate documentation from tests (works with Asciidoctor plugin). See: https://spring.io/projects/spring-restdocs#learn

Build plugins:
- spring-boot-maven-plugin: Build/run Spring Boot applications (repackage jar, build image, etc.). See: https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/
- asciidoctor-maven-plugin (+ spring-restdocs-asciidoctor): Process AsciiDoc to HTML during build and integrate Spring REST Docs. See: https://docs.asciidoctor.org/maven-tools/latest/ and https://spring.io/projects/spring-restdocs#learn

Properties:
- java.version=24: Target Java level for compilation. See: https://openjdk.org/projects/jdk/24/
- spring-restdocs.version=3.0.3: Controls REST Docs plugin/dependency version. See: https://spring.io/projects/spring-restdocs#learn


## Useful URLs
- App home: http://localhost:8080/
- Actuator (example): http://localhost:8081/actuator/health


## Troubleshooting
- Port already in use: Stop whatever is using 8080/8081 or configure different ports in application.yaml.
- Docker not running: Start Docker before running the app; Compose/Testcontainers require it.
- Flyway can’t find migrations: Ensure your SQL files are in src/main/resources/db/migration and follow naming (V1__...).
- Authentication/authorization issues in templates: Confirm thymeleaf-extras-springsecurity6 is on classpath and SecurityConfig is applied.


## License
See LICENSE in the repo root.

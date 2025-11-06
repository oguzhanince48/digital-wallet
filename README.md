Digital Wallet Api

Spring Boot application providing wallet operations (search,create,deposit,withdraw) 
with **Spring Security**, **H2**.  
Runs locally or in Docker for consistent environment and easy monitoring.

Prerequisites
- Java 21+
- Docker & Docker Compose
- Gradle

Build and Run Locally
1. Clone the repository:
   ```bash
   git clone https://github.com/oguzhanince48/digital-wallet.git
    cd digital-wallet-api
    ``` 
2. Build the application:
   ```bash
   ./gradlew build
   ```
3. Run the application:
   ```bash
   ./gradlew bootRun
   ```
The application will start on `http://localhost:8080`.
3. Access H2 Console:
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:digital-wallet`
   - Username: root
   - Password: my-secret-pw

Run with Docker
1. Build and run the Docker container using Docker Compose:
   ```bash
   docker-compose up --build
   ```
   The application will be accessible at `http://localhost:8080`.
2. To stop the application, run:
   ```bash
   docker-compose down
   ```
   
API Documentation
Once the application is running, access the Swagger UI for API documentation and testing at:
```
http://localhost:8080/swagger-ui.html
```

For health check, access:
```
http://localhost:8080/actuator/health
```
For prometheus metrics, access:
```
http://localhost:8080/actuator/prometheus
```


Authentication
The API uses Basic Authentication. Use the following default credentials:
- Username: test          - Username: admin
- Password: test123       - Password: admin123
- Role: CUSTOMER          - Role: ADMIN

Postman Collection
A Postman collection is provided in the `docs/Digital Wallet.postman_collection.json` directory for easy testing of the API. Import it into Postman to get started quickly.

                                
# API Respira Aripoka

## 📝 Overview

API Respira Aripoka is a Java-based Spring Boot application for anonymous complaint reporting, featuring secure data handling with MySQL database integration.

## 🎯 Core Features

- **Anonymous Reporting**: Submit complaints securely without revealing identity
- **Efficient Management**: CRUD operations for complaints
- **Smart Filtering**: Search by period, type, and status
- **Analytics Dashboard**: Statistical insights on reported cases
- **Administrative Controls**: Status updates and complaint management

## 🛠️ Technical Stack

- Java 17
- Spring Boot 3.x
- MySQL
- Maven
- JUnit 5
- Spring Data JPA

## 🔄 API Endpoints

### Complaint Management
```http
POST    /v1/denuncia                      # Create complaint
GET     /v1/denuncia/{id}                 # Get by ID
GET     /v1/denuncia/analise             # List all (paginated)
DELETE  /v1/denuncia/gerenciar/{id}      # Delete
PUT     /v1/denuncia/analise/{id}        # Update status
GET     /v1/denuncia/analise/por-periodo      # Period filter
GET     /v1/denuncia/analise/por-tipo         # Type filter
GET     /v1/denuncia/analise/por-periodo-tipo # Combined filter
GET     /v1/denuncia/analise/por-status       # Status filter
GET     /v1/denuncia/analise/estatisticas     # Statistics

📋 Request Example
POST /v1/denuncia
{
    "endereco": "Rua Example, 123",
    "coordenadasGeograficas": "123.456, -789.012",
    "tipo": "AMBIENTAL",
    "descricao": "Complaint description"
}
🚀 Setup Guide
Clone the repository
git clone [repository-url]

Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_name
spring.datasource.username=your_username
spring.datasource.password=your_password

Build and run
mvn clean install
mvn spring-boot:run

Application runs on http://localhost:8080
🧪 Testing
Run tests using:
mvn test

🤝 Contributing
Fork the repository
Create feature branch
Commit changes
Open Pull Request
📄 License
This project is under the MIT License.

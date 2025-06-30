# Device Management REST API

A Java 17 Spring Boot REST API that manages IoT-style devices, supporting full CRUD operations and status-based search. Uses an in-memory H2 database and follows clean architecture with controller, service, and repository layers. Ideal for demonstrating backend skills with modern Java technologies.

## Features

- CRUD operations on devices (name, type, status, createdAt)
- Search devices by status
- Uses Spring Boot, Spring Data JPA, and H2 database
- Easy to run locally with Maven
- H2 console enabled for database inspection

## Getting Started

### Prerequisites

- Java 17+
- Maven

### Run Locally

```bash
git clone git@github.com:YOUR_USERNAME/device-management-api.git
cd device-management-api
./mvnw spring-boot:run


Visit: http://localhost:8080/devices to view devices.

API Endpoints
Method	Endpoint	Description
GET	/devices	List all devices
GET	/devices/{id}	Get device by ID
POST	/devices	Add a new device
PUT	/devices/{id}	Update a device
DELETE	/devices/{id}	Delete a device
GET	/devices/search?status=	Filter devices by status

Example JSON for POST/PUT
{
  "name": "Smart Sensor",
  "type": "Temperature",
  "status": "active"
}
```
Future Improvements

- Add Swagger/OpenAPI documentation
- Implement pagination
- Connect to a persistent database like PostgreSQL 
- Add user authentication and authorization

Author 

Daniel Moshood
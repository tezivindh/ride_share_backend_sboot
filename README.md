# Ride Share API

A RESTful ride-sharing backend application built with Spring Boot 4.0, MongoDB, and JWT authentication.

## 🚀 Tech Stack

- **Java 21**
- **Spring Boot 4.0.0**
- **Spring Security** - JWT-based authentication
- **Spring Data MongoDB** - Database integration
- **MongoDB Atlas** - Cloud database
- **Maven** - Build tool

## 📁 Project Structure

```
src/main/java/com/example/ride_share/
├── RideShareApplication.java      # Main application entry point
├── config/
│   ├── SecurityConfig.java        # Security & JWT configuration
│   └── JwtFilter.java             # JWT authentication filter
├── controller/
│   ├── AuthController.java        # Authentication endpoints
│   ├── RideController.java        # Ride management endpoints
│   ├── DriverController.java      # Driver-specific endpoints
│   └── UserController.java        # User management endpoints
├── dto/
│   ├── AuthRequest.java           # Login request DTO
│   ├── AuthResponse.java          # Login response with JWT
│   └── CreateRideRequest.java     # Ride creation DTO
├── exception/
│   └── GlobalExceptionHandler.java # Global error handling
├── model/
│   ├── User.java                  # User entity
│   └── Ride.java                  # Ride entity
├── repository/
│   ├── UserRepository.java        # User data access
│   └── RideRepository.java        # Ride data access
├── service/
│   ├── AuthService.java           # Authentication logic
│   └── RideService.java           # Ride business logic
└── util/
    └── JwtUtil.java               # JWT token utilities
```

## 🔧 Configuration

### application.properties

```properties
spring.application.name=ride_share

# MongoDB Connection
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster0.xxxxx.mongodb.net/<database>

# JWT Configuration
jwt.secret=YourSuperSecretKeyThatIsAtLeast32CharactersLong!
jwt.expiration=86400000  # 24 hours in milliseconds
```

## 🔐 API Endpoints

### Authentication (Public)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and get JWT token |

### Rides (Authenticated)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/rides` | Request a new ride |
| POST | `/api/v1/rides/{id}/complete` | Complete a ride |

### Driver (Requires ROLE_DRIVER)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/driver/rides/requests` | Get pending ride requests |
| POST | `/api/v1/driver/rides/{id}/accept` | Accept a ride request |

## 📝 API Usage Examples

### Register a User

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john",
    "password": "1234",
    "role": "USER"
  }'
```

### Register a Driver

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "driver1",
    "password": "1234",
    "role": "DRIVER"
  }'
```

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john",
    "password": "1234"
  }'
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Request a Ride (Authenticated)

```bash
curl -X POST http://localhost:8080/api/v1/rides \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-jwt-token>" \
  -d '{
    "pickupLocation": "123 Main St",
    "dropLocation": "456 Oak Ave"
  }'
```

### Get Pending Rides (Driver Only)

```bash
curl -X GET http://localhost:8080/api/v1/driver/rides/requests \
  -H "Authorization: Bearer <driver-jwt-token>"
```

### Accept a Ride (Driver Only)

```bash
curl -X POST http://localhost:8080/api/v1/driver/rides/{rideId}/accept \
  -H "Authorization: Bearer <driver-jwt-token>"
```

### Complete a Ride

```bash
curl -X POST http://localhost:8080/api/v1/rides/{rideId}/complete \
  -H "Authorization: Bearer <your-jwt-token>"
```

## 🏃 Running the Application

### Prerequisites

- Java 21+
- Maven 3.9+
- MongoDB (local or Atlas)

### Run Locally

```bash
# Clone the repository
git clone https://github.com/tezivindh/ride_share_backend_sboot
cd ride_share

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The application will start at `http://localhost:8080`

## 🔒 Security

- Passwords are hashed using BCrypt
- JWT tokens expire after 24 hours
- Role-based access control (USER, DRIVER)
- Stateless session management

## 📊 Data Models

### User

| Field | Type | Description |
|-------|------|-------------|
| id | String | Unique identifier |
| username | String | User's username |
| password | String | BCrypt hashed password |
| role | String | ROLE_USER or ROLE_DRIVER |

### Ride

| Field | Type | Description |
|-------|------|-------------|
| id | String | Unique identifier |
| userId | String | ID of the requesting user |
| driverId | String | ID of the assigned driver |
| pickupLocation | String | Pickup address |
| dropLocation | String | Drop-off address |
| status | String | PENDING, ACCEPTED, COMPLETED |
| createdAt | Date | Ride creation timestamp |

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
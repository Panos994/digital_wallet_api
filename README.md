# Digital Wallet API

A RESTful digital wallet and ledger API built with Java 17 and Spring Boot. It provides user registration and authentication, wallet account management, and atomic fund transfers with debit and credit ledger entries.

## Features

- User registration and role assignment
- Login with JWT-based authentication
- Wallet account creation with an initial zero balance
- Fund transfers between active accounts
- Currency validation for transfers
- Pessimistic account locking for concurrent balance updates
- Double-entry ledger records using debit and credit transaction entries
- Centralized REST exception handling

## Technology Stack

- Java 17
- Spring Boot 4.1.2
- Spring Web MVC
- Spring Security
- Spring Data JPA / Hibernate
- PostgreSQL
- JJWT
- Maven Wrapper
- Lombok

## Project Structure

```text
src/main/java/fintech/core/ledger/digital/wallet/api/
├── controller/  # REST endpoints
├── dto/         # API request and response models
├── entity/      # JPA entities and enums
├── exception/   # Global exception handling
├── repository/  # Spring Data repositories
├── security/    # JWT filter and security configuration
└── service/     # Business logic
```

## Prerequisites

- Java 17 or newer
- Docker and Docker Compose, or a local PostgreSQL instance
- Maven is optional because the Maven Wrapper is included

## Database Setup

The default application configuration uses PostgreSQL on port `5448` with database name `digital_wallet_db`.

Start PostgreSQL with Docker:

```bash
docker run --name my-postgres-digital-wallet-ms \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=digital_wallet_db \
  -p 5448:5432 \
  -d postgres
```

Open the database:

```bash
docker exec -it my-postgres-digital-wallet-ms psql -U postgres -d digital_wallet_db
```

The application uses `update` as the Hibernate DDL mode, so JPA entities are synchronized with the database schema when the application starts.

## Configuration

Review `src/main/resources/application.properties` before running the application.

The default database username is `postgres`. Set the password through configuration or a Spring environment variable:

```bash
export SPRING_DATASOURCE_PASSWORD=postgres
export SPRING_PRIVATE_TOKEN='replace-this-with-a-long-random-secret'
./mvnw spring-boot:run
```

To run on a different host or database, override the datasource URL:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5448/digital_wallet_db
```

For production, replace the JWT signing secret and database credentials with environment-specific secrets. Do not commit real credentials to source control.

## Running the Application

Clone or enter the project directory, then start the API:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
mvnw.cmd spring-boot:run
```

The API listens on:

```text
http://localhost:9718
```

## Running Tests

```bash
./mvnw test
```

## API Overview

| Method | Endpoint | Description | Authentication |
|---|---|---|---|
| `POST` | `/api/users` | Create a user | Public |
| `GET` | `/api/users?email=...` | Find a user by email | Public |
| `PATCH` | `/api/users/{userId}?newRole=ROLE_ADMIN` | Assign a role | Authenticated |
| `POST` | `/api/auth/login` | Authenticate and receive a JWT | Public |
| `POST` | `/api/wallet-accounts?userId={uuid}` | Create a wallet account | Authenticated |
| `POST` | `/api/ledger-transactions` | Transfer funds | Authenticated |

Available roles:

- `ROLE_USER`
- `ROLE_ADMIN`

## Example Requests

### Create a user

```bash
curl -X POST http://localhost:9718/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "email": "alice@example.com",
    "password": "change-me"
  }'
```

### Login

```bash
curl -X POST http://localhost:9718/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "alice@example.com",
    "password": "change-me"
  }'
```

The login response contains a `token`. Send it as a bearer token for protected endpoints:

```text
Authorization: Bearer <token>
```

### Create a wallet account

```bash
curl -X POST "http://localhost:9718/api/wallet-accounts?userId=<USER_UUID>" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "<USER_UUID>",
    "currency": "EUR"
  }'
```

The `userId` body field is not used by the current controller flow; the `userId` query parameter is authoritative.

### Transfer funds

```bash
curl -X POST http://localhost:9718/api/ledger-transactions \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "sourceWalletAccountId": "<SOURCE_ACCOUNT_UUID>",
    "destinationAccountId": "<DESTINATION_ACCOUNT_UUID>",
    "amount": 50.00
  }'
```

A transfer is completed only when the source account has sufficient balance, both accounts are active, and both accounts use the same currency.

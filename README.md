# Pismo - Gabriel dos Santos Neves

## Accounts and Transactions Aplication

### Account Endpoints:
- To create an account: POST `/accounts`
- To find an account by its ID: GET `/accounts/{id}`

### Transaction Endpoint:
- To create a transaction: POST `/transactions`

## Technologies
- Java 11
- Maven
- Spring Boot
- Docker 
- H2
- [Swagger-Documentation](http://localhost:8080/swagger-ui/index.html#/)

## Getting Started
### Clone project in Github
> git clone https://github.com/gabrieldevsantos/pismo-test

### Run locally
```SHELL
> mvn clean install
> mvn spring-boot:run
```

### Run with Docker
```SHELL
> mvn clean install
> docker build -t app .
> docker run -d -p 8080:8080 app
```
**Database**

[H2-Console](http://localhost:8080/h2-console)

	jdbcUrl: jdbc:h2:mem:pismodb
	user: test
	pass: test

## Running Tests
```SHELL
> mvn test
```

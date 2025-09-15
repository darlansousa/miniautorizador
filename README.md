# Getting Started

### Requirements
* Docker
* JDK 21
* Maven 3.8+
* (Optional) GraalVM 22.3+ if you want to build a native executable

### How to run this project

```
$ cd .local
$ docker compose up -d
$ mvn spring-boot:run
```

### What will be running?

* Spring Boot application running on port 8081
* MySQL database running on port 3306
* Kafka instance
* Kafka UI running on port 8080

### Documentation

The project documentation is available at: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

### Default credentials

* User: admin
* Password: admin
* Role: ROLE_ADMIN, ROLE_USER

* User: user
* Password: user
* Role: ROLE_USER

### Extra

* Kafka implementation
  * Including a message producer with transaction status

### Recomendações
  * Manter os endpoints o mais coesos com mostra a documentação: [Richardson Maturity Model](https://martinfowler.com/articles/richardsonMaturityModel.html)
  * Foi necessário fazer o tratamento de exceções customizado. Pode ficar complicado para fazer esse tratamento de 
    erros com comportamentos diferentes nos consumidores.
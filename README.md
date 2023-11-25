# Rabam backend test
 Rabam firması için hazırlanmış işa alım mülakatı testi ve cevapları


This project includes a Spring Boot-based backend application.

## Requirements

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) (for the Backend)
- [PostgreSQL](https://www.postgresql.org/) database

## Backend (Spring Boot) Setup


1. Navigate to the `NormalVersionWithLogger: `cd /NormalVersionWithLogger`.

2. To set up the PostgreSQL database, configure the `application.properties` file located at `NormalVersionWithLogger/src/main/resources/application.yml` with the necessary database connection details:

```bash
datasource:
    url: jdbc:postgresql://localhost:5432/rabam
    username: db Username
    password: db Password
```

## To start the application:
1. After configuring the database, build and install the required dependencies for the Spring project using Maven:

```bash
mvn clean install
```

2. Start the Spring Boot application by running the following command:

```bash
java -jar NormalVersionWithLogger\out\artifacts\rabam_jar
```

This command will launch the Spring Boot application.

## API Documentation
->  When entering people's address information, it must be in a format appropriate to the API used. You can find address examples that comply with the appropriate address format under "/NormalVersionWithLogger/src/resources/static/Usable Address List". If you use the addresses in these examples, the API will find your coordinate information correctly.

->  You can review the required technical requirements in the project from the following file: `Rabam Case[655].pdf` located at the root path.

->  To access the Postman collection with predefined requests, import the file `API Rabam Java Development.postman_collection.json` located at the root path.

->  To explore the backend API endpoints and make requests, you can access the Swagger documentation at `http://localhost:8080/swagger-ui/index.html`.

->  DockerVersion- not ready yet in the process of making

## Contact

->  For any questions or feedback regarding the project, you can contact us at safak.tamses@gmail.com .



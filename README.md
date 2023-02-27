# SPRING SECURITY LAB

This project is a Spring Security Demo project.

- This project uses JWT for the security.
- `SpringSecurityLabApplicationLoader` class implements `CommandLineRunner` class. 
Default users, roles, and authorities are created for demo in it. You can check this class.

## Requirements

- Java 17

## How to Run

- You can run the application with the maven spring-boot plugin:
```
mvn spring-boot:run
```
**_P.S._**: Configurations of the project are in **application.yml** file. You may want to change them.
If you want to change them, you can run the application with giving them to the command of maven spring-boot plugin dynamically.
See details of running an application with maven: https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/#run

# Spring Boot Profiles: Environment-Specific Configurations

## What are Spring Profiles?
Spring Profiles allow us to create environment-specific configurations in a Spring Boot application.  
For example, we can have different settings for:
- Development (`dev`) – Local development
- Testing (`test`) – Automated testing
- Production (`prod`) – Live deployment

Each environment may require different database URLs, API keys, logging levels, etc.

## Defining Profiles in `application.properties`
Spring Boot loads `application.properties` or `application.yml` by default.  
We can create multiple profile-specific property files like:
- `application-dev.properties` → For Development
- `application-test.properties` → For Testing
- `application-prod.properties` → For Production

### Default `application.properties`
```properties
spring.profiles.active=dev
```
This tells Spring Boot to load `application-dev.properties`.

## Create Profile-Specific Configuration Files

### `application-dev.properties` (For Local Development)
```properties
server.port=8081
spring.datasource.url=jdbc:h2:mem:devdb
spring.datasource.username=sa
spring.datasource.password=
logging.level.root=DEBUG
```  

### `application-test.properties` (For Testing)
```properties
server.port=8082
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=test
spring.datasource.password=test
logging.level.root=INFO
```  

### `application-prod.properties` (For Production)
```properties
server.port=8080
spring.datasource.url=jdbc:mysql://prod-db-url:3306/mydatabase
spring.datasource.username=prod_user
spring.datasource.password=prod_pass
logging.level.root=ERROR
```  

Spring Boot automatically picks the correct file based on the active profile.

## Setting the Active Profile

### Option 1: Using `application.properties`
```properties
spring.profiles.active=dev
```  

### Option 2: Using Command Line (For Deployment)
```bash
java -jar myapp.jar --spring.profiles.active=prod
```  

### Option 3: Using Environment Variables
```bash
export SPRING_PROFILES_ACTIVE=test
```  
This is useful for cloud deployments (AWS, Kubernetes, etc.).

## Using `@Profile` in Java Classes
We can define beans that load only for specific profiles using `@Profile`.

### Example: Different Implementations Based on Profile
```java
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")  // Loads only in "dev" profile
public class DevEmailService implements EmailService {
    public void sendEmail(String message) {
        System.out.println("DEV: Sending email - " + message);
    }
}
```
```java
@Service
@Profile("prod")  // Loads only in "prod" profile
public class ProdEmailService implements EmailService {
    public void sendEmail(String message) {
        System.out.println("PROD: Sending email via SMTP - " + message);
    }
}
```
Spring Boot automatically picks the correct bean based on the active profile.

## Using `@Configuration` for Profile-Specific Beans
```java
import org.springframework.context.annotation.*;

@Configuration
public class AppConfig {

    @Bean
    @Profile("dev")
    public String devMessage() {
        return "Development Mode";
    }

    @Bean
    @Profile("prod")
    public String prodMessage() {
        return "Production Mode";
    }
}
```
When `dev` profile is active, only `devMessage()` bean is created.

## Profile-Specific Logging
Define different logging levels for different environments.

### `logback-spring.xml` (Example)
```xml
<springProfile name="dev">
    <logger name="com.example" level="DEBUG"/>
</springProfile>

<springProfile name="prod">
    <logger name="com.example" level="ERROR"/>
</springProfile>
```  
Logs detailed output in dev but only errors in prod.

## Running Tests with Profiles
Spring Boot allows us to run tests with a specific profile.

### Set Profile in Test Class
```java
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.profiles.active=test")
class CustomerServiceTest {

    @Test
    void testCustomerService() {
        System.out.println("Running in TEST profile");
    }
}
```
The test automatically loads `application-test.properties`.


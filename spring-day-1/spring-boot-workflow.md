# **How Spring Boot Works Internally 🚀**

Spring Boot simplifies **Java application development** by handling **dependency management, auto-configuration, embedded servers, and production-ready features**.


### When a Spring Boot application starts, the following happens:

1️⃣ **SpringApplication.run()** → Triggers the application startup.  
2️⃣ **Auto-Configuration** → Configures components based on dependencies.  
3️⃣ **Spring Container (IoC & Dependency Injection)** → Manages beans.  
4️⃣ **Embedded Server** → Runs the application without external setup.  
5️⃣ **Production-Ready Features** → Includes monitoring, logging, and metrics.

**Example**: If you add `spring-boot-starter-web`, Spring Boot **automatically configures**:
- `Tomcat` as the embedded server.
- `DispatcherServlet` to handle HTTP requests.
- `Jackson` to convert Java objects to JSON.

---

**`SpringApplication.run()` – Entry Point**
The main method of a Spring Boot application looks like this:

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }
}
```
### **What Happens Internally?**
1. **Loads `SpringApplication` Class**
    - Sets up the **Spring context** (IoC container).
    - Detects the **environment** (e.g., dev, prod).

2. **Enables Auto-Configuration**
    - Reads **`META-INF/spring.factories`** for auto-configuration classes.

3. **Starts Embedded Server**
    - If `spring-boot-starter-web` is present, **Tomcat/Jetty** starts automatically.

4. **Registers Beans & Components**
    - Scans packages for `@Component`, `@Service`, `@Repository`, `@Controller`.



## **Spring Boot Auto-Configuration**
Spring Boot removes the need for **manual XML configuration** by using **Auto-Configuration**.

**How Does It Work?**
- Uses `@EnableAutoConfiguration` (inside `@SpringBootApplication`)
- Loads predefined configurations from `META-INF/spring.factories`
- Configures beans dynamically based on dependencies

### **Example: Auto-Configuring a Database Connection**
If `spring-boot-starter-data-jpa` is present, Spring Boot:
✔ Configures **DataSource** (H2, MySQL, PostgreSQL, etc.).  
✔ Creates a **Hibernate EntityManager** automatically.  
✔ Runs **schema.sql** or **data.sql** if available.

---

## **Spring Boot Annotations & IoC Container**
Spring Boot uses the **Spring IoC (Inversion of Control) Container** to **manage beans**.

### **Key Annotations & Their Internal Working**
| Annotation | Internal Functionality |
|------------|-----------------------|
| `@SpringBootApplication` | Combines `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`. |
| `@ComponentScan` | Scans for `@Component`, `@Service`, `@Repository`, `@Controller`. |
| `@Bean` | Defines a Spring-managed bean. |
| `@Autowired` | Injects dependencies automatically using IoC. |
| `@RestController` | Combines `@Controller` and `@ResponseBody`. |
| `@Configuration` | Declares additional beans manually. |

### **Example: Dependency Injection (DI) in Action**
```java
import org.springframework.stereotype.Service;

@Service
public class BankService {
    public String processTransaction() {
        return "Transaction processed!";
    }
}
```

```java
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankController {
    private final BankService bankService;
    
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/transaction")
    public String performTransaction() {
        return bankService.processTransaction();
    }
}
```
✔ **Spring injects `BankService` into `BankController`** automatically.

---

## **Embedded Server (Tomcat, Jetty, Undertow)**
Spring Boot includes an **embedded web server**, so no external server setup is required.

### **How Does It Work?**
1️⃣ **Detects `spring-boot-starter-web` dependency**  
2️⃣ **Starts Tomcat on port `8080` (default)**  
3️⃣ **Registers `DispatcherServlet`** to handle incoming requests

### **Changing the Port**
Modify `application.properties`:
```properties
server.port=9090
```
Or configure it programmatically:
```java
@Bean
public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerCustomizer() {
    return factory -> factory.setPort(9090);
}
```

---

## **Spring Boot Starters – Preconfigured Dependencies**
Spring Boot provides **Starters** that bundle required dependencies.

### **Common Spring Boot Starters**
| Starter | Purpose |
|---------|---------|
| `spring-boot-starter-web` | REST APIs using Spring MVC |
| `spring-boot-starter-data-jpa` | JPA & Hibernate ORM |
| `spring-boot-starter-security` | Authentication & Authorization |
| `spring-boot-starter-test` | Unit and Integration Testing |

✔ Instead of adding multiple dependencies manually, Spring Boot **bundles them together**.

---


## **Spring Boot External Configurations**
Spring Boot supports **multiple ways** to configure applications:
- **application.properties / application.yml**
- **Command-line arguments**
- **Environment variables**

### **Example: Configuring Database in `application.properties`**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank
spring.datasource.username=root
spring.datasource.password=secret
spring.jpa.hibernate.ddl-auto=update
```
✔ No need for manual configuration—Spring Boot auto-configures it!

---

## **Spring Boot Profiles (Dev, Test, Prod)**
Use **profiles** to define different configurations for different environments.

### **Example: Setting Up Profiles**
Create separate property files:  
✔ `application-dev.properties`  
✔ `application-prod.properties`

Specify profile at runtime:
```shell
java -jar app.jar --spring.profiles.active=prod
```
✔ This allows different configurations for **development, testing, and production**.


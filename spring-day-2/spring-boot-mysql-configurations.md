# **Spring Boot Database Integration with MySQL 🏦**


## **🔹 1️⃣ Add Required Dependencies**
In your `pom.xml`, add the **Spring Boot Starter for JPA and MySQL Driver**:

```xml
   

    <!-- Spring Boot Starter for JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- MySQL Driver -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

```




## **Configure MySQL in `application.properties`**
Create a MySQL database first:
```sql
CREATE DATABASE bankdb;
```

Then, configure MySQL connection settings in `src/main/resources/application.properties`:

```properties
# MySQL Database Connection
spring.datasource.url=jdbc:mysql://localhost:3306/bankdb
spring.datasource.username=root
spring.datasource.password=root

# Hibernate Properties
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```


## **Create a JPA Entity (Bank Account Model)**
Define an **entity class** to represent a `BankAccount` table in MySQL.

```java
import jakarta.persistence.*;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String accountHolderName;
    private double balance;

    // Getters and Setters
}
```


## **Create a Repository (DAO Layer)**
Define a repository interface to handle **CRUD operations**.

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findByAccountHolderName(String accountHolderName);
}
```



## **🔹 5️⃣ Create a Service Layer**
Implement business logic in a service class.

```java
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BankAccountService {
    
    private final BankAccountRepository repository;

    public BankAccountService(BankAccountRepository repository) {
        this.repository = repository;
    }

    public List<BankAccount> getAllAccounts() {
        return repository.findAll();
    }

    public BankAccount createAccount(BankAccount account) {
        return repository.save(account);
    }

    public BankAccount getAccountById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public void deleteAccount(Long id) {
        repository.deleteById(id);
    }
}
```


# **JPA Relationships in Spring Boot**

# **JPA Relationships**
In a real-world **banking system**, we may have different relationships:
- **One Bank has Many Customers (One-to-Many)**
- **One Customer has Many Bank Accounts (One-to-Many)**
- **One Account has One Branch (One-to-One)**


## **One-to-One Relationship**
### **Example: A Bank Account has One Branch**
Each **bank account** belongs to a **single branch**.

### **📌 Entity: `Branch`**
```java
import jakarta.persistence.*;

@Entity
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String branchName;
    private String address;

    // Getters and Setters
}
```

### **Entity: `BankAccount`**
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

    @OneToOne
    @JoinColumn(name = "branch_id") // Foreign Key in bank_accounts table
    private Branch branch;

    // Getters and Setters
}
```



## **One-to-Many Relationship**
### **Example: One Customer Has Many Bank Accounts**
A **customer** can have **multiple accounts** in a bank.

### **Entity: `Customer`**
```java
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<BankAccount> accounts;

    public Customer() {}

    public Customer(String name, List<BankAccount> accounts) {
        this.name = name;
        this.accounts = accounts;
    }

    // Getters and Setters
}
```

### **Update `BankAccount` to include Customer**
```java
@ManyToOne
@JoinColumn(name = "customer_id") // Foreign Key in bank_accounts table
private Customer customer;
```


## **Many-to-Many Relationship**
### **Customers & Bank Services**

- A **Customer** can subscribe to **multiple Banking Services** (e.g., Net Banking, Mobile Banking, Loan Service).
- A **Banking Service** can be used by **multiple Customers**.



## **Defining the Entities**
We need three tables:
1. **`customers`** → Stores customer details.
2. **`bank_services`** → Stores different banking services.
3. **`customer_services`** (Join Table) → Maps which customer uses which services.

---

### **Entity: `Customer`**
```java
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @ManyToMany
    @JoinTable(
        name = "customer_services",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<BankService> services;

    // Getters and Setters
}
```
✔ **`@ManyToMany`** → Defines the many-to-many relationship.  
✔ **`@JoinTable`** → Creates a **junction table** (`customer_services`) with `customer_id` and `service_id`.

---

### **Entity: `BankService`**
```java
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "bank_services")
public class BankService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serviceName;

    @ManyToMany(mappedBy = "services")
    private List<Customer> customers;
    

    // Getters and Setters
}
```
✔ **`@ManyToMany(mappedBy = "services")`** → This refers back to `Customer` to complete the bidirectional mapping.









# **JPA Inheritance**
### **Example: Different Types of Bank Accounts**
A bank has different types of accounts like:
- **Savings Account**
- **Current Account**
- **Fixed Deposit Account**

Instead of creating separate tables for each, we can use **JPA inheritance**.

---

## **Single Table Strategy (`@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`)**
Stores all **account types in one table** with a **discriminator column**.

```java
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "bank_accounts")
public abstract class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountHolderName;
    private double balance;
    

    // Getters and Setters
}
```

✔ **`@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`** → Stores all subclasses in **one table**.  
✔ **`@DiscriminatorColumn(name = "account_type")`** → Differentiates types of accounts.

### **📌 Create Subclasses**
```java
@Entity
@DiscriminatorValue("SAVINGS")
public class SavingsAccount extends BankAccount {
    private double interestRate;

    // Getters and Setters
}
```

```java
@Entity
@DiscriminatorValue("CURRENT")
public class CurrentAccount extends BankAccount {
    private double overdraftLimit;

   
}
```

---

## **Joined Table Strategy (`@Inheritance(strategy = InheritanceType.JOINED)`)**
Stores **each subclass in a separate table** and joins them using foreign keys.

```java
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BankAccount {  }
```

✔ Each subclass has its **own table**.  
✔ **More normalized structure** than `SINGLE_TABLE`.


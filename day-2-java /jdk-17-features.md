# **New Java Features in JDK 17 **


## **1. Records (Finalized in JDK 16)**
### **What Problem Do Records Solve?**
Before Java 16, defining a simple data class required **boilerplate code**:
```java
class Employee {
    private final String name;
    private final int age;
    
    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public boolean equals(Object obj) { /* Implementation */ }
    @Override
    public int hashCode() { /* Implementation */ }
    @Override
    public String toString() { /* Implementation */ }
}
```
✅ **Too much boilerplate!**

### **Solution: Use Records**
```java
public record Employee(String name, int age) {}
```
✔ **Auto-generates `equals()`, `hashCode()`, `toString()`**  
✔ **Immutable by default**

### **Real-World Use Case: REST API Response DTOs**
When building REST APIs, we often use **DTOs (Data Transfer Objects)** for responses:
```java
public record UserDTO(String username, String email) {}
```
Instead of:
```java
class UserDTO { /* Boilerplate code for getters, constructors, equals, etc. */ }
```

#### **Example: Returning a `UserDTO` in a Spring Boot API**
```java
@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable int id) {
        return new UserDTO("Alice", "alice@example.com");
    }
}
```
✅ **Cleaner and more efficient!**

### **Adding Custom Logic to Records**
You can **validate fields** inside a record:
```java
public record Product(String name, double price) {
    public Product {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative!");
    }
}
```

---

## **2. Sealed Classes (Finalized in JDK 17)**
### **What Problem Do Sealed Classes Solve?**
Before Java 17, **any class could extend another**, leading to **uncontrolled inheritance**:
```java
class Animal {} // Any class can extend this
```
✅ **Issue**: You might want to control who extends `Animal`.

### **Solution: Use Sealed Classes**
```java
sealed class Animal permits Dog, Cat {}

final class Dog extends Animal {}
final class Cat extends Animal {}
```
✔ **Restricts inheritance to specific subclasses**  
✔ **Ensures better maintainability & security**

### **Real-World Use Case: Payment Processing System**
Let's say we have a **payment processing system**:
```java
sealed interface Payment permits CreditCard, PayPal, BankTransfer {}

record CreditCard(String cardNumber) implements Payment {}
record PayPal(String email) implements Payment {}
record BankTransfer(String accountNumber) implements Payment {}
```
✅ **Prevents new, unsupported payment types from being added accidentally.**

### **Using Sealed Classes in a `switch` Statement**
```java
static void processPayment(Payment payment) {
    switch (payment) {
        case CreditCard cc -> System.out.println("Processing Credit Card: " + cc.cardNumber());
        case PayPal pp -> System.out.println("Processing PayPal: " + pp.email());
        case BankTransfer bt -> System.out.println("Processing Bank Transfer: " + bt.accountNumber());
    }
}
```
✔ **Ensures exhaustive checking**  
✔ **Prevents `default` cases when all types are known**

---

## **3. Pattern Matching in Java**
### **What Problem Does Pattern Matching Solve?**
Before Java 16, `instanceof` checks required **explicit casting**:
```java
Object obj = "Hello";
if (obj instanceof String) {
    String str = (String) obj;  // Explicit cast needed
    System.out.println(str.length());
}
```
✅ **Unnecessary casting!**

### **Solution: Pattern Matching in `instanceof`**
```java
Object obj = "Hello";
if (obj instanceof String str) { // No explicit cast needed
    System.out.println(str.length());
}
```
✔ **Cleaner code**  
✔ **Less redundant casting**

### **Real-World Use Case: Processing API Responses**
Imagine you're building an API client that processes different types of responses:
```java
static void processResponse(Object response) {
    if (response instanceof String message) {
        System.out.println("Response is a message: " + message);
    } else if (response instanceof Integer errorCode) {
        System.out.println("Error Code: " + errorCode);
    }
}
```

---

## **4. Enhanced Switch Expressions (Finalized in JDK 17)**
### **What Problem Does Enhanced `switch` Solve?**
Before JDK 14, `switch` was **verbose**:
```java
String day = "MONDAY";
int num;
switch (day) {
    case "MONDAY": num = 1; break;
    case "TUESDAY": num = 2; break;
    default: num = -1;
}
```
✅ **Too much boilerplate!**

### **Solution: Enhanced Switch Expression**
```java
String day = "MONDAY";
int num = switch (day) {
    case "MONDAY" -> 1;
    case "TUESDAY" -> 2;
    default -> -1;
};
```
✔ **More concise**  
✔ **Can return values directly**

### **Real-World Use Case: Order Processing System**
Let's say we need to determine **shipping cost** based on the order type:
```java
public double getShippingCost(String orderType) {
    return switch (orderType) {
        case "EXPRESS" -> 10.0;
        case "STANDARD" -> 5.0;
        case "FREE" -> 0.0;
        default -> throw new IllegalArgumentException("Invalid order type");
    };
}
```
✅ **Readable and safe!**

### **Using `yield` for Multi-line Cases**
```java
String statusMessage = switch (orderType) {
    case "SHIPPED" -> {
        System.out.println("Logging shipping status...");
        yield "Order is on the way!";
    }
    case "DELIVERED" -> "Order has been delivered";
    default -> "Unknown status";
};
```


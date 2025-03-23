
# Java Turns 30
## A Journey Through Three Decades

Java started as a simple idea—“Write Once, Run Anywhere (WORA)”—and it delivered on that promise. Over the years, it has powered everything from enterprise applications and Android development to cloud computing and microservices.

### Why Java Still Reigns Supreme

- **Stability & Performance**: Java continues to power mission-critical applications across industries.
- **Strong Ecosystem**: Backed by frameworks like Spring, Jakarta EE, and Quarkus, Java remains a top choice for developers.
- **Thriving Community**: Millions of developers contribute to Java’s growth, keeping it modern and relevant.

### The Future of Java

With advancements in Project Loom, Virtual Threads, and GraalVM, Java is evolving to meet the needs of modern workloads. Whether it’s AI, cloud computing, or containerization, Java continues to adapt and innovate.

As we celebrate 30 years of Java, one thing is clear—it’s here to stay!

## **1. Java’s Role in Modern Software Development**
### **Why Java?**
- Platform independence (Write Once, Run Anywhere)
- Strong memory management and security
- Large ecosystem and rich APIs
- Supports microservices, cloud computing, and enterprise applications

### **Java in Different Domains**
- **Enterprise Applications**: Java EE, Spring Boot
- **Cloud Computing**: AWS Lambda, Kubernetes, Docker integration
- **Big Data**: Apache Spark, Hadoop
- **Mobile Applications**: Android (Kotlin & Java)
- **AI & ML**: Deep Java Library (DJL), TensorFlow Java API

---

## **2. Setting Up Java Development Environment**
### **Step 1: Install Java**
- Download JDK from [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or [OpenJDK](https://openjdk.org/)
- Install and set `JAVA_HOME`

```sh
# Check Java version
java -version
```

### **Step 2: Install an IDE**
- **IntelliJ IDEA**
- **Eclipse**
- **VS Code with Java extensions**

### **Step 3: Compile & Run Java Code**
```sh
javac HelloWorld.java  
java HelloWorld  
```

---

## **3. Object-Oriented Programming Concepts**
### **Core OOP Principles in Java**
1. **Encapsulation**
    - Hiding data using private variables and public getters/setters
2. **Inheritance**
    - Extending functionality using `extends` keyword
3. **Polymorphism**
    - Method overloading & overriding
4. **Abstraction**
    - Hiding implementation details using abstract classes and interfaces

### **Example: Implementing OOP Concepts**
```java
// Encapsulation Example
class Employee {
    private String name;
    private int salary;
    
    public Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() { return name; }
    public int getSalary() { return salary; }
}

// Inheritance & Polymorphism
class Manager extends Employee {
    private int bonus;

    public Manager(String name, int salary, int bonus) {
        super(name, salary);
        this.bonus = bonus;
    }

    @Override
    public int getSalary() {
        return super.getSalary() + bonus;
    }
}

public class Main {
    public static void main(String[] args) {
        Employee e = new Employee("John", 50000);
        Manager m = new Manager("Alice", 70000, 10000);
        
        System.out.println(e.getName() + " Salary: " + e.getSalary());
        System.out.println(m.getName() + " Salary: " + m.getSalary());
    }
}
```

---

## **4. Exception Handling**
### **Types of Exceptions in Java**
- **Checked Exceptions**: Must be handled (e.g., `IOException`)
- **Unchecked Exceptions**: Runtime errors (e.g., `NullPointerException`)

### **Example: Handling Exceptions**
```java
public class ExceptionExample {
    public static void main(String[] args) {
        try {
            int result = divide(10, 0);
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Execution complete.");
        }
    }

    static int divide(int a, int b) throws ArithmeticException {
        return a / b;
    }
}
```

---

## **5. Collections and Generics**
### **Java Collections Framework (JCF)**
- **List (ArrayList, LinkedList)**
- **Set (HashSet, TreeSet)**
- **Map (HashMap, TreeMap, LinkedHashMap)**

### **Example: Using Collections**
```java
import java.util.*;

public class CollectionsExample {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Spring");
        list.add("Hibernate");

        System.out.println("ArrayList: " + list);
        
        Set<String> set = new HashSet<>(list);
        System.out.println("HashSet: " + set);
        
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Python");
        map.put(2, "JavaScript");
        System.out.println("HashMap: " + map);
    }
}
```

### **Generics in Java**
```java
class Box<T> {
    private T value;

    public void set(T value) { this.value = value; }
    public T get() { return value; }
}

public class GenericsExample {
    public static void main(String[] args) {
        Box<Integer> intBox = new Box<>();
        intBox.set(10);
        System.out.println("Integer Value: " + intBox.get());

        Box<String> strBox = new Box<>();
        strBox.set("Hello Generics");
        System.out.println("String Value: " + strBox.get());
    }
}
```

---

## **6. Hands-on Lab**
### **Lab 1: Implementing OOP Concepts and Exception Handling**
#### **Task:**
1. Create a `BankAccount` class with `deposit` and `withdraw` methods.
2. Implement a `CurrentAccount` subclass with an overdraft limit.
3. Add exception handling for insufficient balance.

#### **Expected Output**
```sh
Initial Balance: 10000
Deposited: 5000, New Balance: 15000
Withdrew: 12000, New Balance: 3000
Error: Insufficient funds!
```


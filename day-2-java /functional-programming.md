# **Functional Programming & Stream API in Java**


## **1. Introduction to Functional Programming in Java**
### **What is Functional Programming?**
Functional Programming (FP) is a programming paradigm that treats computation as the evaluation of mathematical functions and avoids changing state & mutable data. Java introduced FP features in **Java 8** with **Lambda Expressions, Functional Interfaces, and the Stream API**.

### **Key Principles of Functional Programming**
1. **Immutability** – Avoid modifying existing data, prefer new object creation.
2. **Pure Functions** – Output only depends on input (no side effects).
3. **Higher-Order Functions** – Functions that take other functions as parameters.
4. **Declarative Approach** – Emphasizes "what to do" rather than "how to do it".

---

## **2. Functional Interfaces in Java**
A **functional interface** is an interface with exactly one abstract method. Java provides many built-in functional interfaces in the `java.util.function` package.

### **Common Functional Interfaces**
| Interface | Method | Description |
|-----------|--------|-------------|
| `Predicate<T>` | `boolean test(T t)` | Used for filtering (returns `true` or `false`) |
| `Function<T, R>` | `R apply(T t)` | Takes an input and returns a transformed output |
| `Consumer<T>` | `void accept(T t)` | Performs an action without returning a result |
| `Supplier<T>` | `T get()` | Supplies values without input |

### **Example: Using Functional Interfaces**
```java
import java.util.function.*;

public class FunctionalInterfaceExample {
    public static void main(String[] args) {
        // Predicate Example
        Predicate<Integer> isEven = num -> num % 2 == 0;
        System.out.println(isEven.test(4)); // Output: true

        // Function Example
        Function<String, Integer> lengthFunc = str -> str.length();
        System.out.println(lengthFunc.apply("Java")); // Output: 4

        // Consumer Example
        Consumer<String> printConsumer = message -> System.out.println("Message: " + message);
        printConsumer.accept("Hello Functional Programming");

        // Supplier Example
        Supplier<Double> randomSupplier = () -> Math.random();
        System.out.println("Random Value: " + randomSupplier.get());
    }
}
```

---

## **3. Lambda Expressions in Java**
A **lambda expression** is a concise way to implement functional interfaces.

### **Syntax of Lambda Expression**
```java
(parameter) -> { body }
```

### **Example: Using Lambda Expressions**
```java
import java.util.*;

public class LambdaExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Java", "Spring", "Hibernate");

        // Using Lambda to iterate
        names.forEach(name -> System.out.println(name));

        // Sorting using Lambda
        names.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
        System.out.println(names);
    }
}
```

---

## **4. Stream API in Java**
### **What is Stream API?**
The **Stream API** (introduced in Java 8) is used to process collections in a functional and declarative way.

### **Key Features of Streams**
1. **Lazy Evaluation** – Operations are not executed until a terminal operation is invoked.
2. **Parallel Processing** – Streams support parallel execution for better performance.
3. **Immutable** – Does not modify the original data.

### **Stream Operations**
Streams support two types of operations:
- **Intermediate Operations**: Transform the stream (e.g., `filter`, `map`, `sorted`).
- **Terminal Operations**: Produce a result (e.g., `collect`, `count`, `forEach`).

### **Example: Filtering, Mapping, and Reducing with Streams**
```java
import java.util.*;
import java.util.stream.Collectors;

public class StreamExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        // Filter names that start with 'A'
        List<String> filteredNames = names.stream()
                .filter(name -> name.startsWith("A"))
                .toList();
        System.out.println("Filtered Names: " + filteredNames);

        // Convert names to uppercase
        List<String> upperCaseNames = names.stream()
                .map(String::toUpperCase)
                .toList();
        System.out.println("Uppercase Names: " + upperCaseNames);

        // Count names longer than 3 characters
        long count = names.stream()
                .filter(name -> name.length() > 3)
                .count();
        System.out.println("Count of names > 3 chars: " + count);
    }
}
```

---

## **5. Advanced Stream Operations**
### **Sorting a List with Streams**
```java
import java.util.*;
import java.util.stream.Collectors;

public class StreamSortingExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(5, 2, 9, 1, 7);

        // Sorting in ascending order
        List<Integer> sortedList = numbers.stream()
                .sorted()
                .toList();
        System.out.println("Sorted List: " + sortedList);

        // Sorting in descending order
        List<Integer> descSortedList = numbers.stream()
                .sorted((a, b) -> b - a)
                .toList();
        System.out.println("Descending Sorted List: " + descSortedList);
    }
}
```

---

### **Grouping Data Using `Collectors.groupingBy`**
```java
import java.util.*;
import java.util.stream.Collectors;

class Employee {
    String name;
    String department;
    double salary;

    public Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }
}

public class StreamGroupingExample {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "IT", 70000),
                new Employee("Bob", "HR", 60000),
                new Employee("Charlie", "IT", 75000),
                new Employee("David", "Finance", 80000)
        );

        // Group employees by department
        Map<String, List<Employee>> groupedByDept = employees.stream()
                .collect(Collectors.groupingBy(e -> e.department));

        System.out.println("Employees grouped by department: " + groupedByDept);
    }
}
```

---

### **Parallel Streams for Performance**
```java
import java.util.*;
import java.util.stream.IntStream;

public class ParallelStreamExample {
    public static void main(String[] args) {
        // Sequential Processing
        long startTime = System.currentTimeMillis();
        IntStream.range(1, 1000000).forEach(i -> {});
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken with sequential stream: " + (endTime - startTime) + "ms");

        // Parallel Processing
        startTime = System.currentTimeMillis();
        IntStream.range(1, 1000000).parallel().forEach(i -> {});
        endTime = System.currentTimeMillis();
        System.out.println("Time taken with parallel stream: " + (endTime - startTime) + "ms");
    }
}
```

---

## **6. Hands-on Lab**
### **Lab 1: Employee Salary Processing with Stream API**
#### **Task:**
1. Create a list of `Employee` objects with attributes (`name`, `department`, `salary`).
2. Filter employees earning more than **60,000**.
3. Sort them by salary in descending order.
4. Collect and print the names.

#### **Expected Output**
```sh
Filtered Employees: [Alice, Charlie, David]
```




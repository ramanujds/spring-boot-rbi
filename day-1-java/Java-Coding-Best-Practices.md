## **Java Coding Standards and Best Practices**

Writing clean, maintainable, and efficient Java code is essential for professional development. Adhering to Java coding standards and best practices enhances code readability, reduces errors, and improves collaboration in software development teams.

### **1. Java Naming Conventions**
Consistent naming conventions improve code readability and maintainability.

- **Classes & Interfaces:** Use **PascalCase** (e.g., `ShoppingCart`, `PaymentService`).
- **Methods & Variables:** Use **camelCase** (e.g., `calculateTotalPrice`, `customerName`).
- **Constants:** Use **UPPER_CASE_WITH_UNDERSCORES** (e.g., `MAX_DISCOUNT`, `DEFAULT_TAX_RATE`).
- **Packages:** Use **lowercase with dots** (e.g., `com.store.inventory`).

Example:
```java
public class ShoppingCart {
    private double totalAmount;  // camelCase for variables

    public void addItem(Product product) {  // camelCase for method names
        totalAmount += product.getPrice();
    }
}
```

### **2. Writing Meaningful Variable & Method Names**
Descriptive names make code self-explanatory.

❌ **Bad Example:**
```java
int x = 100;
public void p() {
    // process payment logic
}
```

✅ **Good Example:**
```java
int productPrice = 100;
public void processPayment() {
    // logic to process payment
}
```

### **3. Keeping Methods Short & Focused**
Each method should perform a **single responsibility**.

❌ **Bad Example: A long method doing multiple tasks**
```java
public void checkout() {
    //  logic for validating Cart;
    //  logic for applying Discounts;
    //  logic for calculating Tax;
    //  logic for generating Invoice;
    //  logic for sending Confirmation Email;
}
```

✅ **Good Example: Break methods into smaller units**
```java
public void validateCart() { }
public void applyDiscounts() { }
public void calculateTax() { }
public void generateInvoice() { }
public void sendConfirmationEmail() { }
```

### **4. Proper Indentation & Formatting**
Follow consistent indentation (4 spaces per level) and use blank lines for readability.

✅ **Example:**
```java
public class OrderProcessor {
    public void processOrder(Order order) {
        if (order.isValid()) {
            order.confirm();
        } else {
            throw new InvalidOrderException("Order is not valid.");
        }
    }
}
```

### **5. Avoiding Hardcoded Values**
Use **constants** instead of hardcoded values.

❌ **Bad Example:**
```java
double taxRate = 0.18;
```

✅ **Good Example:**
```java
private static final double TAX_RATE = 0.18;
```

### **6. Using Try-With-Resources for Resource Management**
Resources like files and database connections should be closed automatically.

✅ **Example:**
```java
try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

### **7. Using Proper Exception Handling**
Handle exceptions **gracefully** instead of using generic catch blocks.

❌ **Bad Example:**
```java
try {
    int result = 10 / 0;
} catch (Exception e) { 
    System.out.println("Something went wrong");
}
```

✅ **Good Example:**
```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero: " + e.getMessage());
}
```

### **8. Using Java Collections Framework Effectively**
Choose the appropriate collection based on requirements.

✅ **Example: Using HashMap for Product Inventory**
```java
Map<String, Double> productPrices = new HashMap<>();
productPrices.put("Laptop", 1200.0);
productPrices.put("Phone", 800.0);

System.out.println("Price of Laptop: " + productPrices.get("Laptop"));
```

### **9. Writing Unit Tests with JUnit**
Ensure code reliability with automated unit tests.

✅ **Example: JUnit Test for Shopping Cart**
```java
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ShoppingCartTest {
    @Test
    void testAddItem() {
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product("Laptop", 1200.0);
        cart.addItem(product);
        
        assertEquals(1, cart.getItemCount());
    }
}
```

---

### **Conclusion**
By following these best practices, Java developers can ensure their code is:
✅ Readable  
✅ Maintainable  
✅ Scalable  
✅ Robust


## Code Optimization Techniques

### **1. Use StringBuilder for String Concatenation**

❌ **Bad Example:**
```java
String result = "";
for (int i = 0; i < 1000; i++) {
    result += i;
}
```

✅ **Good Example:**
```java
StringBuilder result = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    result.append(i);
}

String finalResult = result.toString();
```

### **2. Minimize Object Creation**

Avoid creating unnecessary objects, especially within loops.

❌ **Bad Example: Creating new object in a loop**
```java
List<String> fruits = new ArrayList<>();
for (String fruit : getFruits()) {
    fruits.add(new String(fruit));
}
```

✅ **Good Example:**
```java
List<String> fruits = new ArrayList<>();
for (String fruit : getFruits()) {
    fruits.add(fruit);
}
```

### **3. Use Enhanced For Loop**

Enhanced for loop simplifies iterating over collections.

❌ **Bad Example:**
```java
List<String> fruits = getFruits();
for (int i = 0; i < fruits.size(); i++) {
    System.out.println(fruits.get(i));
}
```

✅ **Good Example:**
```java
List<String> fruits = getFruits();
for (String fruit : fruits) {
    System.out.println(fruit);
}
```

### **4. Prefer Primitive Types Over Wrapper Classes**

Use primitive types to reduce memory overhead.

❌ **Bad Example:**
```java
Integer count = new Integer(10);
```

✅ **Good Example:**
```java
int count = 10;
```

### **5. Use Static Imports for Constants**

Simplify access to static fields by using static imports.

✅ **Example:**
```java
import static java.lang.Math.PI;

double area = PI * radius * radius;
```

### **6. Avoid Unnecessary Method Calls**

Minimize method calls within loops for better performance.

❌ **Bad Example:**
```java
for (int i = 0; i < list.size(); i++) {
    processItem(list.get(i));
}
```

✅ **Good Example:**
```java
for (Item item : list) {
    processItem(item);
}
```

### **7. Use Primitives Instead of Strings for Switch Statements**

Switching on strings is less efficient than using enums or integers.

❌ **Bad Example:**
```java
String day = "MON";
switch (day) {
    case "MON":
        // logic for Monday
        break;
    case "TUE":
        // logic for Tuesday
        break;
    // other cases
}
```

✅ **Good Example:**
```java
enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }

Day day = Day.MON;
switch (day) {
    case MON:
        // logic for Monday
        break;
    case TUE:
        // logic for Tuesday
        break;
    // other cases
}
```

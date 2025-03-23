### **Virtual Threads in Java 21**
Virtual Threads were introduced in **Java 19 (preview)** and became a stable feature in **Java 21** as part of **Project Loom**. They provide a lightweight and efficient way to handle concurrency, particularly for I/O-bound applications.

---

## **What are Virtual Threads?**
- Traditional threads in Java (`Thread` class) are **OS threads**, meaning they are heavyweight and managed by the operating system.
- **Virtual Threads** are **lightweight, managed by the JVM**, and do not map 1:1 to OS threads.
- Virtual threads **allow millions of concurrent tasks** to run without overwhelming system resources.

---

## **Use Cases of Virtual Threads**
1. **High-Concurrency Applications**
    - APIs and Web Servers handling thousands/millions of concurrent requests.
    - Microservices and reactive applications.

2. **Database-Driven Applications**
    - Query-heavy applications where database calls block the thread.

3. **Message Processing**
    - Applications consuming and processing large numbers of messages from Kafka, RabbitMQ, etc.

4. **Asynchronous Processing without Callbacks**
    - Replaces **CompletableFuture** and callback-based async programming.

---

## **How to Implement Virtual Threads in Java 21**
### **1. Creating Virtual Threads**
Virtual threads are created using `Thread.ofVirtual().start()` or via an `ExecutorService`.

#### **Example 1: Simple Virtual Thread**
```java
public class VirtualThreadExample {
    public static void main(String[] args) {
        Thread vThread = Thread.ofVirtual().start(() -> {
            System.out.println("Running in Virtual Thread: " + Thread.currentThread());
        });

        // Wait for the virtual thread to complete
        try {
            vThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

---

### **2. Using Virtual Thread Executor**
You can use `Executors.newVirtualThreadPerTaskExecutor()` to create a thread pool that automatically assigns tasks to virtual threads.

#### **Example 2: Virtual Thread Executor**
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadExecutor {
    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10; i++) {
                executor.submit(() -> {
                    System.out.println("Task executed by: " + Thread.currentThread());
                });
            }
        }  // ExecutorService will close automatically
    }
}
```
👉 **This creates a new virtual thread for each task, avoiding the need to reuse OS threads.**

---

### **3. Running Millions of Virtual Threads**
Since virtual threads are lightweight, you can create millions without exhausting resources.

#### **Example 3: Creating 1 Million Virtual Threads**
```java
import java.util.stream.IntStream;

public class MillionVirtualThreads {
    public static void main(String[] args) {
        IntStream.range(0, 1_000_000).forEach(i ->
            Thread.ofVirtual().start(() -> {
                System.out.println("Virtual Thread: " + i);
            })
        );
    }
}
```
👉 **Try running this with platform (OS) threads—you will hit system limits!**

---

## **Comparison: Virtual Threads vs Platform Threads**
| Feature         | Virtual Threads (`Thread.ofVirtual()`) | Platform Threads (`new Thread()`) |
|---------------|----------------------------------|---------------------------|
| OS Dependency | Managed by JVM                  | Managed by OS            |
| Cost          | Lightweight                      | Expensive                 |
| Number of Threads | Millions                     | Limited by OS             |
| Best For      | High-concurrency, I/O-heavy apps | CPU-intensive apps       |

---

## **Best Practices for Virtual Threads**
1. **Use Virtual Threads for I/O-bound tasks** (network calls, DB queries).
2. **Avoid CPU-intensive tasks** (encryption, large computations) in virtual threads.
3. **Use structured concurrency** (`ScopedValue`, `StructuredTaskScope`) to handle dependencies.


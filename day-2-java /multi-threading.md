# **Multi-threading in Java**



## **🧵 1️⃣ What is Multi-threading?**
Multi-threading allows a program to execute **multiple tasks at the same time**.

Imagine a **banking system** where:
- One thread processes **deposits**
- Another thread processes **withdrawals**
- Another thread checks **account balance**

Instead of doing these one after another, multi-threading **runs them in parallel**.

---

## **👨‍💻 2️⃣ Creating Threads in Java**

### **🔹 Example 1: Using the `Thread` Class**
```java
class BankTask extends Thread {
    private String task;

    public BankTask(String task) {
        this.task = task;
    }

    @Override
    public void run() {
        System.out.println(task + " is being processed by " + Thread.currentThread().getName());
    }
}

public class BankThreadExample {
    public static void main(String[] args) {
        BankTask deposit = new BankTask("Deposit");
        BankTask withdraw = new BankTask("Withdraw");

        deposit.start();  // Starts a new thread
        withdraw.start(); // Starts another thread
    }
}
```
**📝 Output (order may vary due to multi-threading):**
```
Deposit is being processed by Thread-0  
Withdraw is being processed by Thread-1  
```
**📌 Key Points:**  
✔ `start()` launches a new thread.  
✔ Each task runs **independently**.

---

### **🔹 Example 2: Using `Runnable` Interface**
A better way is using `Runnable`, so we don’t have to extend `Thread`.

```java
class Transaction implements Runnable {
    private String task;

    public Transaction(String task) {
        this.task = task;
    }

    @Override
    public void run() {
        System.out.println(task + " is being processed by " + Thread.currentThread().getName());
    }
}

public class BankRunnableExample {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Transaction("Deposit"));
        Thread t2 = new Thread(new Transaction("Withdraw"));

        t1.start();
        t2.start();
    }
}
```
✔ **More flexible than extending `Thread`**.  
✔ **Useful for thread pooling**.

---

## **🔄 3️⃣ Thread Synchronization (Avoiding Data Issues)**
If multiple threads access the **same bank account balance**, they might **withdraw more money than available**! We use **synchronized** to prevent this.

### **🔹 Example 3: Preventing Overdraw with Synchronization**
```java
class BankAccount {
    private double balance = 10000;

    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            System.out.println(Thread.currentThread().getName() + " is withdrawing $" + amount);
            balance -= amount;
            System.out.println("Remaining Balance: $" + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " tried to withdraw but insufficient funds!");
        }
    }
}

public class SynchronizedExample {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        Runnable task = () -> account.withdraw(5000);

        Thread t1 = new Thread(task, "Customer1");
        Thread t2 = new Thread(task, "Customer2");
        Thread t3 = new Thread(task, "Customer3");

        t1.start();
        t2.start();
        t3.start();
    }
}
```
✔ `synchronized` ensures **only one thread at a time** can withdraw money.

---

## **🚀 4️⃣ Thread Pooling with `ExecutorService`**
Instead of creating new threads **every time**, we can use a **thread pool** to reuse existing threads.

### **🔹 Example 4: Processing Transactions Efficiently**
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class TransactionTask implements Runnable {
    private String task;

    public TransactionTask(String task) {
        this.task = task;
    }

    @Override
    public void run() {
        System.out.println(task + " processed by " + Thread.currentThread().getName());
    }
}

public class ThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3); // 3 threads

        executor.execute(new TransactionTask("Deposit"));
        executor.execute(new TransactionTask("Withdraw"));
        executor.execute(new TransactionTask("Transfer"));

        executor.shutdown();
    }
}
```
✔ **Thread pools reuse threads**, reducing overhead.  
✔ Use `shutdown()` to stop execution after tasks are completed.


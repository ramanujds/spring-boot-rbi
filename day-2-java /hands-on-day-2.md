# **🛠️ Hands-on Tasks: Bank Account Management System**


### **📖 Story 1: Defining Bank Account Model Using Records**
Your bank needs a **lightweight model** to represent customer accounts. You decide to use **Java Records** to store account details.

💡 **TODO:**
1. Define a `record` named `BankAccount` with the following fields:
    - **Account Number** (String)
    - **Account Holder Name** (String)
    - **Balance** (double)
    - **Account Type** (String) (`SAVINGS`, `CURRENT`)
2. Create a **list of bank accounts** in your application.
3. Print all account details.

---

### **📖 Story 2: Filtering and Sorting Accounts Using Streams**
The bank wants to generate **reports on account balances**.

💡 **TODO:**
1. **Filter** accounts with a balance greater than $10,000.
2. **Sort** them by balance in **descending order**.
3. **Map** the result to print only **account numbers and balances**.

---

### **📖 Story 3: Implementing Different Account Types Using Sealed Classes**
The bank offers different types of accounts:
- **Savings Account** (earns 4% interest)
- **Current Account** (no interest, but allows overdrafts)

You need to **restrict inheritance** so only specific account types are allowed.

💡 **TODO:**
1. Define a **sealed class** `BankAccountType` with `permits` for `SavingsAccount` and `CurrentAccount`.
2. Implement an `interestCalculation()` method for **SavingsAccount**.
3. Implement an `overdraftLimit()` method for **CurrentAccount**.

---

### **📖 Story 4: Processing Transactions Using Pattern Matching**
Your bank system processes **different types of transactions**:
- **Deposit** increases the balance.
- **Withdrawal** decreases the balance (ensuring overdraft limits for current accounts).
- **Check Balance** simply returns the balance.

You decide to use **Pattern Matching in `switch` statements** to handle these cases.

💡 **TODO:**
1. Define a `Transaction` record with fields: `transactionType` (String), `amount` (double).
2. Implement a `switch` statement using **Pattern Matching** to handle different transaction types.
3. **Print the updated balance** after each transaction.

---

### **📖 Story 5: Grouping Accounts by Type Using Streams**
The bank wants a **summary of accounts grouped by type**.

💡 **TODO:**
1. **Group** accounts into `SAVINGS` and `CURRENT` using `Collectors.groupingBy()`.
2. Print each **account type** along with the list of accounts in that category.

---


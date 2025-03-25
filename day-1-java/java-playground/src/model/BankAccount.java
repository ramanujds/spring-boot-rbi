package model;

import java.util.Objects;

public class BankAccount implements Comparable<BankAccount> {

    public String accountNumber;
    public String accountHolderName;
    private double balance;
    private AccountType accountType;

    public BankAccount(String accountNumber, String accountHolderName, double balance, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("model.BankAccount{");
        sb.append("accountNumber='").append(accountNumber).append('\'');
        sb.append(", accountHolderName='").append(accountHolderName).append('\'');
        sb.append(", balance=").append(balance);
        sb.append(", accountType='").append(accountType).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        BankAccount that = (BankAccount) o;
        return Double.compare(balance, that.balance) == 0 && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(accountHolderName, that.accountHolderName) && accountType == that.accountType;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(accountNumber);
        result = 31 * result + Objects.hashCode(accountHolderName);
        result = 31 * result + Double.hashCode(balance);
        result = 31 * result + Objects.hashCode(accountType);
        return result;
    }

    public int compareTo(BankAccount b) {
       return this.accountHolderName.compareTo(b.accountHolderName);
    }

}

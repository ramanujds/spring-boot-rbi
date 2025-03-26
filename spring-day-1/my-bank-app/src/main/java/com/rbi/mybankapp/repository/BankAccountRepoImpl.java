package com.rbi.mybankapp.repository;

import com.rbi.mybankapp.model.BankAccount;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BankAccountRepoImpl implements BankAccountRepo {

    List<BankAccount> bankAccounts = new ArrayList<>();

    public BankAccount save(BankAccount account) {
        bankAccounts.add(account);
        return account;
    }

    public BankAccount getAccountDetails(String accountNumber) {
        return bankAccounts.stream().filter(b -> b.getAccountNumber().equals(accountNumber)).findFirst()
                .orElse(null);
    }

    public List<BankAccount> getAllAccounts() {
        return bankAccounts;
    }

    public void removeAccount(String accountNumber) {
        bankAccounts.removeIf(b->b.getAccountNumber().equals(accountNumber));
    }
}

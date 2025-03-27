package com.rbi.bankappspringdatajpa.service;

import com.rbi.bankappspringdatajpa.model.BankAccount;
import com.rbi.bankappspringdatajpa.repository.BankAccountRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService{

    private BankAccountRepo accountRepo;

    public BankAccountServiceImpl(BankAccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public BankAccount createAccount(BankAccount account) {
        return accountRepo.save(account);
    }

    public BankAccount getAccountById(long id) {
        return accountRepo.findById(id).get();
    }

    public List<BankAccount> getAllAccount() {
        return accountRepo.findAll();
    }

    public void removeAccount(long id) {
        accountRepo.deleteById(id);
    }

    public BankAccount deposit(long id, double amount) {
        BankAccount account = getAccountById(id);
        double updatedBalance = account.getBalance()+amount;
        account.setBalance(updatedBalance);
        return accountRepo.save(account);
    }

    public BankAccount withdraw(long id, double amount) {
        BankAccount account = getAccountById(id);
        if(amount>account.getBalance()) {
            throw new RuntimeException("Insufficient account balance");
        }
        double updatedBalance = account.getBalance() - amount;
        account.setBalance(updatedBalance);
        return accountRepo.save(account);
    }
}

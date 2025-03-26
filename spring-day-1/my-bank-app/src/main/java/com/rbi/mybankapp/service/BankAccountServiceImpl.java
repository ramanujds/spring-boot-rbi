package com.rbi.mybankapp.service;

import com.rbi.mybankapp.model.BankAccount;
import com.rbi.mybankapp.repository.BankAccountRepo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountRepo repo;

    public BankAccountServiceImpl(BankAccountRepo repo) {
        this.repo = repo;
    }

    public BankAccount createAccount(BankAccount account) {
        return repo.save(account);
    }

    public BankAccount getAccountDetails(String accountNumber) {
        return repo.getAccountDetails(accountNumber);
    }

    public List<BankAccount> getAllAccounts() {
        return repo.getAllAccounts();
    }

    public void removeAccount(String accountNumber) {
        repo.removeAccount(accountNumber);
    }
}

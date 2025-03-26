package com.rbi.mybankapp.repository;

import com.rbi.mybankapp.model.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class BankAccountRepoImpl implements BankAccountRepo {

    List<BankAccount> bankAccounts = new ArrayList<>();

    @Override
    public BankAccount save(BankAccount account) {
        return null;
    }

    @Override
    public BankAccount getAccountDetails(String accountNumber) {
        return null;
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        return List.of();
    }

    @Override
    public void removeAccount(String accountNumber) {

    }
}

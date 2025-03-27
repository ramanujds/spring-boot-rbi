package com.rbi.bankappspringdatajpa.service;

import com.rbi.bankappspringdatajpa.model.BankAccount;

import java.util.List;

public interface BankAccountService {

    BankAccount createAccount(BankAccount account);

    BankAccount getAccountById(long id);

    List<BankAccount> getAllAccount();

    void removeAccount(long id);

    BankAccount deposit(long id, double amount);

    BankAccount withdraw(long id, double amount);

}

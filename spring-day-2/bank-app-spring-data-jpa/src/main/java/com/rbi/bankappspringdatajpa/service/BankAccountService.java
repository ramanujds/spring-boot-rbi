package com.rbi.bankappspringdatajpa.service;

import com.rbi.bankappspringdatajpa.model.BankAccount;

import java.util.List;

public interface BankAccountService {

    BankAccount createAccount(BankAccount account);

    BankAccount getAccountDetails(String accountNumber);

    List<BankAccount> getAllAccount();

    void removeAccount(String accountNumber);

    BankAccount deposit(String accountNumber, double amount);

    BankAccount withdraw(String accountNumber, double amount);

}

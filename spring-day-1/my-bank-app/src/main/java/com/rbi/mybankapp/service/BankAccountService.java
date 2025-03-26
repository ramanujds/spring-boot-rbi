package com.rbi.mybankapp.service;

import com.rbi.mybankapp.model.BankAccount;

import java.util.List;

public interface BankAccountService {

    BankAccount createAccount(BankAccount account);

    BankAccount getAccountDetails(String accountNumber);

    List<BankAccount> getAllAccounts();

    void removeAccount(String accountNumber);

}

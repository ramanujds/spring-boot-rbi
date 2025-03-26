package com.rbi.mybankapp.repository;

import com.rbi.mybankapp.model.BankAccount;

import java.util.List;

public interface BankAccountRepo {

    BankAccount save(BankAccount account);

    BankAccount getAccountDetails(String accountNumber);

    List<BankAccount> getAllAccounts();

    void removeAccount(String accountNumber);

}

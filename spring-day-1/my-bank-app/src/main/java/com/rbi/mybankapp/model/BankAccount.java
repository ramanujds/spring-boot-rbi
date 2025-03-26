package com.rbi.mybankapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {

    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private AccountType accountType;

}

package com.rbi.bankappspringdatajpa.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true, length = 12)
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String accountType;

}

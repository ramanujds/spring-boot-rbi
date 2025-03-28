package com.rbi.bankappspringdatajpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransaction {
    @Id
    private long transactionId;
    private LocalDate date;
    private LocalTime time;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @ManyToOne
    private BankAccount account;

}

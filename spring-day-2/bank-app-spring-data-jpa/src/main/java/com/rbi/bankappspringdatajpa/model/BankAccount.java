package com.rbi.bankappspringdatajpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_account")
public class BankAccount {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
    @Id
    @Column( length = 12)
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String accountType;
    @OneToMany(mappedBy = "account")
    private Set<AccountTransaction> transactions = new HashSet<>();

}

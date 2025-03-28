package com.rbi.bankappspringdatajpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "bank_account")
public class BankAccount {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
    @Id
    @Column( length = 12)
    private String accountNumber;
    @JsonProperty("name")
    private String accountHolderName;
    private double balance;
    private String accountType;
    @OneToMany(mappedBy = "account")
    private Set<AccountTransaction> transactions = new HashSet<>();

}

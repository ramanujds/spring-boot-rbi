package com.rbi.bankappspringdatajpa.repository;

import com.rbi.bankappspringdatajpa.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount, String> {

 //   @Query("from BankAccount where accountNumber = :accountNumber")
 //   @Query(nativeQuery = true, value = "select * from bank_account where account_number = :accountNumber")

    Optional<BankAccount> findByAccountNumber(String accountNumber);
    
}

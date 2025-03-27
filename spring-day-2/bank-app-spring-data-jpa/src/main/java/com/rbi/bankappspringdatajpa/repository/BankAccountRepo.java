package com.rbi.bankappspringdatajpa.repository;

import com.rbi.bankappspringdatajpa.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount, Long> {
}

package com.rbi.bankappspringdatajpa.repository;

import com.rbi.bankappspringdatajpa.model.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionRepo extends JpaRepository<AccountTransaction,Long> {
}

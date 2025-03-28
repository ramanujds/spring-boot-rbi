package com.rbi.bankappspringdatajpa.service;

import com.rbi.bankappspringdatajpa.exception.RecordNotFoundException;
import com.rbi.bankappspringdatajpa.model.BankAccount;
import com.rbi.bankappspringdatajpa.repository.BankAccountRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @Mock
    BankAccountRepo repo;

    @InjectMocks
    BankAccountServiceImpl service;


    @Test
    void createAccount() {
    }

    @Test
    void getAccountDetailsPositiveTest() {

        BankAccount account = new BankAccount("123456789","John",2000,"Savings",null);

        Mockito.when(repo.findByAccountNumber("123456789")).thenReturn(Optional.of(account));

        Assertions.assertDoesNotThrow(()->service.getAccountDetails("123456789"));

        Mockito.verify(repo,Mockito.times(1)).findByAccountNumber("123456789");


    }

    @Test
    void getAccountDetailsNegativeTest() {

        BankAccount account = new BankAccount("123456789","John",2000,"Savings",null);

        Mockito.when(repo.findByAccountNumber("123456789")).thenReturn(Optional.empty());

        Assertions.assertThrows(RecordNotFoundException.class,()->service.getAccountDetails("123456789"));

        Mockito.verify(repo,Mockito.times(1)).findByAccountNumber("123456789");


    }


}
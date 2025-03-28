package com.rbi.bankappspringdatajpa.service;

import com.rbi.bankappspringdatajpa.dto.AccountTransactionRequestDto;
import com.rbi.bankappspringdatajpa.dto.AccountTransactionResponseDto;
import com.rbi.bankappspringdatajpa.exception.RecordNotFoundException;
import com.rbi.bankappspringdatajpa.model.AccountTransaction;
import com.rbi.bankappspringdatajpa.model.BankAccount;
import com.rbi.bankappspringdatajpa.model.TransactionType;
import com.rbi.bankappspringdatajpa.repository.AccountTransactionRepo;
import com.rbi.bankappspringdatajpa.repository.BankAccountRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Service
public class BankAccountServiceImpl implements BankAccountService{

    private BankAccountRepo accountRepo;
    private AccountTransactionRepo transactionRepo;

    public BankAccountServiceImpl(BankAccountRepo accountRepo, AccountTransactionRepo transactionRepo) {
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
    }

    public BankAccount createAccount(BankAccount account) {
        return accountRepo.save(account);
    }

    @Override
    public BankAccount getAccountDetails(String accountNumber) {
        return accountRepo.findByAccountNumber(accountNumber).orElseThrow(()->new RecordNotFoundException("Account with Account Number ["+accountNumber+"] Not Found"));
    }


    public List<BankAccount> getAllAccount() {
        return accountRepo.findAll();
    }

    @Override
    public void removeAccount(String accountNumber) {
        BankAccount account = getAccountDetails(accountNumber);
        if (account!=null){
            accountRepo.delete(account);
        }
    }


    @Transactional
    public AccountTransactionResponseDto deposit(AccountTransactionRequestDto transactionRequestDto) {
        BankAccount account = getAccountDetails(transactionRequestDto.accountNumber());
        if (account==null){
            throw new RuntimeException("Invalid Account Details");
        }
        double updatedBalance = account.getBalance()+transactionRequestDto.amount();
        account.setBalance(updatedBalance);
        account = accountRepo.save(account);
        AccountTransaction transaction = createTransaction(account, transactionRequestDto.amount(), TransactionType.CREDIT);
        transaction = transactionRepo.save(transaction);
    //    account.getTransactions().add(transaction);
        return getTransactionResponseDto(transaction);

    }

    private AccountTransaction createTransaction(BankAccount account, double amount, TransactionType type){
        Random rand = new Random(10000000);
        long transactionId = Math.abs(rand.nextLong());
        AccountTransaction transaction = new AccountTransaction(transactionId,
                LocalDate.now(), LocalTime.now(),
                amount,
                type,
                account);
        return transaction;
    }

    private AccountTransactionResponseDto getTransactionResponseDto(AccountTransaction transaction){
        AccountTransactionResponseDto dto = new AccountTransactionResponseDto(
                transaction.getAccount().getAccountNumber(),
                transaction.getTransactionId(),
                transaction.getDate(),
                transaction.getTime(),
                transaction.getAmount(),
                transaction.getTransactionType()
        );
        return dto;
    }

    public BankAccount withdraw(String accountNumber, double amount) {
        BankAccount account = getAccountDetails(accountNumber);
        if (account==null){
            throw new RuntimeException("Invalid Account Details");
        }
        if(amount>account.getBalance()) {
            throw new RuntimeException("Insufficient account balance");
        }
        double updatedBalance = account.getBalance() - amount;
        account.setBalance(updatedBalance);
        return accountRepo.save(account);
    }



}

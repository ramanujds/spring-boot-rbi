package com.rbi.bankappspringdatajpa.service;

import com.rbi.bankappspringdatajpa.dto.AccountTransactionRequestDto;
import com.rbi.bankappspringdatajpa.dto.AccountTransactionResponseDto;
import com.rbi.bankappspringdatajpa.model.BankAccount;
import com.rbi.bankappspringdatajpa.repository.BankAccountRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class BankAccountServiceImpl implements BankAccountService{

    private BankAccountRepo accountRepo;

    public BankAccountServiceImpl(BankAccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public BankAccount createAccount(BankAccount account) {
        return accountRepo.save(account);
    }

    @Override
    public BankAccount getAccountDetails(String accountNumber) {
        return accountRepo.findByAccountNumber(accountNumber).orElse(null);
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


    public AccountTransactionResponseDto deposit(AccountTransactionRequestDto transactionRequestDto) {
        BankAccount account = getAccountDetails(transactionRequestDto.accountNumber());
        if (account==null){
            throw new RuntimeException("Invalid Account Details");
        }
        double updatedBalance = account.getBalance()+transactionRequestDto.amount();
        account.setBalance(updatedBalance);
        account = accountRepo.save(account);
        Random rand = new Random(10000000);
        long transactionId = rand.nextLong();
        AccountTransactionResponseDto transactionResponseDto = new AccountTransactionResponseDto(
                account.getAccountNumber(),
                transactionId,
                LocalDateTime.now(),
                transactionRequestDto.amount(),
                "Deposit",
                account.getBalance()
        );
        return transactionResponseDto;

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

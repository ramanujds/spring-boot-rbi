package com.rbi.bankappspringdatajpa.api;

import com.rbi.bankappspringdatajpa.model.BankAccount;
import com.rbi.bankappspringdatajpa.service.BankAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class BankAccountController {

    private BankAccountService bankService;

    public BankAccountController(BankAccountService bankService) {
        this.bankService = bankService;
    }

//    @PostMapping
//    @ResponseStatus(code = HttpStatus.CREATED)
//    public BankAccount createAccount(@RequestBody BankAccount account){
//        return bankService.createAccount(account);
//    }

    @PostMapping
    public ResponseEntity<BankAccount> createAccount(@RequestBody BankAccount account){
        BankAccount savedAccount = bankService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }



    @GetMapping
    public List<BankAccount> getAllAccounts(){
        return bankService.getAllAccount();
    }

    @GetMapping("/{accountNumber}")
    public BankAccount findAccount(@PathVariable String accountNumber){
        return bankService.getAccountDetails(accountNumber);
    }

    @DeleteMapping("/{accountNumber}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAccountById(@PathVariable String accountNumber){
        bankService.removeAccount(accountNumber);
    }

    @PatchMapping("/{accountNumber}/deposit/{amount}")
    @ResponseStatus(code=HttpStatus.ACCEPTED)
    public BankAccount depositAmount(@PathVariable String accountNumber, @PathVariable double amount){
        return bankService.deposit(accountNumber,amount);
    }


    @PatchMapping("/{accountNumber}/withdraw/{amount}")
    @ResponseStatus(code=HttpStatus.ACCEPTED)
    public BankAccount withdrawAmount(@PathVariable String accountNumber, @PathVariable double amount){
        return bankService.withdraw(accountNumber,amount);
    }


}

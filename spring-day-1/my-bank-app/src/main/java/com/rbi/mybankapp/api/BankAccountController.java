package com.rbi.mybankapp.api;

import com.rbi.mybankapp.model.BankAccount;
import com.rbi.mybankapp.service.BankAccountService;
import com.rbi.mybankapp.service.BankAccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class BankAccountController {

    private BankAccountService service;

    public BankAccountController(BankAccountService service) {
        this.service = service;
    }


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public BankAccount createAccount(@RequestBody BankAccount account){
        return service.createAccount(account);
    }

    @GetMapping
    public List<BankAccount> getAllAccounts(){
        return service.getAllAccounts();
    }

    @GetMapping("/{accountNumber}")
    public BankAccount findBankAccount(@PathVariable String accountNumber){
        return service.getAccountDetails(accountNumber);
    }


    @DeleteMapping("/{accountNumber}")
    public void deleteAccount(@PathVariable("accountNumber") String accountNumber){
        service.removeAccount(accountNumber);
    }



}

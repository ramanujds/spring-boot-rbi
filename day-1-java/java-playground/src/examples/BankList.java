package examples;

import model.AccountType;
import model.BankAccount;
import model.BankAccountDto;

import java.util.*;

public class BankList {

    public static void main(String[] args) {

        Set<BankAccount> bankAccounts = new TreeSet<>();

        var account1 = new BankAccount("abcd1234","Harsh",10000,AccountType.SAVINGS);
        var account2 = new BankAccount("xyz12345","Amit",50000, AccountType.CURRENT);
        var account3 = new BankAccount("pqr12345","Mohit",20000, AccountType.SAVINGS);
        var account4 = new BankAccount("lmn12345","Rahul",30000, AccountType.CURRENT);



        bankAccounts.add(account1);
        bankAccounts.add(account2);
        bankAccounts.add(account3);
        bankAccounts.add(account4);




       // Find all the account with balances more than 20000

        List<BankAccount> filteredAccounts = bankAccounts.stream()
                .filter(b->b.getBalance()>=20000)
                .peek(b-> b.setBalance(b.getBalance()*1.1))
                .toList();


//        for (var bankAccount : filteredAccounts) {
//            System.out.println(bankAccount);
//        }

        filteredAccounts.forEach(System.out::println);

        // Update the balance by 10%


    }

}

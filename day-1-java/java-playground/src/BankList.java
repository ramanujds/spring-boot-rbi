import java.util.*;

public class BankList {

    public static void main(String[] args) {

        Set<BankAccountDto> bankAccounts = new TreeSet<>();

        var account1 = new BankAccountDto("abcd1234","Harsh",10000,AccountType.SAVINGS);
        var account2 = new BankAccountDto("xyz12345","Amit",50000,AccountType.CURRENT);



        bankAccounts.add(account1);
        bankAccounts.add(account2);


       for (var bankAccount : bankAccounts) {
           System.out.println(bankAccount);
       }

    }

}

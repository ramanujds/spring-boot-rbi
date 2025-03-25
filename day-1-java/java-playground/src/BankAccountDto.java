public record BankAccountDto(String accountNumber,
                             String accountHolderName,
                             double balance,
                             AccountType accountType) implements Comparable<BankAccountDto> {


    @Override
    public int compareTo(BankAccountDto b) {
        return this.accountHolderName.compareTo(b.accountHolderName);
    }
}

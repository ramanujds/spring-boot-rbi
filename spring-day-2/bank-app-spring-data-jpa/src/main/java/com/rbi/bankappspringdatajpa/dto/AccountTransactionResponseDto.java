package com.rbi.bankappspringdatajpa.dto;

import java.time.LocalDateTime;

public record AccountTransactionResponseDto(String accountNumber, long transactionId, LocalDateTime time, double amount, String type, double balance) {
}

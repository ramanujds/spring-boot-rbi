package com.rbi.bankappspringdatajpa.dto;

import com.rbi.bankappspringdatajpa.model.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AccountTransactionResponseDto(String accountNumber, long transactionId, LocalDate date, LocalTime time, double amount, TransactionType type) {
}

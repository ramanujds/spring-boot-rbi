package com.rbi.bankappspringdatajpa.exception;


public class DuplicateRecordException extends RuntimeException{

    public DuplicateRecordException(String message) {
        super(message);
    }
}

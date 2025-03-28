package com.rbi.bankappspringdatajpa.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class RecordNotFoundException extends RuntimeException{

    public RecordNotFoundException(String message) {
        super(message);
    }
}

package com.rbi.bankappspringdatajpa.exception;

import com.rbi.bankappspringdatajpa.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class BankAccountApiExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleRecordNotFoundException(RecordNotFoundException ex,
                                                                         HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponseDto dto = new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(dto);

    }


//    @ExceptionHandler(DuplicateRecordException.class)
//    public ResponseEntity<ErrorResponseDto> handleDuplicateRecordException(DuplicateRecordException ex,
//                                                                          HttpServletRequest request){
//        HttpStatus status = HttpStatus.CONFLICT;
//        ErrorResponseDto dto = new ErrorResponseDto(
//                LocalDateTime.now(),
//                status.value(),
//                status.getReasonPhrase(),
//                ex.getMessage(),
//                request.getRequestURI()
//        );
//        return ResponseEntity.status(status).body(dto);
//
//    }

    @ExceptionHandler(DuplicateRecordException.class)
    public ProblemDetail handleDuplicateRecordException(DuplicateRecordException ex){
        ProblemDetail response = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        response.setDetail(ex.getMessage());
        return response;
    }



}

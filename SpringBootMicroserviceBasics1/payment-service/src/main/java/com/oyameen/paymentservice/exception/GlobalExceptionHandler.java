package com.oyameen.paymentservice.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BadPaymentRequestException.class})
    public ResponseEntity<ErrorModel> handleBadPaymentRequestException(BadPaymentRequestException exception)
    {
        return ResponseEntity.status(400).body(new ErrorModel(
                System.currentTimeMillis(),
                400,
                "Bad Payment Request",
                exception.getMessage()
        ));
    }
}

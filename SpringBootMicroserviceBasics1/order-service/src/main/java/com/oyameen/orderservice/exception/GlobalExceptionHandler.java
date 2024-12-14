package com.oyameen.orderservice.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BadOrderRequestException.class})
    public ResponseEntity<ErrorModel> handleBadOrderRequestException(BadOrderRequestException exception)
    {
        return ResponseEntity.status(400).body(new ErrorModel(
                System.currentTimeMillis(),
                400,
                "Bad Order Request",
                exception.getMessage()
        ));
    }
}

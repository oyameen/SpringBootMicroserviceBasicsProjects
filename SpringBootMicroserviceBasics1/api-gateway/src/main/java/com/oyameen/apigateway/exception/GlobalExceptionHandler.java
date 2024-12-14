package com.oyameen.apigateway.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {UnauthorizedAccessException.class})
    public ResponseEntity<ErrorModel> handleUnauthorizedAccessException(UnauthorizedAccessException exception)
    {
        return ResponseEntity.status(401).body(new ErrorModel(
                System.currentTimeMillis(),
                401,
                "Unauthorized Request",
                exception.getMessage()
        ));
    }
}

package com.oyameen.paymentservice.exception;

public class BadPaymentRequestException extends RuntimeException {
    public BadPaymentRequestException(String message) {
        super(message);
    }
}

package com.oyameen.paymentservice.service;

import com.oyameen.paymentservice.exception.BadPaymentRequestException;
import com.oyameen.paymentservice.model.Payment;
import com.oyameen.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public static void validateAccess(Map<String, String> headers) {
        if (headers == null || headers.isEmpty() || !headers.containsKey("useremail") || !headers.containsKey("roles")) {
            throw new BadPaymentRequestException("Request headers must contains userEmail and roles.");
        }
        if (headers.get("roles") == null || headers.get("roles").isEmpty()) {
            throw new BadPaymentRequestException("Headers role should not be empty.");
        }
        List<String> roles = Arrays.asList(headers.get("roles").split(","));
        if (!roles.contains("ROLE_ADMIN") && !roles.contains("ROLE_USER")) {
            throw new BadPaymentRequestException("Payment service api, must be accessed by admin or normal user only.");
        }
    }

    public Payment getPaymentByOrderId(int orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElse(null);
        if (payment == null) {
            throw new BadPaymentRequestException("Payment with order id = [ " + orderId + " ], not found.");
        }
        return payment;
    }

    public Payment makePayment(Payment payment) {
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setStatus(new Random().nextBoolean() ? "SUCCESS" : "FAILED");
        return paymentRepository.save(payment);
    }
}

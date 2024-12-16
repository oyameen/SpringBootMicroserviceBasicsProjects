package com.oyameen.paymentservice.controller;

import com.oyameen.paymentservice.model.Payment;
import com.oyameen.paymentservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.oyameen.paymentservice.service.PaymentService.validateAccess;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{orderId}")
    public Payment getPaymentByOrderId(@PathVariable int orderId, @RequestHeader Map<String, String> headers) {
        validateAccess(headers);
        log.info("getPaymentByOrderId api invoked by userEmail = {}", headers.get("useremail"));
        return paymentService.getPaymentByOrderId(orderId);
    }


    @PostMapping("/makePayment")
    public Payment makePayment(@RequestBody Payment payment, @RequestHeader Map<String, String> headers) {
        validateAccess(headers);
        log.info("makePayment api invoked by userEmail = {}", headers.get("useremail"));
        return paymentService.makePayment(payment);
    }
}

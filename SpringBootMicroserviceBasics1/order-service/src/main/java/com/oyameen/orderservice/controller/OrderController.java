package com.oyameen.orderservice.controller;

import com.oyameen.orderservice.dto.OrderResponseDto;
import com.oyameen.orderservice.exception.BadOrderRequestException;
import com.oyameen.orderservice.exception.ErrorModel;
import com.oyameen.orderservice.model.Order;
import com.oyameen.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private static final String ORDER_SERVICE = "orderService";
    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    @CircuitBreaker(name = ORDER_SERVICE, fallbackMethod = "paymentServiceUnavailableFallback")
    @Retry(name = ORDER_SERVICE,fallbackMethod = "paymentServiceRetryFallback")
    public ResponseEntity<OrderResponseDto> addOrder(@RequestBody Order order, @RequestHeader Map<String,String> headers)
    {
        log.info("addOrder api invoked by userEmail = {}", headers.get("useremail"));
        return ResponseEntity.ok(orderService.saveOrder(order, headers));
    }
    public ResponseEntity<ErrorModel> paymentServiceUnavailableFallback(Exception e)
    {
        if (e instanceof BadOrderRequestException exception)
        {
            throw exception;
        }
        return ResponseEntity.status(503).body(new ErrorModel(
                System.currentTimeMillis(),
                503,
                "Service not available currently.",
                e.getMessage()
        ));
    }
    public ResponseEntity<ErrorModel> paymentServiceRetryFallback(Exception e)
    {
        if (e instanceof BadOrderRequestException exception)
        {
            throw exception;
        }
        return ResponseEntity.status(503).body(new ErrorModel(
                System.currentTimeMillis(),
                503,
                "Service retrying to connect again.",
                e.getMessage()
        ));
    }
}

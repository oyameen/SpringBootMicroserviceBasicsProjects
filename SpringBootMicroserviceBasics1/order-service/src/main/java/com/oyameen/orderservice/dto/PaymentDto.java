package com.oyameen.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PaymentDto {

    private int id;
    private String status;
    private String transactionId;
    private int orderId;
    private double amount;
}

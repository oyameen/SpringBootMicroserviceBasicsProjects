package com.oyameen.orderservice.dto;

import com.oyameen.orderservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrderResponseDto {

    private PaymentDto paymentDto;
    private Order order;
}

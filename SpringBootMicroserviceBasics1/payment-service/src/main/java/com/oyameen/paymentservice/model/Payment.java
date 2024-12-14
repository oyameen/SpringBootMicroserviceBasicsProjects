package com.oyameen.paymentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "PAYMENT_TABLE")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String status;
    private String transactionId;
    private int orderId;
    private double amount;

}

package com.oyameen.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ORDER_TABLE")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Order {

    @Id
    private int id;
    private String name;
    private double price;
    private int quantity;
}

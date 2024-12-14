package com.oyameen.orderservice.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ErrorModel {
    private long timeStamp;
    private int status;
    private String error;
    private String message;
}

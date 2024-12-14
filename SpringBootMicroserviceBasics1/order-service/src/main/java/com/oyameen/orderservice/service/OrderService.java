package com.oyameen.orderservice.service;

import com.oyameen.orderservice.dto.OrderResponseDto;
import com.oyameen.orderservice.dto.PaymentDto;
import com.oyameen.orderservice.exception.BadOrderRequestException;
import com.oyameen.orderservice.model.Order;
import com.oyameen.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private RestTemplate template;

    @Autowired
    private OrderRepository orderRepository;


    @Value("${microservice.payment-service.endpoint.uri}")
    private String ENDPOINT_URL;

    public OrderResponseDto saveOrder(Order order, Map<String, String> headers){

        if (order.getId() <= 0)
        {
            throw new BadOrderRequestException("Order id cannot be less than or equal zero.");
        }
        if (orderRepository.existsById(order.getId()))
        {
            throw new BadOrderRequestException("Order with id = [ " + order.getId() + " ], already exist.");
        }
        PaymentDto paymentDto = new PaymentDto();

        paymentDto.setOrderId(order.getId());
        paymentDto.setAmount(order.getPrice());
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach(httpHeaders::add);
        HttpEntity<PaymentDto> httpEntity = new HttpEntity<>(paymentDto,httpHeaders);
        PaymentDto paymentResponse = template.postForObject(ENDPOINT_URL, httpEntity, PaymentDto.class);
        orderRepository.save(order);

        return new OrderResponseDto(paymentResponse,order);
    }
}

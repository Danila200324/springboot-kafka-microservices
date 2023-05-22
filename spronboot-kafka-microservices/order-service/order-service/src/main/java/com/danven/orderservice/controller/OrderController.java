package com.danven.orderservice.controller;


import com.danven.orderservice.kafka.OrderProducer;
import com.danven.springbootmicroserviceskafka.dto.Order;
import com.danven.springbootmicroserviceskafka.dto.OrderEvent;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api/")
public class OrderController {

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer){
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public String placeOrder(@RequestBody Order order){
        Random random = new Random();
        order.setId(random.nextInt(200));

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatusOfEvent("Sending");
        orderEvent.setMessage("Order is sending");
        orderEvent.setOrder(order);
        orderProducer.sendMessage(orderEvent);
        return "Success";
    }
}

package org.example.learn.spring.cloud.hello.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{orderId}")
    public ResponseEntity<String> getOrderById(@PathVariable String orderId) {
        String userInfo = restTemplate.getForObject("http://user-service/users/1", String.class);
        return ResponseEntity.ok("Order " + orderId + " for " + userInfo);
    }
}
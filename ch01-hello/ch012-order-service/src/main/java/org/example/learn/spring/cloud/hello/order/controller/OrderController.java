package org.example.learn.spring.cloud.hello.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.learn.spring.cloud.commons.model.dto.BaseResponseData;
import org.example.learn.spring.cloud.commons.model.dto.OrderDto;
import org.example.learn.spring.cloud.commons.model.dto.UserDto;
import org.example.learn.spring.cloud.commons.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{orderId}")
    public BaseResponseData<OrderDto> getOrderById(@PathVariable String orderId) throws JsonProcessingException {
        String responseData = restTemplate.getForObject("http://user-service/users/1", String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponseData<UserDto> userInfo = objectMapper.readValue(responseData, new TypeReference<BaseResponseData<UserDto>>() {});

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(orderId);
        orderDto.setUserInfo(userInfo.getData());
        return ResponseUtils.ok(orderDto);
    }

    @GetMapping("/json/{orderId}")
    public BaseResponseData<OrderDto> getOrderByIdNew(@PathVariable String orderId) {
        ResponseEntity<BaseResponseData<UserDto>> responseData = restTemplate.exchange("http://user-service/users/1", HttpMethod.GET, null, new ParameterizedTypeReference<BaseResponseData<UserDto>>() {});

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(orderId);
        orderDto.setUserInfo(responseData.getBody().getData());;
        return ResponseUtils.ok(orderDto);
    }
}
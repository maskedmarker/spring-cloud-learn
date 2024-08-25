package org.example.learn.spring.cloud.commons.model.dto;

import java.io.Serializable;

public class OrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderId;

    private UserDto userInfo;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public UserDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserDto userInfo) {
        this.userInfo = userInfo;
    }
}

package org.example.learn.spring.cloud.feign.order.third;

import org.example.learn.spring.cloud.commons.model.dto.BaseResponseData;
import org.example.learn.spring.cloud.commons.model.dto.UserDto;
import org.example.learn.spring.cloud.feign.order.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @FeignClient(name = "user-service")与服务在 Eureka（或其他服务发现机制）中的注册名称相同
 * @GetMapping("/users/{id}") 用于指定远程服务的 API 路径
 *
 */
//@Service
@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserServiceClient {

    @GetMapping("/users/{id}")
    BaseResponseData<UserDto> getUserById(@PathVariable("id") Long id);
}

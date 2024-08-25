package org.example.learn.spring.cloud.hello.user.controller;

import org.example.learn.spring.cloud.commons.model.dto.BaseResponseData;
import org.example.learn.spring.cloud.commons.model.dto.UserDto;
import org.example.learn.spring.cloud.commons.util.ResponseUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{userId}")
    public BaseResponseData<UserDto> getUserById(@PathVariable String userId) {
        UserDto userDto = new UserDto();
        userDto.setUserId(userId);
        return ResponseUtils.ok(userDto);
    }
}

package com.example.task_service.client;

import com.example.task_service.config.FeignClientConfig;
import com.example.task_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// name = "user-service" must match the spring.application.name of the User Service
@FeignClient(name = "user-service" , configuration = FeignClientConfig.class)
public interface UserClient {

    // This must exactly match the endpoint signature in the UserController
    @GetMapping("/users/by-email")
    UserResponse getUserByEmail(@RequestParam String email);
}
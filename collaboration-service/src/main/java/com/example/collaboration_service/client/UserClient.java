package com.example.collaboration_service.client;

import com.example.collaboration_service.config.FeignClientConfig;
import com.example.collaboration_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", configuration = FeignClientConfig.class)
public interface UserClient {
    @GetMapping("/users/by-email")
    UserResponse getUserByEmail(@RequestParam("email") String email);
}

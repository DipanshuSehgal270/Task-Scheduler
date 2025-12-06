package com.example.task_service.client;

import com.example.task_service.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "collaboration-service" , configuration = FeignClientConfig.class)
public interface CollaborationClient {

    @GetMapping("/lists/{tasklistId}/check-membership")
    ResponseEntity<?> checkMembership(@PathVariable("tasklistId") Long tasklistId);
}

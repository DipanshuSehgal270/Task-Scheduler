package com.example.collaboration_service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

// this class helps in sending authorization header in the feign
// client request else we will hit 403 as authorization header would be blank.

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        // This is our "Secure Memo Policy"
        return template -> {
            // Get the original request (the one from Postman)
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                // Find the Authorization header on that original request
                String authorizationHeader = attributes.getRequest().getHeader("Authorization");
                if (authorizationHeader != null) {
                    // Copy that header onto the new, outgoing request
                    template.header("Authorization", authorizationHeader);
                }
            }
        };
    }

}

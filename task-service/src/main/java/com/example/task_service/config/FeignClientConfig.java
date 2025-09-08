package com.example.task_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

// this class is here to intercept the jwt token call.
// It needs to first authenticate and then call for requests to another services.

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // LOG MESSAGE
                System.out.println("--- Feign Interceptor is running! ---");

                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    String authorizationHeader = attributes.getRequest().getHeader("Authorization");
                    if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
                        // LOG MESSAGE
                        System.out.println("--- Found Authorization header, adding to Feign request ---");
                        template.header("Authorization", authorizationHeader);
                    } else {
                        System.out.println("--- No Authorization header found in original request ---");
                    }
                }
            }
        };
    }
}

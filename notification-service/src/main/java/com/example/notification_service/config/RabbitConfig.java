package com.example.notification_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue invitationQueue() {
        // "false" means not durable (lost on restart), match this with your producer config
        return new Queue("invitationQueue", false);
    }
}
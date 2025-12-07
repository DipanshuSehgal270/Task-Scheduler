package com.example.collaboration_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfig {

    // Define the queue name so RabbitMQ creates it if it doesn't exist
    @Bean
    public Queue invitationQueue() {
        return new Queue("invitationQueue", false);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

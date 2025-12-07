package com.example.notification_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue invitationQueue() {
        // "false" means not durable (lost on restart), match this with your producer config
        return new Queue("invitationQueue", false);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
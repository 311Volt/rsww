package com.yetistudios.rsww.rswwtouroperator.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue defaultQueue() {
        return new Queue("testqueue", true);
    }
}

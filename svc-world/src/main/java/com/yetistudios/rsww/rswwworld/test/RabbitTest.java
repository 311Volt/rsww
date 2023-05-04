package com.yetistudios.rsww.rswwworld.test;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RabbitTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 2000)
    public void testsend() {
        System.err.println("testsend");
        rabbitTemplate.convertAndSend("testqueue", "yeti4ever");
    }
}

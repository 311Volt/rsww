package com.yetistudios.rsww.rswwworld.test;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RabbitTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 20000)
    public void testsend() {
        rabbitTemplate.convertAndSend("testqueue", "yeti4ever");
    }
}

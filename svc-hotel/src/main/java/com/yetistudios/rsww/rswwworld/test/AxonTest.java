package com.yetistudios.rsww.rswwworld.test;

import com.yetistudios.rsww.messages.YeetEvent;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AxonTest {

    @Autowired
    private EventGateway eventGateway;

    @Scheduled(fixedDelay = 700)
    public void yeet() {
        YeetEvent message = new YeetEvent();
        message.contents = "bruh xD";
        eventGateway.publish(message);
    }
}

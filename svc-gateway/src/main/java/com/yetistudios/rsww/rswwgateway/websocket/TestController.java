package com.yetistudios.rsww.rswwgateway.websocket;

import com.yetistudios.rsww.common.messages.event.OfferDecreaseAmountEvent;
import com.yetistudios.rsww.common.util.RandomUtil;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("ws-test")
public class TestController {

    @Autowired
    private EventGateway eventGateway;

    private static final Random random = new Random();

    @PostMapping("/req7-send-event")
    public void r7sendEvent() {
        eventGateway.publish(
                new OfferDecreaseAmountEvent(
                        UUID.randomUUID().toString(),
                        "exampleOfferId",
                        RandomUtil.randomInt(1, 10)
                )
        );
    }
}

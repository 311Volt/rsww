package com.yetistudios.rsww.rswwgateway.websocket;

import com.yetistudios.rsww.common.messages.event.OfferDecreaseAmountEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class Req7Service {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @EventHandler
    public void handle(OfferDecreaseAmountEvent event) {
        simpMessagingTemplate.convertAndSend("/req7topic/purchase/" + event.getOfferId(), event);
    }

}

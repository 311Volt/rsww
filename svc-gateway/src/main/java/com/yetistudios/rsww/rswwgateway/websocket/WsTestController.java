package com.yetistudios.rsww.rswwgateway.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WsTestController {
    @MessageMapping("/test")
    public String test() {
        return "ok";
    }
}

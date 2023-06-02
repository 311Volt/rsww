package com.yetistudios.rsww.rswwgateway.test;

import com.yetistudios.rsww.messages.event.YeetEvent;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AxonTest {

    private Logger logger = LoggerFactory.getLogger(AxonTest.class);

    @EventHandler
    public void onYeetMsg(YeetEvent event) {
        logger.info("EVENT RECEIVED: " + event.contents);
    }
}

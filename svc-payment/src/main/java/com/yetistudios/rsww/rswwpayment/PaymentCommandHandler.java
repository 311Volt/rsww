package com.yetistudios.rsww.rswwpayment;

import com.yetistudios.rsww.common.messages.command.ValidatePaymentCommand;
import com.yetistudios.rsww.common.messages.event.PaymentInvalidEvent;
import com.yetistudios.rsww.common.messages.event.PaymentValidEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentCommandHandler {
    @Autowired
    private EventGateway eventGateway;

    @CommandHandler
    void on(ValidatePaymentCommand command) {
        //wait(20000);

        if(Math.random() <= 0.01){
            eventGateway.publish(new PaymentInvalidEvent(command.getReservationId()));
        } else {
            eventGateway.publish(new PaymentValidEvent(command.getReservationId()));
        }

    }
}

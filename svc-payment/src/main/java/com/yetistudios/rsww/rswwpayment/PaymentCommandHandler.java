package com.yetistudios.rsww.rswwpayment;

import com.yetistudios.rsww.common.dto.ReservationDto;
import com.yetistudios.rsww.common.messages.command.ValidatePaymentCommand;
import com.yetistudios.rsww.common.messages.event.PaymentInvalidEvent;
import com.yetistudios.rsww.common.messages.event.PaymentValidEvent;
import com.yetistudios.rsww.common.messages.query.GetReservationQuery;
import lombok.SneakyThrows;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentCommandHandler {
    @Autowired
    private EventGateway eventGateway;

    @Autowired
    private QueryGateway queryGateway;

    @CommandHandler
    void on(ValidatePaymentCommand command) {
        if(command.isPaid()){
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    Thread.sleep((long) (Math.random() * 10000));
                    eventGateway.publish(new PaymentValidEvent(command.getReservationId()));
                }
            }).start();

        } else {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    Thread.sleep(60000);

                    GetReservationQuery getReservationQuery = new GetReservationQuery(command.getReservationId());

                    ReservationDto reservation;
                    try {
                        reservation = queryGateway.query(getReservationQuery, ResponseTypes.instanceOf(ReservationDto.class)).join();
                    } catch (Exception e){
                        eventGateway.publish(new PaymentInvalidEvent(command.getReservationId()));
                        return;
                    }
                    if(!reservation.isPaid()){
                        eventGateway.publish(new PaymentInvalidEvent(command.getReservationId()));
                    }
                }
            }).start();
        }
    }
}

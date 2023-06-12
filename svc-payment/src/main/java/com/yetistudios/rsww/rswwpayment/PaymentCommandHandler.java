package com.yetistudios.rsww.rswwpayment;

import com.yetistudios.rsww.common.dto.ReservationDto;
import com.yetistudios.rsww.common.messages.command.ValidatePaymentCommand;
import com.yetistudios.rsww.common.messages.event.PaymentInvalidEvent;
import com.yetistudios.rsww.common.messages.event.PaymentValidEvent;
import com.yetistudios.rsww.common.messages.query.GetReservationQuery;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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
                    log.info("New reservation that has paid status as: " + command.isPaid());
                    Thread.sleep(60000);
                    GetReservationQuery getReservationQuery = new GetReservationQuery(command.getReservationId());

                    ReservationDto reservation;
                    try {
                        reservation = queryGateway.query(getReservationQuery, ResponseTypes.instanceOf(ReservationDto.class)).join();
                    } catch (Exception e){
                        log.info("Failed to get reservation");
                        eventGateway.publish(new PaymentInvalidEvent(command.getReservationId()));
                        return;
                    }
                    log.info("Got reservation");
                    if(!reservation.isPaid()){
                        log.info("Still wasn't paid");
                        eventGateway.publish(new PaymentInvalidEvent(command.getReservationId()));
                    }
                }
            }).start();
        }
    }
}

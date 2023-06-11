package com.yetistudios.rsww.rswwhotel.command;

import com.yetistudios.rsww.common.messages.command.BookHotelCommand;
import com.yetistudios.rsww.common.messages.command.CancelHotelBookingCommand;
import com.yetistudios.rsww.common.messages.event.HotelReservationFailedEvent;
import com.yetistudios.rsww.common.messages.event.HotelReservationSuccessfulEvent;
import com.yetistudios.rsww.rswwhotel.command.service.HotelOccupationCommandService;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HotelOccupationCommandHandler {

    @Autowired
    private HotelOccupationCommandService service;

    @Autowired
    private EventGateway eventGateway;

    @CommandHandler
    void on(BookHotelCommand command) {
        try {
            service.bookRooms(command);
            eventGateway.publish(
                    HotelReservationSuccessfulEvent
                        .builder()
                        .reservationId(command.reservationId)
                        .build()
            );
        } catch(Exception ex) {
            eventGateway.publish(
                    HotelReservationFailedEvent
                            .builder()
                            .reservationId(command.reservationId)
                            .reason(ex.getClass().getName())
            );
        }
    }

    @CommandHandler
    void on(CancelHotelBookingCommand command) {
        service.cancelReservation(command);
        eventGateway.publish(
                HotelReservationFailedEvent
                        .builder()
                        .reservationId(command.getReservationId())
                        .reason("Cancel Hotel booking for " + command.getReservationId())
                        .build());
    }

}

package com.yetistudios.rsww.rswwhotel.command;

import com.yetistudios.rsww.messages.command.ReserveHotelCommand;
import com.yetistudios.rsww.messages.event.HotelReservationFailedEvent;
import com.yetistudios.rsww.messages.event.HotelReservationSuccessfulEvent;
import com.yetistudios.rsww.rswwhotel.command.exception.HotelDoesNotExistException;
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
    void on(ReserveHotelCommand command) {
        try {
            service.bookRooms(command);
            eventGateway.publish(
                    HotelReservationSuccessfulEvent
                        .builder()
                        .reservationId(command.reservationId).build()
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

}

package com.yetistudios.rsww.rswwflight.command;

import com.yetistudios.rsww.common.messages.command.BookFlightCommand;
import com.yetistudios.rsww.common.messages.command.BookReturnFlightCommand;
import com.yetistudios.rsww.common.messages.command.CancelFlightBookingCommand;
import com.yetistudios.rsww.common.messages.event.PlaneReservationFailedEvent;
import com.yetistudios.rsww.common.messages.event.PlaneReservationSuccessfulEvent;
import com.yetistudios.rsww.common.messages.event.ReturnPlaneReservationFailedEvent;
import com.yetistudios.rsww.common.messages.event.ReturnPlaneReservationSuccessfulEvent;
import com.yetistudios.rsww.rswwflight.repository.AirportRepository;
import com.yetistudios.rsww.rswwflight.repository.FlightRepository;
import com.yetistudios.rsww.rswwflight.service.FlightAvailabilityService;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlightCommandHandler {
    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightAvailabilityService flightAvailabilityService;

    @Autowired
    private EventGateway eventGateway;

    @CommandHandler
    public void on(BookFlightCommand command) {
        try {
            flightAvailabilityService.bookFlight(command);
            eventGateway.publish(new PlaneReservationSuccessfulEvent(command.reservationId));
        } catch (RuntimeException e) {
            eventGateway.publish(new PlaneReservationFailedEvent(command.reservationId, e.getMessage()));
        }
    }

    @CommandHandler
    public void on(BookReturnFlightCommand command) {
        try {
            BookFlightCommand commandTranslated = new BookFlightCommand();
            BeanUtils.copyProperties(command,commandTranslated);
            flightAvailabilityService.bookFlight(commandTranslated);
            eventGateway.publish(new ReturnPlaneReservationSuccessfulEvent(command.reservationId));
        } catch (RuntimeException e) {
            eventGateway.publish(new ReturnPlaneReservationFailedEvent(command.reservationId, e.getMessage()));
        }
    }

    @CommandHandler
    public void on(CancelFlightBookingCommand command) {
        flightAvailabilityService.cancelFlightBooking(command);
        eventGateway.publish(new PlaneReservationFailedEvent(command.reservationId,"Cancel Flight booking for " + command.getReservationId()));
    }

}

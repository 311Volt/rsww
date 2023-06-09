package com.yetistudios.rsww.rswwflight.command;

import com.yetistudios.rsww.messages.command.BookFlightCommand;
import com.yetistudios.rsww.messages.command.CancelFlightBookingCommand;
import com.yetistudios.rsww.messages.event.PlaneReservationFailedEvent;
import com.yetistudios.rsww.messages.event.PlaneReservationSuccessfulEvent;
import com.yetistudios.rsww.rswwflight.repository.AirportRepository;
import com.yetistudios.rsww.rswwflight.repository.FlightRepository;
import com.yetistudios.rsww.rswwflight.service.FlightAvailabilityService;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.gateway.EventGateway;
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
    public void on(CancelFlightBookingCommand command) {
        flightAvailabilityService.cancelFlightBooking(command);
    }

}

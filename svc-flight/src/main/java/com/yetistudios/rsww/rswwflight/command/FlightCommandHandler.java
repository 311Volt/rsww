package com.yetistudios.rsww.rswwflight.command;

import com.yetistudios.rsww.messages.command.BookFlightCommand;
import com.yetistudios.rsww.rswwflight.repository.AirportRepository;
import com.yetistudios.rsww.rswwflight.repository.FlightRepository;
import com.yetistudios.rsww.rswwflight.service.FlightAvailabilityService;
import org.axonframework.commandhandling.CommandHandler;
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

    @CommandHandler
    public void on(BookFlightCommand command) {

    }

}

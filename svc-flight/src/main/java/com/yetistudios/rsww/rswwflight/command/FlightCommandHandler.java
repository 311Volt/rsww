package com.yetistudios.rsww.rswwflight.command;

import com.yetistudios.rsww.messages.command.BookFlightCommand;
import com.yetistudios.rsww.rswwflight.entity.FlightAvailability;
import com.yetistudios.rsww.rswwflight.repository.AirportRepository;
import com.yetistudios.rsww.rswwflight.repository.FlightAvailabilityRepository;
import com.yetistudios.rsww.rswwflight.repository.FlightRepository;
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
    private FlightAvailabilityRepository flightAvailabilityRepository;


    @CommandHandler
    public void on(BookFlightCommand command) {

    }

}

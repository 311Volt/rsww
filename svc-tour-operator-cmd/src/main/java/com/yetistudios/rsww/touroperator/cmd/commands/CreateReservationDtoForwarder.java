package com.yetistudios.rsww.touroperator.cmd.commands;

import com.yetistudios.rsww.common.dto.CreateReservationDto;
import com.yetistudios.rsww.common.messages.command.CreateOfferCommand;
import com.yetistudios.rsww.common.messages.command.CreateReservationCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateReservationDtoForwarder {

    @Autowired
    private CommandGateway commandGateway;

    @CommandHandler
    public void on(CreateReservationDto order) {
        CreateReservationCommand createReservationCommand = CreateReservationCommand.builder()
                .reservationId(UUID.randomUUID().toString())
                .clientId(order.getClientId())
                .offerId(order.getOfferId())
                .price(order.getPrice())
                .departureAirportName(order.getDepartureAirportName())
                .numSingleRooms(order.getNumSingleRooms())
                .numDoubleRooms(order.getNumDoubleRooms())
                .numTripleRooms(order.getNumTripleRooms())
                .nrOfPeople(order.getNrOfPeople())
                .orderStatus("CREATED")
                .build();

        commandGateway.sendAndWait(createReservationCommand);
    }

}

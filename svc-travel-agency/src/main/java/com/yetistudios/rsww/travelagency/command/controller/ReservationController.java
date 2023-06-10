package com.yetistudios.rsww.travelagency.command.controller;

import com.yetistudios.rsww.travelagency.command.command.CreateReservationCommand;
import com.yetistudios.rsww.travelagency.command.dto.CreateReservationDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/order")
public class ReservationController {
    private CommandGateway commandGateway;

    public ReservationController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createOrder(@RequestBody CreateReservationDto order){
        CreateReservationCommand createReservationCommand = CreateReservationCommand.builder()
                .reservationId(UUID.randomUUID().toString())
                .clientId(order.getClientId())
                .offerId(order.getOfferId())
                .price(order.getPrice())
                .departureAirportName(order.getDepartureAirportName())
                .numSingleRooms(order.getNumSingleRooms())
                .numDoubleRooms(order.getNumDoubleRooms())
                .numTripleRooms(order.getNumTripleRooms())
                .orderStatus("CREATED")
                .build();

        commandGateway.sendAndWait(createReservationCommand);
        return "Order Created";
    }
}

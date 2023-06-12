package com.yetistudios.rsww.rswwgateway.controller;

import com.yetistudios.rsww.common.dto.CreateOrderResult;
import com.yetistudios.rsww.common.dto.CreateReservationDto;
import com.yetistudios.rsww.common.messages.command.CreateReservationCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/order")
public class ReservationController {
    private CommandGateway commandGateway;

    public ReservationController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @CrossOrigin
    @PostMapping
    public CreateOrderResult createOrder(@RequestBody CreateReservationDto order){

        CreateReservationCommand command = CreateReservationCommand.builder()
                .reservationId(UUID.randomUUID().toString())
                .clientId(order.getClientId())
                .departureAirportName(order.getDepartureAirportName())
                .nrOfPeople(order.getNrOfPeople())
                .price(order.getPrice())
                .numDoubleRooms(order.getNumDoubleRooms())
                .numSingleRooms(order.getNumSingleRooms())
                .numTripleRooms(order.getNumTripleRooms())
                .offerId(order.getOfferId())
                .orderStatus(order.getOrderStatus())
                .build();

        commandGateway.sendAndWait(command);
        return CreateOrderResult.builder().result("Order created").orderId(command.getReservationId()).build();
    }
}

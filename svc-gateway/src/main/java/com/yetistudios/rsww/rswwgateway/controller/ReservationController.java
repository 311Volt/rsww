package com.yetistudios.rsww.rswwgateway.controller;

import com.yetistudios.rsww.common.dto.CreateOrderResult;
import com.yetistudios.rsww.common.dto.CreateReservationDto;
import com.yetistudios.rsww.common.dto.ListReservationDto;
import com.yetistudios.rsww.common.messages.command.CreateReservationCommand;
import com.yetistudios.rsww.common.messages.command.PayForReservationCommand;
import com.yetistudios.rsww.common.messages.entity.Reservation;
import com.yetistudios.rsww.common.messages.query.GetReservationsQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/order")
public class ReservationController {
    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;

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
                .paid(order.isPaid())
                .build();

        commandGateway.sendAndWait(command);
        return CreateOrderResult.builder().result("Order created").orderId(command.getReservationId()).build();
    }

    @GetMapping("/{userId}")
    public List<Reservation> getUserReservation(@PathVariable("userId") String userId) {
        GetReservationsQuery query = GetReservationsQuery.builder()
                .userId(userId)
                .build();

        ListReservationDto result = queryGateway.query(
                query,
                ResponseTypes.instanceOf(ListReservationDto.class)
        ).join();
        return result.getReservationList();
    }

    @CrossOrigin
    @PostMapping("/pay")
    public String payForReservation(@RequestParam("id") String id) {
        PayForReservationCommand command = PayForReservationCommand.builder()
                .reservationId(id)
                .build();

        commandGateway.sendAndWait(command);
        return "Reservation " + id + " paid";
    }
}

package com.yetistudios.rsww.travelagency.command.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateReservationCommand {

    @TargetAggregateIdentifier
    private String reservationId;
    private String offerId;
    private String clientId;
    private double price;
    private String departureAirportName;
    public int numSingleRooms;
    public int numDoubleRooms;
    public int numTripleRooms;
    private String orderStatus;
}

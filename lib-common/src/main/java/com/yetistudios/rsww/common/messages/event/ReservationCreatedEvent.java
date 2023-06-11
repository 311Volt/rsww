package com.yetistudios.rsww.common.messages.event;

import lombok.Data;

@Data
public class ReservationCreatedEvent {
    private String reservationId;
    private String offerId;
    private String clientId;
    private String departureAirportName;
    private double price;
    private int nrOfPeople;
    private int numSingleRooms;
    private int numDoubleRooms;
    private int numTripleRooms;
    private String orderStatus;
}

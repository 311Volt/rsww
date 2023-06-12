package com.yetistudios.rsww.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateReservationDto {
    private String offerId;
    private String clientId;
    private double price;
    private String departureAirportName;
    private int nrOfPeople;
    private int numSingleRooms;
    private int numDoubleRooms;
    private int numTripleRooms;
    private String orderStatus;
}

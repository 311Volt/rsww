package com.yetistudios.rsww.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private String reservationId;
    private String offerId;
    private String clientId;
    private String departureAirportName;
    private double price;
    private int nrOfPeople;
    private boolean paid;
}
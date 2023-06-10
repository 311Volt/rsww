package com.yetistudios.rsww.messages.entity;

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
}
package com.yetistudios.rsww.messages.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferFlight {
    private String id;
    private String departureAirportName;
}

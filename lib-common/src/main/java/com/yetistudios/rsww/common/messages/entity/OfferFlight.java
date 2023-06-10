package com.yetistudios.rsww.messages.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferFlight {
    public String id;
    public String departureAirportName;
}

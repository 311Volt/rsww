package com.yetistudios.rsww.touroperator.query.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Flight {
    private String id;
    private String departureAirportName;
}

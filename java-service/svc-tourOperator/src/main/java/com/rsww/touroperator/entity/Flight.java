package com.rsww.touroperator.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Flight {
    private String id;
    private String departureAirportName;
}

package com.yetistudios.rsww.touroperator.cmd.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Flight {
    private String id;
    private String departureAirportName;
}

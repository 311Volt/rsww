package com.yetistudios.rsww.touroperator.cmd.entity;

import com.yetistudios.rsww.common.dto.FlightDocument;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Flight {
    private String id;
    private String departureAirportName;

    public static Flight ofDocument(FlightDocument document) {
        return Flight.builder()
                .id(Integer.valueOf(document.flightNumber).toString())
                .departureAirportName(document.departure.airportCode)
                .build();
    }
}

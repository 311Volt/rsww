package com.yetistudios.rsww.touroperator.cmd.entity;

import com.yetistudios.rsww.common.dto.FlightDocument;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightBrief {
    private String id;
    private String departureAirportName;

    public static FlightBrief ofDocument(FlightDocument document) {
        return FlightBrief.builder()
                .id(Integer.valueOf(document.flightNumber).toString())
                .departureAirportName(document.departure.airportCode)
                .build();
    }
}

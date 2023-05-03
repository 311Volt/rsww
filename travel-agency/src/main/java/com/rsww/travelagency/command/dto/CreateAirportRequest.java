package com.rsww.travelagency.command.dto;

import lombok.Data;

@Data
public class CreateAirportRequest {
    private String code;
    private String name;
    private Boolean forDeparture;
}

package com.yetistudios.rsww.touroperator.query.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightModel {
    Flight outboundFlight;
    Flight returnFlight;
}

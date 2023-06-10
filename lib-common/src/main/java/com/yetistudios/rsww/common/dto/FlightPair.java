package com.yetistudios.rsww.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightPair {
    public FlightDocument outboundFlight;
    public FlightDocument returnFlight;
}

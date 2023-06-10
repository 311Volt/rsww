package com.yetistudios.rsww.messages.misc;

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

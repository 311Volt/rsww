package com.yetistudios.rsww.messages.misc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightPair {
    public FlightDocument outboundFlight;
    public FlightDocument returnFlight;
}

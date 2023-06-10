package com.yetistudios.rsww.touroperator.cmd.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightBriefPair {
    public FlightBrief outboundFlight;
    public FlightBrief returnFlight;
}

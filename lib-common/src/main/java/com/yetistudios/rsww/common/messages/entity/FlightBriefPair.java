package com.yetistudios.rsww.common.messages.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightBriefPair {
    public FlightBrief outboundFlight;
    public FlightBrief returnFlight;
}

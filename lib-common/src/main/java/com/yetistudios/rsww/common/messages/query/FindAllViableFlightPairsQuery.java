package com.yetistudios.rsww.common.messages.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAllViableFlightPairsQuery {
    public Long latestAcceptableOutboundArrival;
    public Long earliestAcceptableReturnDeparture;
    public String outboundArrivalAirportCode;
}

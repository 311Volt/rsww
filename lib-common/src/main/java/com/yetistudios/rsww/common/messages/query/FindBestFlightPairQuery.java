package com.yetistudios.rsww.common.messages.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindBestFlightPairQuery {
    public Long latestAcceptableOutboundArrival;
    public Long earliestAcceptableReturnDeparture;
    public String outboundDepartureAirportCode;
    public String outboundArrivalAirportCode;
}

package com.yetistudios.rsww.messages.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindBestFlightPairQuery {
    public Long latestAcceptableOutboundArrival;
    public Long earliestAcceptableReturnDeparture;
    public String outboundDepartureAirportCode;
    public String outboundArrivalAirportCode;
}

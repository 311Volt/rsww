package com.yetistudios.rsww.messages.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckFlightAvailabilityQuery {
    public Integer flightNumber;
    public Integer numSeats;
}

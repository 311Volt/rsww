package com.yetistudios.rsww.common.messages.query;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckFlightAvailabilityQuery {
    public Integer flightNumber;
    public Integer numSeats;
}

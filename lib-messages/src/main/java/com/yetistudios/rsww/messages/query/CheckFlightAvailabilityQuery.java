package com.yetistudios.rsww.messages.query;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckFlightAvailabilityQuery {
    public Integer flightNumber;
    public Integer numSeats;
}

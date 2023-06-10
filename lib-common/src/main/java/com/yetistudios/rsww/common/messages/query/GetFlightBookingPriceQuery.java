package com.yetistudios.rsww.common.messages.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetFlightBookingPriceQuery {
    public Integer flightNumber;
    public Integer numSeats;
}

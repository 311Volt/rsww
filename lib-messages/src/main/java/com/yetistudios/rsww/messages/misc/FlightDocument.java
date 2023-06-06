package com.yetistudios.rsww.messages.misc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightDocument {
    public int flightNumber;
    public int numSeats;
    public boolean isReturn;
    public DepartureOrArrivalDocument departure;
    public DepartureOrArrivalDocument arrival;
}

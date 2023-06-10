package com.yetistudios.rsww.common.dto;

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

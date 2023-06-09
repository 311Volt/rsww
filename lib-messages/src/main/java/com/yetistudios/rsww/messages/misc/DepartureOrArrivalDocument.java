package com.yetistudios.rsww.messages.misc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartureOrArrivalDocument {
    public String airportCode;
    public String date;
    public String time;
}

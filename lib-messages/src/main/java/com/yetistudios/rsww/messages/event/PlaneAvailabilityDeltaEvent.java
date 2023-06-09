package com.yetistudios.rsww.messages.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaneAvailabilityDeltaEvent {
    public Integer flightNumber;
    public Integer deltaSeats;
}

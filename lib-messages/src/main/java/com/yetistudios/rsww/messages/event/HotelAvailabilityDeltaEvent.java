package com.yetistudios.rsww.messages.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelAvailabilityDeltaEvent {
    public int deltaSingleRooms;
    public int deltaDoubleRooms;
    public int deltaTripleRooms;
}
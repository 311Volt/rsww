package com.yetistudios.rsww.rswwworld.query.entity;

import com.yetistudios.rsww.rswwworld.command.event.HotelAvailabilityDeltaEvent;
import lombok.Builder;

@Builder
public class HotelAvailability {
    public int availableSingleRooms = 0;
    public int availableDoubleRooms = 0;
    public int availableTripleRooms = 0;

    public void consumeEvent(HotelAvailabilityDeltaEvent event) {
        availableSingleRooms += event.deltaSingleRooms;
        availableDoubleRooms += event.deltaDoubleRooms;
        availableTripleRooms += event.deltaTripleRooms;
    }

    public void consumeAll(Iterable<HotelAvailabilityDeltaEvent> events) {
        events.forEach(this::consumeEvent);
    }
}
